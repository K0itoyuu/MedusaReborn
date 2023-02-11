package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;

@CheckInfo(name = "Client (B)",description = "Fake Transaction")
public class ClientB extends Check {

    public ClientB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 6000) return;

        if (data.getTransactionProcessor().isInvalidTransaction()) {
            packet.setCancelled(true);
            WrappedPacketOutTransaction transaction = new WrappedPacketOutTransaction(0,data.getTransactionProcessor().getTransactionID(),true);
            data.getTransactionProcessor().handleTransactionSend(transaction);
            fail("Server: " + data.getTransactionProcessor().getServerTransactionID() + ", Client:" + data.getTransactionProcessor().getInvalidTransactionValue());
        }
    }
}
