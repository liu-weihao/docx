package com.dx.ss.data.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Error code definitions. It will be used in some customized exception.
 * @ClassName: ErrCodeEnums 
 * @version V1.0  
 * @date 2016-10-21 
 * @author liu.weihao
 * @see com.dx.ss.data.exception.ExcelDocumentException
 * @see com.dx.ss.data.exception.ResourceException
 */
public enum ErrCodeEnums {
	
	ERR_UNKNOWN_RESOURCE("err_4004", "Err: Resource location not settled."),
	ERR_UNKNOWN_DATA_RESOURCE("err_4014", "Err: Data holder not settled."),
	ERR_UNKNOWN_DOC_TYPE("err_4010", "Err: Document type not find: "),
	ERR_UNKNOWN_DOC_MISMATCH("err_4020", "Err: All of documents are not the same type. "),
	
	ERR_UNKNOWN_WORKBOOK("err_13004", "Err: Excel workbook not specified correctly."),
	ERR_UNKNOWN_SHEET("err_13005", "Err: Excel sheet not specified correctly."),
	ERR_UNKNOWN_ROW("err_13006", "Err: Excel row not specified correctly."),
	
	ERR_UNKNOWN_WORKER("err_25001", "Err: The document worker not specified correctly."),
	ERR_UNKNOWN_WORKER_TYPE("err_25002", "Err: The document worker type not specified correctly."),
	
	ERR_UNKNOWN_DATA_RESOLVER("err_31002", "Err: The document data resolver not specified correctly."),
	ERR_UNKNOWN_DOCUMENT_PARSER("err_32002", "Err: The document parser not specified correctly.");

    private String code;

    private String detail;

    ErrCodeEnums(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public static ErrCodeEnums getEnumByCode(String code) {
        for (ErrCodeEnums errEnum : ErrCodeEnums.values()) {
            if (!StringUtils.isBlank(code) && code.equals(errEnum.getCode())) {
                return errEnum;
            }
        }
        return null;
    }
	
}
