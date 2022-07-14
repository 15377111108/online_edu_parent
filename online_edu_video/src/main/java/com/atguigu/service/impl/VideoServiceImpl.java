package com.atguigu.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.service.VideoService;
import com.atguigu.utils.VideoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VideoServiceImpl implements VideoService {
    @Value("${aliyun.video.ak}")
    private String accessKeyId;
    @Value("${aliyun.video.sk}")
    private String accessKeySecret;
    @Override
    public String uploadAliyunVideo(MultipartFile file) throws Exception{
        String videoId="";
        //online.mp4
        String fileName=file.getOriginalFilename();
        String title=fileName.substring(0,fileName.lastIndexOf("."));
        InputStream inputStream = file.getInputStream();
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        if (response.isSuccess()) {
            videoId=response.getVideoId();
        }
        return videoId;
    }

    @Override
    public boolean deleteSingleVideo(String videoIds) throws Exception {
        DefaultAcsClient client = VideoUtils.initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoIds);
        client.getAcsResponse(request);
        return true;
    }

    @Override
    public String getVideoPlayAuth(String videoId) {
        try {
            DefaultAcsClient client = VideoUtils.initVodClient(accessKeyId, accessKeySecret);
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            response = client.getAcsResponse(request);
            //播放凭证
            return response.getPlayAuth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
