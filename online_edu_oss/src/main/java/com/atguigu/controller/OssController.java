package com.atguigu.controller;

import com.atguigu.response.RetVal;
import com.atguigu.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Date:2022/7/1
 *
 * @author:yz
 */
@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    /**
     * 文件上传
     */
    @PostMapping("uploadFile")
    public RetVal uploadFile(MultipartFile file) throws IOException {
        String retUrl = ossService.uploadFile(file);
        return RetVal.success().data("retUrl",retUrl);
    }
    /**
     * 文件删除
     */
    @DeleteMapping("deleteFile")
    public RetVal deleteSingleFile(String fileName){
        Boolean flag = ossService.deleteSingleFile(fileName);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
    /**
     * 批量删除文件
     *
     */
    @DeleteMapping("deleteMultiFile")
    public RetVal deleteMultiFile(List<String> fileNameList){
        Boolean flag = ossService.deleteMultiFile(fileNameList);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
}
