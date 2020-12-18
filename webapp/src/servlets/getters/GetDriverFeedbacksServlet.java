package servlets.getters;

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
 * Gets the the current session's driver's feedbacks and returns them as Jsons.
 */

@WebServlet(name = "GetDriverFeedbacksServlet", urlPatterns = {"/get-driver-feedbacks"})
public class GetDriverFeedbacksServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            String usernameFromSession = SessionUtils.getUsername(req);
            String usertypeFromSession = SessionUtils.getUserType(req);
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

            if (usertypeFromSession != null && usertypeFromSession.equals(Constants.DRIVER)) {
                TransPoolDriver driver = (TransPoolDriver) userEngine.getUserAccount(usernameFromSession);

                int feedbackVersionFromParameter = Integer.parseInt(req.getParameter(Constants.FEEDBACK_VERSION));
                int driverFeedbacksVersion = driver.getFeedbacksVersion();
                double averageRating = driver.getAverageRating();
                List<Feedback> recentFeedbacks = driver.getFeedbacks(feedbackVersionFromParameter);
                FeedbacksDTO driverFeedbacksDTO = new FeedbacksDTO(recentFeedbacks, averageRating, driverFeedbacksVersion);

                String driverFeedbacksDTOJson = new Gson().toJson(driverFeedbacksDTO);
                out.print(driverFeedbacksDTOJson);
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
