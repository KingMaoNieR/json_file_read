package com.longshare.work.jsonread.utils;

import com.alibaba.fastjson.JSONObject;
import com.longshare.rest.core.problem.ProblemSolver;
import com.longshare.work.jsonread.JsonProblemCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zalando.problem.Status;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@Component
public class JsonfilereadUtil {

    public void Jsonfileread(String packagepath, HttpServletResponse response) {
        File f = null;
        try {
            f = new File(packagepath);
        } catch (Exception e) {
            log.error("文件夹路径错误或不存在");
            throw ProblemSolver.server(JsonProblemCode.JSON_PACKAGE_ERROR).withStatus(Status.UNPROCESSABLE_ENTITY).build();
        }
        File[] paths = null;
        try {
            FilenameFilter fileNameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.lastIndexOf('.') > 0) {
                        // get last index for '.' char
                        int lastIndex = name.lastIndexOf('.');

                        // get extension
                        String str = name.substring(lastIndex);

                        // match path name extension
                        if (str.equals(".json")) {
                            return true;
                        }
                    }
                    return false;
                }
            };
            paths = f.listFiles(fileNameFilter);
            File sqlfile = new File("json.sql");
            if (sqlfile.exists()) {
                sqlfile.delete();
            }
            for (File path : paths) {
                String sql = filedateread(path);
                sqlfile.createNewFile();
                FileOutputStream stream = new FileOutputStream(sqlfile, true);
                byte[] buf = sql.getBytes();
                stream.write(buf);
                String newLine = System.getProperty("line.separator");
                stream.write(newLine.getBytes());
                stream.flush();
                stream.close();
            }
            Download(sqlfile.toString(), sqlfile.getAbsolutePath(), response);
        } catch (Exception e) {
            log.error("目录中无json后缀的文件");
            throw ProblemSolver.server(JsonProblemCode.JSON_FILES_NULL).withStatus(Status.UNPROCESSABLE_ENTITY).build();
        }
    }

    public String filedateread(File path) {
        StringBuffer strbuffer = new StringBuffer();
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            fileInputStream = new FileInputStream(path);  //FileNotFoundException
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8"); //UnsupportedEncodingException
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String read;
            while (null != (read = bufferedReader.readLine())) {   //IOException
                strbuffer.append(read);
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                log.error("bufferedReader流关闭失败");
                throw ProblemSolver.server(JsonProblemCode.JSON_BUFFEREDREADER_ERROR).withStatus(Status.UNPROCESSABLE_ENTITY).build();
            }
            System.out.println(strbuffer.toString().replace(" ", ""));
            JSONObject jsonObject = JSONObject.parseObject(strbuffer.toString().replace(" ", ""));
            String pageCode = jsonObject.getString("pageCode");
            System.out.println(pageCode);
            String sql = creatdate(pageCode, strbuffer.toString().replace(" ", ""));
            return sql;
        } catch (UnsupportedEncodingException e) {
            log.error("输入流失败");

        } catch (FileNotFoundException e) {
            log.error("文件读取失败");
        } catch (IOException e) {
            log.error("读取文件行数据失败");
        }finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.error("fileInputStream流关闭失败");
            }
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                log.error("inputStreamReader流关闭失败");
            }
        }
        return null;
    }

    public String creatdate(String id, String content) {
        String sql = "INSERT INTO REST_UI_COMP_CONF (ID,CONTENT) VALUES ('" + id + "','" + content + "')";
        return sql;
    }

    public void Download(String filename, String filepath, HttpServletResponse response) {
        File file = new File(filepath);
        if (file.exists()) {
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    outputStream.flush();
                    i = bis.read(buffer);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
