package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Client (E)",description = "Delayed sending ConfirmTransaction",experimental = true)
public class ClientE extends Check {

    public ClientE(PlayerData data) {
        super(data);
    }

    private long flyPacketDelay;

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 6000L) return;

        if (packet.isFlying()) {
            long now = data.getJoinTime();
            long flyPacketDelayTemp = now - flyPacketDelay;
            long confirmTransactionDelay = data.getTransactionProcessor().getTransactionPing();
            long a = Math.max(flyPacketDelayTemp,confirmTransactionDelay);
            long b = Math.min(flyPacketDelayTemp,confirmTransactionDelay);
            debug("Max: " + a + ", Min: " + b + ", Delay: " + (a - b));

            if (a - b > 80) {
                buffer++;
            }

            if (buffer >= 10) {
                fail("Delay: " + (a - b));
                buffer -= buffer / 2;
            }

            flyPacketDelay = now;
        }
    }
}
