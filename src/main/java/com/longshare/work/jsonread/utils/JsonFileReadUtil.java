package com.longshare.work.jsonread.utils;

import com.alibaba.fastjson.JSONObject;
import com.longshare.rest.core.problem.ProblemSolver;
import com.longshare.work.jsonread.JsonProblemCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@Component
public class JsonFileReadUtil {

    public void jsonFileRead(String packagePath, HttpServletResponse response) {
        File[] paths = this.getFileArrayByPath(packagePath);
        try {
            StringBuilder sb = new StringBuilder();
            for (File path : paths) {
                sb.append(fileDateRead(path));
            }
            downloadFile(new ByteArrayInputStream(sb.toString().getBytes()), response);
        } catch (Exception e) {
            log.error("程序执行失败，-e：{}", e);
            throw ProblemSolver.server(JsonProblemCode.JSON_RUN_ERROR).build();
        }
    }

    private File[] getFileArrayByPath(String packagePath) {
        File path;
        try {
            path = new File(packagePath);
        } catch (Exception e) {
            log.error("文件夹路径错误或不存在，-filePath：{}", packagePath);
            throw ProblemSolver.server(JsonProblemCode.JSON_PACKAGE_ERROR).build();
        }
        File[] paths;
        FilenameFilter fileNameFilter = (file, name) -> name.endsWith(".json");
        try {
            paths = path.listFiles(fileNameFilter);
        } catch (Exception e) {
            log.error("目录中无json后缀的文件，-filePath：{}", packagePath);
            throw ProblemSolver.server(JsonProblemCode.JSON_FILES_NULL).build();
        }
        return paths;
    }

    public String fileDateRead(File path) {
        StringBuilder strBuilder = new StringBuilder();
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String read;
            while (null != (read = bufferedReader.readLine())) {   //IOException
                strBuilder.append(read);
            }
            String content = strBuilder.toString().replace(" ", "");
            String pageCode = JSONObject.parseObject(content).getString("pageCode");
            String sqlScript = creatDate(pageCode, content);
            return sqlScript.concat("\n");
        } catch (IOException e) {
            log.error("读取文件行数据失败，-filePath：{}", path);
            throw ProblemSolver.server(JsonProblemCode.JSON_READLINE_ERROR).build();
        }
    }

    public String creatDate(String pageCode, String content) {
        String sqlScript = "INSERT INTO REST_UI_COMP_CONF (ID,CONTENT) VALUES ('" + pageCode + "','" + content + "')";
        return sqlScript;
    }

    public void downloadFile(InputStream inputStream, HttpServletResponse response) {
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=json.sql");
        try {
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            log.error("Download失败，-e：{}", e);
            throw ProblemSolver.server(JsonProblemCode.JSON_DOWNLOAD_ERROR).build();
        }
    }
}
