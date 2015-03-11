package com.bbz.filetransform.excel.cfg;


import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.ExcelColumn;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通过xml生成相应的配置(config)文件
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */

class GenTempletCfg extends AbstractGenCfgJava{

    private final String templetClass;
    private final String xmlNode;

    /**
     * 模板文件
     */
    private static final String templetFileName = "templetCfg.t";


    GenTempletCfg( String className, String packageName, Sheet sheet, List<ExcelColumn> excelColumns ){
        super( className, packageName, sheet, excelColumns );
        templetClass = className + "Templet";
        this.className += "TempletCfg";
        xmlNode = packageName;

    }

    GenTempletCfg( String fullExcelPath ){
        super( fullExcelPath );
        templetClass = className + "Templet";
        this.className += "TempletCfg";
        xmlNode = packageName;
    }


    /**
     //* @param path  仅包括包名和类名
     //* @param sheet excel的sheet
     */
//    public GenTempletCfg( String[] path, Sheet sheet ) {
//        super(path, sheet);
//        int pos = className.indexOf("Cfg");
//        templetClass = className.substring(0, pos);
//        xmlNode = path[1];
//    }

    @Override
    protected void gen() {
        content = new TempletFile( TempletType.JAVA, templetFileName ).getTempletStr();
        genMisc();
        //System.out.println( src );

        writeFile();

    }

    private void genMisc() {
        String packageInFile = PathCfg.JAVA_PACKAGE_PATH + packageName;
        //HeroTemplets===>hero
        String xmlPath = templetClass.substring(0, templetClass.indexOf("Templet"));
        xmlPath = Util.firstToLowCase( xmlPath ) + ".xml";
        xmlPath = PathCfg.XML_PATH_IN_CONFIGJAVA_FILE + packageName + "/" + xmlPath;

        content = content.
                replace(D.DATE_TAG, DateFormat.getDateTimeInstance().format(new Date())).
                replace(D.CLASS_NAME_TAG, className).
                replace(D.TEPMLET_CLASS_NAME_TAG, templetClass).
                replace(D.XML_PATH_TAG, xmlPath).
                replace(D.MAP_NAME_TAG, Util.firstToLowCase(templetClass)).
                replace(D.XMl_NODE_TAG, xmlNode).
                replace(D.PACAKAGE_NAME_TAG, packageInFile);

    }

    public static void main( String[] args ){
        new GenTempletCfg( "D:\\phpStudy\\WWW\\server\\svn\\飞机数值表\\customs\\[关卡][怪物]怪物属性表_Monster.xls" ).gen();
    }


}
