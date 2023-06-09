package com.gladurbad.medusa.check.impl.combat.velocity;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import org.bukkit.Location;

@CheckInfo(name = "Velocity (A)",description = "Checks if the player vertical velocity")
public class VelocityA extends Check {

    public VelocityA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            if (data.getVelocityProcessor().getVelocityY() > 0D && data.getVelocityProcessor().getTicksSinceVelocity() <= 1) {
                double deltaY = data.getPositionProcessor().getDeltaY();
                double targetY = hasBlockOnHead() ? 0.17D : data.getVelocityProcessor().getVelocityY() - 0.04D;
                debug("deltaY: " + deltaY + ", targetY: " + targetY);
                if (deltaY < targetY) {
                    buffer += 1.25;
                } else {
                    buffer = Math.max(0, buffer - 0.35);
                }

                if (buffer >= 3.25) {
                    fail("deltaY: " + deltaY + ", targetY: " + targetY);
                    buffer = 0;
                }
            }
        }
    }

    private boolean hasBlockOnHead() {
        Location location = data.getPlayer().getLocation().add(0,2,0);
        return !data.getPlayer().getWorld().getBlockAt(location).isEmpty();
    }
}
