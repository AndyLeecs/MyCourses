package com.andi.mycourses.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author andi
 */
public class SessionUtil {
    public static String getSessionEmail(HttpServletRequest request) {
        return (String)request.getSession().getAttribute("email");
    }
}
