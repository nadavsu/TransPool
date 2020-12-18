package servlets.getters;

import api.MapsEngine;
import com.google.gson.Gson;
import constants.Constants;
import api.transpool.SingleMapEngine;
import api.transpool.trip.request.component.MatchedTripRequestDTO;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Gets all the trip matches in the current map whose name is stored in the requests parameter.
 */
@WebServlet(name = "GetMapTripMatchesServlet", urlPatterns = {"/get-map-matches"})
public class GetMapTripMatchesServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
            MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
            SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);
            List<MatchedTripRequestDTO> matchedTripRequestDTOList = map.getMatchedTripsDetails();
            String matchedTripRequestDTOListJson = new Gson().toJson(matchedTripRequestDTOList);
            out.print(matchedTripRequestDTOListJson);
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
