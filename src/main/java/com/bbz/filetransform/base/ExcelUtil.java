package com.bbz.filetransform.base;

import com.bbz.filetransform.excel.ExcelColumn;
import org.apache.poi.ss.usermodel.Cell;

/**
 * user         LIUKUN
 * time         2015-3-5 14:58
 *
 * 读取excel内容的一些小工具
 */

public class ExcelUtil{

    /**
     * 从excel中获取单元格的字符串内容，int和long型的字符串末尾会产生".0"，因此这里也要过滤掉
     * 如果cell为null，数字型（int,long,float,double）返回空”0“，
     * 其余情况返回""(空字符串)
     *
     * float型会在数字后面增加"F"
     *
     * @param cell          单元格
     * @param excelColumn  字段
     * @return      单元格的内容
     */
    public static String getCellStr( Cell cell, ExcelColumn excelColumn ){
        String data = "";
        if( cell == null) {
            if( isNumber( excelColumn ) ) {
                return "0";

            }
            return data;
        }


        cell.setCellType( Cell.CELL_TYPE_STRING );
        data = cell.getStringCellValue().trim();
        if( fieldIsIntOrLong( excelColumn ) ) {

            int pointPos = data.indexOf( '.' );
            if( pointPos != -1 ) {
                data = data.substring( 0, pointPos );//去掉末尾的.0
            }
        }
        if( fieldIsFloat( excelColumn )){
            data += "F";
        }
        if(data.equals( "" ) && isNumber( excelColumn ) ){
            data = "0";
        }
        return data;

    }

    public static boolean fieldIsIntOrLong( ExcelColumn excelColumn ){
        return excelColumn.getType().equalsIgnoreCase( "int" ) || excelColumn.getType().equalsIgnoreCase( "long" );
    }

    public static boolean fieldIsFloat( ExcelColumn excelColumn ){
        //System.out.println(fieldElement.type );
        return excelColumn.getType().equalsIgnoreCase( "float" );
    }

    /**
     * 判断字段是否数字类型包括（int,long,double,float）
     * @param excelColumn  字段
     * @return  true    数字
     *
     */
    public static boolean isNumber( ExcelColumn excelColumn ){
        return excelColumn.getType().equalsIgnoreCase( "int" ) || excelColumn.getType().equalsIgnoreCase( "long" ) ||
                excelColumn.getType().equalsIgnoreCase( "double" ) || excelColumn.getType().equalsIgnoreCase( "float" );
    }

}
