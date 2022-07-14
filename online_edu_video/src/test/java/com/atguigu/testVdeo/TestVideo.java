package com.atguigu.testVdeo;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class TestVideo {
    //填入AccessKey信息
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    //账号AK信息请填写（必选）
    private static final String accessKeyId = "LTAI5t8gtEas7FW9jdYPgZM6";
    //账号AK信息请填写（必选）
    private static final String accessKeySecret = "0iRwK5rLBqiVexo6MJQuapy0nz2ysz";
//    public static void main(String[] args) {
//        // 视频文件上传
//        // 视频标题（必选）
//        String title = "测试标题";
//        String fileName = "C:\\220217\\temp\\online.mp4";
//        // 本地文件上传
//        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);
//    }
    /**
     * 本地文件上传接口
     */

    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /*获取播放地址函数*/
    public static void getPlayInfo(String[] argv) throws Exception {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("a76ae14dba6f440b909e3489ee8f50b4");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
    }

    /*获取播放凭证函数*/
    public static void getVideoPlayAuth(String[] argv) throws Exception{
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("a76ae14dba6f440b909e3489ee8f50b4");
        response = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
    }
    /*删除视频*/
    public static void deleteVideo(String[] argv) {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds("a76ae14dba6f440b909e3489ee8f50b4,d3de3c4a5ab342519fa2caa370484fb5");
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
