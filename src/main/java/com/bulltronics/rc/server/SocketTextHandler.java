package com.bulltronics.rc.server;

import java.io.IOException;
import java.util.*;

import com.bulltronics.rc.server.model.Command;
import com.bulltronics.rc.server.model.Status;
import com.bulltronics.rc.server.service.CommandHandlerService;
import com.bulltronics.rc.server.ui.UiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {
    private static final Integer MAX_CONNECTIONS_ALLOWED = 10;
    private static final Gson gson = new Gson();
    private static final Set<WebSocketSession> sessions = new HashSet<>();

    public static void broadcastMessage(String messageStr) {
        for (WebSocketSession session : sessions) {
            try {
                WebSocketMessage<String> message = new TextMessage(messageStr);
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            System.out.println("afterConnectionEstablished: " + session.getId());
            if(sessions.size() < MAX_CONNECTIONS_ALLOWED) {
                sessions.add(session);
            } else {
                System.out.println("server overloaded, closing session");
                session.close(CloseStatus.SERVICE_OVERLOAD);
            }
            UiService.setStatusLabel("Server Running, Clients: " + sessions.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("afterConnectionClosed: " + session.getId());
        sessions.remove(session);
        UiService.setStatusLabel("Server Running, Clients: " + sessions.size());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        String payload = message.getPayload();
        //System.out.println("Request Payload: " + payload);

        try {
            List<Command> commandList = gson.fromJson(payload, new TypeToken<ArrayList<Command>>() {
            }.getType());
            List<Status> statusList = CommandHandlerService.handle(commandList);

            String resultList = gson.toJson(statusList);
            //System.out.println("Sending --> " + resultList);
            session.sendMessage(new TextMessage(resultList));
        } catch (Exception exception) {
            session.sendMessage(new TextMessage("Some Error: " + exception.getMessage()));
        }
    }

}
