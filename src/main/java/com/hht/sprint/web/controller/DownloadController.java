package com.hht.sprint.web.controller;

import com.hht.sprint.domain.DownloadDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("sprint")
public class DownloadController {

    private static final Logger logger= LoggerFactory.getLogger(DownloadController.class);

    @GetMapping("/dlMain")
    public ModelAndView dlMain(HttpServletRequest request, HttpServletResponse response, Model model){
        return new ModelAndView("dl-main");
    }

    @GetMapping("/dlForm")
    public String dlForm(HttpServletRequest request, HttpServletResponse response, Model model){
        return "dl-form";
    }

    @GetMapping("/dlMoni")
    public String dlMoni(HttpServletRequest request, HttpServletResponse response, Model model){
        return "dl-moni";
    }

    @PostMapping("/beforeDL")
    @ResponseBody
    public DownloadDomain beforeDL(@RequestBody DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model){
        String filePath="/Users/tsk/Desktop/IdeaProjects/sprint/src/main/resources/file/";
        String fileName="sample.jar";
        dd.setFilePath(filePath);
        dd.setFileName(fileName);
        File file=new File(filePath+fileName);
        Long fileSize=file.length();
        dd.setFileSize(fileSize);
        dd.setFileSizeStr(fileSize + "Bytes");
        return dd;
    }

    @PostMapping("realDL")
    public void readDL(@RequestBody DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String filePath=dd.getFilePath();
        String fileName=dd.getFileName();
        Long fileSize=dd.getFileSize();

    }

    @PostMapping("afterDL")
    public DownloadDomain afterDL(@RequestBody DownloadDomain dd, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String filePath=dd.getFilePath();
        String fileName=dd.getFileName();
        Long fileSize=dd.getFileSize();

        return dd;
    }
}
