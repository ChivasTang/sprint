package com.hht.sprint.web.controller;

import com.hht.sprint.domain.DownloadDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/sprint")
public class DownloadController {

    private static final Logger logger= LoggerFactory.getLogger(DownloadController.class);

    @GetMapping("/dlMain")
    public ModelAndView dlMain(HttpServletRequest request, HttpServletResponse response, Model model){
        return new ModelAndView("dl-main");
    }

    @GetMapping("/dlMoni")
    public String dlMoni(HttpServletRequest request, HttpServletResponse response, Model model){
        return "dl-moni";
    }

    @PostMapping("/beforeDL")
    @ResponseBody
    public DownloadDomain beforeDL(@RequestBody DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model){
        logger.debug("ダウンロード前処理　-Start!-");
        String filePath="/Users/tsk/Desktop/IdeaProjects/sprint/src/main/resources/file/";
        String fileName="sample.zip";
        dd.setFilePath(filePath);
        dd.setFileName(fileName);
        File file=new File(filePath+fileName);
        Long fileSize=file.length();
        dd.setFileSize(fileSize);
        dd.setFileSizeStr(fileSize + "Bytes");
        logger.debug("ダウンロード前処理　-End!-");
        return dd;
    }

    @PostMapping(value = "realDL")
    public void readDL(@ModelAttribute DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        logger.debug("ダウンロード処理　-Start!-");
        String filePath=dd.getFilePath();
        String fileName=dd.getFileName();
        Long fileSize=dd.getFileSize();
        File file=new File(filePath+fileName);
        if(!fileSize.equals(file.length())){
            logger.error("ファイルは、存在しません。");
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);

        ServletOutputStream servletOutputStream = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            servletOutputStream = response.getOutputStream();

            byte[] arBytes = new byte[2048];
            Long count;
            while ((count = (long)fileInputStream.read(arBytes)) > 0){
                servletOutputStream.write(arBytes, 0, count.intValue());
            }
            servletOutputStream.flush();
            logger.debug("ダウンロード処理　-End!-");
        }catch (IOException e) {
            logger.error("ダウンロード処理　-Error!-");
            logger.error(e.getMessage());
        } finally{
            assert servletOutputStream != null;
            servletOutputStream.close();
            fileInputStream.close();
            logger.debug("ダウンロード処理　-Finished!-");
        }
    }

    @PostMapping("afterDL")
    @ResponseBody
    public DownloadDomain afterDL(@RequestBody DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        logger.debug("ダウンロード後処理　-Start!-");
        dd.setCompleted(true);
        logger.info(dd.toString());
        logger.debug("ダウンロード後処理　-End!-");
        return dd;
    }
}
