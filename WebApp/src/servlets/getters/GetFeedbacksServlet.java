package servlets.getters;

import com.google.gson.Gson;
import constants.Constants;
import data.transpool.user.UserEngine;
import data.transpool.user.account.TransPoolDriver;
import data.transpool.user.component.feedback.Feedback;
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


@WebServlet(name = "GetFeedbacksServlet", urlPatterns = {"/get-feedbacks"})
public class GetFeedbacksServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());
            String usernameFromSession = SessionUtils.getUsername(req);
            String userTypeFromSession = SessionUtils.getUserType(req);

            if (userTypeFromSession != null && userTypeFromSession.equals(Constants.DRIVER)) {
                TransPoolDriver driver = (TransPoolDriver) userEngine.getUserAccount(usernameFromSession);
                List<Feedback> feedbackList = driver.getAllFeedbacks();

                String feedbackListJson = new Gson().toJson(feedbackList);
                out.print(feedbackListJson);

            } else {

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
