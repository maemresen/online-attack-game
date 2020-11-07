package com.project.server.request.message;


import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class SendMessageToAll extends RequestHandler<Boolean> {

    private final String msg;

    public SendMessageToAll(ClientConnection clientConnection, String msg) {
        super(RequestType.SEND_MESSAGE_TO_ALL, clientConnection);
        this.msg = msg;
    }

    @Override
    protected Boolean handle() {
        getShared().sendMessageToAll(clientConnection.toString()+" > "+msg);
        return true;
    }
}
