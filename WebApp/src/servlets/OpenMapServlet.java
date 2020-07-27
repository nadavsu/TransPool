package servlets;

import api.MapEngine;
import constants.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OpenMapServlet", urlPatterns = {"/map"})
public class OpenMapServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String mapNameFromParameter = req.getParameter(Constants.MAP_NAME);
        String userTypeFromSession = SessionUtils.getUserType(req);
        String userNameFromSession = SessionUtils.getUserType(req);
        MapEngine mapEngine = ServletUtils.getMapEngine(getServletContext());
        try (PrintWriter out = resp.getWriter()) {
            if (userTypeFromSession != null) {
                if (userTypeFromSession.equals(Constants.RIDER)) {

                } else if (userTypeFromSession.equals(Constants.DRIVER)) {

                }
            } else {
                out.print(Constants.SIGNUP_URL);
            }
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
