package com.dx.ss.data.parse;

import com.dx.ss.data.doc.Documentation;
import com.dx.ss.data.enums.WorkerTypeEnums;
import com.dx.ss.data.factory.DocumentWorkerFactory;
import com.dx.ss.data.factory.ExportWorkerFactory;
import com.dx.ss.data.factory.ImportWorkerFactory;
import com.dx.ss.data.holder.DocumentDataHolder;
import com.dx.ss.data.holder.ExcelDocumentDataHolder;
import com.dx.ss.data.resolver.DocumentDataResolver;
import com.dx.ss.data.worker.DocumentWorker;
import com.dx.ss.data.worker.ExportWorker;
import com.dx.ss.data.worker.ImportWorker;

public class ExcelDocumentParser extends DocumentParser {

	private static ExcelDocumentParser parser;
	
	static{
	    parser = new ExcelDocumentParser();
	}
	
	private ExcelDocumentParser(){  }

    public static ExcelDocumentParser getParserInstance(){ return parser; }
    
	@Override
	public DocumentDataHolder importParser(Documentation document) {
	    DocumentDataResolver<?> importDataResolver = getImportDataResolver();
	    DocumentWorkerFactory workerFactory = getWorkerFactory();
	    DocumentDataHolder dataHolder = new ExcelDocumentDataHolder();
	    if(importDataResolver != null){
    		ImportWorker worker = (ImportWorker) generateWorker(workerFactory, WorkerTypeEnums.IMPORT_WORKER);
    		worker.setSource(document);
    		dataHolder = (DocumentDataHolder) importDataResolver.resolve(worker);
	    }
	    return dataHolder;
	}

    @Override
    public Documentation exportParser(DocumentDataHolder data) {
        DocumentDataResolver<?> exportDataResolver = getExportDataResolver();
        DocumentWorkerFactory workerFactory = getWorkerFactory();
        if(exportDataResolver != null){
            ExportWorker worker = (ExportWorker) generateWorker(workerFactory, WorkerTypeEnums.EXPORT_WORKER);
            worker.setData(data);
            return (Documentation) exportDataResolver.resolve(worker);
        }
        return null;
    }
    
    
    private DocumentWorker  generateWorker(DocumentWorkerFactory workerFactory, WorkerTypeEnums workerType){
        DocumentWorker worker = null;
        if(workerFactory != null) {
            worker = workerFactory.createWorker();
            worker.setWorkerType(workerType.getType());
            return worker;
        }
        if(workerType.equals(WorkerTypeEnums.IMPORT_WORKER)){
            super.setWorkerFactory(new ImportWorkerFactory());
        }else if(workerType.equals(WorkerTypeEnums.EXPORT_WORKER)){
            super.setWorkerFactory(new ExportWorkerFactory());
        }
        worker =  super.getWorkerFactory().createWorker();
        worker.setWorkerType(workerType.getType());
        return worker;
    }
    
    public DocumentWorkerFactory getWorkerFactory(WorkerTypeEnums workerType) {
        if(workerType.equals(WorkerTypeEnums.IMPORT_WORKER)){
            return new ImportWorkerFactory();
        }else if(workerType.equals(WorkerTypeEnums.EXPORT_WORKER)){
            return new ExportWorkerFactory();
        }
        return super.getWorkerFactory();
    }
}
