package com.dil.chat.service;

import com.dil.chat.model.ChatMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: "xx"
 * @Date: 2025/2/2
 */
@Service
public class ChatService {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<ChatMessage> getChatHistory(String roomId, Integer limit, Date before, String sender) {

        Query query = new Query();

        // 添加房间ID条件
        query.addCriteria(Criteria.where("roomId").is(roomId));

        // 如果指定了发送者，添加发送者条件
        if (sender != null && !sender.isEmpty()) {
            query.addCriteria(Criteria.where("sender").is(sender));
        }

        // 如果指定了时间，添加时间条件
        if (before != null) {
            query.addCriteria(Criteria.where("timestamp").lt(before));
        }

        // 按时间倒序排序
        query.with(Sort.by(Sort.Direction.DESC, "timestamp"));

        // 如果指定了限制数量
        if (limit != null && limit > 0) {
            query.limit(limit);
        }

        return mongoTemplate.find(query, ChatMessage.class);
    }

    public List<ChatMessage> getRecentMessages(String roomId, int limit) {
        return mongoTemplate.find(
                Query.query(Criteria.where("roomId").is(roomId))
                        .with(Sort.by(Sort.Direction.ASC, "timestamp"))
                        .limit(limit),
                ChatMessage.class
        );
    }

    public void saveMessage(String sender, String receiver, String content,
                            String roomId, ChatMessage.MessageType type) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setContent(content);
        chatMessage.setRoomId(roomId);
        chatMessage.setTimestamp(new Date());
        chatMessage.setType(type);
        mongoTemplate.save(chatMessage);
    }

    /**
     * 获取私聊消息历史
     */
    public List<ChatMessage> getPrivateMessages(String user1, String user2, String roomId) {
        Criteria criteria = new Criteria().orOperator(
                new Criteria().andOperator(
                        Criteria.where("sender").is(user1),
                        Criteria.where("receiver").is(user2)
                ),
                new Criteria().andOperator(
                        Criteria.where("sender").is(user2),
                        Criteria.where("receiver").is(user1)
                )
        ).and("roomId").is(roomId);

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "timestamp"));

        return mongoTemplate.find(query, ChatMessage.class);
    }
}
