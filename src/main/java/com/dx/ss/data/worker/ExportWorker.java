package com.dx.ss.data.worker;

import com.dx.ss.data.holder.DocumentDataHolder;

public class ExportWorker extends DocumentWorker {
    
    private DocumentDataHolder data;
    
    public DocumentDataHolder getData() {
        return data;
    }

    public void setData(DocumentDataHolder data) {
        this.data = data;
    }
}
