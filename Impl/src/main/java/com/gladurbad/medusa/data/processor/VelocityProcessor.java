package com.gladurbad.medusa.data.processor;


import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.util.PacketUtil;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityvelocity.WrappedPacketOutEntityVelocity;
import io.github.retrooper.packetevents.packetwrappers.play.out.explosion.WrappedPacketOutExplosion;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
public class VelocityProcessor {
    private final PlayerData data;

    private double velocityX,velocityY,velocityZ;
    private double explosionX,explosionY,explosionZ;
    private short velocityID,explosionID;
    @Setter
    private boolean isVerifyVelocity;
    private int ticksSinceVelocity,verifyVelocityTick;


    public VelocityProcessor(PlayerData playerData) {
        this.data = playerData;
    }

    public void handleInTransaction(short transactionID) {
        if (transactionID == this.velocityID || transactionID == this.explosionID) {
            this.isVerifyVelocity = false;
        }
    }

    public void handleOutVelocity(WrappedPacketOutEntityVelocity wrapper) {
        this.ticksSinceVelocity = 0;
        this.velocityX = wrapper.getVelocityX();
        this.velocityY = wrapper.getVelocityY();
        this.velocityZ = wrapper.getVelocityZ();
        this.velocityID = (short) ThreadLocalRandom.current().nextInt(32767);
        PacketUtil.sendPacket(data.getPlayer(),new WrappedPacketOutTransaction(0,this.velocityID,false));
        this.isVerifyVelocity = true;
        this.verifyVelocityTick = 0;
    }

    public void handleOutExplosion(WrappedPacketOutExplosion wrapper) {
        this.ticksSinceVelocity = 0;
        this.explosionX = wrapper.getPlayerMotionX();
        this.explosionY = wrapper.getPlayerMotionY();
        this.explosionZ = wrapper.getPlayerMotionZ();
        this.explosionID = (short) ThreadLocalRandom.current().nextInt(32767);
        PacketUtil.sendPacket(data.getPlayer(),new WrappedPacketOutTransaction(0,this.explosionID,false));
        this.isVerifyVelocity = true;
        this.verifyVelocityTick = 0;
    }

    public void handleFlying() {
        ++ticksSinceVelocity;
        if (isVerifyVelocity) {
            ++verifyVelocityTick;
        }
    }

    public boolean isVerifyVelocity() {
        return isVerifyVelocity;
    }
}
