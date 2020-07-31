package servlets.getters;

import api.MapsEngine;
import com.google.gson.Gson;
import constants.Constants;
import data.transpool.SingleMapEngine;
import data.transpool.trip.offer.component.TripOfferDTO;
import data.transpool.trip.request.component.MatchedTripRequestDTO;
import data.transpool.trip.request.component.TripRequestDTO;
import data.transpool.user.UserEngine;
import data.transpool.user.account.TransPoolRider;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetRequestMapServlet", urlPatterns = {"/get-request-map"})
public class GetRequestMapServlet extends HttpServlet {
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        String usernameFromSession = SessionUtils.getUsername(req);
        String userTypeFromSession = SessionUtils.getUserType(req);

        try (PrintWriter out = resp.getWriter()) {
            if (userTypeFromSession != null && userTypeFromSession.equals(Constants.RIDER)) {
                MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
                UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

                TransPoolRider rider = (TransPoolRider) userEngine.getUserAccount(usernameFromSession);
                SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);

                List<TripRequestDTO> userTripRequests = rider.getTripRequestsDetails();
                List<MatchedTripRequestDTO> userMatchedTripRequests = rider.getMatchedTripRequestDetails();
                List<TripOfferDTO> mapTripOffers = map.getTripOffersDetails();
                List<String> mapStops = map.getAllStopNamesAsList();

                String userTripRequestsJson = new Gson().toJson(userTripRequests);
                String userMatchedTripRequestsJson = new Gson().toJson(userMatchedTripRequests);
                String mapTripOffersJson = new Gson().toJson(mapTripOffers);
                String mapStopsJson = new Gson().toJson(mapStops);

                String response = "[" + userTripRequestsJson + ","
                        + userMatchedTripRequestsJson + ","
                        + mapTripOffersJson + ","
                        + mapStopsJson + "]";

                out.print(response);
            } else{
                resp.sendRedirect(Constants.SIGNUP_URL);
            }
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
