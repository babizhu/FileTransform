package com.bbz.filetransform.excel.define;


import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.AbstractGen;
import com.bbz.filetransform.excel.FieldElimentManager;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import com.bbz.tool.common.FileUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 根据excel文件(define类文件)构建相应的json文档供客户端使用
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午4:32
 */
class GenJson extends AbstractGen{

    private final String className;
    private final String packageName;

    public GenJson( String className, String packageName, Sheet sheet ){

        super( new FieldElimentManager( sheet ).getFields(), sheet );
        this.className = className;
        this.packageName = packageName;

    }


    void genJson(){

        StringBuilder sb = new StringBuilder( "{");
        //sb.append( className ).append( "s" ).append( "\":[" );
        //sb.append( "arr" ).append( "s" ).append( "\":[" );//前端希望此处写死为arrs
        for( Row row : sheet ) {
            if( row.getRowNum() < D.EXCEL_HEAD_COUNT ) {
                continue;
            }

            if( row.getCell( 0 ) == null ) {
                System.out.println( "以下空白，停止处理" );
                break;
            }

            sb.append( genContent( row ) ).append( "," );
        }
        if( sb.length() > 1 ){
            sb.deleteCharAt( sb.length() - 1 );
        }
        sb.append( "}" );
        //sb.append( "]}" );
        //System.out.println( sb.toString() );

        String path = PathCfg.EXCEL_OUTPUT_JSON_PATH + packageName + "/" + Util.firstToLowCase( className ) + ".json";

        FileUtil.writeTextFile( path, sb.toString() );
    }

    public String genContent( Row row ){

        //printRow( row );不出错，无需打印
        String name = getCellStr( row.getCell( 0 ), fields.get( 0 ) );//变量名


        String value = getCellStr( row.getCell( 2 ), fields.get( 2 ) );//变量值
        StringBuilder sb = new StringBuilder();
        sb.append( "\"" ).append( name ).append( "\":\"" ).append( value ).append( "\"" );


        return sb.toString();
    }


}
