package com.bbz.filetransform.excel.cfg;


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
 * 根据excel文件构建xml文档
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午4:32
 */
class GenXml extends AbstractGen{

    private final String className;
    private final String packageName;

    public GenXml( String[] path, Sheet sheet ){
        super(new FieldElimentManager( sheet ).getFields(),sheet);
        className = path[1];
        packageName = path[0];
    }

    void generate(){

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
                    append( genContent( row ) ).append( "</" ).append( className ).append( ">" );
        }
        sb.append( "</" ).append( className ).append( "s" ).append( ">" );
        //System.out.println( sb.toString() );

        String path = PathCfg.EXCEL_OUTPUT_XML_PATH + packageName + File.separator + Util.firstToLowCase( className ) + ".xml";
        FileUtil.writeTextFile( path, sb.toString() );
//        06911523
    }

    @SuppressWarnings("UnusedDeclaration")
    private void printRow( Row row ){
        for( Cell cell : row ) {
            System.out.print( cell + " " );
        }
        System.out.println();

    }
    public String genContent( Row row ){
//
//        printRow( row );
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for( FieldElement element : fields ) {
            sb.append( "<" ).append( element.getName() ).append( ">" );

            Cell cell = row.getCell( i++ );


            //System.out.println( row.getCell(i++) );
            sb.append( getCellStr( cell, element  ) );
            sb.append( "</" ).append( element.getName() ).append( ">" );

        }
        return sb.toString();

    }

}
