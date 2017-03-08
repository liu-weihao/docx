package com.dx.ss.data.holder;

import java.util.List;

import com.dx.ss.data.beans.DocumentBean;
import com.dx.ss.data.doc.Documentation;

public class ExcelDocumentDataHolder extends DocumentDataHolder {
    
    private Exception ex;

	public ExcelDocumentDataHolder() {
		super();
	}

	public ExcelDocumentDataHolder(Documentation documentation, List<? extends DocumentBean> dataList) {
		super(documentation, dataList);
	}

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

}
