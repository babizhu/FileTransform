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
     * 如果cell为null，int型或者long型则返回空”0“，其余情况返回""(空字符串)
     *
     * @param cell
     * @param isIntOrLong
     * @return
     */
    public String getCellStr( Cell cell, boolean isIntOrLong ){
        String data = "";
        if( cell == null ) {
            if( isIntOrLong ) {
                return "0";

            }
            return data;
        }

        cell.setCellType( Cell.CELL_TYPE_STRING );
        data = cell.getStringCellValue().trim();
        if( isIntOrLong ) {

            int pointPos = data.indexOf( '.' );
            if( pointPos != -1 ) {
                data = data.substring( 0, pointPos );//去掉末尾的.0
            }
        }
        return data;

    }

    public boolean fieldIsIntOrLong( FieldElement fieldElement ){
        return fieldElement.type.equals( "int" ) || fieldElement.type.equals( "long" );
    }

}
