package servlets;

import constants.Constants;
import data.transpool.user.UserEngine;
import data.transpool.user.account.TransPoolDriver;
import data.transpool.user.account.TransPoolRider;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final static String SIGNUP_URL = "signup.html";
    private final static String LOGIN_ERROR_URL = "/pages/loginerror/login_error_attempt.jsp";
    private final static String HOME_URL = "home.html";

    private void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(req);
        UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

        //First time logging in
        if (usernameFromSession == null) {
            String userNameFromParameter = req.getParameter(Constants.USERNAME);
            String userTypeFromParameter = req.getParameter(Constants.ACCOUNT_TYPE);

            //Redirected from index.html
            if (userNameFromParameter == null || userNameFromParameter.isEmpty()) {
                res.sendRedirect(SIGNUP_URL);
            } else {
                userNameFromParameter = userNameFromParameter.trim();
                synchronized (this) {
                    if (userEngine.isUserExists(userNameFromParameter)) {
                        String errorMessage = "The username " + userNameFromParameter + " is already taken.";
                        req.setAttribute(Constants.USERNAME_ERROR, errorMessage);
                        getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(req, res);
                    } else {
                        if (userTypeFromParameter.equals(Constants.RIDER)) {
                            userEngine.addUser(new TransPoolRider(userNameFromParameter));
                        } else if (userNameFromParameter.equals(Constants.DRIVER)) {
                            userEngine.addUser(new TransPoolDriver(userNameFromParameter));
                        } else {
                            res.sendRedirect(SIGNUP_URL);
                            return;
                        }
                        req.getSession(true).setAttribute(Constants.USERNAME, userNameFromParameter);
                        req.getSession(true).setAttribute(Constants.ACCOUNT_TYPE, userTypeFromParameter);
                        res.sendRedirect(HOME_URL);
                    }
                }
            }
        } else {
            res.sendRedirect(HOME_URL);
        }

    }

    //<editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
