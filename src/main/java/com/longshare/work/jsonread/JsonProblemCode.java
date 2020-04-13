package com.longshare.work.jsonread;

import com.longshare.rest.core.problem.ProblemCodeDefinition;

public enum JsonProblemCode implements ProblemCodeDefinition{
    JSON_PACKAGE_ERROR("文件夹路径错误或不存在"),
    JSON_FILES_NULL("目录中无json后缀的文件"),
    JSON_BUFFEREDREADER_ERROR("bufferedReader流关闭失败"),
    ;

    private String detail;
    JsonProblemCode(String detail) {
        this.detail = detail;
    }

    @Override
    public String getTitle() {
        return this.name().toLowerCase();
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public String getTypeFormat() {
        return "https://example.org/problems/%s";
    }
}
