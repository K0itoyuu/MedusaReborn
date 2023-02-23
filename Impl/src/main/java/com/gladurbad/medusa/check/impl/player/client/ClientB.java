package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Client (B)",description = "PingSpoof")
public class ClientB extends Check {
    public ClientB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        long diff = Math.abs(data.getTransactionProcessor().getTransactionPing() - data.getTransactionProcessor().getPing());
        boolean invalid = Math.abs(data.getTransactionProcessor().getTransactionPing() - data.getTransactionProcessor().getPing()) > 120;

        if (data.getJoinTime() < 6000L) return;

        if (invalid) {
            buffer += 0.5;
        } else {
            buffer -= 0.5;
        }

        if (buffer>= 5.0) {
            fail("diff:" + diff);
            buffer = 0;
        }
    }
}
