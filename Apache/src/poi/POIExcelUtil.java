package poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @author xiaofan
 *
 */
public class POIExcelUtil {
    public static final String FILE_EXTENSION_XLS = "xls";

    public static final String FILE_EXTENSION_XLSX = "xlsx";

    public static final Integer pageSize = 10000;

    /**
     * 导入excel（默认格式为xls）.
     * 
     * @param list
     *            将要写入excel的对象链表
     * @param file
     *            excel文件全路径
     * @return boolean
     */
    public static <T> boolean write(List<T> list, File file) {
        try {
            if (list == null || list.size() == 0) {
                return false;
            }
            Workbook wb = getWorkbookForWrite(file);
            CreationHelper createHelper = null;
            Sheet sheet = null; // 工作表
            Row row = null;
            // 判断页数
            int sheetNum = (int) Math.ceil(list.size() / (float) pageSize);
            String[] fieldName = getFiledNameByObject(list.get(0));
            for (int i = 0; i < sheetNum; i++) {
                // 初次导入，定义表头
                createHelper = wb.getCreationHelper();
                sheet = wb.createSheet("sheet" + i);
                row = sheet.createRow(0);
                for (int j = 0; j < fieldName.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(createHelper.createRichTextString(fieldName[j]));
                }
                // 填充表单内容
                int endSize = 0;
                if (list.size() < (i + 1) * pageSize) {
                    endSize = list.size();
                } else {
                    endSize = (i + 1) * pageSize;
                }
                for (int j = i * pageSize; j < endSize; j++) {
                    T obj = list.get(j);
                    int index = 0;
                    Row row1 = sheet.createRow(j + 1 - i * pageSize);
                    for (int k = 0; k < fieldName.length; k++) {
                        Cell cell = row1.createCell(index++);
                        cell.setCellValue(getFieldValueByName(fieldName[k], obj).toString());
                    }
                }
            }
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 导入excel（默认格式为xls）. 多线程,不同线程写不同的sheet.
     * 
     * @param list
     *            将要写入excel的对象链表
     * @param file
     *            excel文件全路径
     * @return boolean
     */
    public static <T> boolean writeFast(List<T> list, File file) {
        try {
            if (list == null || list.size() == 0) {
                return false;
            }
            Workbook wb = getWorkbookForWrite(file);
            // 判断页数
            int sheetNum = (int) Math.ceil(list.size() / (float) pageSize);
            String[] fieldName = getFiledNameByObject(list.get(0));

            ExecutorService service = Executors.newCachedThreadPool();
            CountDownLatch countDownLatch = new CountDownLatch(sheetNum);
            for (int i = 0; i < sheetNum; i++) {
                // 填充表单内容
                int endSize = 0;
                if (list.size() < (i + 1) * pageSize) {
                    endSize = list.size();
                } else {
                    endSize = (i + 1) * pageSize;
                }
                POIRunnable<T> runnable = new POIRunnable<T>(countDownLatch, wb.createSheet("sheet" + i), list, i * pageSize, endSize, fieldName, wb);
                service.execute(runnable);
            }
            countDownLatch.await();
            service.shutdown();
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 追加excel（默认格式为xls）.
     * 
     * @param list
     *            将要写入excel的对象链表
     * @param file
     *            excel文件全路径
     * @return boolean
     */
    public static <T> boolean append(List<T> list, File file) {
        try {
            if (list == null || list.size() == 0) {
                return false;
            }
            Workbook wb = getWorkbookForRead(file);
            CreationHelper createHelper = null;
            Sheet sheet = null;
            Row row = null;
            int sheetNum = wb.getNumberOfSheets() - 1;
            sheet = wb.getSheetAt(sheetNum);
            int rowNum = sheet.getLastRowNum();
            int size = sheetNum * pageSize + rowNum + list.size();
            // 判断页数
            int nowSheetNum = (int) Math.ceil(size / (float) pageSize);
            String[] fieldName = getFiledNameByObject(list.get(0));
            for (int i = sheetNum; i < nowSheetNum; i++) {
                if (i != sheetNum) {
                    // 定义表头
                    sheet = wb.createSheet("sheet" + i);
                    createHelper = wb.getCreationHelper();
                    row = sheet.createRow(0);
                    for (int j = 0; j < fieldName.length; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(createHelper.createRichTextString(fieldName[j]));
                    }
                    // 填充表单内容
                    int endSize = 0;
                    if (list.size() - (pageSize - rowNum) < (i - sheetNum) * pageSize) {
                        endSize = list.size();
                    } else {
                        endSize = (i - sheetNum) * pageSize + pageSize - rowNum;
                    }
                    for (int j = (i - sheetNum) * pageSize - rowNum; j < endSize; j++) {
                        T obj = list.get(j);
                        int index = 0;
                        Row row1 = sheet.createRow(j + 1 - ((i - sheetNum) * pageSize - rowNum));
                        for (int k = 0; k < fieldName.length; k++) {
                            Cell cell = row1.createCell(index++);
                            cell.setCellValue(getFieldValueByName(fieldName[k], obj).toString());
                        }
                    }
                } else {
                    // 填充表单内容
                    int endSize = pageSize - rowNum;
                    if (list.size() < endSize) {
                        endSize = list.size();
                    }
                    for (int j = 0; j < endSize; j++) {
                        T obj = list.get(j);
                        int index = 0;
                        Row row1 = sheet.createRow(j + rowNum + 1);
                        for (int k = 0; k < fieldName.length; k++) {
                            Cell cell = row1.createCell(index++);
                            cell.setCellValue(getFieldValueByName(fieldName[k], obj).toString());
                        }
                    }
                }

            }
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取excel内容.
     * 
     * @param t
     *            保存一条数据的对象
     * @param file
     *            路径
     * @return list
     */
    public static <T> List<T> read(T t, File file) {
        List<T> list = new ArrayList<T>();
        try {
            Workbook wb = getWorkbookForRead(file);
            Sheet sheet = null;
            int sheetNum = wb.getNumberOfSheets();
            String[] field = getFiledNameByObject(t);
            Class cla = t.getClass();
            Map<String, Class> fieldMap = getFieldsInfo(t);
            for (int i = 0; i < sheetNum; i++) {
                sheet = wb.getSheetAt(i);
                for (int j = 0; j < sheet.getLastRowNum(); j++) {
                    Object param = cla.newInstance();
                    for (int k = 0; k < field.length; k++) {
                        String firstLetter = field[k].substring(0, 1).toUpperCase();
                        String setter = "set" + firstLetter + field[k].substring(1);
                        Method method = param.getClass().getMethod(setter, new Class[]{fieldMap.get(field[k])});
                        String cellValue = sheet.getRow(j + 1).getCell(k).getStringCellValue();
                        if (String.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, cellValue);
                        } else if (int.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Integer.valueOf(cellValue));
                        } else if (Integer.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Integer.valueOf(cellValue));
                        } else if (float.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Float.valueOf(cellValue));
                        } else if (Float.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Float.valueOf(cellValue));
                        } else if (double.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Double.valueOf(cellValue));
                        } else if (Double.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Double.valueOf(cellValue));
                        } else if (char.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, cellValue.charAt(0));
                        } else if (Character.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, cellValue.charAt(0));
                        } else if (byte.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Byte.valueOf(cellValue));
                        } else if (Byte.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Byte.valueOf(cellValue));
                        } else if (boolean.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Boolean.valueOf(cellValue));
                        } else if (Boolean.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Boolean.valueOf(cellValue));
                        } else if (short.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Short.valueOf(cellValue));
                        } else if (Short.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Short.valueOf(cellValue));
                        } else if (long.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Long.valueOf(cellValue));
                        } else if (Long.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, Long.valueOf(cellValue));
                        } else if (BigDecimal.class.equals(fieldMap.get(field[k]))) {
                            method.invoke(param, new BigDecimal(cellValue));
                        } else if (Date.class.equals(fieldMap.get(field[k]))) {
                            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
                            method.invoke(param, format.parse(cellValue));
                        }
                    }
                    list.add((T) param);
                }
            }
            wb.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Workbook对象.
     * 
     * @param file
     */
    private static Workbook getWorkbookForWrite(File file) {
        String filename = file.getName();
        String type = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        if (type.equals(FILE_EXTENSION_XLS)) {
            return new HSSFWorkbook();
        }
        if (type.equals(FILE_EXTENSION_XLSX)) {
            return new SXSSFWorkbook(100);
        }
        return null;
    }

    /**
     * 获取Workbook对象.
     * 
     * @param file
     *            file
     * @throws Exception
     *             Exception
     */
    private static Workbook getWorkbookForRead(File file) throws Exception {
        String filename = file.getName();
        String type = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        if (type.equals(FILE_EXTENSION_XLS)) {
            return new HSSFWorkbook(new FileInputStream(file));
        }
        if (type.equals(FILE_EXTENSION_XLSX)) {
            // return new XSSFWorkbook(new FileInputStream(file));
            return new SXSSFWorkbook(100);
        }
        return null;
    }

    /**
     * 获取属性名数组
     */
    private static String[] getFiledNameByObject(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     */
    private static Object getFieldValueByName(String fieldName, Object obj) throws Exception {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = obj.getClass().getMethod(getter, new Class[]{});
        Object value = method.invoke(obj, new Object[]{});
        return value;
    }

    /**
     * @throws Exception
     */
    private static Map<String, Class> getFieldsInfo(Object o) throws Exception {
        Field[] fields = o.getClass().getDeclaredFields();
        Map<String, Class> map = new HashMap<String, Class>();
        for (int i = 0; i < fields.length; i++) {
            map.put(fields[i].getName(), fields[i].getType());
        }
        return map;
    }

    static class POIRunnable<T> implements Runnable {

        private final CountDownLatch countDownLatch;

        private Sheet sheet;

        private int start;

        private int end;

        private List<T> list;

        private String[] fieldName;

        private Workbook wb;

        public POIRunnable(CountDownLatch countDownLatch, Sheet sheet, List<T> list, int start, int end, String[] fieldName, Workbook wb) {
            this.countDownLatch = countDownLatch;
            this.list = list;
            this.sheet = sheet;
            this.start = start;
            this.end = end;
            this.fieldName = fieldName;
            this.wb = wb;
        }

        @Override
        public void run() {
            try {
                CreationHelper createHelper = wb.getCreationHelper();
                Row row = null;
                row = sheet.createRow(0);
                for (int j = 0; j < fieldName.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(createHelper.createRichTextString(fieldName[j]));
                }

                for (int j = start; j < end; j++) {
                    T obj = list.get(j);
                    int index = 0;
                    Row row1 = sheet.createRow(j + 1 - start);
                    for (int k = 0; k < fieldName.length; k++) {
                        Cell cell = row1.createCell(index++);
                        cell.setCellValue(getFieldValueByName(fieldName[k], obj).toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }

    }

}
