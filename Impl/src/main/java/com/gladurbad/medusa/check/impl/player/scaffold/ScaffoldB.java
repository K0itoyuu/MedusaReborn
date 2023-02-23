package com.gladurbad.medusa.check.impl.player.scaffold;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockplace.WrappedPacketInBlockPlace;

@CheckInfo(name = "Scaffold (B)",description = "Checks for tower cheat")
public class ScaffoldB extends Check {

    private int lastBlockX,lastBlockY,lastBlockZ;
    private int blockX,blockY,blockZ,movement,count,lastMovement;

    public ScaffoldB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isBlockPlace()) {
            boolean isExempt = isExempt(ExemptType.FLYING);
            if (isExempt) return;
            WrappedPacketInBlockPlace blockPlace = new WrappedPacketInBlockPlace(packet.getRawPacket());
            if (data.getPlayer().getItemInHand().getType().isBlock()) {
                lastBlockX = blockX;
                lastBlockY = blockY;
                lastBlockZ = blockZ;
                blockX = blockPlace.getBlockPosition().getX();
                blockY = blockPlace.getBlockPosition().getY();
                blockZ = blockPlace.getBlockPosition().getZ();
                boolean isTower = lastBlockX == blockX && lastBlockZ == blockZ && blockY > lastBlockY;
                if (isTower) {
                    debug("m:" + movement + ",c:" + count);
                    if (movement == lastMovement) {
                        count++;
                    } else {
                        count = 0;
                        buffer = 0;
                    }

                    if (lastMovement == movement) {
                        buffer += 2;
                    }

                    if (buffer >= 4) {
                        fail("m:" + movement + ",c:" + count);
                    }

                    lastMovement = movement;
                    movement = 0;
                } else {
                    buffer = Math.max(0,buffer - 0.5);
                }
            }
        } else if (packet.isFlying()) {
            if (movement >= 8) {
                movement = 0;
                return;
            }

            movement ++;
        }
    }
}
