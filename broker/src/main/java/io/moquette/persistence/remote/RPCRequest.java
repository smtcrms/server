package io.moquette.persistence.remote;

import io.moquette.persistence.RPCCenter;
import win.liyufan.im.ErrorCode;

import java.io.Serializable;

import static io.moquette.persistence.RPCCenter.CHECK_USER_COUNT_LIMIT;
import static io.moquette.persistence.RPCCenter.CHECK_USER_MASTER_LICENSE;
import static io.moquette.persistence.RPCCenter.UPDATE_USER_COUNT;

public class RPCRequest implements Runnable, Serializable {
    final int requestId;
    final byte[] message;
    final String from;
    final String request;
    final String fromUser;
    final String clientId;

    public RPCRequest(String fromUser, String clientId, byte[] message, int requestId, String from, String request) {
        this.requestId = requestId;
        this.message = message;
        this.from = from;
        this.request = request;
        this.fromUser = fromUser;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        if(!RPCCenter.getInstance().handleCommercialRequest(request, clientId, from, requestId)) {
            RPCCenter.getInstance().onReceiveRequest(fromUser, clientId, message, requestId, from, request);
        }
    }
}