package com.eisenbits.esdemo.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.eisenbits.esdemo.Constants;
import com.eisenbits.esdemo.esconn.ConnectorException;
import com.eisenbits.esdemo.utils.JSONConverter;

/**
 * TODO make sure document ID is auto-generated
 */
@MultipartConfig
public class UploadServlet extends ESDemoServlet {

    private static final long            serialVersionUID = 1L;
    private final Logger                 log              = Logger.getLogger(UploadServlet.class.getCanonicalName());
    private final DocumentBuilderFactory xmlFactory       = DocumentBuilderFactory.newInstance();

    public UploadServlet() {
        super();
        xmlFactory.setValidating(Constants.ValidateXML);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.fine("GET: " + req);
        getServletContext().getRequestDispatcher(ServletConstants.JSP_UPLOAD).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.fine("POST: " + req);
        Part filePart = req.getPart(ServletConstants.FORM_FIELD_DATA_FILE);

        if (null == filePart) {
            req.setAttribute(ServletConstants.ERROR_MSG, "File is null!");
            getServletContext().getRequestDispatcher(ServletConstants.JSP_UPLOAD).forward(req, resp);
        } else {
            try (InputStream is = filePart.getInputStream()) {
                // 1. parse as XML DOM
                DocumentBuilder builder = xmlFactory.newDocumentBuilder();
                Document document = builder.parse(is);

                // 2. convert XML DOM to JSON
                JsonValue jsonStruct = JSONConverter.INSTANCE.xml2json(document.getDocumentElement());
                
                // 3. serialize JSON as String
                List<String> jss = new ArrayList<>();
                if (jsonStruct instanceof JsonArray) {
                    for (JsonValue json : (JsonArray) jsonStruct) {
                        jss.add(json.toString());
                    }
                } else {
                    jss.add(jsonStruct.toString());
                }

                // 4. index the documents using ElasticSearch's bulk API
                String[] jssArr = jss.toArray(new String[0]);
                log.fine("indexing " + jssArr.length + " documents using bulk API");
                for (int j=0; (j < jssArr.length) && (j < 5); ++j) {
                    log.fine(j + ": " + jssArr[j]);
                }
                getConnector().bulkIndex(Constants.DocTypeName, jssArr);
                final String infoMsg = "Successfully indexed " + jssArr.length + " JSON objects using bulk API"; 
                ServletUtils.INSTANCE.setInfoMsg(req, infoMsg);
                log.info(infoMsg);
            } catch (ParserConfigurationException e) {
                warning(log, e);
                throw new ServletException(e);
            } catch (SAXException e) {
                warning(log, e);
                throw new ServletException(e);
            } catch (ConnectorException e) {
                warning(log, e);
                throw new ServletException(e);
            }

            ServletUtils.INSTANCE.prepareClusterHealth(this, req);
            getServletContext().getRequestDispatcher(ServletConstants.JSP_STATUS).forward(req, resp);
        }
    }
}
