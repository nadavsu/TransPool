package servlets.getters;

import com.google.gson.Gson;
import constants.Constants;
import api.transpool.user.UserEngine;
import api.transpool.user.account.TransPoolUserAccount;
import api.transpool.user.dto.TransPoolUserAccountDTO;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Gets a the current user data from the session.
 */
@WebServlet(name = "GetUserServlet", urlPatterns = {"/get-user"})
public class GetUserServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()) {

            String userNameFromSession = SessionUtils.getUsername(req);
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

            //If somehow there is no session when reaching the home page.
            if (userNameFromSession == null) {
                resp.setContentType("text/html;charset=UTF-8");
                out.print(Constants.SIGNUP_URL);
            } else {
                resp.setContentType("application/json");
                TransPoolUserAccount currentUser = userEngine.getUserAccount(userNameFromSession);
                TransPoolUserAccountDTO currentUserDetails = currentUser.getDetails();
                Gson gson = new Gson();
                String userJson = gson.toJson(currentUserDetails);
                out.print(userJson);
                out.flush();
            }

        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
