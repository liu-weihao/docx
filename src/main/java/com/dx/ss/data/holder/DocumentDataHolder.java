package com.dx.ss.data.holder;

import java.util.List;

import com.dx.ss.data.beans.DocumentBean;
import com.dx.ss.data.doc.Documentation;


/** <p class="detail">
 * Hold the data parsing from document.
 * </p>
 * @ClassName: DocumentDataHolder 
 * @version V1.0  
 * @date 2016-11-10 
 * @author liu.weihao
 * Copyright 2016 tsou.com, Inc. All rights reserved
 */
public abstract class DocumentDataHolder {
	
	/** The document of data resource. */
	private Documentation documentation;
	
	/** The parsing result data list from document. */
	private List<? extends DocumentBean> dataList;
	
	public DocumentDataHolder() {
		super();
	}

	public DocumentDataHolder(Documentation documentation, List<? extends DocumentBean> dataList) {
		super();
		this.documentation = documentation;
		this.dataList = dataList;
	}

	public Documentation getDocumentation() {
		return documentation;
	}

	public void setDocumentation(Documentation documentation) {
		this.documentation = documentation;
	}

	public List<? extends DocumentBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<? extends DocumentBean> dataList) {
		this.dataList = dataList;
	}

}
