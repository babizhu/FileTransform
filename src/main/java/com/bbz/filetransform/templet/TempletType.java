package com.bbz.filetransform.templet;

import com.bbz.filetransform.PathCfg;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-21
 * Time: 下午3:21
 */
public enum TempletType{
    DB{
        @Override
        String getDir(){
            return PathCfg.DB_TEMPLET_PATH;
        }
    },
    JAVA{
        @Override
        String getDir(){
            return PathCfg.JAVA_TEMPLET_PATH;

        }


    },
    DJAVA{
        @Override
        String getDir(){
            return PathCfg.D_JAVA_TEMPLET_PATH;

        }
    };

    abstract String getDir();
}