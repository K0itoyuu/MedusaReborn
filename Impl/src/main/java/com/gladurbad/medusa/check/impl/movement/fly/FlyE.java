package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.block.Block;

import java.util.List;


@CheckInfo(name = "Fly (E)",description = "GroundSpoof 2")
public class FlyE extends Check {
    public FlyE(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            WrappedPacketInFlying flying = new WrappedPacketInFlying(packet.getRawPacket());
            if (data.getPlayer().getFallDistance() > 3) {
                if (flying.isOnGround()) {
                    flying.setOnGround(false);
                    fail();
                }
            }
        }
    }
}
