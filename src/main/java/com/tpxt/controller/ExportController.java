package com.tpxt.controller;

import com.tpxt.dao.ContentDao;
import com.tpxt.entity.Content;
import com.tpxt.utils.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
@RequestMapping("/report")
public class ExportController {

    @Resource
    private ContentDao contentDao;

    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){

        List<Content> contentList = contentDao.listAll();
        //excel标题
        String[] title = {"序号","姓名", "标题","内容","票数", "联系方式", "是否删除"};
        //excel文件名
        String fileName = "投票信息表"+System.currentTimeMillis()+".xls";
        //sheet名
        String sheetName = "投票信息表";
        String[][] contentArray = new String[contentList.size()][7];
        for (int i = 0; i < contentList.size(); i++) {
            Content content = contentList.get(i);
            //标题
            contentArray[i][0] = String.valueOf(content.getId());
            //姓名
            contentArray[i][1] = content.getName();
            //标题
            contentArray[i][2] = content.getTitle();
            //描述
            contentArray[i][3] = content.getContent();
            //票数
            contentArray[i][4] = String.valueOf(content.getTicket());
            //联系方式
            contentArray[i][5] = content.getContactWay();
            //是否删除
            if("1".equals(content.getStatus())){
                contentArray[i][6] = "否";
            }else{
                contentArray[i][6] = "是";
            }

        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExportExcelUtil.getHSSFWorkbook(sheetName, title, contentArray, null);
        this.setResponseHeader(response, fileName);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    //do nothing
                }
            }
        }


    }

    /**
     * 发送响应流方法
     * @param response
     * @param fileName
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=utf-8");
//            response.setContentType("application/x-download;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
