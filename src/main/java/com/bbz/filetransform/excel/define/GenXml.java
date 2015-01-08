package com.bbz.filetransform.excel.define;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.AbstractGen;
import com.bbz.filetransform.excel.FieldElement;
import com.bbz.filetransform.excel.FieldElimentManager;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import com.bbz.tool.common.FileUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;

/**
 * user         LIUKUN
 * time         2015-1-8 11:19
 * 生成define类excle的xml文件
 */

public class GenXml extends AbstractGen{

    private final String className;
    private final String packageName;

    public GenXml( String className, String packageName, Sheet sheet ){

        super( new FieldElimentManager( sheet ).getFields(), sheet );
        this.className = className;
        this.packageName = packageName;

    }

    public void genXml(){
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

            sb.append( "<" ).append( className ).
                    append( genContent( row ) ).append( " />" );
        }
        sb.append( "</" ).append( className ).append( "s" ).append( ">" );
//        System.out.println( sb.toString() );

        String path = PathCfg.EXCEL_OUTPUT_XML_PATH + packageName + File.separator + Util.firstToLowCase( className ) + ".xml";
//        System.out.println( path);
        FileUtil.writeTextFile( path, sb.toString() );

    }

    public String genContent( Row row ){
        //printRow( row );不出错，无需打印
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for( FieldElement element : fields ) {
            sb.append( " " ).append( element.getName() ).append( "=\"" );

            Cell cell = row.getCell( i++ );


            //System.out.println( row.getCell(i++) );
            sb.append( getCellStr( cell, element  ) );
            sb.append( "\" " );

        }

        if( sb.length() > 1 ) {
            sb.deleteCharAt( sb.length() - 1 );
        }
        return sb.toString();
    }
}
