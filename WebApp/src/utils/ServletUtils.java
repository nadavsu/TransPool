package utils;

import data.transpool.user.UserEngine;
import data.transpool.user.UserEngineBase;

import javax.servlet.ServletContext;

public class ServletUtils {

    private static final String USER_ENGINE_ATTRIBUTE_NAME = "userEngine";

    private static final Object userEngineLock = new Object();


    //TODO: Note - you are creating a new UserEngine which is not related to the DataEngine.
    //Maybe you should just keep it out of the data engine.
    public static UserEngine getUserEngine(ServletContext servletContext) {
        synchronized (userEngineLock) {
            if (servletContext.getAttribute(USER_ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(USER_ENGINE_ATTRIBUTE_NAME, new UserEngineBase());
            }
        }
        return (UserEngine) servletContext.getAttribute(USER_ENGINE_ATTRIBUTE_NAME);
    }
}
