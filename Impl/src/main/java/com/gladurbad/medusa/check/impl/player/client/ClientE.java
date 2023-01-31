package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Client (E)",description = "High Ping")
public class ClientE extends Check {
    public ClientE(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getTransactionProcessor().getServerTransactionPing() > 1500) {
            buffer += 0.5;
        } else {
            buffer -= 0.2;
        }

        if (buffer >= 2.0) {
            fail("ServerPing: " + data.getTransactionProcessor().getServerTransactionPing());
        }
    }
}
