package com.longshare.work.jsonread.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
public class JsonfilereadUtil {

    public void Jsonfileread(String packagepath) {
        File f = new File(packagepath);
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
            if(sqlfile.exists()){
                sqlfile.delete();
            }
            for (File path : paths) {
                // prints file and directory paths
                System.out.println(path);
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

        } catch (Exception e) {
            log.error("目录中无json后缀的文件");
        }
    }

    public String filedateread(File path) {
        StringBuffer strbuffer = new StringBuffer();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String read;
            while (null != (read = bufferedReader.readLine())) {
                strbuffer.append(read);
            }
            bufferedReader.close();
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
            log.error("流关闭失败");
        }
        return null;
    }

    public String creatdate(String id, String content) {
        String sql = "INSERT INTO REST_UI_COMP_CONF (ID,CONTENT) VALUES ('" + id + "','" + content + "')";
        return sql;
    }
}
