package com.gladurbad.medusa.check.impl.movement.speed;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;

@CheckInfo(name = "Speed (D)",description = "InWeb Speed")
public class SpeedD extends Check {

    public SpeedD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition() && data.getPositionProcessor().isInWeb()) {
            WrappedPacketInFlying flying = new WrappedPacketInFlying(packet.getRawPacket());
            if (flying.isPosition() && isExempt(ExemptType.FLYING,ExemptType.VELOCITY)) {
                double motionY = data.getPositionProcessor().getLastDeltaY();
                double maxMotionY = PlayerUtil.maxMotionY(data.getPlayer()) * 0.2D;
                double motionXZ = data.getPositionProcessor().getLastDeltaXZ();
                double maxMotionXZ = PlayerUtil.maxSpeed(data.getPlayer()) * 0.2D;
                if (motionY > maxMotionY) {
                    fail("YDiff:" + (motionY - maxMotionY));
                }

                if (motionXZ > maxMotionXZ) {
                    fail("XZDiff:" + (motionXZ - maxMotionXZ));
                }
            }
        }
    }
}
