/*
package cn.jsut.scoder.controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author:
 * @date:2018/1/20 16:13
 * @version:
 * @copyright:
 *//*

@ServerEndpoint(value = "/websocket/{username}")
public class WebsocketController {
    private Session session;
    private static Map<String,Session> sessionPool = new HashMap<>();
    private static Map<String,String> userPool = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username") String username){
        this.session = session;
        sessionPool.put(username,session);
        userPool.put(session.getId(),username);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
//        收到后发送信息
        sendMessage("经过一系列处理",userPool.get(session.getId()));
    }

    public static void sendMessage(String message,String userId) throws IOException {
        Session s = sessionPool.get(userId);
        if(s!=null){
            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(){
        sessionPool.remove(userPool.get(session.getId()));
    }

    @OnError
    public void onError(Throwable error) throws Exception {
        throw new Exception("websocket error");
    }

    //   获取在线人数
    public static int getOnlineNum(){
        return sessionPool.size();
    }

}
*/
