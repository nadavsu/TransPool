package servlets.getters;

import api.MapsEngine;
import com.google.gson.Gson;
import constants.Constants;
import api.transpool.SingleMapEngine;
import api.transpool.map.BasicMapDTO;
import api.transpool.trip.offer.component.TripOfferDTO;
import api.transpool.trip.request.component.MatchedTripRequestDTO;
import api.transpool.trip.request.component.TripRequestDTO;
import api.transpool.user.UserEngine;
import api.transpool.user.account.TransPoolRider;
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
import java.util.Set;

/**
 * Loads the request map. Sends multiple Jsons at once by chaining them together inside an array.
 */
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
                Set<String> feedbackablesUsernames = rider.getAllFeedbackablesUsernames();
                BasicMapDTO mapDetails = map.getMapDetails();

                //For the select menu in the matching tab.
                List<TripRequestDTO> userTripRequestsInMap = rider.getTripRequestsDetailsFromMap(map);

                String mapDetailsJson = new Gson().toJson(mapDetails);
                String userTripRequestsJson = new Gson().toJson(userTripRequests);
                String userMatchedTripRequestsJson = new Gson().toJson(userMatchedTripRequests);
                String mapTripOffersJson = new Gson().toJson(mapTripOffers);
                String feedbackablesUsernamesJson = new Gson().toJson(feedbackablesUsernames);
                String userTripRequestsInMapJson = new Gson().toJson(userTripRequestsInMap);

                String response = "[" + mapDetailsJson + ","
                        + userTripRequestsJson + ","
                        + userMatchedTripRequestsJson + ","
                        + mapTripOffersJson + ","
                        + feedbackablesUsernamesJson + ","
                        + userTripRequestsInMapJson + "]";

                out.print(response);
                out.flush();
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
