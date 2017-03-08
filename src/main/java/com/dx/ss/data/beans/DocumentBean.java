package com.dx.ss.data.beans;

import java.util.ArrayList;
import java.util.List;

/** 
 * Super class of document data bean.
 * @ClassName: DocumentBean 
 * @version V1.0  
 * @date 2016-11-21 
 * @author liu.weihao
 * @see com.tsou.community.data.holder.DocumentDataHolder
 */
public abstract class DocumentBean {

    /** It means the column represent serial number. */
    public static final String SERIAL_PROPERTY_NAME = "serial";
    
    /** It means the data of this column will be added into {@code tempDataList} */
    public static String TEMP_PROPERTY_NAME = "tmp";
    
    /**
     * Inconvenient to deal directly with the data in document.
     * i.e. The {@code gender} property is a {@code Integer} data(1-man, 2-woman),
     * but it is expressed as a "man"/"woman" {@code String} in document. 
     * This list hold data temporarily, and do something in another place. 
     * Since {@code String} type of data appears more often, the temporarily data list 
     * is {@code String} type list.
     */
    private List<String> tempDataList;
    
    /**
     * <p>Create a {@code ArrayList<String>} contained the properties in
     * current bean.</p>
     * <strong>Pay Attention: </strong>
     * One to one correspondence by document columns.
     * Property named {@value #TEMP_PROPERTY_NAME} means adding 
     * document data into {@code tempDataList}.
     * @author liu.weihao
     * @date 2016-11-21 
     * @return  a {@code ArrayList<String>} contained the properties
     * @see #getTempDataList()
     */
    public abstract ArrayList<String> propertiesAssembly();

    
    public List<String> getTempDataList() {
        return tempDataList;
    }

    public void setTempDataList(List<String> tempDataList) {
        this.tempDataList = tempDataList;
    }
}
