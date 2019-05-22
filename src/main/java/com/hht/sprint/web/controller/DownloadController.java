package com.hht.sprint.web.controller;

import com.hht.sprint.domain.DownloadDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
public class DownloadController {

    private static final Logger logger= LoggerFactory.getLogger(DownloadController.class);

    @GetMapping("/sprint5")
    public ModelAndView sprint5(HttpServletRequest request, HttpServletResponse response, Model model){
        return new ModelAndView("sprint5");
    }

    @PostMapping("/beforeDL")
    @ResponseBody
    public DownloadDomain beforeDL(@RequestBody DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model){
        String filePath="/Users/tsk/Desktop/IdeaProjects/sprint/src/main/resources/file/";
        String fileName="sample.jar";
        dd.setFilePath(filePath);
        dd.setFileName(fileName);
        File file=new File(filePath+fileName);
        dd.setFileSize(file.length());
        return dd;
    }

    @PostMapping("realDL")
    public void readDL(@RequestBody DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String filePath=dd.getFilePath();
        String fileName=dd.getFileName();
        Long fileSize=dd.getFileSize();
        InputStream ins = null;
        BufferedInputStream bins = null;
        OutputStream outs = null;
        BufferedOutputStream bouts = null;
        File file = new File(filePath+fileName);
        if (!file.exists() || !fileSize.equals(file.length())) {
            logger.error("指定されているファイルが存在しない。");
        }

        long currentLen = 0;// 読み取り済ファイルサイズ
        long totleLen = file.length();// ファイルのサイズを取得
        Long percent; //ダウンロードの進捗
        try {
            ins = new FileInputStream(file.getPath());
            bins = new BufferedInputStream(ins);
            outs = response.getOutputStream();
            bouts = new BufferedOutputStream(outs);
            response.setContentType("application/force-download");// 设置response内容的类型
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));// ヘッダ情報を設定
            int bytesRead;
            byte[] buffer = new byte[4096];
            // ダウンロード開始
            while ((bytesRead = bins.read(buffer)) != -1){
                currentLen += bytesRead;
                bouts.write(buffer, 0, bytesRead);
                // ダウンロード進捗監視
                percent = Math.round(currentLen * 1.0 / totleLen * 100);
                logger.info("ダウンロードの進捗:{}",percent+"%");
                if(percent == 100) {
                    logger.info("ダウンロード完了。");
                }
            }
        } catch (IOException e) {
            percent = Math.round(currentLen * 1.0 / totleLen * 100);
            logger.error("ダウンロードが中断されました、目前のダウンロードの進捗：{};{}",percent +"%",e.getMessage(),e);
        } finally {
            if (ins != null) {
                ins.close();
            }
            if (bins != null) {
                bins.close();
            }
            if (outs != null) {
                outs.close();
            }
            if (bouts != null) {
                bouts.flush();
                bouts.close();
            }
        }
    }
}
