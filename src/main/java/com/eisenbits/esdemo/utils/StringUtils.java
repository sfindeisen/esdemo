package com.eisenbits.esdemo.utils;

public enum StringUtils {
    INSTANCE;
    
    public boolean isEmpty(String s) {
        return ((null==s) ? true : s.isEmpty());
    }
}