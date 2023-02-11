package com.gladurbad.medusa.util;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packetwrappers.SendableWrapper;
import io.github.retrooper.packetevents.packetwrappers.play.out.entity.WrappedPacketOutEntity;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityteleport.WrappedPacketOutEntityTeleport;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityvelocity.WrappedPacketOutEntityVelocity;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import org.bukkit.entity.Player;

public class PacketUtil {

    public static WrappedPacketOutEntityTeleport S08PacketPlayerPosLook(Player player,double x,double y,double z,double yaw,double pitch,boolean onGround) {
        return new WrappedPacketOutEntityTeleport(player.getEntityId(),x,y,z,(float) yaw,(float) pitch,onGround);
    }

    public static void sendPacket(Player player, SendableWrapper packet) {
        PacketEvents.get().getPlayerUtils().sendPacket(player,packet);
    }
}
