/*
 * 版权所有(C) 浙江天搜科技股份有限公司2016-2020
 * Copyright 2016-2020 Zhejiang Tsou Technology Co., Ltd.
 *  
 * This software is the confidential and proprietary information of
 * Zhejiang Tsou Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Zhejiang Tsou
 */
package com.dx.ss.data.exception;

/**
 * <p class="detail">
 * This is the root interface of the exception hierarchy defined
 * for the Connector architecture.
 * The ResourceException provides the following information:
 * <UL>
 *   <LI> A excel document adapter vendor specific string describing the error.
 *        This string is a standard Java exception message and is available
 *        through getMessage() method.
 *   <LI> resource adapter vendor specific error code.
 * </UL>
 * </p>
 * @ClassName: ExcelDocumentException
 * @version V1.0
 * @date 2016-11-11
 * @author liu.weihao 
 * Copyright 2016 tsou.com, Inc. All rights reserved
 */
public class ExcelDocumentException extends Exception {

	private static final long serialVersionUID = -4792013005759366037L;

	/** Vendor specific error code */
	private String errorCode;

	/**
	 * Constructs a new instance with null as its detail message.
	 */
	public ExcelDocumentException() {
		super();
	}

	/**
	 * Constructs a new instance with the specified detail message.
	 * 
	 * @param message the detail message.
	 */
	public ExcelDocumentException(String message) {
		super(message);
	}

	/**
	 * Create a new throwable with the specified message and error code.
	 * 
	 * @param message a description of the exception.
	 * @param errorCode a string specifying the vendor specific error code.
	 */
	public ExcelDocumentException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Get the error code.
	 * 
	 * @return the error code.
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Set the error code.
	 * 
	 * @param errorCode the error code.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
     * Returns a detailed message string describing this exception.
     * @return a detailed message string.
     */
    public String getMessage() {
		String msg = super.getMessage();
		String ec = getErrorCode();
		if ((msg == null) && (ec == null)) {
		    return null;
		}
		if ((msg != null) && (ec != null)) {
		    return (msg + ", error code: " + ec);
		}
		return ((msg != null) ? msg : ("error code: " + ec));
    }
}
