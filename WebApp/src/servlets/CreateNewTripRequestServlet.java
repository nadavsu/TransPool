package servlets;

import api.MapsEngine;
import constants.Constants;
import api.transpool.SingleMapEngine;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.trip.request.component.TripRequestData;
import api.transpool.user.UserEngine;
import api.transpool.user.account.TransPoolRider;
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

@WebServlet(name = "CreateNewTripRequestServlet", urlPatterns = {"/create-new-request"})
public class CreateNewTripRequestServlet extends HttpServlet {


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String userNameFromSession = SessionUtils.getUsername(req);

        UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());
        MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());

        //Getting the parameters from the form.
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        LocalTime departureTime = ParameterUtils.getTimeFromParameter(req.getParameter(Constants.DEPARTURE_TIME));
        int departureDay = Integer.parseInt(req.getParameter(Constants.DEPARTURE_DAY));
        String sourceStop = req.getParameter(Constants.SOURCE_STOP).trim();
        String destinationStop = req.getParameter(Constants.DESTINATION_STOP).trim();
        boolean isContinuous = ParameterUtils.getBooleanParameter(req.getParameter(Constants.IS_CONTINUOUS));

        SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);
        TransPoolRider rider = (TransPoolRider) userEngine.getUserAccount(userNameFromSession);

        try (PrintWriter out = resp.getWriter()) {
            try {
                TripRequest newRequest = new TripRequestData(map, rider.getUsername(), sourceStop, destinationStop, departureDay, departureTime, false, isContinuous);
                mapsEngine.addNewTripRequest(newRequest, rider, mapNameFromParameter);
                out.print("Request added successfully!");
            } catch (TransPoolDataException e) {
                out.print(e.getMessage());
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
