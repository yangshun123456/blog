package com.ysmork.blog.framework.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import com.ysmork.blog.common.model.WebSocketConstants;
import com.ysmork.blog.common.model.server.Server;
import com.ysmork.blog.common.model.server.ServerVO;
import com.ysmork.blog.common.util.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: blog
 * @description:
 * @author: YangShun
 * @create: 2020-12-10 16:05
 **/
@Slf4j
@Component
public class SystemTask {

    @Resource
    private SimpMessagingTemplate wsTemplate;

    /**
     * 按照标准时间来算，每隔 2s 执行一次
     */
//    @Scheduled(cron = "0/2 * * * * ?")
    public void websocket() throws Exception {
        log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date ()));
        // 查询服务器状态
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        Dict dict = ServerUtil.wrapServerDict(serverVO);
        wsTemplate.convertAndSend(WebSocketConstants.PUSH_SERVER, JSONUtil.toJsonStr(dict));
        log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
    }
}
