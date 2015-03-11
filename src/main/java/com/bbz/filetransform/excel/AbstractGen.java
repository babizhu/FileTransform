package com.bbz.filetransform.excel;

import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import com.bbz.tool.common.FileUtil;
import com.bbz.tool.common.MiscUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * user         LIUKUN
 * time         2015-3-10 11:19
 */

public abstract class AbstractGen{

    /**
     * excel表
     */
    protected final Sheet sheet;

    /**
     * 生成的内容
     */
    protected String content;

    /**
     * 生成java文件的类名
     */
    protected String className;

    /**
     * 生成java文件的包名
     */
    protected final String packageName;


    /**
     * excel文件的列信息
     */
    protected final List<ExcelColumn> excelColumns;


    protected AbstractGen(String className, String packageName, Sheet sheet, List<ExcelColumn> excelColumns){
        this.className = className;
        this.packageName = packageName;
        this.sheet = sheet;
        this.excelColumns = excelColumns;

    }
    /**
     *
     * @param fullExcelPath  excel的文件路径 D:\飞机数值表\define\[定义]道具id表_PropIdDefine.xls
     */
    protected AbstractGen( String fullExcelPath ){

        int lastPointIndex = fullExcelPath.lastIndexOf( '.' );
        String purePath = fullExcelPath.substring( 0, lastPointIndex );
        //D:\飞机数值表\define\[定义]道具id表_PropIdDefine

        String[] path;
        if( MiscUtil.isWindowsOS() ) {
            path = purePath.split( File.separator + File.separator );
        } else {
            path = purePath.split( File.separator );
        }
        path = Arrays.copyOfRange( path, path.length - 2, path.length );//只保留最后2个元素
        String temp = path[1];
        path[1] = temp.substring( temp.indexOf( '_' ) + 1, temp.length() );//去除中文名称

        className = path[1];//PropIdDefine
        packageName = path[0];//define


        sheet = openExcelFile( fullExcelPath );
        excelColumns = new ExcelColumnManager( sheet ).getExcelColumns();
    }



    protected abstract String fileName();
    protected abstract void gen();

    /**
     * 处理自定义的内容，不要误删除了
     */
    protected void writeFile(){
        String path = fileName();
        String manualContent = "";
        if( Util.isExist( path ) ) {
            String oldData = FileUtil.readTextFile( path );
            int beginPos = oldData.indexOf(D.MANUAL_WORK_BEGIN);
            int endPos = oldData.indexOf(D.MANUAL_WORK_END);
            if( endPos != -1 && beginPos != -1 ) {
                manualContent = oldData.substring(beginPos + D.MANUAL_WORK_BEGIN.length(), endPos);
            }
        }
        content = content.replace(D.MANUAL_WORK_TAG, manualContent);//把自定义的内容加上去
        FileUtil.writeTextFile(path, content);
    }

    private Sheet openExcelFile( String excelFile ){
        Sheet sheet = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( excelFile );
            sheet = new HSSFWorkbook( fis ).getSheetAt( 0 );

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
        return sheet;
    }

    @SuppressWarnings("UnusedDeclaration")
    protected void printRow( Row row ){
        for( Cell cell : row ) {
            System.out.print( cell + " " );
        }
        System.out.println();

    }

}
