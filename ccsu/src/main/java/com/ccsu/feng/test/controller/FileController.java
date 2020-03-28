package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.utils.FtpFileUtil;
import com.ccsu.feng.test.utils.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author admin
 * @create 2020-02-19-19:24
 */
@RestController
@RequestMapping("/admin/file")
public class FileController {

    public static final String HOST_URL = "http://www.liaoyunfeng.top/images/";

    //ftp处理文件上传
    @RequestMapping(value = "/ftpUploadImg")
    public @ResponseBody
    Result<String> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        //获取后缀名
        String sname = fileName.substring(fileName.lastIndexOf("."));
        String substring = fileName.substring(0, fileName.lastIndexOf("."));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = df.format(new Date());
        fileName = substring + "—"+format+"-" + sname;
        InputStream inputStream = file.getInputStream();
        String filePath = null;
        boolean flag = FtpFileUtil.uploadFile(fileName, inputStream);
        if (!flag) {
            return Result.error(ResultEnum.ERROR.getCode(), "上传图片失败");
        }
        filePath = HOST_URL + fileName;
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), filePath);
    }

}
