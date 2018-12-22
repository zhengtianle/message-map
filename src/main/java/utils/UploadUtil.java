package utils;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-21
 * @Author ZhengTianle
 * Description:
 */
public class UploadUtil {
    private static final Logger LOG = LoggerFactory.getLogger(UploadUtil.class);

    public static String uploadFile(MultipartFile file, String path) throws IOException {
        String name = file.getOriginalFilename();   //上传文件的真实名称
        LOG.info("上传的文件名：" + name);
        String suffixName = name.substring(name.lastIndexOf("."));  //获取后缀名
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd-hh-mm-ss");
        //生成唯一文件名
        String hash = format.format(new Date()) + Integer.toHexString(new Random().nextInt());
        String fileName = hash + suffixName;
        File tempFile = new File(path, fileName);
        LOG.info("修改后的文件位置：" + tempFile.getAbsolutePath());
        if(tempFile.exists()){
            tempFile.delete();
        }
        tempFile.createNewFile();
        file.transferTo(tempFile);

        return tempFile.getName();
    }
}
