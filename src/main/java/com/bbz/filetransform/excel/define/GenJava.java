package com.bbz.filetransform.excel.define;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.base.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * user         LIUKUN
 * time         2015-3-5 14:21
 *
 * 生成excel常量配置文件相对应的JAVA文件
 */

class GenJava extends AbstractGenDefine{



    /**
     * 模板文件
     */
    private static final String templetFileName = "D.t";



    GenJava( String className, String packageName, Sheet sheet,  List<ExcelColumn> excelColumns ){
        super( className, packageName, sheet, excelColumns );

    }

    @Override
    protected String fileName(){
        System.out.println(PathCfg.EXCEL_OUTPUT_JAVA_PATH + packageName + File.separator + className + ".java");
        return PathCfg.EXCEL_OUTPUT_JAVA_PATH + packageName + File.separator + className + ".java";
    }

    @Override
    protected void gen(){ String packageInFile = PathCfg.JAVA_PACKAGE_PATH + packageName;
        content = new TempletFile( TempletType.DJAVA, templetFileName ).getTempletStr();
        content = content.
                replace( D.DATE_TAG, DateFormat.getDateTimeInstance().format( new Date() ) ).
                replace( "#FILEDS#", genFileds() ).
                replace( "#RELOAD_ALL#", genReloadAll() ).
                replace( "#CLASS_NAME#", className ).
                replace( "#packageName#", packageInFile );


        writeFile();

    }

    @Override
    String genRowContent( Row row ){
        //printRow( row );不出错，无需打印
        StringBuilder sb = new StringBuilder();

        String comment = ExcelUtil.getCellStr( row.getCell( 3 ), excelColumns.get( 3 ) );//注释
        sb.append( "/** " ).append( comment ).append( " **/\r\n" );

        String type = ExcelUtil.getCellStr( row.getCell( 1 ), excelColumns.get( 1 ) );//变量类型
        sb.append( "public static " ).append( type ).append( " " );

        String name = ExcelUtil.getCellStr( row.getCell( 0 ), excelColumns.get( 0 ) );//变量名
        sb.append( name ).append( " = " );

        String value = ExcelUtil.getCellStr( row.getCell( 2 ), excelColumns.get( 2 ) );//变量值
        if( type.equalsIgnoreCase( "float" ) ) {
            value += "F";
        }

        if( type.equalsIgnoreCase( "string" )){
            value = "\"" + value + "\"";
        }
        sb.append( value ).append( ";" );
        return sb.toString();
    }

    /**
     * 生成reload函数，方便程序可在运行中修改Define.java的内容
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

            sb.append( genRowContent( row ) );
        }
        return sb.toString();
    }


    /**
     * 生成reload函数的具体内容，类似
     * ADD_FRIEND_CHARM = getInt("ADD_FRIEND_CHARM");
     *
     * @param row 一行excel数据
     */
    private String genReloadAllContent( Row row ){
        StringBuilder sb = new StringBuilder();


        String type = ExcelUtil.getCellStr( row.getCell( 1 ), excelColumns.get( 1 ) );//变量类型
        String name = ExcelUtil.getCellStr( row.getCell( 0 ), excelColumns.get( 0 ) );//变量名

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

}
