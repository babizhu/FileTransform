package com.bbz.filetransform.excel;

import com.bbz.filetransform.PathCfg;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-22
 * Time: 下午5:56
 * 包的启动类，通过excel文件生成相应的配置表文件和xml文件
 */
public class Launch {
    public static void main(String[] args) {


        GenXmlAndCfg gen = new GenXmlAndCfg();

        gen.genAll( PathCfg.EXCEL_PATH);
    }
}
