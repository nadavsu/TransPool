package servlets;

import api.MapsEngine;
import constants.Constants;
import api.transpool.SingleMapEngine;
import api.transpool.time.component.Recurrence;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.user.UserEngine;
import api.transpool.user.account.TransPoolDriver;
import exception.parser.TransPoolDataException;
import utils.ParameterUtils;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.List;

/**
 * A servlet which creates a new trip request.
 */
@WebServlet(name = "CreateNewTripOffer", urlPatterns = {"/create-new-offer"})
public class CreateNewTripOfferServlet extends HttpServlet {
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String userNameFromSession = SessionUtils.getUsername(req);

        UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());
        MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());

        //Getting the parameters from the form.
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        LocalTime departureTime = ParameterUtils.getTimeFromParameter(req.getParameter(Constants.DEPARTURE_TIME));
        Recurrence recurrences = ParameterUtils.getRecurrenceFromParameter(req.getParameter(Constants.RECURRENCES));
        int dayStart = Integer.parseInt(req.getParameter(Constants.DEPARTURE_DAY));
        int PPK = Integer.parseInt(req.getParameter(Constants.PPK));
        int passengerCapacity = Integer.parseInt(req.getParameter(Constants.PASSENGER_CAPACITY));
        List<String> route = ParameterUtils.getRouteFromParameter(req.getParameter(Constants.ROUTE));

        SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);
        TransPoolDriver driver = (TransPoolDriver) userEngine.getUserAccount(userNameFromSession);

        try (PrintWriter out = resp.getWriter()) {
            try {
                TripOffer newOffer = new TripOffer(map, driver, departureTime, dayStart, recurrences, passengerCapacity, PPK, route);
                mapsEngine.addNewTripOffer(newOffer, driver, mapNameFromParameter);
                out.print("Offer added successfully!");
            } catch (TransPoolDataException e) {
                out.print(e);
            } finally {
                out.flush();
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
