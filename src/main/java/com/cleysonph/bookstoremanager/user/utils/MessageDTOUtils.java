package com.cleysonph.bookstoremanager.user.utils;

import com.cleysonph.bookstoremanager.user.dto.MessageDTO;
import com.cleysonph.bookstoremanager.user.entity.User;

public class MessageDTOUtils {

    public static MessageDTO creationMessage(User createdUser) {
        return returnMessage(createdUser, "created");
    }

    public static MessageDTO updatedMessage(User updatedUser) {
        return returnMessage(updatedUser, "updated");
    }

    public static MessageDTO returnMessage(User user, String action) {
        String username = user.getUsername();
        Long id = user.getId();
        String userMessage = String.format("User %s with ID %s successfully %s", username, id, action);
        return MessageDTO.builder()
            .message(userMessage)
            .build();
    }

}
