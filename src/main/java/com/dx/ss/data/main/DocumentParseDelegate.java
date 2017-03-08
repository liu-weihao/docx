package com.dx.ss.data.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.dx.ss.data.doc.Documentation;
import com.dx.ss.data.enums.ErrCodeEnums;
import com.dx.ss.data.enums.WorkerTypeEnums;
import com.dx.ss.data.exception.ResourceException;
import com.dx.ss.data.factory.DocumentSimpleFactory;
import com.dx.ss.data.holder.DocumentDataHolder;
import com.dx.ss.data.parse.DocumentParser;
import com.dx.ss.data.resolver.DocumentDataResolver;

/**
 * Delegate for document parsing.
 * Use the {@link Builder} to build a delegation.
 * @ClassName: DocumentParseDelegate 
 * @version V1.0  
 * @date 2016-11-17 
 * @author liu.weihao
 */
public class DocumentParseDelegate {

    private Resource[] configResources;
    
    /** Required, if worker type is import. It means the file paths to be parsed. */
    private String[] configLocations;
    
    /** Required, if worker type is export. It holds all of document data to be exported. */
    private DocumentDataHolder dataHolder;
    
    /** Required to Specify the document parser. */
    private DocumentParser parser;
    
    /** Required if worker type is importing.  */
    private DocumentDataResolver<? extends DocumentDataHolder> importResolver;
    
    /** Required if worker type is exporting. */
    private DocumentDataResolver<? extends Documentation> exportResolver;
    
    /** required parameter  */
    private WorkerTypeEnums parseWorkType;
    
    /**
     * Constructor is private. A new delegate is created by builder.
     * @param builder   
     */
    private DocumentParseDelegate(Builder builder){
        this.configLocations = builder.configLocations;
        this.dataHolder = builder.dataHolder;
        this.parser = builder.parser;
        this.importResolver = builder.importResolver;
        this.exportResolver = builder.exportResolver;
        this.parseWorkType = builder.parseWorkType;
        
        validParms();   //check the parameters are valid or not.
        
        if(WorkerTypeEnums.IMPORT_WORKER.equals(parseWorkType)){
            this.parser.setImportDataResolver(this.importResolver);
        }else if(WorkerTypeEnums.EXPORT_WORKER.equals(parseWorkType)){
            this.parser.setExportDataResolver(this.exportResolver);
        }
    }
    
    /**
     * do parse.
     * @author liu.weihao
     * @date 2016-11-16 
     * @return
     * @throws ResourceException
     * @throws IOException
     */
    public List<?> parse() throws ResourceException, IOException{
        
        List<Object> dataList = new ArrayList<Object>();
        if(WorkerTypeEnums.IMPORT_WORKER.equals(parseWorkType)){
            validLocations();   //check whether the locations is allowed.
            
            prepare();    //prepare document resources.
            
            for(Resource resource: configResources){
                Documentation documentation = DocumentSimpleFactory.createDocument(resource);
                documentation.setDataResource(resource);
                dataList.add(parser.importParser(documentation));
            }
        }else if(WorkerTypeEnums.EXPORT_WORKER.equals(parseWorkType)){
            dataList.add(parser.exportParser(dataHolder));
        }
        return dataList;
    }

    /**
     * Transfer locations to resources before doing parse. 
     * @author liu.weihao
     * @date 2016-11-16 
     */
    private void prepare(){
        if(configLocations == null || configLocations.length == 0)  return;
        this.configResources = new Resource[configLocations.length];
        for (int i = 0, len = configLocations.length; i < len; i++) {
            this.configResources[i] = new FileSystemResource(configLocations[i]);
        }
    }
    
    /**
     * Check whether the locations are the same type.
     * eg: If the type of document is Excel, the extension in locations is xls or xlsx.
     * @author liu.weihao
     * @throws ResourceException if document locations are not valid.
     * @date 2016-11-16
     */
    private void validLocations() throws ResourceException{
        Documentation.checkFileType(configLocations);
    }
    
