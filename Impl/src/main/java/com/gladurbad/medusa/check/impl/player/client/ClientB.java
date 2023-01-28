package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Client (B)",description = "Fake Transaction")
public class ClientB extends Check {

    public ClientB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getTransactionProcessor().isInvalidTransaction()) {
            fail("Server: " + data.getTransactionProcessor().getServerTransactionID() + ", Client:" + data.getTransactionProcessor().getInvalidTransactionValue());
        }
    }
}
