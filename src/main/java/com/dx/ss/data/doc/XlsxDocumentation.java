/*
 * 版权所有(C) 浙江天搜科技股份有限公司2016-2020
 * Copyright 2016-2020 Zhejiang Tsou Technology Co., Ltd.
 *  
 * This software is the confidential and proprietary information of
 * Zhejiang Tsou Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Zhejiang Tsou
 */
package com.dx.ss.data.doc;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;

import com.dx.ss.data.enums.ErrCodeEnums;
import com.dx.ss.data.exception.ResourceException;

/**
 * Documentation for Excel files(Excel-2007)
 * @ClassName: XlsxDocumentation
 * @version V1.0
 * @date 2016-11-10
 * @author liu.weihao 
 */
public class XlsxDocumentation extends ExcelDocument {

    public XlsxDocumentation() {  }

    @Override
    public Workbook workbook() throws ResourceException, IOException {
        Resource resource = super.getDataResource();
        if (resource == null)
            throw new ResourceException(
                    ErrCodeEnums.ERR_UNKNOWN_RESOURCE.getDetail(),
                    ErrCodeEnums.ERR_UNKNOWN_RESOURCE.getCode());
        return new XSSFWorkbook(resource.getInputStream());
    }

    @Override
    public Workbook newWorkbook() {
        return new XSSFWorkbook();
    }
}
