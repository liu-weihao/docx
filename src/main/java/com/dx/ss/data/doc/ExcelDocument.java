package com.dx.ss.data.doc;

import com.dx.ss.data.enums.ErrCodeEnums;
import com.dx.ss.data.exception.ExcelDocumentException;
import com.dx.ss.data.exception.ResourceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/** 
 * Super class for all of Excel documents
 * @ClassName: ExcelDocument 
 * @version V1.0  
 * @date 2016-11-17 
 * @author liu.weihao
 */
public abstract class ExcelDocument extends Documentation {
    
    
    /** It is convenient to get cell type through this filed type and cell type mapping. */
    private static Map<Class<?>, CellType> cellTypeMap = new HashMap<Class<?>, CellType>();

    /**
     * This attribute means the length of content displayed at most.
     * It can be changed by {@link #setMaxColumnWidth(int)}.
     */
    private int maxColumnWidth = 16;

    public int getMaxColumnWidth() {
        return maxColumnWidth;
    }

    public void setMaxColumnWidth(int maxColumnWidth) {
        this.maxColumnWidth = maxColumnWidth;
    }

    static {
        cellTypeMap.put(Date.class, CellType.NUMERIC);
        cellTypeMap.put(Timestamp.class, CellType.NUMERIC);
        cellTypeMap.put(Long.class, CellType.NUMERIC);
        cellTypeMap.put(Double.class, CellType.NUMERIC);
        cellTypeMap.put(Integer.class, CellType.NUMERIC);
        cellTypeMap.put(Short.class, CellType.NUMERIC);
        cellTypeMap.put(BigDecimal.class, CellType.NUMERIC);
        
        cellTypeMap.put(Boolean.class, CellType.BOOLEAN);
        
        cellTypeMap.put(String.class, CellType.STRING);
    }
    
    /**
     * Get a cell type by a filed type.
     * @author liu.weihao
     * @date 2016-11-25 
     * @param type  a filed type, like <code>Date</code>, <code>String</code>, etc.
     * @return  a cell type.
     */
    public static CellType getCellType(Class<?> type) {
        if(type == null) return CellType.BLANK;
        return cellTypeMap.get(type);
    }
    
    /**
     * Create a new Excel workbook.<br/>
     * Overriden by subclass.
     * @author liu.weihao
     * @date 2016-11-23 
     * @return  High level representation of a Excel workbook
     */
    public abstract Workbook newWorkbook();
    
    /**
     * Get a Excel workbook from resource settled in document.<br/>
     * a {@link ResourceException} thrown if the resource not settled.<br/>
     * a {@link IOException} thrown by POI if workbook created failed. 
     * @author liu.weihao
     * @date 2016-11-10 
     * @return  A new Excel workbook. {@link HSSFWorkbook} is for Excel-2003, 
     * and {@link XSSFWorkbook} is for Excel-2007.
     * @throws ResourceException
     * @throws IOException
     */
    public abstract Workbook workbook() throws ResourceException, IOException;
    
    /**
     * Create a new sheet in workbook with a name.
     * @author liu.weihao
     * @date 2016-11-11 
     * @param wb    A excel workbook object. if null, will throw a ExcelDocumentException.
     * @param sheetName     The name for new sheet. If the sheet name is blank, 
     * it will be set in a formation that is (sheet +  (getNumberOfSheets+1)).
     * @return  A new excel sheet.
     * @throws ExcelDocumentException throw a exception if workbook is null.
     */
    public Sheet createSheet(Workbook wb, String sheetName) throws ExcelDocumentException{
        if(wb == null)  
            throw new ExcelDocumentException(
                ErrCodeEnums.ERR_UNKNOWN_WORKBOOK.getDetail(),
                ErrCodeEnums.ERR_UNKNOWN_WORKBOOK.getCode());
        if(StringUtils.isBlank(sheetName)){
            int num = wb.getNumberOfSheets();
            sheetName = "sheet" + (num+1);
        }
        return wb.createSheet(sheetName);
    }
    
    /**
     * <p class="detail">
     * Create a new row with specified row index in the sheet.
     * </p>
     * @author liu.weihao
     * @date 2016-11-11 
     * @param sheet A excel sheet object. if null, will throw a ExcelDocumentException.
     * @param rownum    The zero-based row num.
     * @return  A new excel row.
     * @throws ExcelDocumentException   throw a exception if sheet is null.
     */
    public Row createRow(Sheet sheet, int rownum) throws ExcelDocumentException{
        if(sheet == null)   
            throw new ExcelDocumentException(
                    ErrCodeEnums.ERR_UNKNOWN_SHEET.getDetail(),
                    ErrCodeEnums.ERR_UNKNOWN_SHEET.getCode());
        return sheet.createRow(rownum);
    }
    
