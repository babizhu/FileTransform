package com.bbz.filetransform.excel.cfg;


import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通过xml生成相应的配置文件
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */


class GenTemplet extends AbstractGenCfgJava{

    /**
     * 模板文件
     */
    private static final String templetFileName = "xTemplet.t";

    GenTemplet( String className, String packageName, Sheet sheet, List<ExcelColumn> excelColumns ){
        super( className, packageName, sheet, excelColumns );
        this.className += "Templet";
    }

    GenTemplet( String fullExcelPath ){
        super( fullExcelPath );
        this.className += "Templet";
    }



    @Override
    protected void gen(){

        content = new TempletFile( TempletType.JAVA, templetFileName ).getTempletStr();
        genMisc();

        genFieldS();
        genToString();
        genConstruct();
        writeFile();
    }

    /**
     * 生成toString方法
     */
    private void genToString(){

        StringBuilder sb = new StringBuilder();
        for (ExcelColumn fe : excelColumns) {
            String temp = fe.getName() + " = \" + " + fe.getName() + " + \",";
            sb.append(temp);
        }

        content = content.replace( D.TO_STRING_TAG, sb.substring( 0, sb.length() - 5 ) );
    }

    private void genMisc(){
        String packageInFile = PathCfg.JAVA_PACKAGE_PATH + packageName;
        content = content.
                replace( D.DATE_TAG, DateFormat.getDateTimeInstance().format( new Date() ) ).
                replace( D.CLASS_NAME_TAG, className ).
                replace( D.PACAKAGE_NAME_TAG, packageInFile );

    }

    private void genFieldS(){
        StringBuilder sb = new StringBuilder();
        for (ExcelColumn fe : excelColumns) {
            sb.append(genField(fe));
        }

        int index = 0;
        for (Row row : sheet) {
            if( index++ > 4 ){
                break;
            }
//            for (Cell cell : row) {
//
//                System.out.print(cell.toString() + "\t");
//
//            }

        }
        content = content.replace( D.FIELD_AREA_TAG, sb.toString() );
    }

    private void genConstruct(){
        StringBuilder sb = new StringBuilder();
        for (ExcelColumn fe : excelColumns) {
            sb.append(fe.getName()).append(" = ").
                    append(parseJavaType(fe)).
                    append(fe.getName()).
                    append("\").trim()");
            if (!fe.getType().equals( "String" )) {
                sb.append(" )");
            }
            sb.append(";").
                    append(System.lineSeparator());
        }

//        zoneId = Short.parseShort( element.getChildText( "zone_id" ) );
        content = content.replace( D.CONSTRUCT_TAG, sb.toString() );

    }


    private String genField( ExcelColumn column ){
        String ret = new TempletFile( TempletType.JAVA, "fieldTemplet.t" ).getTempletStr();
        ret = ret.
                replace( D.ANNOTATION_TAG, column.getAnnotation() ).
                replace( D.FIELD_TYPE_TAG, column.getType() ).
                replace( D.FIELD_TAG, column.getName() ).
                replace( D.METHOD_NAME_GET_TAG, Util.genGet( column.getName() ) ).
                replace( D.METHOD_NAME_SET_TAG, Util.genSet( column.getName() ) );

        return ret;
    }


    public static void main( String[] args ){
        new GenTemplet( "D:\\phpStudy\\WWW\\server\\svn\\飞机数值表\\customs\\[关卡][怪物]怪物属性表_Monster.xls" ).gen();
    }


}
