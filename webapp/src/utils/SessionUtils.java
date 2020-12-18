package utils;

import constants.Constants;
import api.transpool.trip.matching.component.PossibleRoutesList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static String getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = null;
        if (session != null) {
            sessionAttribute = session.getAttribute(Constants.USERNAME);
        }
        if (sessionAttribute != null) {
            return sessionAttribute.toString();
        } else {
            return null;
        }
    }

    public static PossibleRoutesList getPossibleRoutes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = null;
        if (session != null) {
            sessionAttribute = session.getAttribute(Constants.POSSIBLE_ROUTES);
        }
        if (sessionAttribute != null) {
            return (PossibleRoutesList) sessionAttribute;
        } else {
            return null;
        }
    }

    public static String getUserType(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = null;
        if (session != null) {
            sessionAttribute = session.getAttribute(Constants.ACCOUNT_TYPE);
        }
        if (sessionAttribute != null) {
            return sessionAttribute.toString();
        } else {
            return null;
        }
    }
}