    /**
     * Use this method to create new cells within the row and return it.
     * @author liu.weihao
     * @date 2016-11-11 
     * @param row       A excel row object. if null, will throw a ExcelDocumentException.
     * @param column The zero-based column num.
     * @return  A new excel cell.
     * @throws ExcelDocumentException   throw a exception if sheet is null.
     */
    public Cell createCell(Row row, int column) throws ExcelDocumentException{
        if(row == null) 
            throw new ExcelDocumentException(
                    ErrCodeEnums.ERR_UNKNOWN_ROW.getDetail(),
                    ErrCodeEnums.ERR_UNKNOWN_ROW.getCode());
        Cell cell = row.createCell(column);
        cell.setCellType(CellType.STRING);
        return cell;
    }
    
    /**
     * Provide a default font.
     * font-name: <code>Arial</code>, font-size: 11
     * @author liu.weihao
     * @date 2016-11-24 
     * @param workbook  a excel workbook.
     * @return  a new font.
     */
    public Font defaultFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_NORMAL);
        font.setCharSet(Font.DEFAULT_CHARSET);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        return font;
    }
    
    /**
     * Create a font for the title.
     * font-name: <code>Microsoft YaHei UI</code>, font-size: 22,
     * font-weight: <code>bold</code>.
     * @author liu.weihao
     * @date 2016-11-25 
     * @param workbook  a excel workbook.
     * @return  a new font.
     * @see #defaultFont(Workbook)
     */
    public Font titleFont(Workbook workbook) {
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontName("Microsoft YaHei UI");
        titleFont.setFontHeightInPoints((short) 22);
        return titleFont;
    }
    
    /**
     * Create a font(based default font) for the header.
     * font-name: <code>Arial</code>, font-size: 11,
     * @author liu.weihao
     * @date 2016-11-25 
     * @param workbook  a excel workbook.
     * @return  a new font.
     * @see #defaultFont(Workbook)
     */
    public Font headerFont(Workbook workbook) {
        return this.defaultFont(workbook);
    }

    /**
     * Provide a default cell style.
     * <p>Horizontal-Alignment: <code>LEFT</code></p>
     * <p>Vertical-Alignment: <code>CENTER</code></p>
     * <p>Wrap-Text: <code>false</code></p>
     * <p>Border: <code>THIN</code> top bottom right</p>
     * @author liu.weihao
     * @date 2016-11-24 
     * @param workbook  a excel workbook.
     * @return  a new cell style.
     */
    public CellStyle defaultCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(false);
        cellStyle.setFont(defaultFont(workbook));
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }
    
    /**
     * Create a cell style(based default cell style) for the top-title within a title font.
     * <code>bold</code> font, <code>blue</code> foreground color, 
     * <code>Horizontal-CENTER</code>.
     * @author liu.weihao
     * @date 2016-11-25 
     * @param workbook  a excel workbook.
     * @return  a new cell style.
     * @see #defaultCellStyle(Workbook)
     * @see #titleFont(Workbook)
     */
    public CellStyle titleCellStyle(Workbook workbook) {
        CellStyle titleCellStyle = this.defaultCellStyle(workbook);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFont(this.titleFont(workbook));
        return titleCellStyle;
    }
    
    /**
     * Create a cell style(based default cell style) for the header within a header font.
     * <code>bold</code> font, <code>blue</code> foreground color, 
     * <code>Horizontal-CENTER</code>.
     * @author liu.weihao
     * @date 2016-11-25 
     * @param workbook  a excel workbook.
     * @return  a new cell style.
     * @see #defaultCellStyle(Workbook)
     * @see #headerFont(Workbook)
     */
    public CellStyle headerCellStyle(Workbook workbook) {
        CellStyle headerCellStyle = this.defaultCellStyle(workbook);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setFont(this.headerFont(workbook));
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerCellStyle;
    }
    
    /**
     * Create a header row in a sheet with names and cell style.
     * @author liu.weihao
     * @date 2016-11-25 
     * @param sheet a sheet in excel.
     * @param rowIndex the index of header row.
     * @param headerNames   a array list of header names.
     * @param cellStyle the cell style for the header columns.
     * @throws ExcelDocumentException
     */
    public void createHeaderRow(Sheet sheet, int rowIndex, ArrayList<String> headerNames, CellStyle cellStyle) throws ExcelDocumentException{
        if(sheet == null)   return;
        if(headerNames == null || headerNames.size() == 0)  return;
        Row row = this.createRow(sheet, rowIndex);
        row.setHeightInPoints(23);//23px default.
        int columnIndex = 0;
        for(String name: headerNames){
            Cell cell = this.createCell(row, columnIndex);
            cell.setCellValue(name);
            if(cellStyle != null)   cell.setCellStyle(cellStyle);
            //Eight times the length of the name default. Adjust the length after this method.
            sheet.setColumnWidth(columnIndex, (int) (Math.ceil(name.length() / this.maxColumnWidth) * 256));
            columnIndex++;
        }
    }
    
}
