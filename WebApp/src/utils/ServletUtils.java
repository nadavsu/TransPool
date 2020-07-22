package utils;

import api.TransPoolMapEngine;
import data.transpool.user.UserEngine;
import data.transpool.user.UserEngineBase;

import javax.servlet.ServletContext;

public class ServletUtils {

    private static final String USER_ENGINE_ATTRIBUTE_NAME = "userEngine";
    private static final String MAP_ENGINE_ATTRIBUTE_NAME = "mapEngine";

    private static final Object userEngineLock = new Object();
    private static final Object mapEngineLock = new Object();


    public static UserEngine getUserEngine(ServletContext servletContext) {
        synchronized (userEngineLock) {
            if (servletContext.getAttribute(USER_ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(USER_ENGINE_ATTRIBUTE_NAME, new UserEngineBase());
            }
        }
        return (UserEngine) servletContext.getAttribute(USER_ENGINE_ATTRIBUTE_NAME);
    }

    public static TransPoolMapEngine getMapEngine(ServletContext servletContext) {
        synchronized (mapEngineLock) {
            if (servletContext.getAttribute(MAP_ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(MAP_ENGINE_ATTRIBUTE_NAME, new TransPoolMapEngine());
            }
        }
        return (TransPoolMapEngine) servletContext.getAttribute(MAP_ENGINE_ATTRIBUTE_NAME);
    }
}
