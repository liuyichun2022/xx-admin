package com.xx.p.modules.admin.service.impl;

import com.xx.p.modules.admin.service.CatchRemoteImageService;
import com.xx.p.modules.admin.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class CatchRemoteImageServiceImpl implements CatchRemoteImageService {
    
    @Resource
    private MinioService minioService;
    
    @Override
    public String reSave(String content) {

        //解析html
        Document doc = Jsoup.parse(content);
        //获取所有img标签
        Elements imgList = doc.body().getElementsByTag("img");
        for(Element element : imgList){
            //获取src属性值
            String src = element.attr("src");
            System.out.println(src);

            if (src.indexOf("xiumi.us") != -1) {
                String newSrc = minioService.upload(src);
                //修改src属性值
                element.attr("src", newSrc);
            }
        }
        return doc.html();
    }
}
