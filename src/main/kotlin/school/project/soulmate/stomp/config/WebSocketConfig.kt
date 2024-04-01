package school.project.soulmate.stomp.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/pub") // 클라이언트에서 보낸 메세지를 받을 prefix
        // 스프링에서 제공하는 내장 메시지 브로커 simple broker
        registry.enableSimpleBroker("/sub") // 해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // 웹 소켓 연결을 위한 endpoint
        registry.addEndpoint("/ws-stomp").withSockJS()
    }
}