    /**
     * Check the parameters are valid or not.
     * @author liu.weihao
     * @date 2016-11-17
     */
    private void validParms(){
        if(parseWorkType == null)   
            throw new IllegalArgumentException(ErrCodeEnums.ERR_UNKNOWN_WORKER_TYPE.getDetail());
        if(parser == null)
            throw new IllegalArgumentException(ErrCodeEnums.ERR_UNKNOWN_DOCUMENT_PARSER.getDetail());
        if(WorkerTypeEnums.IMPORT_WORKER.equals(parseWorkType)){
            if(configLocations == null || configLocations.length == 0)
                throw new IllegalArgumentException(ErrCodeEnums.ERR_UNKNOWN_RESOURCE.getDetail());
            if(importResolver == null)
                throw new IllegalArgumentException(ErrCodeEnums.ERR_UNKNOWN_DATA_RESOLVER.getDetail());
        }
        if(WorkerTypeEnums.EXPORT_WORKER.equals(parseWorkType)){
            if(dataHolder == null)
                throw new IllegalArgumentException(ErrCodeEnums.ERR_UNKNOWN_DATA_RESOURCE.getDetail());
            if(exportResolver == null)
                throw new IllegalArgumentException(ErrCodeEnums.ERR_UNKNOWN_DATA_RESOLVER.getDetail());
        }
    }
    /**
     * Return an array of Resource objects, containing all of documents to be parsed.
     * @author liu.weihao
     * @date 2016-11-15 
     * @return  an array of Resource objects, or {@code null} if none
     */
    protected Resource[] getConfigResources() {
        return configResources;
    }
    
    public String[] getConfigLocations() {
        return configLocations;
    }

    public DocumentDataHolder getDataHolder() {
        return dataHolder;
    }

    public DocumentParser getParser() {
        return parser;
    }

    public WorkerTypeEnums getParseWorkType() {
        return parseWorkType;
    }
    
    public DocumentDataResolver<? extends DocumentDataHolder> getImportResolver() {
        return importResolver;
    }
    
    public DocumentDataResolver<? extends Documentation> getExportResolver() {
        return exportResolver;
    }

    /**
     * Create a new parse delegate by this builder
     * @ClassName: Builder 
     * @version V1.0  
     * @date 2016-11-16 
     * @author liu.weihao
     */
    public static class Builder {
        
        /** Required, if worker type is import. It means the file paths to be parsed. */
        private String[] configLocations;
        
        /** Required, if worker type is export. It holds all of document data to be exported. */
        private DocumentDataHolder dataHolder;
        
        /** Required to Specify the document parser. */
        private DocumentParser parser;
        
        /** Required if worker type is importing.  */
        private DocumentDataResolver<? extends DocumentDataHolder> importResolver;
        
        /** Required if worker type is exporting. */
        private DocumentDataResolver<? extends Documentation> exportResolver;
        
        /** required parameter  */
        private WorkerTypeEnums parseWorkType;
        
        public Builder(WorkerTypeEnums parseWorkType) {
            super();
            this.parseWorkType = parseWorkType;
        }

        /** Set location of documents.  */
        public Builder locations(String[] configLocations){
            this.configLocations = configLocations;
            return this;
        }
        
        /** Set the document data to be exported. */
        public Builder dataHolder(DocumentDataHolder dataHolder){
            this.dataHolder = dataHolder;
            return this;
        }
        
        /** Set document parser */
        public Builder parser(DocumentParser parser){
            this.parser = parser;
            return this;
        }
        
        /** Set document import-resolver */
        public Builder importResolver(DocumentDataResolver<? extends DocumentDataHolder> importResolver){
            this.importResolver = importResolver;
            return this;
        }
        
        /** Set document export-resolver */
        public Builder exportResolver(DocumentDataResolver<? extends Documentation> exportResolver){
            this.exportResolver = exportResolver;
            return this;
        }
        
        /** Set document resolve worker type. */
        public Builder workerType(WorkerTypeEnums parseWorkType){
            this.parseWorkType = parseWorkType;
            return this;
        }
        
        /** One new Document Parse Delegate built */
        public DocumentParseDelegate build(){
            return new DocumentParseDelegate(this);
        }
    }
}
