package com.wstore.controller;


import com.wstore.common.utils.WstoreResultMsg;
import com.wstore.pojo.admin.Poster;
import com.wstore.service.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PosterController
 * @Author Koi
 * @Date 2018/8/11 11:08
 * @Version 1.0
 */
@Controller
public class PosterController {

    @Autowired
    PosterService posterService;

    @GetMapping("/poster/{type}/{pn}")
    public String toPoster(@PathVariable("type") Integer type, @PathVariable("pn") Integer pn, Map<String,Object> map){
        Map<String, Object> posters = posterService.getPosterByPosition(type, pn);
        map.put("posters",posters);
        return "poster";
    }

    @PostMapping("/poster")
    @ResponseBody
    public WstoreResultMsg addPoster(Poster poster){
        if (poster.getName().length()<1 || poster.getName().length() > 10){
            return WstoreResultMsg.fail().add("error","请输入正确的名称");
        }
        if (poster.getUrl().length()<3){
            return WstoreResultMsg.fail().add("error","请输入正确的链接");
        }
        posterService.addPoster(poster);
        return WstoreResultMsg.success();
    }

    /**
     * 改变广告状态
     * @param id
     * @param status
     */
    @PostMapping("/poster/status/{id}/{status}")
    @ResponseBody
    public void deletePoster(@PathVariable("id") Integer id,@PathVariable("status") Integer status){
        posterService.updataStatus(id,status);
    }
}
