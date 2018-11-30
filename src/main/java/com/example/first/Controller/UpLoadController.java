package com.example.first.Controller;

import com.csvreader.CsvWriter;
import com.example.first.Util.CsvWriteUtil;
import com.example.first.annocation.Log;
import com.example.first.bean.User;
import com.example.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UpLoadController {

    @Autowired
    private UserService userService;

    //生成csv文件模板代码
    @RequestMapping("/downloadCsv")
    public String downloadCsv(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "aim.csv";// 设置文件名，根据业务需要替换成要下载的文件名
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        List<User> userList = userService.findUserAll(new User());

        try(OutputStream os = response.getOutputStream();) {
            CsvWriter csvWriter = new CsvWriter(os,',', Charset.forName("UTF-8"));
            String[] csvHeaders = { "编号", "姓名", "邮箱" };
            csvWriter.writeRecord(csvHeaders);
            userList.stream()
                    .map(user -> {String[] res ={user.getUserId().toString(),user.getUserName(),user.getUserEmail()};return res;})
                    .forEach(a -> CsvWriteUtil.writeRecord(csvWriter,a));
            csvWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
