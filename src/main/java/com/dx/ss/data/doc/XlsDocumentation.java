package com.dx.ss.data.doc;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;

import com.dx.ss.data.enums.ErrCodeEnums;
import com.dx.ss.data.exception.ResourceException;

/**
 * Documentation for Excel files(Excel-2003).
 * @ClassName: XlsDocumentation
 * @version V1.0
 * @date 2016-11-10
 * @author liu.weihao 
 */
public class XlsDocumentation extends ExcelDocument {

    
    public XlsDocumentation() { }

    @Override
    public Workbook workbook() throws ResourceException, IOException {
        Resource resource = super.getDataResource();
        if (resource == null)
            throw new ResourceException(
                    ErrCodeEnums.ERR_UNKNOWN_RESOURCE.getDetail(),
                    ErrCodeEnums.ERR_UNKNOWN_RESOURCE.getCode());
        return new HSSFWorkbook(resource.getInputStream());
    }

    @Override
    public Workbook newWorkbook() {
        return new HSSFWorkbook();
    }

}
