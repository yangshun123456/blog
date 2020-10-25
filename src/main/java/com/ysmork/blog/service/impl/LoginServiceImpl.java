package com.ysmork.blog.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.ysmork.blog.common.util.FastDfsUtil;
import com.ysmork.blog.common.util.RedisCache;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.entity.param.UserParam;
import com.ysmork.blog.framework.security.LoginUser;
import com.ysmork.blog.framework.security.service.TokenService;
import com.ysmork.blog.framework.web.entity.DictDataConstants;
import com.ysmork.blog.framework.web.entity.FastDFSFile;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
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
 * @description: 登录
 * @date 2020/10/12 21:57
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private RedisCache redisCache;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenService tokenService;

    @Override
    public Result login(UserParam param) {
        if (StringUtils.isAnyEmpty(
                param.getCaptcha(),
                param.getPassword(),
                param.getUsername(),
                param.getCaptchaKey ()
        )) {
            return Result.requestParamError("参数不对");
        }
        //验证验证码
        String cacheObject = redisCache.getCacheObject (param.getCaptchaKey ()).toString ();
        if(!param.getCaptcha ().equalsIgnoreCase (cacheObject)){
            deleteFile (param.getCaptchaKey ());
            return Result.error ("验证码错误");
        }else {
            deleteFile (param.getCaptchaKey ());
        }

        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken (param.getUsername(), param.getPassword()));
        }catch (BadCredentialsException exception){
            return Result.error ("用户或密码错误");
        } catch (Exception e){
            e.printStackTrace ();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        String token = tokenService.createToken(loginUser);

//        sysUserMapper.updateByPrimaryKey(loginUser.getUser());
        return Result.success (token);
    }

    @Override
    public Result captcha(String key) {
        //判断是否有key传入
        deleteFile(key);
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

    @Override
    public Result getUserDetail(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser (request);
        return Result.success (loginUser.getSysUser ());
    }

    /**
     * 将BufferedImage转换为byte[]
     *
     * @param image
     * @return
     */
    private byte[] bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream ();
        try {
            ImageIO.write (image, "jpg", os);
            return os.toByteArray ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return null;
    }

    private void deleteFile(String key){
        if(StringUtils.isNotEmpty (key)) {
            FastDfsUtil.delete (DictDataConstants.FAST_FDS_GROUP, key);
            Object cacheObject1 = redisCache.getCacheObject (key);
            if (cacheObject1 != null) {
                redisCache.deleteObject (key);
            }
        }
    }
}
