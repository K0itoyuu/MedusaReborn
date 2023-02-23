package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.List;


@CheckInfo(name = "Fly (E)",description = "GroundSpoof 2")
public class FlyE extends Check {
    public FlyE(PlayerData data) {
        super(data);
    }

    private boolean exempt = false;

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            WrappedPacketInFlying flying = new WrappedPacketInFlying(packet.getRawPacket());
            Location location = data.getPlayer().getLocation();
            if (!data.getPlayer().getWorld().getBlockAt(location.getBlockX(),location.getBlockY()-2,location.getBlockZ()).isEmpty()) {
                exempt = true;
            }

            if (data.getPositionProcessor().isMathematicallyOnGround() && data.getPositionProcessor().getFallDistance() == 0)
                exempt = false;

            boolean isExempt = isExempt(ExemptType.UNDER_BLOCK,ExemptType.PLACING);
            if (!exempt && flying.isOnGround() && data.getPositionProcessor().isInAir() && !isExempt && data.getPositionProcessor().getFallDistance() > 3.3) {
                fail();
                exempt = false;
            }
        }
    }
}
