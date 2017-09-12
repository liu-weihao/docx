package com.dx.ss.data.beans;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Locale;

/**
 * The document header settings.
 * @ClassName: DocumentHeader
 * @version V1.0
 * @date 2017-09-12
 * @author liu.weihao
 */
public class DocumentHeader {

    /**
     * Header name.
     */
    private String name;

    /**
     * Column width. name.length() default.
     */
    private int width;

    /**
     * Header cell style.
     */
    private CellStyle cellStyle;

    /**
     * The locale of header name.
     */
    private Locale locale;

    public DocumentHeader(String name) {
        if(StringUtils.isBlank(name)) throw new IllegalArgumentException("Header name can not be empty.");
        this.name = name;
    }

    public DocumentHeader(String name, int width, Locale locale) {
        this(name);
        if (width > 0) {
            this.width = width;
        } else {
            this.width = name.length();
        }
        if (locale != null) {
            this.locale = locale;
        } else {
            this.locale = Locale.ENGLISH;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
