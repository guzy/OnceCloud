package com.beyondsphere.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.beyondsphere.entity.User;

@Component
public class MessageWebSocketHandler implements WebSocketHandler {
	
    
	
	private static List<WebSocketSession> currentUsers;

	private static List<WebSocketSession> getCurrentUsers() {
		return currentUsers;
	}

	private static void setCurrentUsers(List<WebSocketSession> currentUsers) {
		MessageWebSocketHandler.currentUsers = currentUsers;
	}

	static {
		MessageWebSocketHandler
				.setCurrentUsers(new ArrayList<WebSocketSession>());
	}

	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		MessageWebSocketHandler.getCurrentUsers().add(session);
		User user = (User) session.getAttributes().get("user");
		if (user != null) {
		}
	}

	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		MessageWebSocketHandler.getCurrentUsers().remove(session);
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		MessageWebSocketHandler.getCurrentUsers().remove(session);
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	public void sendMessageToUsers(Message message) {
		for (WebSocketSession user : MessageWebSocketHandler.getCurrentUsers()) {
			try {
				if (user.isOpen()) {
					user.sendMessage(new TextMessage(new ObjectMapper()
							.writeValueAsString(message)));
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	public void sendMessageToUser(int userId, Message message) {
		for (WebSocketSession user : MessageWebSocketHandler.getCurrentUsers()) {
			User iterator = (User) user.getAttributes().get("user");
			if (iterator != null) {
				if (iterator.getUserId() == userId) {
					try {
						if (user.isOpen()) {
							user.sendMessage(new TextMessage(new ObjectMapper()
									.writeValueAsString(message)));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}