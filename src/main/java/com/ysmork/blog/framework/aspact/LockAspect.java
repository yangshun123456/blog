package com.ysmork.blog.framework.aspact;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.common.annonation.Lock;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.common.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author YangShun
 * @since 2021/1/1
 */
@Aspect
@Component
@Slf4j
public class LockAspect {

    @Resource
    private RedisCache redisCache;

    @Resource
    private DruidDataSource dataSource;

    private final static Integer WAIT_TIME = 2000;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.ysmork.blog.common.annonation.Lock)")
    public void logPointCut() {
    }

    /**
     * 环绕通知
     *
     * @param pjd  执行参数
     * @param lock 注解
     * @return
     */
    @Around("logPointCut() && @annotation(lock)")
    public Object aroundMethod(ProceedingJoinPoint pjd, Lock lock) {
        Object result = null;
        String params = null;
        StringBuilder redisKey = new StringBuilder ();
        try {
            //获取传入参数
            params = getParam (pjd);
            log.info ("切面日志记录传入参数为：{}", params);
            if (lock.lock ()) {
                redisKey = doBefore (pjd, lock);
                if (!redisCache.tryLock (redisKey.toString (), WAIT_TIME, TimeUnit.MILLISECONDS)) {
                    throw new RuntimeException ("您点击太快啦，系统正在执行!");
                }
            }
            if(StringUtils.isBlank (isInsert(pjd, lock)) || DictDataConstants.NULL.equals(isInsert(pjd, lock))) {
                selectRepeat (pjd, lock);
            }
            //执行目标方法
            result = pjd.proceed ();
        } catch (Throwable e) {
            log.error ("iot切面捕获异常：{}", e.getMessage ());
            throw new RuntimeException (e);
        } finally {
            if (lock.lock ()) {
                redisCache.unlock (redisKey.toString ());
            }
            log.info ("方法执行完毕，返回值为：{}", result);
        }
        return result;
    }

    /**
     * 根据唯一标识创建redisKey
     *
     * @param pjd
     * @param lock
     */
    private StringBuilder doBefore(ProceedingJoinPoint pjd, Lock lock) {
        StringBuilder redisKey = new StringBuilder ();
        //设定数据内容
        String data = getData (pjd, lock);
        //构建唯一key
        if (data != null) {
            redisKey.append (lock.redisKey ());
            redisKey.append (lock.onlyName ());
            redisKey.append ("::");
            redisKey.append (data);
        }
        return redisKey;
    }

    /**
     * 获取唯一标识的数值，用于查询
     * @param pjd
     * @param lock
     * @return
     */
    private String getData(ProceedingJoinPoint pjd, Lock lock) {
        return getDataByField (pjd, lock , 1);
    }

    private String getParam(ProceedingJoinPoint pjd) {
        //获取参数名称(与下面一一对应)
        String[] parameter = ((MethodSignature) pjd.getSignature ()).getParameterNames ();
        //获取参数值
        Object[] args = pjd.getArgs ();
        StringBuilder requestParam = new StringBuilder ();
        requestParam.append ("{");
        for (int i = 0; i < parameter.length; i++) {
            if (i == parameter.length - 1) {
                requestParam.append ("\"").append (parameter[i]).append ("\":");
                requestParam.append ("\"").append (args[i]).append ("\"}");
            } else {
                requestParam.append ("\"").append (parameter[i]).append ("\":");
                requestParam.append ("\"").append (args[i]).append ("\",");
            }
        }
        return requestParam.toString ();
    }


    /**
     * 使用druid连接池获取connection
     */
    private void selectRepeat(ProceedingJoinPoint pjd, Lock lock) {
        //获取唯一参数名
        String paramName = lock.onlyName ();
        //获取唯一参数值
        String data = getData (pjd, lock);
        //获取数据库表名称
        String[] split = lock.className ().getName ().split ("\\.");
        //如果字段存在测查询一次数据库
        DruidPooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (!StringUtils.isAnyEmpty (paramName, data) || split.length == 0) {
            try {
                StringBuilder appendSql = new StringBuilder ();
                if (lock.status ()) {
                    appendSql.append (StringUtils.format ("and status != {}", DictDataConstants.DELETE_STATUS));
                }
                String sql = StringUtils.format ("select {} from {} where {} = '{}' {}",
                        getParam (lock.onlyName ()), getParam (split[split.length - 1]),
                        getParam (lock.onlyName ()), data, appendSql.toString ());
                //获取连接
                connection = dataSource.getConnection ();
                preparedStatement = connection.prepareStatement (sql);
                resultSet = preparedStatement.executeQuery ();
                //如果存在结果，则抛出异常
                if (resultSet.next ()) {
                    throw new RuntimeException ("该提交已经存在! ");
                }
            }catch (Exception e){
                log.error (e.getMessage ());
                throw new RuntimeException (e.getMessage ());
            }finally {
                //关闭resultSet 与 preparedStatement
                if(resultSet != null){
                    try {
                        resultSet.close ();
                    } catch (SQLException e) {
                        e.printStackTrace ();
                    }
                }
                if(preparedStatement != null){
                    try {
                        preparedStatement.close ();
                    } catch (SQLException e) {
                        e.printStackTrace ();
                    }
                }
                //将连接收回到连接池
                if(connection != null){
                    try {
                        connection.close ();
                    } catch (SQLException e) {
                        e.printStackTrace ();
                    }
                }
            }

        }
    }

    /**
     * 参数转换成数据库字段形式 userName -> user_name
     *
     * @param paramName
     * @return
     */
    private String getParam(String paramName) {
        String[] names = paramName.split ("(?=\\p{Upper})");
        for (int i = 0; i < names.length; i++) {
            if (i != 0) {
                names[i] = "_".concat (names[i].toLowerCase ());
            } else {
                names[i] = names[i].toLowerCase ();
            }
        }
        return String.join ("", names);
    }

    /**
     * 判断是否是insert
     * @param pjd
     * @param lock
     * @return
     */
    private String isInsert(ProceedingJoinPoint pjd, Lock lock){
        return getDataByField (pjd, lock , 2);
    }

    /**
     * 获取数据
     * @param pjd
     * @param lock
     * @param type 1: 唯一标识 2：id
     * @return
     */
    private String getDataByField(ProceedingJoinPoint pjd, Lock lock, Integer type){
        //获取参数名称(与下面一一对应)
        String[] parameterNames = ((MethodSignature) pjd.getSignature ()).getParameterNames ();
        //获取参数值
        Object[] args = pjd.getArgs ();
        String data = null;
        //传入的参数为分开的
        for (int i = 0; i < parameterNames.length; i++) {
            if (lock.onlyName ().equalsIgnoreCase (parameterNames[i])) {
                data = args[i].toString ();
            }
        }
        //传入参数的整个对象中的
        if(data == null) {
            for (Object arg : args) {
                String[] split = arg.toString ().split (",");
                if (split.length != 0) {
                    for (int i = 0; i < split.length; i++) {
                        if (split[i].contains (type.equals (1) ? lock.onlyName (): lock.idName ())) {
                            data = split[i].split ("=")[1];
                            data = data.replace (")","");
                        }
                    }
                }
            }
        }
        return data;
    }


}
