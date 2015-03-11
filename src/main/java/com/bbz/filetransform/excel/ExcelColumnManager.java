package com.bbz.filetransform.excel;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-8
 * Time: 下午4:54
 */

import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存一个excel文件所有列信息的类
 */

public class ExcelColumnManager{

    private final Sheet sheet;

    public List<ExcelColumn> getExcelColumns() {
        return excelColumns;
    }

    final List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();

    public ExcelColumnManager( Sheet sheet ) {
        this.sheet = sheet;
        genCloumns();

    }

    private void genCloumns() {
        int maxCol = sheet.getRow(0).getLastCellNum();
        for (int col = 0; col < maxCol; col++) {
            ExcelColumn fe = new ExcelColumn();
            fe.setAnnotation( sheet.getRow(0).getCell(col).toString() );
            fe.setHasClient( Boolean.parseBoolean(sheet.getRow(1).getCell(col).toString()) );
            fe.setType( sheet.getRow(2).getCell(col).toString() );
            fe.setName( sheet.getRow(3).getCell(col).toString() );
            excelColumns.add( fe );
            //System.out.println( fe );
        }
    }
}
