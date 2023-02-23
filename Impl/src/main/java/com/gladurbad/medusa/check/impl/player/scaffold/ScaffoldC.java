package com.gladurbad.medusa.check.impl.player.scaffold;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockplace.WrappedPacketInBlockPlace;
import io.github.retrooper.packetevents.utils.player.Direction;

@CheckInfo(name = "Scaffold (C)",description = "Place block delay")
public class ScaffoldC extends Check {

    private int movement,lastMovement,lastY;

    public ScaffoldC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isBlockPlace()) {
            boolean isExempt = isExempt(ExemptType.FLYING);
            if (isExempt) return;
            WrappedPacketInBlockPlace blockPlace = new WrappedPacketInBlockPlace(packet.getRawPacket());

            if (data.getPlayer().getItemInHand().getType().isBlock() && !blockPlace.getDirection().equals(Direction.OTHER)) {
                if (lastY == blockPlace.getBlockPosition().getY()) {
                    if (movement == lastMovement) {
                        buffer += 1.25;
                    } else {
                        buffer = 0;
                    }

                    if (buffer >= 5.0) {
                        fail("m:" + movement);
                    }
                }
                lastMovement = movement;
                movement = 0;
            }

            lastY = blockPlace.getBlockPosition().getY();
        }

        if (packet.isFlying()) {
            if (movement >= 6) {
                movement = 0;
                return;
            }

            movement ++;
        }
    }
}
