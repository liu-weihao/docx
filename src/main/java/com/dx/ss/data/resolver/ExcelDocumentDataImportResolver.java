package com.dx.ss.data.resolver;

import com.dx.ss.data.doc.ExcelDocument;
import com.dx.ss.data.enums.WorkerTypeEnums;
import com.dx.ss.data.holder.ExcelDocumentDataHolder;
import com.dx.ss.data.worker.DocumentWorker;
import com.dx.ss.data.worker.ImportWorker;

/**
 * Super resolver class for all of excel importing.
 * @ClassName: ExcelDocumentDataImportResolver 
 * @version V1.0  
 * @date 2016-11-17 
 * @author liu.weihao
 * @param <T>   The data type resolved from excel document.
 */
public abstract class ExcelDocumentDataImportResolver<T extends ExcelDocumentDataHolder> implements DocumentDataResolver<T> {

    public T resolve(DocumentWorker worker) {
        
        String workerType = worker.getWorkerType();
        if(workerType.equals(WorkerTypeEnums.IMPORT_WORKER.getType())){
            if(worker instanceof ImportWorker){
                ImportWorker importWorker = (ImportWorker) worker;
                return this.resolveByImport((ExcelDocument) importWorker.getSource());
            }
        }
        return null;
    }

    /**
     * Subclass override data-import method
     * @author liu.weihao
     * @date 2016-11-17 
     * @param document  The document object.
     * @return  The data type resolved from excel document.
     */
    public abstract T resolveByImport(ExcelDocument document);
    
}
