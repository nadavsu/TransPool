package servlets.getters;

import api.transpool.user.dto.TransPoolDriverDTO;
import com.google.gson.Gson;
import constants.Constants;
import api.transpool.user.UserEngine;
import api.transpool.user.account.TransPoolDriver;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetDriverServlet", urlPatterns = {"/get-driver"})
public class GetDriverServlet extends HttpServlet {

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            String usernameFromSession = SessionUtils.getUsername(req);
            String usertypeFromSession = SessionUtils.getUserType(req);
            UserEngine userEngine = ServletUtils.getUserEngine(getServletContext());

            /*if (usertypeFromSession != null && usertypeFromSession.equals(Constants.DRIVER)) {
                TransPoolDriver driver = (TransPoolDriver) userEngine.getUserAccount(usernameFromSession);
                TransPoolDriverDTO driverDTO = driver.getDetails();
                String driverDTOJson = new Gson().toJson(driverDTO);
                out.print(driverDTOJson);
                out.flush();
            }*/
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
