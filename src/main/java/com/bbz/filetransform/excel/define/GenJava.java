package com.bbz.filetransform.excel.define;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.AbstractGen;
import com.bbz.filetransform.excel.FieldElimentManager;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.tool.common.FileUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

/**
 * user         LIUKUN
 * time         2015-1-8 11:19
 * 生成define类D.java文件
 */

public class GenJava extends AbstractGen{

    private final String className;
    private final String packageName;
    public static final String templetFileName = "D.t";
    private String src = new TempletFile( TempletType.DJAVA, templetFileName ).getTempletStr();

    public GenJava( String className, String packageName, Sheet sheet ){

        super( new FieldElimentManager( sheet ).getFields(), sheet );
        this.className = className;
        this.packageName = packageName;

    }

    public void genJAVA(){
        String packageInFile = PathCfg.JAVA_PACKAGE_PATH + packageName;

        src = src.
                replace( D.DATE_TAG, DateFormat.getDateTimeInstance().format( new Date() ) ).
                replace( "#FILEDS#", genFileds() ).
                replace( "#RELOAD_ALL#", genReloadAll() ).
                replace( "#CLASS_NAME#", className ).
                replace( D.PACAKAGE_NAME_TAG, packageInFile );


        String path = PathCfg.EXCEL_OUTPUT_JAVA_PATH + packageName + File.separator + className + ".java";
        System.out.println( path );
        FileUtil.writeTextFile( path, src );

    }

    /**
     * 生成reload函数，方便动态修改Define.java的内容
     *
     */
    private String genReloadAll(){

        StringBuilder sb = new StringBuilder();
        for( Row row : sheet ) {
            if( row.getRowNum() < D.EXCEL_HEAD_COUNT ) {
                continue;
            }

            if( row.getCell( 0 ) == null ) {
                System.out.println( className + "存在空白行" );
                break;
            }

            sb.append( genReloadAllContent( row ) );
        }
        return sb.toString();
    }

    private String genFileds(){
        StringBuilder sb = new StringBuilder();
        for( Row row : sheet ) {
            if( row.getRowNum() < D.EXCEL_HEAD_COUNT ) {
                continue;
            }

            if( row.getCell( 0 ) == null ) {
                System.out.println( className + "存在空白行" );
                break;
            }

            sb.append( genContent( row ) );
        }
        return sb.toString();
    }


    /**
     * 生成reload函数的具体内容，类似
     * ADD_FRIEND_CHARM = getInt("ADD_FRIEND_CHARM");
     *
     * @param row 一行excel数据
     */
    public String genReloadAllContent( Row row ){
        StringBuilder sb = new StringBuilder();


        String type = getCellStr( row.getCell( 1 ), fields.get( 1 ) );//变量类型
        String name = getCellStr( row.getCell( 0 ), fields.get( 0 ) );//变量名

        sb.append( name ).append( " = " );
        switch( type ) {
            case "int":
                sb.append( "getInt(\"" );
                break;
            case "float":
                sb.append( "getFloat(\"" );
                break;
            case "bool":
            case "boolean":
                sb.append( "getBoolean(\"" );
                break;
            case "string":
            case "String":
                sb.append( "getString(\"" );
                break;
        }
        sb.append( name );
        sb.append( "\");" );
        return sb.toString();
    }

    public String genContent( Row row ){
        //printRow( row );不出错，无需打印
        StringBuilder sb = new StringBuilder();

        String comment = getCellStr( row.getCell( 3 ), fields.get( 3 ) );//注释
        sb.append( "/** " ).append( comment ).append( " **/\r\n" );

        String type = getCellStr( row.getCell( 1 ), fields.get( 1 ) );//变量类型
        sb.append( "public static " ).append( type ).append( " " );

        String name = getCellStr( row.getCell( 0 ), fields.get( 0 ) );//变量名
        sb.append( name ).append( " = " );

        String value = getCellStr( row.getCell( 2 ), fields.get( 2 ) );//变量值
        if( type.equals( "float" ) ) {
            value += "F";
        }
        if( type.equalsIgnoreCase( "string" )){
            value = "\"" + value + "\"";
        }
        sb.append( value ).append( ";" );
        return sb.toString();

    }
}
