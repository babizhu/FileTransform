package com.bbz.filetransform.excel;

import com.bbz.filetransform.PathCfg;
import com.bbz.filetransform.base.IGen;
import com.bbz.filetransform.templet.TempletFile;
import com.bbz.filetransform.templet.TempletType;
import com.bbz.filetransform.util.D;
import com.bbz.filetransform.util.Util;
import com.bbz.tool.common.FileUtil;
import org.apache.poi.ss.usermodel.Sheet;


abstract class AbstractGenJava implements IGen{


    /**
     * 包名，例如game.cfg.fighter.HeroTemplet.java文件的包名fighter
     */
    String packageName;

    /**
     * 包名，例如game.cfg.fighter.HeroTemplet.java文件的类名HeroTemplet
     */
    String className;

    final Sheet sheet;
    String src;


    //private List<FieldElement> fields = new ArrayList<FieldElement>();

    /**
     * @param path  仅包括包名和类名
     * @param sheet excel的sheet
     */
    public AbstractGenJava(String[] path, Sheet sheet){

        this.sheet = sheet;

        className = genClassName(path[1]);
        packageName = path[0];
        src = new TempletFile( TempletType.JAVA, getTempletFileName()).getTempletStr();
    }


    protected abstract String getTempletFileName();


    protected String parseJavaType(FieldElement fe){
        String type = fe.type;
        if( type.equals("int") ) {
            return "Integer.parseInt( element.getChildText(\"";
        } else if( type.equals("short") ) {
            return "Short.parseShort( element.getChildText(\"";
        } else if( type.equals("float") ) {
            return "Float.parseFloat( element.getChildText(\"";
        } else if( type.equals("String") ) {
            return "element.getChildText(\"";
        } else if( type.equals("double") ) {
            return "Double.parseDouble( element.getChildText(\"";
        } else if( type.equals("byte") ) {
            return "Byte.parseByte( element.getChildText(\"";
        } else if( type.equals("char") ) {
            return "(char)" + "Integer.parseInt( element.getChildText(\"";
        } else if( type.equals("boolean") ) {
            return "Boolean.parseBoolean( element.getChildText(\"";
        } else if( type.equals("long") ) {
            return "Long.parseLong( element.getChildText(\"";
        } else {
            return "???";
        }
    }


    /**
     * 通过文件名生成类名
     *
     * @param name 文件名
     * @return 类名
     */
    abstract protected String genClassName(String name);


    /**
     * 处理自定义的内容，不要误删除了
     */
    protected void writeFile(){
        String path = PathCfg.EXCEL_OUTPUT_JAVA_PATH + packageName + "/" + className + D.JAVA_FILE_SUFFIXES;
        String manualContent = "";
        if( Util.isExist( path ) ) {
            String oldData = FileUtil.readTextFile( path );
            int beginPos = oldData.indexOf(D.MANUAL_WORK_BEGIN);
            int endPos = oldData.indexOf(D.MANUAL_WORK_END);
            if( endPos != -1 && beginPos != -1 ) {
                manualContent = oldData.substring(beginPos + D.MANUAL_WORK_BEGIN.length(), endPos);
            }
        }
        src = src.replace(D.MANUAL_WORK_TAG, manualContent);//把自定义的内容加上去
        FileUtil.writeTextFile(path, src);
    }

}


