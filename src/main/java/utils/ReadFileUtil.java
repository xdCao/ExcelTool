package utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * created by xdCao on 2017/11/23
 */

public class ReadFileUtil {


    public static void readExcel(File file, ArrayList list){
        if (checkVersion(file)){
            readNewExcel(file,list);
        }else {
            readOldExcel(file,list);
        }
    }


    //检查文件格式版本，老版返回false，新版返回true
    public static boolean checkVersion(File file){
        if (file.isFile()){
            if (file.getName().endsWith(".xls")){
                return false;
            }else if (file.getName().endsWith(".xlsx")){
                return true;
            }else {
                throw new RuntimeException("文件格式不对!!");
            }
        }else {
            throw new RuntimeException("请选择文件!!");
        }
    }


    /*
    读老版xls格式文件
     */
    private static void readOldExcel(File file,ArrayList list){

        try {
            POIFSFileSystem poifsFileSystem=new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(poifsFileSystem);
            HSSFSheet hssfSheet=hssfWorkbook.getSheetAt(0);

            int rowStart=hssfSheet.getFirstRowNum();
            int rowEnd=hssfSheet.getLastRowNum();

            for (int i=rowStart;i<rowEnd;i++){
                HSSFRow row=hssfSheet.getRow(i);
                if (null==row)
                    continue;
                list.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readNewExcel(File file,ArrayList list){
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(file);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            int rowstart = xssfSheet.getFirstRowNum();
            int rowEnd = xssfSheet.getLastRowNum();
            for(int i=rowstart;i<=rowEnd;i++)
            {
                XSSFRow row = xssfSheet.getRow(i);
                if(null == row)
                    continue;
                list.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }


}
