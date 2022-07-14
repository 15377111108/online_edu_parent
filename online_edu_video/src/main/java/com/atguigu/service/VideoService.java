package com.atguigu.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Date:2022/7/5
 *
 * @author:yz
 */
public interface VideoService {

    String uploadAliyunVideo(MultipartFile file) throws Exception;

    boolean deleteSingleVideo(String videoId) throws Exception;

    String getVideoPlayAuth(String videoId);
}
