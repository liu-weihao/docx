package com.dx.ss.data.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.dx.ss.data.doc.ExcelDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.dx.ss.data.beans.DocumentBean;

/** 
 * Some utils for excel document.
 * @ClassName: ExcelDocumentUtil 
 * @version V1.0  
 * @date 2016-11-17 
 * @author liu.weihao
 */
public class ExcelDocumentUtil extends DocumentUtil {

    /**
     * <p>Return the value in a excel cell.</p>
     * <p>A <code>null</code> cell input will return the empty string.</p>
     * 
     * <p>A <code>CELL_TYPE_BLANK</code> or <code>CELL_TYPE_ERROR</code> cell type 
     * will return the empty string.</p>
     *
     * @author liu.weihao
     * @date 2016-11-17 
     * @param cell a Cell handle.
     * @return  The cell value always not <code>null</code>.
     */
    public static Object getCellValue(Cell cell) {
        if(cell == null) return StringUtils.EMPTY;
        
        CellType cellType = cell.getCellTypeEnum();
        if(cellType.equals(CellType.STRING)) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        }else if (cellType.equals(CellType.NUMERIC)) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
        }else if (cellType.equals(CellType.FORMULA)) {
            if (!StringUtils.isBlank(cell.getStringCellValue())) {
                return StringUtils.trimToEmpty(cell.getStringCellValue());
            } else {
                return cell.getNumericCellValue();
            }
        }else if (cellType.equals(CellType.BLANK) || cellType.equals(CellType.ERROR)) {
            return StringUtils.EMPTY;
        }else if (cellType.equals(CellType.BOOLEAN)) {
            return cell.getBooleanCellValue();
        }
        return StringUtils.EMPTY;
    }
    
    
    /**
     * invoke setXxx method of document data bean.
     * @author liu.weihao
     * @date 2016-11-22 
     * @param bean  a java bean extends {@code DocumentBean}. 
     * @param method    setXxx method
     * @param parameterType the data type of property.
     * @param cellValue property value from excel cell.
     * @param extras    Customized data by K-V. 
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException 
     * @throws IllegalArgumentException 
     */
    private static void invoke(DocumentBean bean, Method method, Class<?> parameterType,
                               Object cellValue, Map<String, Object> extras)
                                                                            throws IllegalAccessException,
                                                                            InvocationTargetException, IllegalArgumentException, ParseException {
        if (parameterType.equals(Date.class)  || parameterType.equals(Timestamp.class)) { //Date Type
            if (!StringUtils.isBlank(cellValue.toString())){
                String pattern = extras.get("date_pattern") == null ? "yyyy-MM-dd" : extras.get("date_pattern").toString();
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                method.invoke(bean, format.parse(cellValue.toString()));
            }
        } else if (parameterType.equals(BigDecimal.class)) { //BigDecimal Type
            
            method.invoke(bean, new BigDecimal(cellValue.toString()));
        } else if (parameterType.equals(Boolean.class)) { //Boolean Type

            String val = cellValue.toString();
            if (extras.get("true_value") != null) {

                method.invoke(bean, val.equalsIgnoreCase(extras.get("true_value").toString()));
            } else {

                method.invoke(bean, val.equals("1") || val.equalsIgnoreCase("true"));
            }
        } else if (parameterType.equals(Double.class)) { //Double Type
            
            method.invoke(bean, Double.valueOf(cellValue.toString()));
        } else if (parameterType.equals(Integer.class)) { //Integer Type
            
            method.invoke(bean, Integer.valueOf(cellValue.toString()));
        } else if (parameterType.equals(Long.class)) { //Long Type
            
            method.invoke(bean, Long.valueOf(cellValue.toString()));
        } else if (parameterType.equals(Short.class)) { //Short Type
            
            method.invoke(bean, Short.valueOf(cellValue.toString()));
        } else {    //others
            
            method.invoke(bean, cellValue);
        }
    }
    
    /**
     * Set cell value according the input value type. 
     * <code> FORMULA </code> is not supported.<br/>
     * if the cell type is not settled, get the cell type by the type of value, 
     * then set it to the cell type.  
     * @author liu.weihao
     * @date 2016-11-25
     * @param cell  a cell
     * @param cellType cell type.
     * @param value the cell value
     * @param extras Customized data by K-V.
     */
    public static void setCellValue(Cell cell, CellType cellType, Object value, Map<String, Object> extras) {
        if(cell == null || value == null) return;
        if(cellType.equals(CellType.STRING)) {
            cell.setCellValue(StringUtils.trimToEmpty(value.toString()));
        } else if (cellType.equals(CellType.BOOLEAN)) {
            String val = value.toString();
            if (extras.get("true_value") != null && val.equalsIgnoreCase(extras.get("true_value").toString())
                    || val.equals("1") || val.equalsIgnoreCase("true")) {

                cell.setCellValue(true);
            } else {

                cell.setCellValue(false);
            }

        } else if (cellType.equals(CellType.NUMERIC)) {
            Class<?> clz = value.getClass();
            if(clz.equals(Date.class) || clz.equals(Timestamp.class)) {   //Date type
                String pattern = extras.get("date_pattern") == null ? "yyyy-MM-dd" : extras.get("date_pattern").toString();
                cell.setCellValue(new SimpleDateFormat(pattern).format(value));
            }else if(clz.equals(Boolean.class)){   //Boolean type
                
                cell.setCellValue(Boolean.valueOf(value.toString()));
            }else if (clz.equals(BigDecimal.class)) { //BigDecimal Type
                
                cell.setCellValue(new BigDecimal(value.toString()).doubleValue());
            }else if(clz.equals(Double.class)){    //Double type
                
                cell.setCellValue(Double.valueOf(value.toString()));
            }else if (clz.equals(Integer.class)) { //Integer Type
                
                cell.setCellValue(Integer.valueOf(value.toString()));
            } else if (clz.equals(Long.class)) { //Long Type
                
                cell.setCellValue(Long.valueOf(value.toString()));
            } else if (clz.equals(Short.class)) { //Short Type
                
                cell.setCellValue(Short.valueOf(value.toString()));
            }
        } else { //others, set empty("") value.
            cell.setCellValue(StringUtils.EMPTY);
        }
    }
    
    /**
     * Filling data into the excel sheet. You need to specify the <code>columns</code> 
     * and the corresponding <code>dataList</code>.
     * columns and properties are sequence-required which means one-to-one correspondence.
     * It is a very useful for exporting work.
     * @author liu.weihao
     * @date 2016-11-25 
     * @param sheet a excel sheet handle.
     * @param firstRow  the index of first row which begin to fill data. 
     * @param properties   <b>Not Empty.</b>the property name of data bean in <code>dataList</code>.
     *                     also can set some special properties, like {@code DocumentBean.SERIAL_PROPERTY_NAME}.
     * @param dataList <b>Not Empty.</b>list of data you want to fill. In <code>dataList</code>, one element one row.
     * @param cellStyle  specify a cell style.
     * @param extras Customized data by K-V.
     *               <code>date_pattern</code> supported. like "date_pattern": "yyyy-MM-dd".
     *               <code>true_value</code> supported. like "true_value": "1" which means character "1" equals true.
     *               For the Boolean-Type cell value, both "true" and "1" are represent {@code true} default.
     * @see #setCellValue(Cell, CellType, Object, Map)
     * @throws Exception
     */
    public static void fillData(Sheet sheet, int firstRow, ArrayList<String> properties, List<?> dataList, CellStyle cellStyle, Map<String, Object> extras) throws Exception{
        if(dataList == null || dataList.isEmpty()) return;
        if(properties == null || properties.isEmpty()) return;
        for(Object data:dataList){
            Class<?> clz = data.getClass();
            Row row = sheet.createRow(firstRow++);
            int columnIndex = 0;
            int serialNum = 1;
            for(String property:properties){    //Iterate properties, and set value.
                Cell cell = row.createCell(columnIndex++);    //create a new cell, and specify the cell type.
                cell.setCellStyle(cellStyle);
                if(property.equals(DocumentBean.SERIAL_PROPERTY_NAME)){// set serial number
                    cell.setCellValue(serialNum++);
                    continue;
                }
                String firstLetter = StringUtils.left(property, 1);
                if (StringUtils.isBlank(firstLetter)) continue; // bean property is empty, continue.
                // get the getXxx method.
                Method method = clz.getMethod(property.replaceFirst(firstLetter, "get" + firstLetter.toUpperCase()));
                if(method != null){
                    /**
                     * Attention: setCellType not work well. Do not use it.
                     * See bug 59791.
                     */
                    //cell.setCellType(ExcelDocument.getCellType(field.getType()));
                    Field field = clz.getDeclaredField(property);
                    //invoke getXxx method, and set the value to cell.
                    setCellValue(cell, ExcelDocument.getCellType(field.getType()), method.invoke(data), extras);
                }
            }
        }
    }
    
    /**
     * According to the data bean properties, set a data bean property values column by column.
     * It is a very useful for importing work.
     * @author liu.weihao
     * @date 2016-11-22 
     * @param row   a excel row contained data. Do nothing if the row is <code>null</code>.
     * @param bean   a java bean extends {@code DocumentBean}.  Do nothing if it is <code>null</code>.
     * @param properties    a list of data bean properties,  column by column correspondence.
     *                     Also can set some special properties, like {@code DocumentBean.TEMP_PROPERTY_NAME}.
     *                      Do nothing if it is <code>null</code> or empty.
     * @param offset Zero-based offset from the beginning of cells. 0 means no offset. 
     * Do nothing when the offset equals or exceed the index of the last cell contained in this row. 
     * @param extras    Customized data by K-V.
     *                  <code>date_pattern</code> supported currently.
     *                  <code>true_value</code> supported. like "true_value": "yes" which means character "yes" equals true.
     *                  For the Boolean-Type cell value, both "true" and "1" are represent {@code true} default.
     * @param skips The index of cells. Skipping specified cells.
     * @throws Exception    field not found, method not found, illegalArgument, etc
     */
    public static void copyPropertiesByRow(Row row, DocumentBean bean, ArrayList<String> properties, int offset, Map<String, Object> extras, Integer... skips) throws Exception{
        if(row == null || bean == null || properties == null || properties.size() <= 0) return;
        if(extras == null)  extras = new HashMap<String, Object>();
        if(skips == null)   skips = new Integer[]{};
        if(offset >= row.getLastCellNum())   return;
        List<String> tempDataList = new ArrayList<String>();
        List<Integer> skipList = Arrays.asList(skips);
        Class<?> model = bean.getClass();
        int i = 0;

        for (int index = offset, cellNum = row.getLastCellNum(); index < cellNum; index++) {
            if (skipList.contains(index)) continue;   //matched in skip list, continue.
            Cell cell = row.getCell(index); //get current cell.
            String property = properties.get(i++);  //get current property name.

            //  add document data into tempDataList and continue
            if (property.equalsIgnoreCase(DocumentBean.TEMP_PROPERTY_NAME)) {
                tempDataList.add(getCellValue(cell).toString());
                continue;
            }
            String firstLetter = StringUtils.left(property, 1);
            if (StringUtils.isBlank(firstLetter)) continue; // bean property is empty, continue.
            Field field = model.getDeclaredField(property);
            Method method = model.getMethod(    //get the setXxx method.
                    property.replaceFirst(firstLetter, "set" + firstLetter.toUpperCase(Locale.ENGLISH)),
                    field.getType());
            if (method != null) {
                method.setAccessible(true); //make it accessible.
                Class<?>[] parameterTypes = method.getParameterTypes(); // get the parameter(s) of setXxx method.
                if (parameterTypes.length > 0) {
                    Class<?> type = parameterTypes[0];  //Usually, get the first parameter type.
                    Object value = getCellValue(cell);
                    if (!StringUtils.isBlank(value.toString())) {
                        invoke(bean, method, type, value, extras);  //invoke setXxx method
                    }
                }
            }
        }
        bean.setTempDataList(tempDataList); // set tempDataList.
    }

}
