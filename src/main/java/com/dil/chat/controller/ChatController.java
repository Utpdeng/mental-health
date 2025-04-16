package com.dil.chat.controller;

import com.dil.chat.model.ChatMessage;
import com.dil.chat.service.ChatService;
import com.dil.chat.service.ZhiPuService;
import com.dil.common.annotation.RateLimiterAnnotation;
import com.dil.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: "xx"
 * @Date: 2025/2/2
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    @Resource
    private ChatService chatService;

    @Resource
    private ZhiPuService zhiPuService;

    @GetMapping("/history/{sessionId}")
    public Result<List<ChatMessage>> getChatHistory(@PathVariable("sessionId") String roomId,
                                         @RequestParam(required = false, defaultValue = "20") Integer limit) {
        return Result.success(chatService.getRecentMessages(roomId, limit));
    }

    @RateLimiterAnnotation(permitsPerSecond = 0.3, blacklistCount = 5, fallbackMethod = "testFallback")
    @GetMapping("/chatbot")
    public Result<String> chatbot(String req) throws Exception {
        log.info("用户请求问题：{}", req);
        String res = zhiPuService.talk(req);
        return Result.success(res);
    }

    public Result<String> testFallback(String req) {
        log.info("限流了");
        return Result.success("服务器繁忙，请稍后再试");
    }

}
