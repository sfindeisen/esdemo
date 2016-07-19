package com.eisenbits.esdemo.servlet;

import javax.servlet.http.HttpServletRequest;

public enum ServletUtils {
    INSTANCE;

    void setErrorMsg(HttpServletRequest req, String msg) {
        req.setAttribute(ServletConstants.ERROR_MSG, msg);
    }

    void setInfoMsg(HttpServletRequest req, String msg) {
        req.setAttribute(ServletConstants.INFO_MSG, msg);
    }

    void prepareClusterHealth(ESDemoServlet servlet, HttpServletRequest req) {
        req.setAttribute(ServletConstants.JSP_ATTR_CLUSTER_HEALTH, servlet.getConnector().getClusterHealth());
    }

    void prepareGetDocument(ESDemoServlet servlet, HttpServletRequest req, String id) {
        req.setAttribute(ServletConstants.JSP_ATTR_SELECTED_DOCUMENT, servlet.getConnector().getDocument(id).getSourceAsString());
    }
}