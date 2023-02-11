package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PacketUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import io.github.retrooper.packetevents.packetwrappers.play.in.transaction.WrappedPacketInTransaction;
import io.github.retrooper.packetevents.utils.player.ClientVersion;

import java.util.LinkedList;

@CheckInfo(name = "Client (D)",description = "FreeCam Cheat")
public class ClientD extends Check {
    LinkedList<WrappedPacketInTransaction> transactions = new LinkedList<>();
    LinkedList<WrappedPacketInFlying> flyings = new LinkedList<>();
    public ClientD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (!isExempt(ExemptType.TPS)) {
            if (packet.isIncomingTransaction()) {
                WrappedPacketInTransaction wrapped = new WrappedPacketInTransaction(packet.getRawPacket());
                transactions.add(wrapped);

                if (transactions.size() >= 2) {
                    if (flyings.isEmpty()) {
                        PacketUtil.sendPacket(
                                data.getPlayer(),
                                PacketUtil.S08PacketPlayerPosLook(
                                        data.getPlayer(),
                                        data.getPositionProcessor().getX(),
                                        data.getPositionProcessor().getY(),
                                        data.getPositionProcessor().getZ(),
                                        data.getRotationProcessor().getYaw(),
                                        data.getRotationProcessor().getPitch(),
                                        data.getPositionProcessor().isOnGround()
                                )
                        );
                        fail("FreeCam Cheating...");
                    }
                    transactions.clear();
                    flyings.clear();
                }
            }
            if (packet.isFlying()) {
                WrappedPacketInFlying flying = new WrappedPacketInFlying(packet.getRawPacket());
                flyings.add(flying);
            }
        }
    }
}
