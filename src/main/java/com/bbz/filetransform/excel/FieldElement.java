package com.bbz.filetransform.excel;

import lombok.Data;

/**
 * user         LIUKUN
 * time         2015-1-8 11:58
 */

@Data
public class FieldElement {
    String annotation;//field的注释
    String name;
    String type;
    boolean hasClient;

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
