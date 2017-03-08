package com.dx.ss.data.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;

import com.dx.ss.data.doc.CSVDocument;
import com.dx.ss.data.doc.Documentation;
import com.dx.ss.data.doc.XlsDocumentation;
import com.dx.ss.data.doc.XlsxDocumentation;
import com.dx.ss.data.util.DocumentUtil;

/** 
 * A simple factory for all of document types.
 * @ClassName: DocumentFactory 
 * @version V1.0  
 * @date 2016-11-17 
 * @author liu.weihao
 */
public class DocumentSimpleFactory {

    public static Map<String, Class<?>> documentTypeClazz = new HashMap<String, Class<?>>();
    
    static {
        documentTypeClazz.put(Documentation.XLS, XlsDocumentation.class);
        documentTypeClazz.put(Documentation.XLSX, XlsxDocumentation.class);
        documentTypeClazz.put(Documentation.CSV, CSVDocument.class);
    }
    
    public static Documentation createDocument(Resource resource){
        
        if(resource == null)   return null;
        
        String ext = DocumentUtil.getDocumentExt(resource);
        
        Class<?> documentClazz = documentTypeClazz.get(ext);
        try {
            if(documentClazz != null){
                return (Documentation) documentClazz.getConstructor().newInstance();
            }
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
}
