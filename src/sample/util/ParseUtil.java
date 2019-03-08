package sample.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import sample.listener.ExcelListener;
import sample.listener.ParseListener;
import sample.model.MatchResult;

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
        EasyExcelFactory.readBySax(inputStream, new Sheet(sheetIndex, 1), excelListener);

        inputStream.close();
    }

    public static void beginMatch(List<String> firstList, List<String> secondList, int matchCharCount) {

        HashMap<Integer, List<MatchResult>> result = new HashMap<>();

        for (int i = 0; i < firstList.size(); i++) {

            String firstStr = firstList.get(i);
            LinkedList<MatchResult> matchResultList = new LinkedList<>();
            for (String secondStr : secondList) {

                int repeatCount = match(firstStr, secondStr);
                if (repeatCount >= matchCharCount) {
                    matchResultList.add(new MatchResult(repeatCount, secondStr));
                }
            }

            if (!matchResultList.isEmpty()) {
                result.put(i, matchResultList);
            }
        }

        System.out.println("111");

    }

    /**
     * match two string to find repeat char count.
     * @param firstStr
     * @param secondStr
     * @return
     */
    private static int match(String firstStr, String secondStr) {

        int count = 0;
        for (int i = 0; i < secondStr.length(); i++) {

            if(firstStr.contains(secondStr.charAt(i)+"")) {
                count++;
            }
        }

        return count;
    }
}
