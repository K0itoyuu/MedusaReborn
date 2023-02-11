package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Client (G)",description = "C0F Spam Crasher")
public class ClientG extends Check {
    private long lastTransactionDelay=114514L,transactionDelay = 114514L;

    public ClientG(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 10000) return;
        if (packet.isIncomingTransaction()) {
            lastTransactionDelay = transactionDelay;
            transactionDelay = System.currentTimeMillis();
            long delay = transactionDelay - lastTransactionDelay;
            if (delay <= 2) {
                fail("Invalid C0F Delay");
                packet.setCancelled(true);
            }
        }
    }
}
