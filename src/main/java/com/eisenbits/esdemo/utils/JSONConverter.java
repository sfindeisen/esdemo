package com.eisenbits.esdemo.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.eisenbits.esdemo.Constants;

/**
 * Simple XML to JSON converter.
 * 
 * @author Stanislaw Findeisen
 */
public enum JSONConverter {
    INSTANCE;

    private final Pattern     patInt                       = Pattern.compile("(\\-|\\+)?[0-9]+");
    private final Pattern     patFloat                     = Pattern.compile("(\\-|\\+)?[0-9]+\\.[0-9]+");
    private final Pattern     patTrue                      = Pattern.compile("true|yes", Pattern.CASE_INSENSITIVE);
    private final Pattern     patFalse                     = Pattern.compile("false|no", Pattern.CASE_INSENSITIVE);
    private final Pattern     patNull                      = Pattern.compile("null", Pattern.CASE_INSENSITIVE);
    /** TODO fix this (see below) */
    private final Set<String> typeInferenceWhitelistFields = new HashSet<String>(Arrays.asList(
            new String[] {
                    Constants.Field_employeeDiscount,
                    Constants.Field_maxOrderQuantity,
                    Constants.Field_price,
                    Constants.Field_stock,
                    Constants.Field_vat,
                    Constants.Field_warranty,
                    Constants.Field_weight
            }));

    /**
     * Transforms the given XML DOM into JSON.
     */
    public JsonValue xml2json(Element xmlElement) {
        final NodeList children = xmlElement.getChildNodes();
        int textCount = 0;
        final Set<String> subNames = new HashSet<>();
        final List<Element> elemChildren = new ArrayList<>(); // for convenience

        // count text/element nodes (children)
        for (int i = 0; i < children.getLength(); ++i) {
            final Node child = children.item(i);
            final short childType = child.getNodeType();

            if ((Node.TEXT_NODE == childType) && (child instanceof Text)) {
                final String content = child.getTextContent();
                // we would use child.isElementContentWhitespace, but it is
                // unreliable
                if (!content.trim().isEmpty()) {
                    ++textCount;
                }
            } else if ((Node.ELEMENT_NODE == childType) && (child instanceof Element)) {
                Element childElem = (Element) child;
                elemChildren.add(childElem);
                subNames.add(childElem.getTagName());
            }
        }

        final int elemCount = elemChildren.size();
        if ((1 <= elemCount) && (1 <= textCount))
            throw new IllegalArgumentException("mixed content is not supported! (" + xmlElement.getTagName() + ")");

        if (1 <= elemCount) {
            if ((2 <= elemCount) && (1 == subNames.size())) {
                // all child element names are the same: make it a JSON array
                final JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
                for (Element ec : elemChildren) {
                    arrBuilder.add(xml2json(ec));
                }
                return arrBuilder.build();
            } else if (elemCount == subNames.size()) {
                // all child element names are different: make it a JSON object
                final JsonObjectBuilder objBuilder = Json.createObjectBuilder();
                for (Element ec : elemChildren) {
                    objBuilder.add(ec.getTagName(), xml2json(ec));
                }
                return objBuilder.build();
            } else {
                throw new IllegalArgumentException("elemCount=" + elemCount + ", subNames:" + subNames.size());
            }
        } else if (1 <= textCount) {
            // TODO fix this
            //
            // Here we want to prevent different json types on the same
            // path-to-the-root (schema consistency)
            //
            // This quick'n'dirty solution only allows type inference for
            // specified field names.
            final String ct = xmlElement.getTextContent();
            return (typeInferenceWhitelistFields.contains(xmlElement.getTagName()) ? parseAtomic(ct) : wrapString(ct));
        } else {
            // nothing in here (no meaningful children)
            return JsonValue.NULL;
        }
    }

    /**
     * Parse atomic JSON value from a String.
     *
     * @param value
     *            JSON value (atomic)
     */
    private JsonValue parseAtomic(String value) {
        final JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        if (patNull.matcher(value).matches())
            arrBuilder.addNull();
        else if (patFalse.matcher(value).matches())
            arrBuilder.add(false);
        else if (patTrue.matcher(value).matches())
            arrBuilder.add(true);
        else if (patInt.matcher(value).matches())
            arrBuilder.add(new BigInteger(value));
        else if (patFloat.matcher(value).matches())
            arrBuilder.add(new BigDecimal(value));
        else
            arrBuilder.add(value);
        // javax.json does not provide atomic json value parsing
        // method, so we are using a singleton array trick
        return arrBuilder.build().get(0);
    }

    private JsonValue wrapString(String value) {
        return Json.createArrayBuilder().add(value).build().get(0);
    }
}
