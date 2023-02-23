package com.gladurbad.medusa.packet.processor;

import com.gladurbad.medusa.data.PlayerData;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityteleport.WrappedPacketOutEntityTeleport;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityvelocity.WrappedPacketOutEntityVelocity;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.out.explosion.WrappedPacketOutExplosion;
import io.github.retrooper.packetevents.packetwrappers.play.out.position.WrappedPacketOutPosition;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import org.bukkit.Bukkit;

import java.util.Random;

public final class SendingPacketProcessor  {
    private long cur;
    public void handle(final PlayerData data, final Packet packet) {
        if (packet.isVelocity()) {
            final WrappedPacketOutEntityVelocity wrapper = new WrappedPacketOutEntityVelocity(packet.getRawPacket());
            if (wrapper.getEntity() == data.getPlayer()) {
                data.getVelocityProcessor().setBypassTicks(22);
                data.getVelocityProcessor().handle(wrapper.getVelocityX(), wrapper.getVelocityY(), wrapper.getVelocityZ());
            }
        }
        if (packet.isExplosion()) {
            data.getVelocityProcessor().setBypassTicks(22);
            final WrappedPacketOutExplosion wrapper = new WrappedPacketOutExplosion(packet.getRawPacket());
            data.getVelocityProcessor().handle(wrapper.getPlayerMotionX(), wrapper.getPlayerMotionY(), wrapper.getPlayerMotionZ());
        }
        if (packet.isOutPosition()) {
            final WrappedPacketOutPosition wrapper = new WrappedPacketOutPosition(packet.getRawPacket());
            data.getPositionProcessor().handleServerPosition(wrapper);
        }
        if (!data.getPlayer().hasPermission("medusa.bypass") || data.getPlayer().isOp()) {
            data.getChecks().forEach(check -> check.handle(packet));
        }
        if (System.currentTimeMillis() - cur >= 100) {
            final WrappedPacketOutTransaction wrapped = new WrappedPacketOutTransaction(0, (short) new Random().nextInt(32767),false);
            data.getTransactionProcessor().handleTransactionSend(wrapped);
            cur = System.currentTimeMillis();
        }
    }
}
