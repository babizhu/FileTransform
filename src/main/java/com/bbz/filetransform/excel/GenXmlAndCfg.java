package com.bbz.filetransform.excel;


import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.define.GenDefine;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.tool.common.FileUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午4:54
 */

public class GenXmlAndCfg {

    /**
     * 用于CfgInit.java的包导入部分
     */
    String packageImportStr = "";

    /**
     * 用于CfgInit.java的init函数部分
     */
    String initStr = "";

    public static void main( String[] args ){

    }

    public void genAll( String path ){
        //System.out.println( "遍历excel目录：" + path );
        File dir = new File( path );
        File[] files = dir.listFiles();

        if( files == null ) {
            System.err.println( path + "没找到，程序退出！" );
            return;
        }
        for( File file : files ) {
            String strFileName = file.getAbsolutePath();

            if( file.isDirectory() ) {
                genAll( file.getAbsolutePath() );
            } else {
                System.out.println( "开始处理 " + strFileName );

                if(file.getParent().endsWith( "define" ) ){
                    buildDefineJava( strFileName );
                }else {
//
                    genOne( strFileName );
                }
//                System.out.println( "处理完毕 " + strFileName );

            }
        }
        buildCfgInit();

    }

    /**
     * 根据define目录下的文件生成相应的常规属性JAVA文件（D.java）
     * @param fullExcelPath       完整的的excel文件路径
     */
    private void buildDefineJava( String fullExcelPath ){
        new GenDefine( fullExcelPath ).generate();
    }

    /**
     * 生成CfgInit.java文件
     */
    private void buildCfgInit(){
        String packageInFile = PathCfg.JAVA_PACKAGE_PATH;
        packageInFile = packageInFile.substring( 0, packageInFile.length() - 1 );//去掉配置表中最后一个.
        String file = "cfgInit.templet";
        TempletFile t = new TempletFile( TempletType.JAVA, file );
        String fileContent = t.getTempletStr();
        fileContent = fileContent.replace( "#pack#", packageImportStr )
                .replace( "#data#", initStr )
                .replace(D.PACAKAGE_NAME_TAG, packageInFile);
//        System.out.println(fileContent);
        String path = PathCfg.EXCEL_OUTPUT_JAVA_PATH + "/CfgInit" + D.JAVA_FILE_SUFFIXES;
        FileUtil.writeTextFile( path, fileContent );


    }

    private void genOne( String fullExcelPath ){
        GenConfig pe = new GenConfig( fullExcelPath );
        pe.gen();
        String separator = System.getProperties().getProperty( "line.separator" );
        packageImportStr += pe.getImportStr() + separator;

        initStr += pe.getInitStr() + separator;

    }
}
