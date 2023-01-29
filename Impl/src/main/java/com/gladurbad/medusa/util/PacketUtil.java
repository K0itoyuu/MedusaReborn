package com.gladurbad.medusa.util;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packetwrappers.SendableWrapper;
import io.github.retrooper.packetevents.packetwrappers.play.out.entity.WrappedPacketOutEntity;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityteleport.WrappedPacketOutEntityTeleport;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityvelocity.WrappedPacketOutEntityVelocity;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import org.bukkit.entity.Player;

public class PacketUtil {
    public static WrappedPacketOutEntityVelocity S12PacketEntityVelocity(Player player,double velocityX,double velocityY,double velocityZ) {
        return new WrappedPacketOutEntityVelocity(player,velocityX,velocityY,velocityZ);
    }

    public static WrappedPacketOutEntityTeleport S08PacketPlayerPosLook(Player player,double x,double y,double z,double yaw,double pitch,boolean onGround) {
        return new WrappedPacketOutEntityTeleport(player.getEntityId(),x,y,z,(float) yaw,(float) pitch,onGround);
    }

    public static WrappedPacketOutTransaction S32PacketConfirmTransaction(Player player,int windowID, short actionNumber, boolean accepted) {
        return new WrappedPacketOutTransaction(windowID,actionNumber,accepted);
    }

    public static void sendPacket(Player player, SendableWrapper packet) {
        PacketEvents.get().getPlayerUtils().sendPacket(player,packet);
    }
}
