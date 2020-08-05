package servlets;

import api.MapsEngine;
import com.google.gson.Gson;
import constants.Constants;
import api.transpool.SingleMapEngine;
import api.transpool.trip.matching.component.PossibleRoutesList;
import exception.parser.NoResultsFoundException;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FindMatchesServlet", urlPatterns = {"/find-a-match"})
public class FindMatchesServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            MapsEngine mapsEngine = ServletUtils.getMapEngine(getServletContext());
            String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
            SingleMapEngine map = mapsEngine.getMap(mapNameFromParameter);

            int idOfRequestToMatch = Integer.parseInt(req.getParameter(Constants.ID_OF_REQUEST_TO_MATCH));
            int numOfResults = Integer.parseInt(req.getParameter(Constants.NUM_OF_RESULTS));

            try {
                PossibleRoutesList possibleRoutes = map.getAllPossibleRoutes(idOfRequestToMatch, numOfResults);

                //Saving the possible routes list in the session.
                req.getSession(false).setAttribute(Constants.POSSIBLE_ROUTES, possibleRoutes);

                String possibleRoutesJson = new Gson().toJson(possibleRoutes.getDetails());
                String idOfRequestToMatchJson = new Gson().toJson(idOfRequestToMatch);

                String ajaxResponse = "[" + possibleRoutesJson + "," + idOfRequestToMatchJson + "]";
                out.print(ajaxResponse);
            } catch (NoResultsFoundException e) {
                resp.setContentType("text/html");
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
