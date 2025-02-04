package com.dil.chat.controller;

import com.dil.chat.model.ChatMessage;
import com.dil.chat.service.ChatService;
import com.dil.common.response.Result;
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

    @Resource
    private ChatService chatService;

    @GetMapping("/history/{sessionId}")
    public Result<List<ChatMessage>> getChatHistory(@PathVariable("sessionId") String roomId,
                                         @RequestParam(required = false, defaultValue = "20") Integer limit) {
        return Result.success(chatService.getRecentMessages(roomId, limit));
    }

}
