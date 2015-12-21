package com.oncecloud.message;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class UpISOMessageConfig extends WebMvcConfigurerAdapter implements
		WebSocketConfigurer {
	private UpISOMessageWebSocketHandler messageWebSocketHandler;
	private MessageHandshakeInterceptor messageHandshakeInterceptor;

	private UpISOMessageWebSocketHandler getMessageWebSocketHandler() {
		return messageWebSocketHandler;
	}

	@Autowired
	private void setMessageWebSocketHandler(
			UpISOMessageWebSocketHandler messageWebSocketHandler) {
		this.messageWebSocketHandler = messageWebSocketHandler;
	}

	private MessageHandshakeInterceptor getMessageHandshakeInterceptor() {
		return messageHandshakeInterceptor;
	}

	@Autowired
	private void setMessageHandshakeInterceptor(
			MessageHandshakeInterceptor messageHandshakeInterceptor) {
		this.messageHandshakeInterceptor = messageHandshakeInterceptor;
	}

	@Bean
	public DefaultHandshakeHandler handshakeHandler() {
		WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
		policy.setMaxBinaryMessageSize(1024*1024*10);
		policy.setInputBufferSize(32768);
		policy.setIdleTimeout(3600000);
		return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(
				new WebSocketServerFactory(policy)));
	}

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this.getMessageWebSocketHandler(),
				"upISOMessagingService")
				.addInterceptors(this.getMessageHandshakeInterceptor())
				.setHandshakeHandler(handshakeHandler());
		registry.addHandler(this.getMessageWebSocketHandler(),
				"/sockjs/upISOMessagingService")
				.addInterceptors(this.getMessageHandshakeInterceptor())
				.setHandshakeHandler(handshakeHandler()).withSockJS();
	}
}