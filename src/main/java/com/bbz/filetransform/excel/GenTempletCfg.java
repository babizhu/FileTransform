package com.bbz.filetransform.excel;


import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.util.Date;

/**
 * 通过xml生成相应的配置(config)文件
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */

class GenTempletCfg extends AbstractGenJava {

    private final String templetClass;
    private final String xmlNode;

    /**
     * @param path  仅包括包名和类名
     * @param sheet excel的sheet
     */
    public GenTempletCfg(String[] path, Sheet sheet) {
        super(path, sheet);
        int pos = className.indexOf("Cfg");
        templetClass = className.substring(0, pos);
        xmlNode = path[1];
    }

    @Override
    public void gen() {
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

        src = src.
                replace(D.DATE_TAG, DateFormat.getDateTimeInstance().format(new Date())).
                replace(D.CLASS_NAME_TAG, className).
                replace(D.TEPMLET_CLASS_NAME_TAG, templetClass).
                replace(D.XML_PATH_TAG, xmlPath).
                replace(D.MAP_NAME_TAG, Util.firstToLowCase(templetClass)).
                replace(D.XMl_NODE_TAG, xmlNode).
                replace(D.PACAKAGE_NAME_TAG, packageInFile);

    }

    @Override
    protected String getTempletFileName() {
        return "templetCfg.t";
    }

    @Override
    public String genClassName(String name) {
        return Util.firstToUpperCase(name) + "TempletCfg";
    }

//    public static void main(String[] args) {
//        GenTempletCfg g = new GenTempletCfg();
//        g.generate();
//    }

}
