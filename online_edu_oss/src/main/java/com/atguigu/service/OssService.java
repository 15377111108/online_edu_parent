package com.atguigu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Date:2022/7/1
 *
 * @author:yz
 */
public interface OssService {
    /**
     *上传文件
     * @param file
     * @return
     * @throws IOException
     */
    String uploadFile(MultipartFile file) throws IOException;

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    boolean deleteSingleFile(String fileName);

    /**
     * 批量删除文件
     * @param fileName
     * @return
     */
    boolean deleteMultiFile(List<String> fileNameList);
}
