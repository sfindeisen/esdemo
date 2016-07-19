package com.eisenbits.esdemo.servlet;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.eisenbits.esdemo.Constants;
import com.eisenbits.esdemo.esconn.AbstractConnector;
import com.eisenbits.esdemo.esconn.TransportConnector;

public abstract class ESDemoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final Logger log = Logger.getLogger(ESDemoServlet.class.getCanonicalName());
    private AbstractConnector conn;

    @Override
    public void init() throws ServletException {
        super.init();
        if (null == conn) {
            checkInitConnector();
            conn.checkCreateIndex();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (null != conn) {
            try {
                conn.close();
            } catch (Exception e) {
                log.warning("Connection close error: " + e.getMessage());
            } finally {
                conn = null;
            }
        }
    }

    protected void fixCharacterEncoding(HttpServletRequest req) throws UnsupportedEncodingException {
        final String charset = req.getCharacterEncoding();
        if (null==charset)
            req.setCharacterEncoding(ServletConstants.DEFAULT_CHARSET);
    }

    protected Integer getIntParameter(HttpServletRequest req, String key) {
        String s = req.getParameter(key);
        if (null != s) {
            try {
                return new Integer(s);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    protected void warning(Logger log, Throwable t) {
        log.log(Level.WARNING, t.getMessage(), t);
    }

    private void checkInitConnector() {
        if (null == conn)
            conn = new TransportConnector(Constants.ElasticSearchHost, Constants.ElasticSearchPort);        
    }

    protected AbstractConnector getConnector() {
        checkInitConnector();
        return conn;
    }
}
