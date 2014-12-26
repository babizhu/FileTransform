package com.bbz.filetransform.templet;


import com.bbz.tool.common.FileUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午5:30
 */


public class TempletFile{
    private final String templetPath;

    public TempletFile(TempletType type, String templetPath){

        this.templetPath = type.getDir() + templetPath;
    }

    public String getTempletStr(){
        return FileUtil.readTextFile( templetPath );
//        return Util.readTextFile(templetPath);
    }

    public static void main(String[] args){
        String file = "xTemplet.t";
        TempletFile t = new TempletFile(TempletType.DB, file);
        System.out.println(t.getTempletStr());

        String a = "fsafa#adfds";
        a = a.replace("#a", "xxxxx");
        System.out.println(a);
    }

}
