package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.util.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@RestController
public class UploadController {
//    //本地存储
//    @PostMapping("/upload")
//    public Result upload(String username, Integer age, MultipartFile image) throws IOException {
//        log.info("文件上传：{},{},{}",username,age,image);
//        //获取原始的文件名
//        //构造唯一的文件名（不能重复）---uuid(通用唯一识别码)
//        String originalFilename = image.getOriginalFilename();
//        int index = originalFilename.lastIndexOf(".");
//        String extname = originalFilename.substring(index);
//        String newFileName = UUID.randomUUID().toString() + extname;
//        log.info("新的文件名{}",newFileName);
//        image.transferTo(new File("E:\\Images\\"+newFileName));
//        return Result.success();
//    }

@Autowired
    private AliOSSUtils aliOSSUtils;
    //这里云存储
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传，文件名：{}",image.getOriginalFilename());
        String url = aliOSSUtils.upload(image);
        log.info("上传成功，文件访问的url：{}",url);
        return Result.success(url);//这里需要返回上传成功的url
    }
}
