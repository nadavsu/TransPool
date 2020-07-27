package servlets;

import constants.Constants;
import data.transpool.user.UserEngine;
import data.transpool.user.account.TransPoolDriver;
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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {



    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String usernameFromSession = SessionUtils.getUsername(request);
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

            //First time logging in
            if (usernameFromSession == null) {
                String userNameFromParameter = request.getParameter(Constants.USERNAME);
                String userTypeFromParameter = request.getParameter(Constants.ACCOUNT_TYPE);

                //Redirected from index.html
                if (userNameFromParameter == null || userNameFromParameter.isEmpty()) {
                    out.print(Constants.SIGNUP_URL);
                } else {
                    userNameFromParameter = userNameFromParameter.trim();
                    synchronized (this) {
                        if (userEngine.isUserExists(userNameFromParameter)) {
                            out.print("The username " + userNameFromParameter + " is already taken.");
                        } else {
                            if (userTypeFromParameter.equals(Constants.RIDER)) {
                                userEngine.addUser(new TransPoolRider(userNameFromParameter));
                            } else if (userTypeFromParameter.equals(Constants.DRIVER)) {
                                userEngine.addUser(new TransPoolDriver(userNameFromParameter));
                            } else {
                                out.print("User must be a rider or a driver.");
                                return;
                            }
                            request.getSession(true).setAttribute(Constants.USERNAME, userNameFromParameter);
                            request.getSession(true).setAttribute(Constants.ACCOUNT_TYPE, userTypeFromParameter);
                            out.print(Constants.HOME_URL);
                        }
                    }
                }
            } else {
                out.print(Constants.HOME_URL);
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
