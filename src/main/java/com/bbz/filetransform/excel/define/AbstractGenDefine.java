package com.bbz.filetransform.excel.define;

import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.excel.AbstractGen;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * user         LIUKUN
 * time         2015-3-5 14:20
 *
 * 通过excel生成常量定义的相关内容
 */

abstract class AbstractGenDefine extends AbstractGen{



    protected AbstractGenDefine( String fullExcelPath  ){
        super(fullExcelPath);
    }


    protected AbstractGenDefine(String className, String packageName, Sheet sheet, List<ExcelColumn> excelColumns){
        super( className,packageName,sheet,excelColumns);
    }
    /**
     * 读取excel的一行数据,并生成对应的内容
     * @param row   excel的一行数据
     * @return      相应的字符串
     */
    abstract String genRowContent( Row row );
}

