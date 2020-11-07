package com.project.server.request.message;


import com.project.core.Connection;
import com.project.core.game.Game;
import com.project.core.Person;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class SendGameMessage extends RequestHandler<Boolean> {

    private final String message;


    public SendGameMessage(ClientConnection clientConnection, String message) {
        super(RequestType.SEND_GAME_MESSAGE, clientConnection);

        this.message = message;
    }

    @Override
    protected Boolean handle() {

        Game game = getShared().getGame(clientConnection);

        if (game == null) {
            return false;
        }

        sendMessageToPerson(game.getCreator());
        sendMessageToPerson(game.getOpponent());

        return true;
    }

    private void sendMessageToPerson(Person person) {

        if (person == null) {
            return;
        }


        Connection connection = getShared().getConnectionById(person.getConnectionId());

        if (connection == null) {
            return;
        }

        connection.addGameMessage(clientConnection+" > "+message);

    }
}
