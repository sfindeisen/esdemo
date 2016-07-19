package com.eisenbits.esdemo;

/**
 * Application constants.
 */
public class Constants {
    /**
     * The _all pseudo-field:
     * https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-
     * all-field.html
     */
    // public static final String  ElasticSearchAllField  = "_all";
    public static final String  ElasticSearchAllTypes  = "_all";
    public static final String  ElasticSearchHost      = "localhost";
    public static final int     ElasticSearchPort      = 9300;
    public static final String  IndexName              = "esdemo";
    public static final String  DocTypeName            = "product";

    /** validate XML files on upload */
    public static final boolean ValidateXML            = false;

    public static final String  Field_employeeDiscount = "employeeDiscount";
    public static final String  Field_maxOrderQuantity = "maxOrderQuantity";
    public static final String  Field_stock            = "stock";
    public static final String  Field_vat              = "vat";
    public static final String  Field_warranty         = "warranty";
    public static final String  Field_weight           = "weight";
    public static final String  Field_name             = "name";
    public static final String  Field_description      = "description";
    public static final String  Field_price            = "price";
}