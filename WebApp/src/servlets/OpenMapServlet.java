package servlets;

import constants.Constants;
import utils.SessionUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet determines which map to open based on the type of the user (rider or driver). Generates a context path
 * with the relevant values and parameters for the next servlet.
 */
@WebServlet(name = "OpenMapServlet", urlPatterns = {"/get-map-type"})
public class OpenMapServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        String userTypeFromSession = SessionUtils.getUserType(req);

        try (PrintWriter out = resp.getWriter()) {
            if (userTypeFromSession != null) {
                if (userTypeFromSession.equals(Constants.RIDER)) {
                    out.print(Constants.TRIP_REQUEST_MAP_URL + "?" + Constants.MAP_NAME + "=" + mapNameFromParameter);
                } else if (userTypeFromSession.equals(Constants.DRIVER)) {
                    out.print(Constants.TRIP_OFFER_MAP_URL + "?" + Constants.MAP_NAME + "=" + mapNameFromParameter);
                } else {
                    out.print(Constants.SIGNUP_URL);
                }
            } else {
                out.print(Constants.SIGNUP_URL);
            }
            out.flush();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }
}
