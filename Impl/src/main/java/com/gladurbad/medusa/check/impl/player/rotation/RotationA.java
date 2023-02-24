package com.gladurbad.medusa.check.impl.player.rotation;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import org.bukkit.util.Vector;

@CheckInfo(name = "Rotation (A)",description = "checks for rotation direction")
public class RotationA extends Check {
    private int teleportTicks;
    private int offGroundTicks;
    private final Vector direction = new Vector(0, 0, 0);
    public RotationA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            if (data.getActionProcessor().isSprinting() && data.getVelocityProcessor().getTicksSinceVelocity() > 15 && ++teleportTicks > 10 && !data.getPlayer().isFlying()) {
                double deltaX = data.getPositionProcessor().getX() - data.getPositionProcessor().getLastX();
                double deltaZ = data.getPositionProcessor().getZ() - data.getPositionProcessor().getLastZ();
                double directionX = -Math.sin(data.getPlayer().getEyeLocation().getYaw() * (float)Math.PI / 180.0f) * 1.0 * 0.5;
                double directionZ = Math.cos(data.getPlayer().getEyeLocation().getYaw() * (float)Math.PI / 180.0f) * 1.0 * 0.5;
                Vector positionDifference = new Vector(deltaX, 0.0, deltaZ);
                if (data.getPlayer().isOnGround()) {
                    offGroundTicks = 0;
                    direction.setX(directionX);
                    direction.setY(0);
                    direction.setZ(directionZ);
                } else {
                    ++offGroundTicks;
                }
                double angle = Math.toDegrees(positionDifference.angle(direction));
                boolean invalid = !data.getPositionProcessor().isInLiquid() && angle > 69.0 && data.getPositionProcessor().getDeltaXZ() > 0.256 && offGroundTicks < 8 && !isExempt(ExemptType.TELEPORT, ExemptType.VELOCITY);
                debug("angle=" + angle + " dxz=" + data.getPositionProcessor().getDeltaXZ() + " buffer=" + buffer);
                if (invalid) {
                    buffer += 1.25;
                    if (buffer >= 9.0) {
                        fail(String.format("angle=%.2f, buffer=%.2f", angle, buffer));
                    }
                } else {
                    buffer = Math.max(buffer - 0.5, 0.0);
                }
            }
        } else if (packet.isTeleport()) {
            teleportTicks = 0;
        }
    }
}
