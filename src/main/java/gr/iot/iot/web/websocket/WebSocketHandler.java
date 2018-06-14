package gr.iot.iot.web.websocket;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Aspect
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.debug("Websocket: connection established '{}'", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LOGGER.debug("Websocket: connection closed '{}', Reason '{}'", session.getId(), status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        LOGGER.debug("Websocket: transport error: '{}', Session '{}'", exception, session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        LOGGER.debug("Received message: {}", message.asBytes());
    }
}
