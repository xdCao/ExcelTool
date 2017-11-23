package job;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import utils.ReadFileUtil;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * created by xdCao on 2017/11/23
 */

public class Compare {

    private File smallFile;
    private File biggerFile;

    private ArrayList smallList;
    private ArrayList bigList;


    public Compare(File smallFile, File biggerFile) {
        this.smallFile = smallFile;
        this.biggerFile = biggerFile;
    }

    public ArrayList<Integer> compareFiles(int column1, int column2) {
        if (ReadFileUtil.checkVersion(smallFile)&&ReadFileUtil.checkVersion(biggerFile)){
            smallList=new ArrayList<XSSFRow>();
            bigList=new ArrayList<XSSFRow>();
        }else if((!ReadFileUtil.checkVersion(smallFile))&&(!ReadFileUtil.checkVersion(biggerFile))){
            smallList=new ArrayList<HSSFRow>();
            bigList=new ArrayList<HSSFRow>();
        }
        ReadFileUtil.readExcel(smallFile,smallList);
        ReadFileUtil.readExcel(biggerFile,bigList);

        System.out.println("较小的文件内容： ");
        printAllElements(smallList);
        System.out.println("较大的文件内容： ");
        printAllElements(bigList);

        ArrayList<Integer> rows=getRowsSame(smallList,bigList,column1,column2);
        System.out.println();
        System.out.println("条目编号： ");
        for (Integer integer:rows){
            System.out.println(integer);
        }
        return rows;

    }

    private ArrayList<Integer> getRowsSame(ArrayList smallList, ArrayList bigList,int column1,int column2) {
        ArrayList<Integer> rows=new ArrayList<Integer>();
        for (int i=0;i<smallList.size();i++){
            for (int k=0;k<bigList.size();k++){
                if (compareRow(i,k,column1,column2)){
                    Object o = bigList.get(k);
                    if (o instanceof XSSFRow){
                        rows.add(((XSSFRow) o).getRowNum());
                    }else if (o instanceof HSSFRow){
                        rows.add(((HSSFRow) o).getRowNum());
                    }
                }
            }
        }
        return rows;
    }

    private boolean compareRow(int i, int k,int column1,int column2) {
        Object small = smallList.get(i);
        Object big = bigList.get(k);
        if ((small instanceof XSSFRow)&&(big instanceof XSSFRow)){
            XSSFRow smallRow=(XSSFRow)small;
            XSSFRow bigRow=(XSSFRow)big;
            XSSFCell smallRowCell = smallRow.getCell(column1);
            XSSFCell bigRowCell = bigRow.getCell(column2);
            return compareCell(smallRowCell,bigRowCell);
        }else if ((small instanceof HSSFRow)&&(big instanceof HSSFRow)){
            HSSFRow smallRow=(HSSFRow)small;
            HSSFRow bigRow=(HSSFRow)big;
            HSSFCell smallRowCell = smallRow.getCell(column1);
            HSSFCell bigRowCell = bigRow.getCell(column2);
            return compareCell(smallRowCell, bigRowCell);
        } else {
            return false;
        }
    }

    private boolean compareCell(XSSFCell smallRowCell, XSSFCell bigRowCell) {
        if (smallRowCell.getCellType()==bigRowCell.getCellType()){
            switch (smallRowCell.getCellType()){
                case HSSFCell.CELL_TYPE_NUMERIC:
                    return smallRowCell.getNumericCellValue()==bigRowCell.getNumericCellValue();
                case HSSFCell.CELL_TYPE_STRING:
                    return smallRowCell.getStringCellValue().equals(bigRowCell.getStringCellValue());
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    return smallRowCell.getBooleanCellValue()==bigRowCell.getBooleanCellValue();
                case HSSFCell.CELL_TYPE_BLANK:
                    return true;
                case HSSFCell.CELL_TYPE_ERROR:
                    return false;
                default:
                    return false;
            }
        }else {
            return false;
        }
    }

    private boolean compareCell(HSSFCell smallRowCell, HSSFCell bigRowCell) {
        if (smallRowCell.getCellType()==bigRowCell.getCellType()){
            switch (smallRowCell.getCellType()){
                case HSSFCell.CELL_TYPE_NUMERIC:
                    return smallRowCell.getNumericCellValue()==bigRowCell.getNumericCellValue();
                case HSSFCell.CELL_TYPE_STRING:
                    return smallRowCell.getStringCellValue().equals(bigRowCell.getStringCellValue());
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    return smallRowCell.getBooleanCellValue()==bigRowCell.getBooleanCellValue();
                case HSSFCell.CELL_TYPE_BLANK:
                    return true;
                case HSSFCell.CELL_TYPE_ERROR:
                    return false;
                default:
                    return false;
            }
        }else {
            return false;
        }
    }


    private void printAllElements(ArrayList list) {
        for (int i=0;i<list.size();i++){
            Object o = list.get(i);
            if (o instanceof XSSFRow){
                XSSFRow row=(XSSFRow)o;
                int startCell=row.getFirstCellNum();
                int endCell=row.getLastCellNum();
                for (int k=startCell;k<endCell;k++){
                    System.out.print(row.getCell(k)+" ");
                }
                System.out.print("\n");
            }else if (o instanceof HSSFRow){
                HSSFRow row=(HSSFRow)o;
                int startCell=row.getFirstCellNum();
                int endCell=row.getLastCellNum();
                for (int k=startCell;k<endCell;k++){
                    System.out.print(row.getCell(k)+" ");
                }
                System.out.print("\n");
            }
        }
    }


}
