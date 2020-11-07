package com.project.server.request.message;


import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class GetMessage extends RequestHandler<String> {


    public GetMessage(ClientConnection clientConnection) {
        super(RequestType.GET_MESSAGE, clientConnection);
    }

    @Override
    protected String handle() {
        String message = clientConnection.getMessage();
        if (message != null && message.equalsIgnoreCase("")) {
            printResponse(message);
        }

        return message;
    }
}
