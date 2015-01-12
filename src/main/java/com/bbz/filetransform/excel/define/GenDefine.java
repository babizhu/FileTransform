package com.bbz.filetransform.excel.define;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * user         LIUKUN
 * time         2015-1-7 18:19
 * 根据配置表生成D.java，以及xml，json的内容
 */

public class GenDefine{

    private Sheet sheet;
    private final String className;
    private final String packageName;

    /**
     * path[0]为包名称，path[1]为文件名，比如excelFile为d:/game/resource/fighter/dogz,那么path[0]为fighter,path[1]为dogz
     */
    private String[] path;

    public GenDefine( String fullExcelPath ){

        int lastPointIndex = fullExcelPath.lastIndexOf( '.' );
        String purePath = fullExcelPath.substring( 0, lastPointIndex ); //d:\work\the-city-of-hero\.\tools\excel\corona\[轮盘]轮盘物品库_coronabank

        path = purePath.split( "\\\\" );
        path = Arrays.copyOfRange( path, path.length - 2, path.length );//只保留最后2个元素[corona],[[轮盘]轮盘物品库_coronabank]
        String temp = path[1];
        path[1] = temp.substring( temp.indexOf( '_' ) + 1, temp.length() );//去除中文名称

        className = path[1];
        packageName = path[0];

        openExcelFile( fullExcelPath );

    }

    public void generate(){

        new GenXml( className, packageName, sheet ).genXml();
        new GenJava( className,packageName,sheet ).genJAVA();
        new GenJson( className,packageName,sheet ).genJson();

    }

    private void openExcelFile( String excelFile ){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( excelFile );
            this.sheet = new HSSFWorkbook( fis ).getSheetAt( 0 );

        } catch( Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                if( fis != null ) {
                    fis.close();
                }
            } catch( IOException e ) {
                e.printStackTrace();
            }
        }
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

    public static void main( String[] args ){

    }


}
