package com.bbz.filetransform.excel.cfg;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import com.bbz.filetransform.base.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.util.List;

/**
 * user         LIUKUN
 * time         2015-3-5 14:21
 *
 * 生成excel常量配置文件相对应的xml文件
 */

class GenXml extends AbstractGenCfg{

    public GenXml( String className, String packageName, Sheet sheet,List<ExcelColumn> excelColumns ){

        super( className, packageName, sheet, excelColumns );
    }

    @Override
    protected String fileName(){
        return PathCfg.EXCEL_OUTPUT_XML_PATH + packageName + File.separator + Util.firstToLowCase( className ) + ".xml";
    }

    @Override
    protected void gen(){

        StringBuilder sb = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" );
        sb.append( "<" ).append( className ).append( "s" ).append( ">" );
        for( Row row : sheet ) {
            if( row.getRowNum() < D.EXCEL_HEAD_COUNT ) {
                continue;
            }

            if( row.getCell( 0 ) == null ) {
                System.out.println( "以下空白，停止处理" );
                break;
            }

            sb.append( "<" ).append( className ).append( ">" ).
                    append( genRowContent( row ) ).append( "</" ).append( className ).append( ">" );
        }
        sb.append( "</" ).append( className ).append( "s" ).append( ">" );
        //System.out.println( sb.toString() );

        content = sb.toString();
        writeFile();
    }



    @Override
     String genRowContent( Row row ){
//
//        printRow( row );
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for( ExcelColumn element : excelColumns ) {
            sb.append( "<" ).append( element.getName() ).append( ">" );

            Cell cell = row.getCell( i++ );


            //System.out.println( row.getCell(i++) );
            sb.append( ExcelUtil.getCellStr( cell, element ) );
            sb.append( "</" ).append( element.getName() ).append( ">" );

        }
        return sb.toString();

    }


}
