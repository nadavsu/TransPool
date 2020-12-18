package servlets.getters;

import api.transpool.trip.request.component.MatchedTripRequestPart;
import api.transpool.user.component.feedback.Feedback;
import com.google.gson.Gson;
import constants.Constants;
import api.transpool.user.UserEngine;
import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.component.feedback.FeedbacksDTO;
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

/**
 * Gets the the current session's driver's riders (matched trips) and returns them as Jsons.
 * Used for checking notifications.
 */
@WebServlet(name = "GetDriverRidersServlet", urlPatterns = {"/get-driver-riders"})
public class GetDriverRidersServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            String usernameFromSession = SessionUtils.getUsername(req);
            String usertypeFromSession = SessionUtils.getUserType(req);
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

            if (usertypeFromSession != null && usertypeFromSession.equals(Constants.DRIVER)) {
                TransPoolDriver driver = (TransPoolDriver) userEngine.getUserAccount(usernameFromSession);

                int ridersVersionFromPatameter = Integer.parseInt(req.getParameter(Constants.RIDERS_VERSION));
                int driverRidersVersion = driver.getRidersVersion();
                List<MatchedTripRequestPart> recentRiders = driver.getRiders(ridersVersionFromPatameter);
                RidersAndVersion ridersAndVersion = new RidersAndVersion(recentRiders, driverRidersVersion);

                String ridersAndVersionJson = new Gson().toJson(ridersAndVersion);
                out.print(ridersAndVersionJson);
                out.flush();
            }
        }
    }

    private class RidersAndVersion {
        private List<MatchedTripRequestPart> riders;
        private int version;

        public RidersAndVersion(List<MatchedTripRequestPart> riders, int version) {
            this.riders = riders;
            this.version = version;
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
