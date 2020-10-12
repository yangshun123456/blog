package com.ysmork.blog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.ysmork.blog.common.util.FastDfsUtil;
import com.ysmork.blog.common.util.RedisCache;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.framework.security.LoginUser;
import com.ysmork.blog.framework.web.entity.FastDFSFile;
import com.ysmork.blog.framework.web.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 登录接口
 * @date 2020/10/4 16:52
 */
@RestController
public class LoginController {

//    @Resource
//    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @PostMapping("login")
    public Result login(LoginUser user) {

        return null;
    }

    @GetMapping("captcha")
    public Result captcha(String key) {
        //判断是否有key传入
        if(StringUtils.isNotEmpty (key)){
            FastDfsUtil.delete ("group1",key);
            Object cacheObject = redisCache.getCacheObject (key);
            if(cacheObject != null){
                //有的话则删除缓存
                redisCache.deleteObject (key);
            }
        }
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha (130, 50, 4, 4);
        BufferedImage image = shearCaptcha.getImage ();
        byte[] bytes = bufferedImageToInputStream (image);

        //上传验证码图片到fastFds
        FastDFSFile file = new FastDFSFile ();
        file.setAuthor ("ys");
        file.setContent (bytes);
        file.setExt ("jpg");
        file.setName ("captcha");

        //返回验证码地址
        Map<String,String> path = FastDfsUtil.upload (file);
        Map<String, String> map = new HashMap<> ();
        map.put ("relPath", path.get ("relPath"));
        map.put ("path", path.get ("path"));

        //获取验证码 存入redis 过期五分钟
        String code = shearCaptcha.getCode ();
        String redisKey = path.get ("path");
        redisCache.setCacheObject (redisKey,code,5, TimeUnit.MINUTES);
        Result success = Result.success (map);
        return success;
    }

    /**
     * 将BufferedImage转换为byte[]
     *
     * @param image
     * @return
     */
    public byte[] bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream ();
        try {
            ImageIO.write (image, "jpg", os);
            return os.toByteArray ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return null;
    }
}
