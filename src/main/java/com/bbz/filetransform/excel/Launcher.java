package com.bbz.filetransform.excel;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.cfg.GenCfgFile;
import com.bbz.filetransform.excel.define.GenDefineFile;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.tool.common.FileUtil;

import java.io.File;

/**
 * user         LIUKUN
 * time         2015-3-10 18:12
 */

public class Launcher{

    /**
     * 用于CfgInit.java的包导入部分
     */
    String packageImportStr = "";

    /**
     * 用于CfgInit.java的init函数部分
     */
    String initStr = "";


    public static void main( String[] args ){

        new Launcher().genAll( PathCfg.EXCEL_PATH );

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

                if( file.getParent().endsWith( "define" ) ) {
                    genDefine( strFileName );
                } else {
//
                    genCfg( strFileName );
                }
//                System.out.println( "处理完毕 " + strFileName );

            }
        }
        buildCfgInit();

    }


    /**
     * 生成初始化所有cfg配置文件的java源程序代码
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
        String path = PathCfg.EXCEL_OUTPUT_JAVA_PATH + File.separator + "CfgInit" + D.JAVA_FILE_SUFFIXES;
        FileUtil.writeTextFile( path, fileContent );
    }

    private void genCfg( String strFileName ){
        GenCfgFile cfgFile = new GenCfgFile();
        cfgFile.gen( strFileName );
        String separator = System.getProperties().getProperty( "line.separator" );
        packageImportStr += "import " + PathCfg.JAVA_PACKAGE_PATH;
        packageImportStr += cfgFile.getPackageName();
        packageImportStr += "." + cfgFile.getClassName() + "TempletCfg;" + separator;

        initStr += cfgFile.getClassName() + "TempletCfg.init();";
    }

    private void genDefine( String strFileName ){
        new GenDefineFile().gen( strFileName );
    }

}
