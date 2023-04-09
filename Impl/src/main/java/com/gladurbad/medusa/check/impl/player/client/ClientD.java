package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.transaction.WrappedPacketInTransaction;

import java.util.LinkedList;

@CheckInfo(name = "Client (D)",description = "Cancelled flying packet")
public class ClientD extends Check {

    public ClientD(PlayerData data) {
        super(data);
    }

    private final LinkedList<Packet> transaction = new LinkedList<>();

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 6000L) return;

        if (data.getPlayer().isDead()) return;

        if (packet.isIncomingTransaction()) {
            transaction.add(packet);
        }

        if (packet.isFlying()) {
            transaction.clear();
        }

        if (transaction.size() >= 15) {
            buffer++;
        }

        if (buffer >= 20) {
            fail("size:" + transaction.size());
            buffer = 0;
        }
    }
}
