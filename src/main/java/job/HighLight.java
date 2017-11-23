package job;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.*;
import utils.ReadFileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 * created by xdCao on 2017/11/23
 */

public class HighLight {

    private ArrayList<Integer> rowNums;
    private File bigFile;

    public HighLight(ArrayList<Integer> rowNums, File biggerFile) {
        this.bigFile=biggerFile;
        this.rowNums=rowNums;
    }

    public void highLight(){
        if (ReadFileUtil.checkVersion(bigFile)){
            highLightNew();
        }else {
            highLightOld();
        }


    }

    private void highLightNew(){
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(bigFile);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFCellStyle cellStyle = xssfWorkbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(new XSSFColor(Color.RED));

            for (Integer integer:rowNums){
                XSSFRow row = xssfSheet.getRow(integer);
                row.setRowStyle(cellStyle);
            }
            xssfWorkbook.write(new FileOutputStream(new File("D://"+System.currentTimeMillis()+".xlsx")));
            JOptionPane.showMessageDialog(null,"发现"+rowNums.size()+"个条目相同.已上色，路径："+"D://"+System.currentTimeMillis()+".xlsx");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void highLightOld(){
        POIFSFileSystem poifsFileSystem=null;
        HSSFWorkbook hssfWorkbook= null;
        try {
            poifsFileSystem=new POIFSFileSystem(new FileInputStream(bigFile));
            hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
            HSSFSheet hssfSheet=hssfWorkbook.getSheetAt(0);
            HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
            cellStyle.setFillBackgroundColor(new HSSFColor.RED().getIndex());
            for (Integer integer:rowNums){
                HSSFRow row = hssfSheet.getRow(integer);
                row.setRowStyle(cellStyle);
            }
            hssfWorkbook.write(new FileOutputStream(new File("D://"+System.currentTimeMillis()+".xls")));
            JOptionPane.showMessageDialog(null,"发现"+rowNums.size()+"个条目相同.已上色，路径："+"D://"+System.currentTimeMillis()+".xls");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
