package com.bbz.filetransform.excel.cfg;


import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.excel.FieldElement;
import com.bbz.filetransform.excel.FieldElimentManager;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
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


class GenTemplet extends AbstractGenJava {

    private List<FieldElement> fields;

    /**
     * @param path  仅包括包名和类名
     * @param sheet execl的sheet
     */
    public GenTemplet(String[] path, Sheet sheet) {
        super(path, sheet);
        fields = new FieldElimentManager(sheet).getFields();
    }

    @Override
    public void gen() {
        genMisc();

        genFieldS();
        genToString();
        genConstruct();
        writeFile();
    }

    @Override
    protected String getTempletFileName() {
        return "xTemplet.t";
    }

    @Override
    public String genClassName(String name) {
        return Util.firstToUpperCase( name ) + "Templet";

    }


    /**
     * 生成toString方法
     */
    private void genToString() {

        StringBuilder sb = new StringBuilder();
        for (FieldElement fe : fields) {
            String temp = fe.getName() + " = \" + " + fe.getName() + " + \",";
            sb.append(temp);
        }

        src = src.replace( D.TO_STRING_TAG, sb.substring(0, sb.length() - 5));
    }

    private void genMisc() {
        String packageInFile = PathCfg.JAVA_PACKAGE_PATH + packageName;
        src = src.
                replace(D.DATE_TAG, DateFormat.getDateTimeInstance().format(new Date())).
                replace(D.CLASS_NAME_TAG, className).
                replace(D.PACAKAGE_NAME_TAG, packageInFile);

    }

    private void genFieldS() {
        StringBuilder sb = new StringBuilder();
        for (FieldElement fe : fields) {
            sb.append(genField(fe));
        }

//        for (Row row : sheet) {
//            if( index++ > 4 ){
//                break;
//            }
//            for (Cell cell : row) {
//
//                System.out.print(cell.toString() + "\t");
//
//            }
//            System.out.println();
//        }
        src = src.replace(D.FIELD_AREA_TAG, sb.toString());
    }

    private void genConstruct() {
        StringBuilder sb = new StringBuilder();
        for (FieldElement fe : fields) {
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
        src = src.replace(D.CONSTRUCT_TAG, sb.toString());

    }


    private String genField(FieldElement fe) {
        String ret = new TempletFile( TempletType.JAVA, "fieldTemplet.t").getTempletStr();
        ret = ret.
                replace(D.ANNOTATION_TAG, fe.getAnnotation()).
                replace(D.FIELD_TYPE_TAG, fe.getType()).
                replace(D.FIELD_TAG, fe.getName()).
                replace(D.METHOD_NAME_GET_TAG, Util.genGet(fe.getName())).
                replace(D.METHOD_NAME_SET_TAG, Util.genSet(fe.getName()));

        return ret;
    }


    public static void main(String[] args) {

    }


}
