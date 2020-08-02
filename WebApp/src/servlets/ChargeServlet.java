package servlets;

import constants.Constants;
import data.transpool.user.UserEngine;
import data.transpool.user.account.TransPoolUserAccount;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChargeServlet", urlPatterns = {"/charge"})
public class ChargeServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userNameFromSession = SessionUtils.getUsername(req);
        UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());
        TransPoolUserAccount currentUser = userEngine.getUserAccount(userNameFromSession);

        if (currentUser == null) {
            resp.sendRedirect(Constants.SIGNUP_URL);
        } else {
            String amountToAddFromParameter = req.getParameter(Constants.AMOUNT_TO_ADD);
            double amountToAdd = Double.parseDouble(amountToAddFromParameter);
            currentUser.depositCredit(amountToAdd);
            resp.sendRedirect(Constants.ACCOUNT_URL);
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
