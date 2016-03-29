package poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

/**
 * 本util是公司项目需要导出为Excel，小组成员合力制作（尤其感谢shaorong、xuanwei）
 *
 */
public class POITest {
    /**
     * 大数据量测试.
     * 
     * @throws ParseException
     */
    @Test
    public void testPOIBigWrite() throws ParseException {
        List<PersonVo> list = new LinkedList<PersonVo>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) { // 10W数据，约150s
            PersonVo vo = new PersonVo(i);
            list.add(vo);
        }
        POIExcelUtil.writeFast(list, new File("d:/bigtest2.xlsx"));
        System.err.println("耗时：" + (System.currentTimeMillis() - start) / 1000 + "s");
    }

    @Test
    public void createExcle() throws Exception {
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("第一页");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("test");
        book.write(new FileOutputStream(new File("d:/POI.xls")));
        book.close();
    }

    @Test
    public void readExcle() throws Exception {
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(new File("d:/POI.xls")));
        HSSFSheet sheet = book.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(1);
        System.out.println(cell.getStringCellValue());
        book.close();
    }

    @Test
    public void readAndCreate() throws Exception {
        // 读取
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(new File("d:/POI.xls")));
        HSSFSheet sheet = book.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell = row.getCell(0);
        System.out.println(cell.getStringCellValue());
        // 创建
        HSSFWorkbook book2 = new HSSFWorkbook();
        HSSFSheet sheet2 = book2.createSheet("第一页");
        HSSFRow row2 = sheet2.createRow(0);
        HSSFCell cell2 = row2.createCell(0);
        cell2.setCellValue(cell.getStringCellValue() + " new");
        book2.write(new FileOutputStream(new File("d:/POI2.xls")));
        book2.close();
        book.close();
    }

    @Test
    public void testPOIExcelUtilWrite() throws ParseException {
        List<PersonVo> list = new LinkedList<PersonVo>();
        for (int i = 0; i < 400; i++) {
            PersonVo vo = new PersonVo(i);
            list.add(vo);
        }
        POIExcelUtil.write(list, new File("d:/222.xlsx"));
    }

    @Test
    public void testPOIExcelUtilAppend() throws ParseException {
        List<PersonVo> list = new LinkedList<PersonVo>();
        for (int i = 0; i < 100; i++) {
            PersonVo vo = new PersonVo(i);
            list.add(vo);
        }
        POIExcelUtil.append(list, new File("d:/222.xlsx"));
    }

    @Test
    public void testPOIExcelUtilRead() {
        List<PersonVo> list = POIExcelUtil.read(new PersonVo(), new File("d:/222.xlsx"));
        System.out.println(list.size());
    }

}
