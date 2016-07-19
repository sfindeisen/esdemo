package com.eisenbits.esdemo.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StatusServlet extends ESDemoServlet {

    private static final long serialVersionUID = 1L;
    private final Logger log = Logger.getLogger(StatusServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.fine("GET: " + req);
        ServletUtils.INSTANCE.prepareClusterHealth(this, req);
        getServletContext().getRequestDispatcher(ServletConstants.JSP_STATUS).forward(req, resp);
    }
}