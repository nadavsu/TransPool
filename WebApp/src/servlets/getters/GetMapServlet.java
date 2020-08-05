package servlets.getters;

import api.MapsEngine;
import com.google.gson.Gson;
import constants.Constants;
import api.transpool.SingleMapEngine;
import api.transpool.map.BasicMapDTO;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetMapServlet", urlPatterns = {"/get-map"})
public class GetMapServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
            MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
            SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);
            BasicMapDTO mapDTO = map.getMapDetails();
            String mapDTOJson = new Gson().toJson(mapDTO);
            out.print(mapDTOJson);
            out.flush();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
