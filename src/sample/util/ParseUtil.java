package sample.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.util.List;

import sample.listener.ExcelListener;
import sample.listener.ParseListener;

/**
 * @Author: haitian
 * @Date: 2019/3/6 10:09 PM
 * @Description:
 */
public class ParseUtil {

    /**
     * parse sheet in excel.
     * @param filename
     * @param sheetIndex
     * @throws Exception
     */
    public static void parseSheetInExcel(String filename, int sheetIndex, ParseListener listener) throws Exception {

        InputStream inputStream = FileUtil.getFileInputStream(filename);
        ExcelListener excelListener = new ExcelListener(listener);
        EasyExcelFactory.readBySax(inputStream, new Sheet(0, 1), excelListener);

        inputStream.close();
    }
}
