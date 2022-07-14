package com.atguigu.oss;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;
@Component
public class OssTemplate {
    //从yml里面注入属性值
    @Value("${oss.endpoint}")
    private String endPoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    /**
     * endpoint:oss-cn-wulanchabu.aliyuncs.com
     * bucketName:yz1999
     * @param fileName:上传到服务器的文件名
     * @param inputStream
     * @return
     */
    //1.文件上传
    public String uploadFile(String fileName, InputStream inputStream){
        // 创建OSSClient实例。  build:构建
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //创建PutObject请求。
        ossClient.putObject(bucketName, fileName, inputStream);
        ossClient.shutdown();
        //返回一个文件地址 https://java220217.oss-cn-wulanchabu.aliyuncs.com/new.jpg
        String retUrl="https://"+bucketName+"."+endPoint+"/"+fileName;
        return retUrl;
    }
    //2.单个删除文件
    public void deleteSingleFile(String fileName) {
        //构建一个实例
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //创建PutObject请求。
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();
    }
    //3.删除多个文件
    public void deleteMultiFile(List<String> fileNameList){
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(fileNameList));
        deleteObjectsResult.getDeletedObjects();
        ossClient.shutdown();
    }
}
