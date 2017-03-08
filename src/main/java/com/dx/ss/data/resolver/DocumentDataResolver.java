package com.dx.ss.data.resolver;

import com.dx.ss.data.doc.Documentation;
import com.dx.ss.data.holder.DocumentDataHolder;
import com.dx.ss.data.worker.DocumentWorker;
import com.dx.ss.data.worker.ExportWorker;
import com.dx.ss.data.worker.ImportWorker;

/**
 * This is intersection between {@link Documentation} and 
 * {@link DocumentDataHolder}. It is usually used by import/export
 * workers of documents
 * @ClassName: DocumentDataResolver 
 * @version V1.0  
 * @date 2016-11-17 
 * @author liu.weihao
 * @param <R>   The data type resolved from document.
 */
public interface DocumentDataResolver<R> {

	/**
	 * Do resolve by worker, including import and export.
	 * @author liu.weihao
	 * @date 2016-11-15 
	 * @param worker   specify a type of worker.
	 * @return Resolve results
	 * @see ImportWorker
	 * @see ExportWorker
	 */
	public R resolve(DocumentWorker worker);
	
}
