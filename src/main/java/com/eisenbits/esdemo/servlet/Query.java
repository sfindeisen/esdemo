package com.eisenbits.esdemo.servlet;

public class Query {
    public final String  nameTerm;
    public final String  descTerm;
    public final boolean nameFullText;
    public final boolean descFullText;
    public final Integer maxPrice;

    public Query(String nameTerm, String descTerm, boolean nameFull, boolean descFull, Integer maxPrice) {
        this.nameTerm = nameTerm;
        this.descTerm = descTerm;
        this.nameFullText = nameFull;
        this.descFullText = descFull;
        this.maxPrice = maxPrice;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name: ");
        sb.append(nameFullText ? "*" : "");
        sb.append(nameTerm);
        sb.append(nameFullText ? "*" : "");
        sb.append(" desc: ");
        sb.append(descFullText ? "*" : "");
        sb.append(descTerm);
        sb.append(descFullText ? "*" : "");
        sb.append(" maxPrice=" + maxPrice);
        return sb.toString();
    }
}