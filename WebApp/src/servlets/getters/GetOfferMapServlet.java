package servlets.getters;

import api.MapsEngine;
import com.google.gson.Gson;
import constants.Constants;
import data.transpool.SingleMapEngine;
import data.transpool.map.BasicMapDTO;
import data.transpool.trip.offer.component.TripOfferDTO;
import data.transpool.trip.request.component.MatchedTripRequestDTO;
import data.transpool.trip.request.component.TripRequestDTO;
import data.transpool.user.UserEngine;
import data.transpool.user.account.TransPoolDriver;
import data.transpool.user.component.feedback.FeedbacksDTO;
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

@WebServlet(name = "GetOfferMapServlet", urlPatterns = {"/get-offer-map"})
public class GetOfferMapServlet extends HttpServlet {
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        String usernameFromSession = SessionUtils.getUsername(req);
        String userTypeFromSession = SessionUtils.getUserType(req);

        try (PrintWriter out = resp.getWriter()) {
            if (userTypeFromSession != null && userTypeFromSession.equals(Constants.DRIVER)) {
                MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
                UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

                TransPoolDriver driver = (TransPoolDriver) userEngine.getUserAccount(usernameFromSession);
                SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);

                List<TripOfferDTO> userTripOffers = driver.getTripOffersDetails();
                List<TripRequestDTO> mapTripRequests = map.getTripRequestsDetails();
                List<MatchedTripRequestDTO> mapMatchedTrips = map.getMatchedTripsDetails();
                FeedbacksDTO feedbacksDetails = driver.getFeedbacksDetails();
                BasicMapDTO mapDetails = map.getMapDetails();

                String allFeedbacksJson = new Gson().toJson(feedbacksDetails);
                String userTripOffersJson = new Gson().toJson(userTripOffers);
                String mapTripRequestsJson = new Gson().toJson(mapTripRequests);
                String mapMatchedTripsJson = new Gson().toJson(mapMatchedTrips);
                String mapDetailsJson = new Gson().toJson(mapDetails);

                String response = "[" + mapDetailsJson + ","
                        + userTripOffersJson + ","
                        + allFeedbacksJson + ","
                        + mapTripRequestsJson + ","
                        + mapMatchedTripsJson + "]";

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
