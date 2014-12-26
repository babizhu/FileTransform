package com.bbz.filetransform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * user         LIUKUN
 * time         2014-4-7 13:38
 * <p/>
 * 服务器的整体配置文件
 */

public class PathCfg{

    /**
     * EXCEL文件所在的路径
     */
    public static final String EXCEL_PATH;

    /**
     * EXCEL对应的xml输出目录
     */
    public static final String EXCEL_OUTPUT_XML_PATH;

    /**
     * EXCEL对应的json输出目录
     */
    public static final String EXCEL_OUTPUT_JSON_PATH;


    /**
     * EXCEL对应的java读取excel的输出目录
     */
    public static final String EXCEL_OUTPUT_JAVA_PATH;


    /**
     * 数据库的模板文件路径
     */
    public static final String DB_TEMPLET_PATH;


    /**
     * java代码的模板文件路径
     */
    public static final String JAVA_TEMPLET_PATH;


    static{
        Properties prop = new Properties();
        try {
//            InputStream in = new BufferedInputStream( new FileInputStream( "resource/path.properties" ) );
//            prop.load( in );
//            in.close();
            File file = new File( "resource/path.properties" );
            FileInputStream inputStream = new FileInputStream( file );
            InputStreamReader streamReader = new InputStreamReader( inputStream );
            prop.load( streamReader );

            inputStream.close();
            streamReader.close();

        } catch( IOException e ) {
            e.printStackTrace();
        }
        EXCEL_PATH = prop.getProperty( "excelPath" ).trim();
        EXCEL_OUTPUT_XML_PATH = prop.getProperty( "outputXml" ).trim();
        EXCEL_OUTPUT_JSON_PATH = prop.getProperty( "outputJson" ).trim();
        EXCEL_OUTPUT_JAVA_PATH = prop.getProperty( "outputJava" ).trim();

        JAVA_TEMPLET_PATH = prop.getProperty( "javaTempletPath" ).trim();
        DB_TEMPLET_PATH = prop.getProperty( "dbTempletPath" ).trim();

    }

    public static void main( String[] args ){
        System.out.println( PathCfg.EXCEL_PATH );
    }
}

