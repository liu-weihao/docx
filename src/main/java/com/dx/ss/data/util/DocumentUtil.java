package com.dx.ss.data.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

public class DocumentUtil {

    public static final String DOT = ".";
    
    /**
     * <p>Gets the extension of a path. Some of types like 
     * file-system, Url, Uri will be supported.</p>
     * <p>A <code>null</code> String will return <code>null</code>.
     * An empty ("") String will also return null.</p>
     * @author liu.weihao
     * @date 2016-11-17 
     * @param path  the path handle.
     * @return  the extension.
     * @see #getFileExt(File)
     */
    public static String getDocumentExt(String path){
        if(StringUtils.isBlank(path))   return null;
        return getFileExt(new File(path));
    }
    
    /**
     * <p>Gets a file extension from a File handle.</p>
     * <p>A <code>null</code> File will return <code>null</code>.</p>
     * <p>A <code>Directory</code> will return <code>null</code>.
     * A file without extension will also  return <code>null</code>.</p>
     * @author liu.weihao
     * @date 2016-11-17 
     * @param file  a File handle.
     * @return  The file extension.
     */
    public static String getFileExt(File file){
        if(file == null)    return null;
        if(file.isDirectory())    return null;
        String fileName = file.getName();
        if(StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, DOT))   return null;
        return StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, DOT) + 1).toLowerCase();
    }
    
    /**
     * <p>Gets a file extension from {@link Resource}.</p>
     * <p>A <code>null</code> Resource will return <code>null</code>.</p>
     * The {@link IOException} will be caught and return <code>null</code> 
     * If the resource cannot be resolved as absolute file path, 
     * i.e. if the resource is not available in a file system
     * @author liu.weihao
     * @date 2016-11-17 
     * @param res   a Resource handle.
     * @return  the extension.
     * @see #getFileExt(File)
     */
    public static String getDocumentExt(Resource res){
        if(res == null)    return null;
        try {
            return getFileExt(res.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
