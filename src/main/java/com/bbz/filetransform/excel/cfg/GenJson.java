package com.bbz.filetransform.excel.cfg;


import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import com.bbz.filetransform.base.ExcelUtil;
import com.bbz.tool.common.StrUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.util.List;

/**
 * 根据excel文件构建json文档
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午4:32
 */
class GenJson extends AbstractGenCfg{

    public GenJson( String className, String packageName, Sheet sheet,List<ExcelColumn> excelColumns ){

        super( className, packageName, sheet, excelColumns );
    }

    @Override
    protected String fileName(){
        return PathCfg.EXCEL_OUTPUT_JSON_PATH + packageName + File.separator + Util.firstToLowCase( className ) + ".json";
    }

    @Override
    protected void gen(){

        StringBuilder sb = new StringBuilder( "{\"" );
        //sb.append( className ).append( "s" ).append( "\":[" );
        sb.append( "arr" ).append( "s" ).append( "\":[" );//前端希望此处写死为arrs
        for( Row row : sheet ) {
            if( row.getRowNum() < D.EXCEL_HEAD_COUNT ) {
                continue;
            }

            if( row.getCell( 0 ) == null ) {
                System.out.println( "以下空白，停止处理" );
                break;
            }

            sb.append( "{" ).
                    append( genRowContent( row ) ).append( "}," );
        }
        StrUtil.removeLastChar( sb );
        sb.append( "]}" );
        //System.out.println( sb.toString() );

        content = sb.toString();

        writeFile();
    }


    String genRowContent( Row row ){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for( ExcelColumn element : excelColumns ) {
            sb.append( "\"" ).append( element.getName() ).append( "\":" );

            Cell cell = row.getCell( i++ );


            //System.out.println( row.getCell(i++) );
            sb.append( "\"" ).append( ExcelUtil.getCellStr( cell, element ) );
            sb.append( "\"," );

        }
        if( sb.length() > 1 ) {
            sb.deleteCharAt( sb.length() - 1 );
        }
        return sb.toString();
    }


}
