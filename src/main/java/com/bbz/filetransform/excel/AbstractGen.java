package com.bbz.filetransform.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * user         LIUKUN
 * time         2015-1-8 10:58
 */

public class AbstractGen{
    public final List<FieldElement> fields;
    public final Sheet sheet;

    public AbstractGen( List<FieldElement> fields, Sheet sheet ){
        this.fields = fields;
        this.sheet = sheet;
    }

    @SuppressWarnings("UnusedDeclaration")
    private void printRow( Row row ){
        for( Cell cell : row ) {
            cell.setCellType( Cell.CELL_TYPE_STRING );
            String data = cell.getStringCellValue();
            System.out.print( data + " " );
        }
        System.out.println();

    }

    /**
     * 从excel中获取单元格的字符串内容，int和long型的字符串末尾会产生".0"，因此这里也要过滤掉
     * 如果cell为null，数字型（int,long,float,double）返回空”0“，其余情况返回""(空字符串)
     * float型会在数字后面增加"F"
     *
     * @param cell          单元格
     * @param fieldElement  字段
     * @return      单元格的内容
     */
    public String getCellStr( Cell cell, FieldElement fieldElement){
        String data = "";
        if( cell == null) {
            if( isNumber(fieldElement) ) {
                return "0";

            }
            return data;
        }


        cell.setCellType( Cell.CELL_TYPE_STRING );
        data = cell.getStringCellValue().trim();
        if( fieldIsIntOrLong( fieldElement ) ) {

            int pointPos = data.indexOf( '.' );
            if( pointPos != -1 ) {
                data = data.substring( 0, pointPos );//去掉末尾的.0
            }
        }
        if( fieldIsFloat( fieldElement )){
            data += "F";
        }
        if(data.equals( "" ) && isNumber( fieldElement ) ){
            data = "0";
        }
        return data;

    }

    public boolean fieldIsIntOrLong( FieldElement fieldElement ){
        return fieldElement.getType().equalsIgnoreCase( "int" ) || fieldElement.getType().equalsIgnoreCase( "long" );
    }

    public boolean fieldIsFloat( FieldElement fieldElement ){
        //System.out.println(fieldElement.type );
        return fieldElement.getType().equalsIgnoreCase( "float" );
    }

    /**
     * 判断字段是否数字类型包括（int,long,double,float）
     * @param fieldElement  字段
     * @return  true    数字
     *
     */
    public boolean isNumber( FieldElement fieldElement ){
        return fieldElement.getType().equalsIgnoreCase( "int" ) || fieldElement.getType().equalsIgnoreCase( "long" ) ||
        fieldElement.getType().equalsIgnoreCase( "double" ) || fieldElement.getType().equalsIgnoreCase( "float" );
    }

}
