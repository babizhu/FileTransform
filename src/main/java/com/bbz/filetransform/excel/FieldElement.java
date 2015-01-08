package com.bbz.filetransform.excel;

import lombok.Data;

/**
 * user         LIUKUN
 * time         2015-1-8 11:58
 * 映射到excel的一列数据
 */

@Data
public class FieldElement {
    private String annotation;//field的注释
    private String name;
    private String type;
    private boolean hasClient;

    @Override
    public String toString() {
        return "FieldElement{" +
                "annotation='" + annotation + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", hasClient=" + hasClient +
                '}';
    }
}
