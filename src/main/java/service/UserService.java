package service;

import com.google.gson.Gson;
import dao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pojo.User;
import utils.UploadUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-19
 * @Author ZhengTianle
 * Description:
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public String login(String tel, String password){
        //System.out.println("接收到客户端的tel: "+tel+"~password: "+password);
        Gson gson = new Gson();
        Map<String, String> resultMap = new HashMap<>();
        User user = userMapper.getUserByTel(tel);
        //System.out.println("数据库中的password: "+user.getPassword());
        if(user.getPassword() != null && user.getPassword().equals(password)){
            //登录成功
            resultMap.put("result","success");
            user.setPassword("");//不用返回密码
            resultMap.put("user", gson.toJson(user));
        } else {
            resultMap.put("result", "error");
        }
        //System.out.println("返回的json：" + gson.toJson(resultMap));
        return gson.toJson(resultMap);
    }

    public String register(String tel, String pin, String password){
        //TODO: pin验证码作为后续功能手机验证（接入第三方接口实现）
        User user = new User();
        user.setTel(tel);
        user.setPassword(password);
        user.setUsername(tel);//默认把手机号当做用户名
        int affectedRows=0;
        Gson gson = new Gson();
        Map<String, String> resultMap = new HashMap<>();
        try{
            affectedRows = userMapper.insertSelective(user);
            if(affectedRows == 1){
                resultMap.put("result", "success");
            } else {
                resultMap.put("result", "error");
            }
        } catch (Exception e){
            //账号重复
            resultMap.put("result", "repeat");
        }

        //System.out.println("返回的json：" + gson.toJson(resultMap));
        return gson.toJson(resultMap);
    }

    /**
     * 修改基本信息和我的山威信息
     */
    public String updateUserInfo(User user){
        int affectedRows=0;
        Gson gson = new Gson();
        Map<String, String> resultMap = new HashMap<>();
        try{
            affectedRows = userMapper.updateSelective(user);
            LOG.info("update影响行数：" + affectedRows);
            User user1 = userMapper.getUserByUid(user.getUid());
            LOG.info("更新后的user: " + user1);
            if(affectedRows == 1){
                resultMap.put("result", "success");
                user1.setPassword("");//不用返回密码
                resultMap.put("user", gson.toJson(user1));
            } else {
                resultMap.put("result", "error");
            }
        } catch (Exception e){
            resultMap.put("result", "error");
        }

        System.out.println("返回的json：" + gson.toJson(resultMap));
        return gson.toJson(resultMap);
    }

    /**
     * 保存头像并修改头像
     */
    public String updateAvatar(MultipartFile file, String path, Integer uid){
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        try{
            //上传头像
            String image = UploadUtil.uploadFile(file, path);
            //更新头像
            User user = new User();
            user.setUid(uid);
            user.setAvatar(image);
            int affectedRows = userMapper.updateSelective(user);
            LOG.info("update影响行数：" + affectedRows);
            User user1 = userMapper.getUserByUid(user.getUid());
            LOG.info("更新后的user: " + user1);
            if(affectedRows == 1){
                map.put("code", 0);
                user1.setPassword("");//不用返回密码
                map.put("user", gson.toJson(user1));
            } else {
                map.put("code", 1);
            }

        } catch (Exception e){
            map.put("code", 1);
            e.printStackTrace();
        }

        return gson.toJson(map);
    }

    /**
     * 修改密码
     */
    public String modifyPassword(Integer uid, String oldPassword, String newPassword) {
        Gson gson = new Gson();
        Map<String, String> resultMap = new HashMap<>();
        User user = userMapper.getUserByUid(uid);
        if(!user.getPassword().equals(oldPassword)) {
            //输入的原密码错误
            resultMap.put("result", "oldPassword");
        } else {
            try {
                User updateUser = new User();
                updateUser.setUid(uid);
                updateUser.setPassword(newPassword);
                int affectedRows = userMapper.updateSelective(updateUser);
                if(affectedRows == 1){
                    resultMap.put("result", "success");
                } else {
                    resultMap.put("result", "error");
                }
            } catch (Exception e) {
                resultMap.put("result", "error");
            }
        }
        return gson.toJson(resultMap);
    }
}
