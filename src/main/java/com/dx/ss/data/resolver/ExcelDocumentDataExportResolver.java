package com.dx.ss.data.resolver;

import com.dx.ss.data.doc.Documentation;
import com.dx.ss.data.enums.WorkerTypeEnums;
import com.dx.ss.data.holder.ExcelDocumentDataHolder;
import com.dx.ss.data.worker.DocumentWorker;
import com.dx.ss.data.worker.ExportWorker;

/** 
 * Super resolver class for all of excel exporting.
 * @ClassName: ExcelDocumentDataExportResolver 
 * @version V1.0  
 * @date 2016-11-17 
 * @author liu.weihao
 */
public abstract class ExcelDocumentDataExportResolver<T extends Documentation> implements DocumentDataResolver<T> {

    public T resolve(DocumentWorker worker) {
        String workerType = worker.getWorkerType();
        if(workerType.equals(WorkerTypeEnums.EXPORT_WORKER.getType()) && worker instanceof ExportWorker){
            ExportWorker exportWorker = (ExportWorker) worker;
            return this.resolveByExport((ExcelDocumentDataHolder) exportWorker.getData());
        }
        return null;
    }

    /**
     * Subclass override data-export method
     * @author liu.weihao
     * @date 2016-11-17 
     * @param dataHolder  The excel document data holder handle.
     * @return  The data type resolved from document data holder.
     */
    public abstract T resolveByExport(ExcelDocumentDataHolder dataHolder);

}
