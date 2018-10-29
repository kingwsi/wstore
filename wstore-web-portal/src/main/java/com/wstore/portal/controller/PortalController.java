package com.wstore.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wstore.pojo.admin.Category;
import com.wstore.portal.service.PortalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProtalController
 * @Author Koi
 * @Date 2018/8/14 12:24
 * @Version 1.0
 */
@Controller
public class PortalController {

    @Reference
    PortalService portalService;

    @GetMapping("/index")
    public String toPortal(){
        /*List<Category> categories = portalService.getAllCategory();
        Map<String, Object> posters = portalService.getAllPoster();
        map.put("categories",categories);
        map.put("posters",posters);*/
        return "index";
    }

    @GetMapping("/item")
    public String item(){
        return "index";
    }

    @GetMapping("/index/debug/json")
    @ResponseBody
    public Map<String,Object> json() throws IOException {
        Map<String,Object> map = new HashMap<>();
        List<Category> categories = portalService.getAllCategory();
        Map<String, Object> posters = portalService.getAllPoster();
        map.put("categories",categories);
        map.put("posters",posters);

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("categories",categories);
        context.setVariable("posters",posters);

        FileWriter writer = new FileWriter("wstore-web-portal\\src\\main\\resources\\templates\\index.html");
        engine.process("templates",context,writer);
        writer.close();
        return map;
    }
}
