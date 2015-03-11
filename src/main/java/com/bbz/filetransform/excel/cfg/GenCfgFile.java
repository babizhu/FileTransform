package com.bbz.filetransform.excel.cfg;

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
 * time         2015-3-10 17:34
 *
 * 生成excel文件的xml、json、xxTemplet、xxTempletCfg文件
 */

public class GenCfgFile{
    private Sheet sheet;
    private String className, packageName;

    public GenCfgFile(){
    }

    /**
     * 针对一个用来表示配置的excel（）文件，生成相应的三个目标文件，包括，java，json，xml
     */
    public void gen( String className, String packageName ){
        List<ExcelColumn> excelColumns = new ExcelColumnManager( sheet ).getExcelColumns();
        new GenJson( className, packageName, sheet, excelColumns ).gen();
        new GenXml( className, packageName, sheet, excelColumns ).gen();
        new GenTemplet( className, packageName, sheet, excelColumns ).gen();
        new GenTempletCfg( className, packageName, sheet, excelColumns ).gen();
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

        className = path[1];//PropIdDefine
        packageName = path[0];//define


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
        new GenCfgFile(  ).gen("D:\\phpStudy\\WWW\\server\\svn\\飞机数值表\\customs\\[关卡][怪物]怪物属性表_Monster.xls");
    }

    public String getClassName(){
        return className;
    }



    public String getPackageName(){
        return packageName;
    }


}
