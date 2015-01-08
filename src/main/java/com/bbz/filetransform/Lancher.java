package com.bbz.filetransform;

import com.bbz.filetransform.excel.cfg.GenXmlAndCfg;

/**
 * user         LIUKUN
 * time         2014-12-26 10:51
 */

public class Lancher{

    public static void main( String[] args ){
        GenXmlAndCfg gen = new GenXmlAndCfg();

        gen.genAll( PathCfg.EXCEL_PATH );
    }
}
