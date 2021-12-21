package com.bulltronics.rc.server;

import com.bulltronics.rc.server.authentication.Authenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        URI uri = request.getURI();
        if(request.getRemoteAddress().getHostName().equals("0:0:0:0:0:0:0:1")) {
            System.out.println("WebSocketInterceptor -> Bypassed Localhost Connection");
            System.out.println("WebSocketInterceptor -> request.getRemoteAddress(): " + request.getRemoteAddress());
            return true;
        }

        if(uri.getQuery() != null) {
            String token = uri.getQuery().replace("token=", "");
            System.out.println("WebSocketInterceptor -> token: " + token);

            return Authenticator.isValidToken(token);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
