package com.eisenbits.esdemo.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchResponse;

import com.eisenbits.esdemo.utils.StringUtils;

public class SearchServlet extends ESDemoServlet {

    private static final long serialVersionUID = 1L;
    private final Logger      log              = Logger.getLogger(SearchServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.fine("GET: " + req);
        getServletContext().getRequestDispatcher(ServletConstants.JSP_SEARCH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fixCharacterEncoding(req);
        log.fine("POST: " + req);
        final String nameTerm = req.getParameter(ServletConstants.FORM_FIELD_NAME_TERM);
        final String descTerm = req.getParameter(ServletConstants.FORM_FIELD_DESC_TERM);

        if (StringUtils.INSTANCE.isEmpty(nameTerm) && StringUtils.INSTANCE.isEmpty(descTerm)) {
            ServletUtils.INSTANCE.setErrorMsg(req, "No query.");
            getServletContext().getRequestDispatcher(ServletConstants.JSP_SEARCH).forward(req, resp);
        } else {
            final boolean nameFull = (null != req.getParameter(ServletConstants.FORM_FIELD_NAME_FULL));
            final boolean descFull = (null != req.getParameter(ServletConstants.FORM_FIELD_DESC_FULL));
            final Integer maxPrice = getIntParameter(req, ServletConstants.FORM_FIELD_MAX_PRICE);
            final Query query = new Query(nameTerm, descTerm, nameFull, descFull, maxPrice);
            SearchResponse sres = getConnector().search(query);
            req.setAttribute(ServletConstants.JSP_ATTR_SEARCH_RESULTS, sres);
            getServletContext().getRequestDispatcher(ServletConstants.JSP_SEARCH_RESULTS).forward(req, resp);
        }
    }
}
