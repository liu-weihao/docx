package com.dx.ss.data.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import com.dx.ss.data.enums.ErrCodeEnums;
import com.dx.ss.data.exception.ResourceException;
import com.dx.ss.data.util.DocumentUtil;


/** <p class="detail">
 * Super abstract class of any type of document, including some base informations. 
 * </p>
 * @ClassName: Documentation 
 * @version V1.0  
 * @date 2016-11-10 
 * @author liu.weihao
 */
public abstract class Documentation {

    public static final String EXCEL_FILE_TYPE = "excel";
    
    public static final String CSV_FILE_TYPE = "csv";
    
	/** .xlsx document type after Excel-2007 */
	public static final String XLSX = "xlsx";
	
	/** .xls document type in Excel-2003 */
	public static final String XLS = "xls";
	
	/** Comma-Separated Values, a kind of plain text without 
	 * any formulations, formations, organized row by row. 
	 */
	public static final String CSV = "csv";
	
	/** The amount of document type supported. */
	private static final int ALLOWED_DOC_TYPE_NUM = 3;
	
	/**
	 * The amount of document type group supported.
	 * xls and xlsx for Excel.
	 * Another is CSV.
	 */
	private static final int ALLOWED_DOC_GROUP_NUM = 2;
	
	/** The list of document type supported, it is convenient to 
	 * check whether the document type input is supported.
	 */
	private static Map<String, String> allowedDocType = new HashMap<String, String>(ALLOWED_DOC_TYPE_NUM);
	
	/** Group by the document file type. */
	private static Map<String, List<String>> allowedDocGroup = new HashMap<String, List<String>>(ALLOWED_DOC_GROUP_NUM);
	
	/** The resource of data, such as FileSystem, ClassPath, Url, etc. */
	private Resource dataResource;
	
	/** The document file name with extension. */
	private String docName;
	
	/** The document file extension without dote. 
	 * The dote will be removed if you pass in.
	 * It must be settled by subclass. if not, ext will settled according to file name.
	 * otherwise, will throw a exception.  
	 */
	private String ext;
	
	
	static {
		allowedDocType.put(XLS, EXCEL_FILE_TYPE);
		allowedDocType.put(XLSX, EXCEL_FILE_TYPE);
		allowedDocType.put(CSV, CSV_FILE_TYPE);
		setFileTypeGroup();
	}

	public Resource getDataResource() {
		return dataResource;
	}

	public void setDataResource(Resource dataResource) {
		this.dataResource = dataResource;
	}
	
	public String getDocName() {
		if(StringUtils.isBlank(this.docName) && dataResource != null) {
			String docName = dataResource.getFilename();
			this.setDocName(docName);
			return docName;
		}
		return docName;
	}
	
	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getExt() {
		String tmp = null;
		if(StringUtils.isBlank(this.ext)){
			if(dataResource != null){
			    tmp = DocumentUtil.getDocumentExt(dataResource);
			}else if(!StringUtils.isBlank(this.docName)){
				tmp = DocumentUtil.getDocumentExt(this.docName);
			}
			if(!StringUtils.isBlank(tmp))	this.setExt(tmp);
			return tmp;
		}
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * Check whether all of documents are allowed and are the same type.
	 * @author liu.weihao
	 * @date 2016-11-16 
	 * @param paths    Document resource path.
	 * @throws ResourceException   If document type is not allowed or not the same type.
	 */
	public static void checkFileType(String[] paths) throws ResourceException{
	    if(paths == null || paths.length <= 0)  return;
	    String extension = DocumentUtil.getDocumentExt(paths[0]);
	    if(StringUtils.isBlank(extension))
			throw new ResourceException(
					ErrCodeEnums.ERR_UNKNOWN_DOC_TYPE.getDetail() + extension,
					ErrCodeEnums.ERR_UNKNOWN_DOC_TYPE.getCode());

	    if(!validExtension(extension))
	        throw new ResourceException(
                ErrCodeEnums.ERR_UNKNOWN_DOC_TYPE.getDetail() + extension, 
                ErrCodeEnums.ERR_UNKNOWN_DOC_TYPE.getCode());
	    if(paths.length == 1) return;
	    for (int i = 1, len = paths.length; i < len; i++) {
	        String tmp = DocumentUtil.getDocumentExt(paths[i]);
	        if(!validExtension(tmp))
	            throw new ResourceException(
	                ErrCodeEnums.ERR_UNKNOWN_DOC_TYPE.getDetail() + tmp, 
	                ErrCodeEnums.ERR_UNKNOWN_DOC_TYPE.getCode());
	        
            if(!extension.equalsIgnoreCase(tmp) && !allowedDocType.get(extension).equals(allowedDocType.get(tmp)))
                throw new ResourceException(
                    ErrCodeEnums.ERR_UNKNOWN_DOC_MISMATCH.getDetail(), 
                    ErrCodeEnums.ERR_UNKNOWN_DOC_MISMATCH.getCode());
	    }
	}

	/**
	 * Group document file type.
	 * @author liu.weihao
	 * @date 2016-11-16
	 */
    private static void setFileTypeGroup() {
        List<String> excelExtList = new ArrayList<String>();
        excelExtList.add(XLSX);
        excelExtList.add(XLS);
        allowedDocGroup.put(EXCEL_FILE_TYPE, excelExtList);
        
        List<String> csvExtList = new ArrayList<String>();
        csvExtList.add(CSV);
        allowedDocGroup.put(CSV_FILE_TYPE, csvExtList);
    }
    
    /**
     * check whether the extension is allowed.
     * @author liu.weihao
     * @date 2016-11-16 
     * @param ext   The extension to be checked.
     * @return  true if the extension is allowed.
     */
    private static boolean validExtension(String ext){
        return allowedDocType.containsKey(ext);
    }
}
