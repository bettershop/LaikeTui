package com.laiketui.root.utils.tool;

import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * exceld导出工具类
 *
 * @author Trick
 * @date 2021/7/14 10:46
 */
public class EasyPoiExcelUtil {


    /**
     * 导出excel
     *
     * @param vo -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/29 16:07
     */
    public static void excelExport(ExcelParamVo vo) throws LaiKeApiException {
        try {
            if (vo.getResponse() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            if (StringUtils.isEmpty(vo.getTitle())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            if (vo.getList() == null || vo.getList().size() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            if (vo.getHeaderList() == null || vo.getHeaderList().length < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            if (vo.getHeaderList().length != vo.getHeaderList().length) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "数据内容和表头数量不匹配");
            }

            //创建Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(vo.getTitle());
            //设置表头样式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            //表头开始位置
            int topTitleIndex = 0;
            //顶部合并单元格-说明专用
            if (vo.getMerge() != null && vo.getMerge().length == 4) {
                //顶部占一行
                HSSFRow rowTop = sheet.createRow(topTitleIndex++);
                //设置表头高度
                rowTop.setHeightInPoints(30);
                //设置内容
                rowTop.createCell(0).setCellStyle(style);
                rowTop.getCell(0).setCellValue(vo.getTopTitle());
                //合并单元格
                CellRangeAddress region = new CellRangeAddress(vo.getMerge()[0], vo.getMerge()[1], vo.getMerge()[2], vo.getMerge()[3]);
                sheet.addMergedRegion(region);
            }

            //创建表头
            HSSFRow row = sheet.createRow(topTitleIndex);
            //设置表头高度
            row.setHeightInPoints(30);
            //生成序号
            if (vo.isNeedNo()) {
                List<String> headerTempList = new ArrayList<>(Arrays.asList(vo.getHeaderList()));
                headerTempList.add(0, "序号");
                vo.setHeaderList(headerTempList.toArray(new String[0]));
            }
            //生成自定义表头
            for (int i = 0; i < vo.getHeaderList().length; i++) {
                row.createCell(i).setCellStyle(style);
                row.getCell(i).setCellValue(vo.getHeaderList()[i]);
            }

            //当前行对应的key
            List<String> valList = DataUtils.convertToList(vo.getValueList());
            if (valList == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            //设置内容
            for (int i = 0; i < vo.getList().size(); i++) {
                //当前找到的数量
                int num = 0;
                //生成当前行
                HSSFRow fieldRow = sheet.createRow(i + topTitleIndex + 1);
                Map<String, Object> dataMap = vo.getList().get(i);
                //循环获取当前列数据
                for (String keyTemp : dataMap.keySet()) {
                    if (num == valList.size()) {
                        break;
                    }
                    //获取当前行数据
                    if (valList.contains(keyTemp)) {
                        //当前行对应的值的坐标
                        int index = valList.indexOf(keyTemp);
                        //获取当前x坐标数据
                        String rowValue = dataMap.get(keyTemp) + "";
                        if (StringUtils.isEmpty(rowValue)) {
                            rowValue = "";
                        }
                        //生成序号,序号每行只生成一个
                        if (vo.isNeedNo()) {
                            if (num == 0) {
                                fieldRow.createCell(0).setCellStyle(style);
                                fieldRow.getCell(0).setCellValue(i + 1);
                            }
                            //加了序号,所以每行偏移一个位置
                            index++;
                        }
                        //创建行 x坐标>样式/值
                        fieldRow.createCell(index).setCellStyle(style);
                        fieldRow.getCell(index).setCellValue(rowValue);
                        num++;
                    }
                }
            }
            for (int i = 0; i < vo.getHeaderList().length; i++) {
                //设置自适应宽度
                sheet.autoSizeColumn(i);
            }
            //设置sheet页
            workbook.setActiveSheet(0);

            vo.getResponse().setContentType("application/msexcel");
            String title = String.format("%s.xls", vo.getTitle());
            vo.getResponse().addHeader("Content-Disposition", "filename=" + URLDecoder.decode(title, GloabConst.Chartset.UTF8));
            OutputStream outputStream = vo.getResponse().getOutputStream();

            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.FILE_EXCEL_ERROR, "EXCEL文件导出失败", "excelExport");
        }
    }

    @Deprecated
    public static void excelExport(String title, String[] headerList, String[] valueList, List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        ExcelParamVo vo = new ExcelParamVo();
        vo.setTitle(title);
        vo.setHeaderList(headerList);
        vo.setValueList(valueList);
        vo.setList(list);
        vo.setResponse(response);
        excelExport(vo);
    }


}
