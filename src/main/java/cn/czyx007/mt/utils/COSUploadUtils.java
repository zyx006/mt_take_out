package cn.czyx007.mt.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/31 - 11:08
 */
@Slf4j
public class COSUploadUtils {

    private static COSClient cosClient;
    private static final String secretId = "";
    private static final String secretKey = "";
    private static final String  regionName = "";

    private static void createCOSClient()  {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参见
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    //实现图片的上传
    public static R uploadImage(MultipartFile file) {
        try {
            createCOSClient();
            //1.重新生成文件的名称-不重复的
            String newName = UUID.randomUUID().toString().replace("-", "");
            //200b4b6ced284daaaca89ba6ff0f85ae.png
            //2.获取原文件的后缀 .png
            String oldName = file.getOriginalFilename();
            String suffix = oldName.substring(oldName.lastIndexOf("."));
            //3.拼接成新的文件的名字
            String newFileName = newName + suffix;
            //4.实现文件上传
            File localFile = File.createTempFile(newName, suffix);
            file.transferTo(localFile);
            //指定文件将要存放的存储桶
            String bucketName = "mt-take-out-1259733126";
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String key = newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            //5.返回域名+图片名，让前端能够显示
            return R.success("https://"+bucketName+".cos."+regionName+".myqcloud.com/" + newFileName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("图片上传失败！");
        } finally {
            cosClient.shutdown();
        }
    }
}
