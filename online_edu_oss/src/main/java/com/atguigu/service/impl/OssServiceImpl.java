package com.atguigu.service.impl;

import com.atguigu.oss.OssTemplate;
import com.atguigu.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Date:2022/7/1
 *
 * @author:yz
 */
@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private OssTemplate ossTemplate;

    /**
     * 上传文件
     * @param file
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        //文件全名 1.jpg
        String filename = file.getOriginalFilename();
        //UUID生成的数字里面有-,比如111-222-333,  .replace将-转换成""空字符串
        String prefix = UUID.randomUUID().toString().replace("-", "");
        /**
         *filename.lastIndexOf(".")最后一个点的索引
         * .substring()去掉括号里面的参数前面所有的字符串,将参数后面的字符串生成一个全新的字符串
         */
        String suffix = filename.substring(filename.lastIndexOf("."));
        filename=prefix+suffix;
        InputStream inputStream = file.getInputStream();
       return ossTemplate.uploadFile(filename,inputStream);
    }

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    @Override
    public boolean deleteSingleFile(String fileName) {
       ossTemplate.deleteSingleFile(fileName);
        return true;
    }
    /**
     * 批量产出文件
     */
    @Override
    public boolean deleteMultiFile(List<String> fileNameList) {
        ossTemplate.deleteMultiFile(fileNameList);
        return true;
    }
}
