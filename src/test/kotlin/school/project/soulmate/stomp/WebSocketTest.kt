package school.project.soulmate.stomp

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketTest {
    @LocalServerPort
    private val port: Int = 8080

    @Test
    fun `WebSocket 연결 에러 테스트`() {
        val wsUrl = "ws://localhost:$port/ws-stomp"
        val client = StandardWebSocketClient()

        try {
            val sockJsClient = SockJsClient(listOf<Transport>(WebSocketTransport(client)))
            val stompClient = WebSocketStompClient(sockJsClient)
        } catch (e: ExecutionException) {
            // 연결 실패 혹은 연결 도중 발생한 예외 처리
            println("연결 실패: ${e.cause}")
        } catch (e: TimeoutException) {
            // 연결 시간 초과
            println("연결 시간 초과")
        } catch (e: InterruptedException) {
            // 스레드 인터럽트
            println("연결 중 스레드 인터럽트 발생")
        }
    }
}
// MyStompSessionHandler 클래스는 STOMP 세션 핸들러의 구현을 포함해야 합니다.
// 이 클래스는 연결, 에러, 메시지 수신 등의 이벤트를 처리합니다.
// class MyStompSessionHandler : StompSessionHandlerAdapter() {
//    // 여기에 연결됐을 때, 메시지를 받았을 때, 에러가 났을 때 등의 로직을 구현할 수 있습니다.
// }
