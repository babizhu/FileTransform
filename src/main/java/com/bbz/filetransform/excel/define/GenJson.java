package com.bbz.filetransform.excel.define;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import com.bbz.filetransform.base.ExcelUtil;
import com.bbz.tool.common.StrUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.util.List;

/**
 * user         LIUKUN
 * time         2015-3-5 14:21
 *
 * 生成excel常量配置文件相对应的JSON文件
 */

class GenJson extends AbstractGenDefine{

    public GenJson( String className, String packageName, Sheet sheet,List<ExcelColumn> excelColumns ){

        super( className, packageName, sheet, excelColumns );
    }

    @Override
    protected String fileName(){
        return PathCfg.EXCEL_OUTPUT_JSON_PATH + packageName + File.separator + Util.firstToLowCase( className ) + ".json";
    }

    @Override
    protected void gen(){


        StringBuilder sb = new StringBuilder( "{");

        for( Row row : sheet ) {
            if( row.getRowNum() < D.EXCEL_HEAD_COUNT ) {
                continue;
            }

            if( row.getCell( 0 ) == null ) {
                System.out.println( "以下空白，停止处理" );
                break;
            }

            sb.append( genRowContent( row ) ).append( "," );
        }
        StrUtil.removeLastChar( sb );
        sb.append( "}" );

        content = sb.toString();
        writeFile();
    }

    @Override
    String genRowContent( Row row ){
        String name = ExcelUtil.getCellStr( row.getCell( 0 ), excelColumns.get( 0 ) );//变量名


        String value = ExcelUtil.getCellStr( row.getCell( 2 ), excelColumns.get( 2 ) );//变量值
        StringBuilder sb = new StringBuilder();
        sb.append( "\"" ).append( name ).append( "\":\"" ).append( value ).append( "\"" );


        return sb.toString();
    }
}
