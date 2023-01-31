package com.gladurbad.medusa.check.impl.player.rotation;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockplace.WrappedPacketInBlockPlace;
import io.github.retrooper.packetevents.utils.player.Direction;

@CheckInfo(name = "Rotation (C)",description = "Impossible placing block pitch")
public class RotationC extends Check {
    public RotationC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isBlockPlace()) {
            WrappedPacketInBlockPlace wrapped = new WrappedPacketInBlockPlace(packet.getRawPacket());
            Direction direction = wrapped.getDirection();
            double pitch = data.getRotationProcessor().getPitch();
            debug("direction:" + direction.name() + ", pitch:" + pitch);
            if (!direction.equals(Direction.OTHER)) {
                boolean invalid = (pitch > 88.3f && !direction.equals(Direction.UP)) || (pitch < -88.3f && !direction.equals(Direction.DOWN));
                if (invalid) {
                    buffer += 1.0;
                }

                if (buffer >= 3.5) {
                    fail("pitch:" + pitch + ", direction:" + direction.name());
                    buffer = 0;
                }
            }
        }
    }
}
