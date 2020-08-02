package servlets;

import constants.Constants;
import data.transpool.user.UserEngine;
import data.transpool.user.component.feedback.Feedback;
import data.transpool.user.component.feedback.Feedbackable;
import data.transpool.user.component.feedback.Feedbacker;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LeaveFeedbackServlet", urlPatterns = {"/leave-feedback"})
public class LeaveFeedbackServlet extends HttpServlet {

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());
            String feedbackerUsernameFromSession = SessionUtils.getUsername(req);

            String feedbackeeUsernameFromParameter = req.getParameter(Constants.FEEDBACKEE);
            int rating = Integer.parseInt(req.getParameter(Constants.RATING));
            String comment = req.getParameter(Constants.COMMENT);

            Feedbacker feedbacker = (Feedbacker) userEngine.getUserAccount(feedbackerUsernameFromSession);
            Feedbackable feedbackable = (Feedbackable) userEngine.getUserAccount(feedbackeeUsernameFromParameter);

            feedbacker.leaveFeedback(feedbackable, new Feedback(feedbacker, rating, comment));
            out.print("Feedbacked " + feedbackeeUsernameFromParameter + " successfully!");
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
