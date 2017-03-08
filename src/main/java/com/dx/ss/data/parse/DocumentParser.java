package com.dx.ss.data.parse;

import com.dx.ss.data.doc.Documentation;
import com.dx.ss.data.factory.DocumentWorkerFactory;
import com.dx.ss.data.holder.DocumentDataHolder;
import com.dx.ss.data.resolver.DocumentDataResolver;

/** 
 * Parse Document
 * @ClassName: DocumentParser 
 * @version V1.0  
 * @date 2016-11-10 
 * @author liu.weihao
 */
public abstract class DocumentParser {

    /** At least one work thread in pool. */
    public static final int MIN_POOL_SIZE = 1;
    
    /** At most ten work threads in pool. */
    public static final int MAX_POOL_SIZE = 10;
    
    /** Data resolver for importing. */
    private DocumentDataResolver<? extends DocumentDataHolder> importDataResolver;
    
    /** Data resolver for exporting. */
    private DocumentDataResolver<? extends Documentation> exportDataResolver;
    
    /** A factory to generate document worker. */
    private DocumentWorkerFactory workerFactory;
    
	/**
	 * Template method which can be overridden to do something before parsing document.
	 * @author liu.weihao
	 * @date 2016-11-14
	 */
	public void before(){
		
	}
	
	/**
	 * doing data-import works from a document.
	 * @author liu.weihao
	 * @date 2016-11-14 
	 * @param documentation	The document to be parsed.
	 * @return The parsed result data holder.
	 */
	public abstract DocumentDataHolder importParser(Documentation documentation);
	
	
	/**
	 * doing data-export works from a document data holder.
	 * @author liu.weihao
	 * @date 2016-11-17 
	 * @param dataHolder   The excel document data holder handle. 
	 * @return the exported document handle.
	 */
	public abstract Documentation exportParser(DocumentDataHolder dataHolder);
	
	/**
	 * Template method which can be overridden to do something after parsing document completed.
	 * @author liu.weihao
	 * @date 2016-11-14 
	 * @param dataHolder	The pasred result data holder.
	 */
	public void after(DocumentDataHolder dataHolder){
		
	}
	
	public DocumentDataResolver<? extends DocumentDataHolder> getImportDataResolver() {
        return importDataResolver;
    }

    public void setImportDataResolver(DocumentDataResolver<? extends DocumentDataHolder> dataResolver) {
        this.importDataResolver = dataResolver;
    }
    
    public DocumentDataResolver<? extends Documentation> getExportDataResolver() {
        return exportDataResolver;
    }

    public void setExportDataResolver(DocumentDataResolver<? extends Documentation> exportDataResolver) {
        this.exportDataResolver = exportDataResolver;
    }

    public DocumentWorkerFactory getWorkerFactory() {
        return workerFactory;
    }

    public void setWorkerFactory(DocumentWorkerFactory workerFactory) {
        this.workerFactory = workerFactory;
    }
}
