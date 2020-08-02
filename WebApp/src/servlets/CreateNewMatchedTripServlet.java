package servlets;

import api.MapsEngine;
import constants.Constants;
import data.transpool.trip.matching.component.PossibleRoute;
import data.transpool.trip.matching.component.PossibleRoutesList;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.user.UserEngine;
import data.transpool.user.account.TransPoolRider;
import exception.data.RideFullException;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateNewMatchedTripServlet", urlPatterns = {"/create-match"})
public class CreateNewMatchedTripServlet extends HttpServlet {

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
            String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);

            String userNameFromSession = SessionUtils.getUsername(req);
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());
            TransPoolRider rider = (TransPoolRider) userEngine.getUserAccount(userNameFromSession);

            int chosenTripOfferIndex = Integer.parseInt(req.getParameter(Constants.POSSIBLE_ROUTE_ID));
            int chosenTripRequestId = Integer.parseInt(req.getParameter(Constants.ID_OF_REQUEST_TO_MATCH));

            PossibleRoutesList possibleRoutes = SessionUtils.getPossibleRoutes(req);
            if (possibleRoutes != null) {
                TripRequest requestToMatch = rider.getRequest(chosenTripRequestId);
                PossibleRoute chosenRoute = possibleRoutes.get(chosenTripOfferIndex);
                try {

                    MatchedTripRequest matchedTripRequest = new MatchedTripRequest(requestToMatch, chosenRoute);
                    mapsEngine.addNewMatchedTripRequest(matchedTripRequest, rider, mapNameFromParameter);
                    out.print("Ride matched successfully!");
                } catch (RideFullException e) {
                    out.print(e.getMessage());
                }
            } else {

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
