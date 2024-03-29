package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.transaction.WrappedPacketInTransaction;

@CheckInfo(name = "Client (A)",description = "Client Cancelled Transaction")
public class ClientA extends Check {

    public ClientA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 6000L) return;

        if (packet.isFlying()) {
            buffer += 1.0;
        } else if (packet.isIncomingTransaction()) {
            buffer = 0;
        }

        if (buffer >= 20.0) {
            fail("buffer:" + buffer);
            buffer = 0;
        }
    }
}
