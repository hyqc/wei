package com.wei.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class CsvExportUtil {
    private static final String CSV_COLUMN_SEPARATOR = ",";
    private static final String CSV_ROW_SEPARATOR = System.lineSeparator();

    /**
     *
     * @param keys 字段
     * @param names 表头
     * @param dataList 数据
     * @param os
     * @throws IOException
     */
    public static void export(List<String> keys, List<String> names, List<Map<String, Object>> dataList,
                              OutputStream os) throws IOException {
        StringBuffer sb = new StringBuffer();
        for (String name : names) {
            sb.append(name).append(CSV_COLUMN_SEPARATOR);
        }
        sb.append(CSV_ROW_SEPARATOR);
        for (Map<String, Object> data : dataList) {
            for (String key : keys) {
                sb.append(data.get(key)).append(CSV_COLUMN_SEPARATOR);
            }
            sb.append(CSV_ROW_SEPARATOR);
        }
        os.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        os.flush();
    }

    public static void setProp(String name, HttpServletResponse response) throws UnsupportedEncodingException {
        String utf = "UTF-8";
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(name, utf));
    }
}
