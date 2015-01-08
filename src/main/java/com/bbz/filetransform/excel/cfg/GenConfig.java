package com.bbz.filetransform.excel.cfg;


import com.bbz.filetransform.PathCfg;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 通过excel生成xml以及相应相应的java文件,包括xxTemplet.java以及xxTempletCfg.java
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午4:41
 */
@Data
class GenConfig{

    private Sheet sheet;

    /**
     * path[0]为包名称，path[1]为文件名，比如excelFile为d:/game/resource/fighter/dogz,那么path[0]为fighter,path[1]为dogz
     */
    private String[] path;
    private String importStr;
    private String initStr;


    public GenConfig( String fullExcelPath ){
        int lastPointIndex = fullExcelPath.lastIndexOf( '.' );
        String purePath = fullExcelPath.substring( 0, lastPointIndex ); //d:\work\the-city-of-hero\.\tools\excel\corona\[轮盘]轮盘物品库_coronabank

        path = purePath.split( "\\\\" );
        path = Arrays.copyOfRange( path, path.length - 2, path.length );//只保留最后2个元素[corona],[[轮盘]轮盘物品库_coronabank]
        String temp = path[1];
        path[1] = temp.substring( temp.indexOf( '_' ) + 1, temp.length() );//去除中文名称
        openExcelFile( fullExcelPath );
        initStr = path[1] + "TempletCfg.init();";
        importStr = "import " + PathCfg.JAVA_PACKAGE_PATH + path[0] + "." + path[1] + "TempletCfg;";
    }

    public static void main( String[] args ){
        //System.out.println(System.getProperty("user.dir")  );
//        String file = "fighter/hero.xls";
        String file = "excel/fighter/npc.xls";
        GenConfig pe = new GenConfig( file );

        pe.gen();


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

    void gen(){

        new GenTemplet( path, sheet ).gen();
        new GenTempletCfg( path, sheet ).gen();

        new GenXml( path, sheet ).generate();
        new GenJson( path, sheet ).generate();
    }

}
