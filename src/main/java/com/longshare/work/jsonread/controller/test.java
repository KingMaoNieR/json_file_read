package com.longshare.work.jsonread.controller;

import com.longshare.work.jsonread.utils.JsonfilereadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class test {

    String url="/Users/mao_nier/IdeaProjects/jsonread/src/test/java/com/longshare/work/jsonread/jsonfiles";

    @Autowired
    private JsonfilereadUtil jsonfilereadUtil;

    @RequestMapping(value = "/jsondbread",method = RequestMethod.GET)
    public void ptest() {
        jsonfilereadUtil.Jsonfileread(url);
    }
}
