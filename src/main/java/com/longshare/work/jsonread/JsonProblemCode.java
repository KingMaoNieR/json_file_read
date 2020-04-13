package com.longshare.work.jsonread;

import com.longshare.rest.core.problem.ProblemCodeDefinition;

public enum JsonProblemCode implements ProblemCodeDefinition {
    JSON_PACKAGE_ERROR("文件夹路径错误或不存在"),
    JSON_FILES_NULL("目录中无json后缀的文件"),
    JSON_BUFFEREDREADER_ERROR("BufferedReader流关闭失败"),
    JSON_FILEINPUTSTREAM_ERROR("文件读取失败"),
    JSON_INPUTSTREAMREADER_ERROR("输入流失败"),
    JSON_READLINE_ERROR("读取文件行数据失败"),
    JSON_FILEINPUTSTREAM_CLOSE_ERROR("Fileinputstream流关闭失败"),
    JSON_INPUTSTREAMREADER_CLOSE_ERROR("Inputstreamreader流关闭失败"),
    JSON_BUFFEREDINPUTSTREAM_CLOSE_ERROR("BufferedInputStream流关闭失败"),
    JSON_FILEOUTPUTSTREAM_ERROR("FileOutputStream流输出失败"),
    JSON_FILEOUTPUTSTREAM_CLOSE_ERROR("FileOutputStream流关闭失败"),
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
