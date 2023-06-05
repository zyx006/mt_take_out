package cn.czyx007.mt.controller;

import cn.czyx007.mt.utils.COSUploadUtils;
import cn.czyx007.mt.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/31 - 11:47
 */
@Slf4j
@RestController
@RequestMapping("common")
public class CommonController {
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info("file: {}", file);
        String filename = file.getOriginalFilename();
        log.info("上传文件的名称：{}", filename);
        return COSUploadUtils.uploadImage(file);
    }
}
