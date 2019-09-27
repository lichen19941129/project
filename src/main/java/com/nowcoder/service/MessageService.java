package com.nowcoder.service;

import com.nowcoder.dao.MessageDAO;
import com.nowcoder.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    public List<Message> getConversationList(int userId,int offset,int limit) {
        return  messageDAO.getConversationList(userId,offset,limit);
    }

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return  messageDAO.getConversationDetail(conversationId, offset, limit);
    }
}
