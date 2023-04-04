package com.xx.p.modules.admin.controller;

import com.xx.p.modules.admin.dto.Ueditor;
import com.xx.p.modules.admin.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequestMapping("/ueditor")
@Controller
@CrossOrigin
public class UeditorController {

    @Resource
    private MinioService minioService;

    @RequestMapping(value = "/cos")
    @ResponseBody
    public Object server(String action, String callback, MultipartFile upfile, HttpServletResponse response) {
        Object result = null;
        switch (action) {
            case Ueditor.ACTION_CONFIG:
                String result1 = "";
                if (callback != null) {
                    result1 = callback + "(" + Ueditor.UEDITOR_CONFIG + ")";
                } else {
                    result1 = Ueditor.UEDITOR_CONFIG;
                }
                try {
                    response.getWriter().write(result1);//返回的十回调函数
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Ueditor.ACTION_UPLOADFILE:
            case Ueditor.ACTION_UPLOADVIDEO:
            case Ueditor.ACTION_UPLOADIMAGE:
                String url = minioService.upload(upfile);
                Ueditor ueditor = new Ueditor();
                ueditor.setUrl(url);
                ueditor.setState(Ueditor.ACTION_SUCCESS);
                ueditor.setTitle(upfile.getOriginalFilename());
                result = ueditor;
                break;
            default:
        }
        return result;
    }
}
