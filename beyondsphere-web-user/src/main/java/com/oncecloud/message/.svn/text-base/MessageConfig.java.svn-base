/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.message;

import javax.annotation.Resource;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
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
public class MessageConfig extends WebMvcConfigurerAdapter implements
		WebSocketConfigurer {
	
	@Resource
	private MessageWebSocketHandler messageWebSocketHandler;
	@Resource
	private MessageHandshakeInterceptor messageHandshakeInterceptor;

	@Bean
	public DefaultHandshakeHandler handshakeHandler() {
		WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
		policy.setInputBufferSize(8192);
		policy.setIdleTimeout(3600000);
		return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(
				new WebSocketServerFactory(policy)));
	}

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(messageWebSocketHandler, "/messagingService")
				.addInterceptors(messageHandshakeInterceptor)
				.setHandshakeHandler(handshakeHandler());
		registry.addHandler(messageWebSocketHandler, "/sockjs/messagingService")
				.addInterceptors(messageHandshakeInterceptor)
				.setHandshakeHandler(handshakeHandler()).withSockJS();
	}
}