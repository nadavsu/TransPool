package servlets;

import api.MapsEngine;
import constants.Constants;
import api.generated.TransPool;
import api.transpool.TransPoolMap;
import exception.FileTypeException;
import exception.parser.TransPoolDataException;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        String uploaderNameFromSession = SessionUtils.getUsername(req);
        Part part = req.getPart(Constants.MAP_FILE);

        synchronized (this) {
            if (mapsEngine.isMapExists(mapNameFromParameter)) {
                out.print("A map with this name already exists.");
                out.flush();
            } else if (part != null) {
                try {
                    loadFile(mapsEngine, part, mapNameFromParameter, uploaderNameFromSession);
                    out.print(Constants.HOME_URL);     //On success.
                } catch (TransPoolDataException | FileTypeException e) {
                    out.print(e.getMessage());
                } catch (JAXBException e) {
                    out.print("Error parsing map.");
                } finally {
                    out.flush();
                }
            } else {
                out.print("File cannot be empty.");
                out.flush();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("upload.html");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void loadFile(MapsEngine mapsEngine, Part file, String mapName, String uploaderName) throws JAXBException,
            TransPoolDataException, IOException, FileTypeException {

        if (file.getContentType().equals("text/xml")) {
            JAXBContext jaxbContext = JAXBContext.newInstance(TransPool.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TransPool JAXBData = (TransPool) jaxbUnmarshaller.unmarshal(file.getInputStream());
            mapsEngine.addMap(new TransPoolMap(mapName, uploaderName, JAXBData));
        } else {
            throw new FileTypeException();
        }
    }
}
