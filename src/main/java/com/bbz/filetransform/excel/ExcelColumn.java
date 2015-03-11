package com.bbz.filetransform.excel;

import lombok.Data;

/**
 * user         LIUKUN
 * time         2015-1-8 11:58
 * 映射到excel的一列数据
 */

@Data
public class ExcelColumn{
    private String annotation;//field的注释
    private String name;
    private String type;
    private boolean hasClient;


}
