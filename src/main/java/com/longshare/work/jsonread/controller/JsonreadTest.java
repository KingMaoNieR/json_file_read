package com.longshare.work.jsonread.controller;

import com.longshare.work.jsonread.utils.JsonfilereadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class JsonreadTest {

    @Autowired
    private JsonfilereadUtil jsonfilereadUtil;

    @RequestMapping(value = "/jsondbread", method = RequestMethod.GET)
    public void ptest(String url , HttpServletResponse response) {
        jsonfilereadUtil.Jsonfileread(url , response);
    }
}
