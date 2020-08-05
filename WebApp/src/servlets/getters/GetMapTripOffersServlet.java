package servlets.getters;

import api.MapsEngine;
import com.google.gson.Gson;
import constants.Constants;
import api.transpool.SingleMapEngine;
import api.transpool.trip.offer.component.TripOfferDTO;
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
 * Gets all the trip offers in the current map whose name is stored in the requests parameter.
 */
@WebServlet(name = "GetMapTripOffersServlet", urlPatterns = {"/get-map-offers"})
public class GetMapTripOffersServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
            MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
            SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);
            List<TripOfferDTO> tripOfferDTOList = map.getTripOffersDetails();
            String tripOfferDTOListJson = new Gson().toJson(tripOfferDTOList);
            out.print(tripOfferDTOListJson);
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
