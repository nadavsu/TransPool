package servlets;

import api.TransPoolMapEngine;
import constants.Constants;
import data.generated.TransPool;
import data.transpool.TransPoolMapBase;
import exception.FileTypeException;
import exception.data.TransPoolDataException;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        TransPoolMapEngine mapEngine = ServletUtils.getMapEngine(getServletContext());
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        String uploaderNameFromSession = SessionUtils.getUsername(req);
        Part part = req.getPart(Constants.MAP_FILE);

        synchronized (this) {
            if (mapEngine.isMapExists(mapNameFromParameter)) {
                out.print("A map with this name already exists.");
            } else if (part != null) {
                try {
                    loadFile(mapEngine, part, mapNameFromParameter, uploaderNameFromSession);
                    out.print(Constants.HOME_URL);     //On success.
                } catch (TransPoolDataException | FileTypeException e) {
                    out.print(e.getMessage());
                } catch (JAXBException e) {
                    out.print("Error parsing map.");
                }
            } else {
                out.print("File cannot be empty.");
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

    private void loadFile(TransPoolMapEngine mapEngine, Part file, String mapName, String uploaderName) throws JAXBException,
            TransPoolDataException, IOException, FileTypeException {

        if (file.getContentType().equals("text/xml")) {
            JAXBContext jaxbContext = JAXBContext.newInstance(TransPool.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TransPool JAXBData = (TransPool) jaxbUnmarshaller.unmarshal(file.getInputStream());
            mapEngine.addMap(new TransPoolMapBase(mapName, uploaderName, JAXBData));
        } else {
            throw new FileTypeException();
        }
    }
}
