package com.eisenbits.esdemo.servlet;

import com.eisenbits.esdemo.Constants;

/**
 * JSP and other servlet constants.
 */
public class ServletConstants extends Constants {
    public static final String DEFAULT_CHARSET            = "UTF-8";
    public static final String ERROR_MSG                  = "errorMsg";
    public static final String INFO_MSG                   = "infoMsg";
    public static final String SERVLET_STATUS             = "status";

    public static final String JSP_ATTR_PREFIX            = "esd_";
    public static final String JSP_ATTR_CLUSTER_HEALTH    = JSP_ATTR_PREFIX + "cluster_health";
    public static final String JSP_ATTR_DELETE_RESULT     = JSP_ATTR_PREFIX + "delete_result";
    public static final String JSP_ATTR_SEARCH_RESULTS    = JSP_ATTR_PREFIX + "search_results";
    public static final String JSP_ATTR_SELECTED_DOCUMENT = JSP_ATTR_PREFIX + "selected_document";

    public static final String JSP_PREFIX                 = "/jsp/";
    public static final String JSP_DELETE                 = JSP_PREFIX + "delete.jsp";
    public static final String JSP_SEARCH                 = JSP_PREFIX + "search.jsp";
    public static final String JSP_SEARCH_RESULTS         = JSP_PREFIX + "search-results.jsp";
    public static final String JSP_STATUS                 = JSP_PREFIX + "status.jsp";
    public static final String JSP_UPLOAD                 = JSP_PREFIX + "upload.jsp";
    public static final String JSP_VIEW                   = JSP_PREFIX + "view.jsp";

    public static final String FORM_FIELD_PREFIX          = "ff";
    public static final String FORM_FIELD_DATA_FILE       = FORM_FIELD_PREFIX + "DataFile";
    public static final String FORM_FIELD_PREFIX_DESC     = FORM_FIELD_PREFIX + "Desc";
    public static final String FORM_FIELD_PREFIX_NAME     = FORM_FIELD_PREFIX + "Name";
    public static final String FORM_FIELD_NAME_TERM       = FORM_FIELD_PREFIX_NAME + "Term";
    public static final String FORM_FIELD_NAME_FULL       = FORM_FIELD_PREFIX_NAME + "Full";
    public static final String FORM_FIELD_DESC_TERM       = FORM_FIELD_PREFIX_DESC + "Term";
    public static final String FORM_FIELD_DESC_FULL       = FORM_FIELD_PREFIX_DESC + "Full";
    public static final String FORM_FIELD_MAX_PRICE       = FORM_FIELD_PREFIX + "MaxPrice";

    public static final String GET_PARAM_PREFIX_ID        = "";
    public static final String GET_PARAM_ID               = GET_PARAM_PREFIX_ID + "id";
}
