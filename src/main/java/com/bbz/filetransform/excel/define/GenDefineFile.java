package com.bbz.filetransform.excel.define;

import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.excel.ExcelColumnManager;
import com.bbz.tool.common.MiscUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * user         LIUKUN
 * time         2015-3-5 16:51
 *
 * 生成一个excle的Define文件的java、xml、json配置文件
 * 例如：
 * D:\phpStudy\WWW\server\svn\飞机数值表\define\[定义]道具id表_PropIdDefine.xls
 */

public class GenDefineFile{

    private Sheet sheet;
    private String className,packageName;

    /**
     * 针对一个用来表示配置的excel（）文件，生成相应的三个目标文件，包括，java，json，xml
     */
    public void gen( String className, String packageName ){
        List<ExcelColumn> excelColumns = new ExcelColumnManager(sheet).getExcelColumns();
        new GenJava( className, packageName, sheet,excelColumns ).gen();
        new GenJson( className, packageName, sheet,excelColumns ).gen();
        new GenXml( className, packageName, sheet,excelColumns ).gen();
    }

    /**
     * 针对一个用来表示配置的excel（）文件，生成相应的三个目标文件，包括，java，json，xml
     *
     * @param fullExcelPath excle文件的绝对路径
     *                      <p/>
     *                      excle文件的规则如下：
     *                      D:\phpStudy\WWW\server\svn\飞机数值表\define\[定义]道具id表_PropIdDefine.xls
     */
    public void gen( String fullExcelPath ){
        int lastPointIndex = fullExcelPath.lastIndexOf( '.' );
        String purePath = fullExcelPath.substring( 0, lastPointIndex );
        //D:\phpStudy\WWW\server\svn\飞机数值表\define\[定义]道具id表_PropIdDefine

        String[] path;
        if( MiscUtil.isWindowsOS() ) {
            path = purePath.split( File.separator + File.separator );
        } else {
            path = purePath.split( File.separator );
        }
        path = Arrays.copyOfRange( path, path.length - 2, path.length );//只保留最后2个元素
        String temp = path[1];
        path[1] = temp.substring( temp.indexOf( '_' ) + 1, temp.length() );//去除中文名称

        String className = path[1];//PropIdDefine
        String packageName = path[0];//define


        openExcelFile( fullExcelPath );

        gen( className, packageName );
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

    public static void main( String[] args ){

        new GenDefineFile().gen( "D:\\phpStudy\\WWW\\server\\svn\\飞机数值表\\define\\[定义]道具id表_PropIdDefine.xls" );
    }

}
