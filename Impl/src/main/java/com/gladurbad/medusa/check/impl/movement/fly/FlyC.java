package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.Location;
import org.bukkit.block.Block;

@CheckInfo(name = "Fly (F)",description = "GhostBlock / FakeBlock")
public class FlyC extends Check {
    public FlyC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition() && data.getPlayer().getFallDistance() < 5) {
            WrappedPacketInFlying wrapped = new WrappedPacketInFlying(packet.getRawPacket());
            boolean positionOnGround = (wrapped.getY() % 0.015625 == 0.0);
            boolean e = isExempt(ExemptType.PLACING,ExemptType.FLYING);
            if (wrapped.isOnGround() && positionOnGround && !e) {
                if (!data.getPlayer().isOnGround() && data.getPositionProcessor().isInAir()) {
                    Location location = new Location(data.getPlayer().getWorld(),wrapped.getX(),wrapped.getY()-0.45,wrapped.getZ());
                    Block underBlock = location.getBlock();
                    if (underBlock.isEmpty()) {
                        buffer += 0.5;
                    }
                }
            }
            if (buffer >= 3.0) {
                fail();
                buffer = 0;
            }
        }
    }
}
