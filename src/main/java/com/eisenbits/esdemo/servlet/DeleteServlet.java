package com.eisenbits.esdemo.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;

import com.eisenbits.esdemo.Constants;

public class DeleteServlet extends ESDemoServlet {

    private static final long serialVersionUID = 1L;
    private final Logger      log              = Logger.getLogger(DeleteServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.fine("GET: " + req);
        getServletContext().getRequestDispatcher(ServletConstants.JSP_DELETE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.fine("POST: " + req);
        final DeleteIndexResponse dres = getConnector().deleteIndex(Constants.IndexName);
        if (dres.isAcknowledged())
            ServletUtils.INSTANCE.setInfoMsg(req, "operation acknowledged");
        else
            ServletUtils.INSTANCE.setErrorMsg(req, "operation NOT acknowledged");
        getServletContext().getRequestDispatcher(ServletConstants.JSP_DELETE).forward(req, resp);
    }
}
