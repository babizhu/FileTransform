package com.bbz.filetransform.excel.cfg;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.util.D;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.List;

/**
 * user         LIUKUN
 * time         2015-3-10 15:40
 *
 * cfg中生成java文件的基类
 */

abstract class AbstractGenCfgJava extends AbstractGenCfg{

    protected AbstractGenCfgJava( String className, String packageName, Sheet sheet, List<ExcelColumn> excelColumns ){
        super( className, packageName, sheet, excelColumns );
    }

    protected AbstractGenCfgJava( String fullExcelPath ){
        super( fullExcelPath );
    }


    protected String parseJavaType(ExcelColumn fe){
        String type = fe.getType();
        if( type.equalsIgnoreCase("int") ) {
            return "Integer.parseInt( element.getChildText(\"";
        } else if( type.equalsIgnoreCase( "short" ) ) {
            return "Short.parseShort( element.getChildText(\"";
        } else if( type.equalsIgnoreCase( "float" ) ) {
            return "Float.parseFloat( element.getChildText(\"";
        } else if( type.equalsIgnoreCase( "String" ) ) {
            return "element.getChildText(\"";
        } else if( type.equalsIgnoreCase( "double" ) ) {
            return "Double.parseDouble( element.getChildText(\"";
        } else if( type.equalsIgnoreCase( "byte" ) ) {
            return "Byte.parseByte( element.getChildText(\"";
        } else if( type.equalsIgnoreCase( "char" ) ) {
            return "(char)" + "Integer.parseInt( element.getChildText(\"";
        } else if( type.equalsIgnoreCase( "boolean" ) ) {
            return "Boolean.parseBoolean( element.getChildText(\"";
        } else if( type.equalsIgnoreCase("long") ) {
            return "Long.parseLong( element.getChildText(\"";
        } else {
            return "未知类型";
        }
    }

    @Override
    protected String fileName(){
        return PathCfg.EXCEL_OUTPUT_JAVA_PATH + packageName + File.separator + className + D.JAVA_FILE_SUFFIXES;
    }

    @Override
    protected String genRowContent( Row row ){
        throw new NotImplementedException();
    }

}
