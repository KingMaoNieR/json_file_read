package com.longshare.work.jsonread.controller;

import com.longshare.work.jsonread.utils.JsonfilereadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class JsonreadController {

    @Autowired
    private JsonfilereadUtil jsonfilereadUtil;

    @RequestMapping(value = "/json_read", method = RequestMethod.GET)
    public void json_read(String url , HttpServletResponse response) {
        jsonfilereadUtil.Jsonfileread(url , response);
    }
}
