package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.Material;
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
            final List<Block> blocks = data.getPositionProcessor().getBoundingBox().expand(1, 1, 1).getBlocks(
                    data.getPlayer().getWorld()
            );

            boolean exempt = isExempt(ExemptType.LIQUID,ExemptType.TELEPORT,ExemptType.UNDER_BLOCK,ExemptType.STAIRS);
            final boolean check = blocks.stream().filter(block -> !block.isEmpty()).count() == 0;
            WrappedPacketInFlying wrapped = new WrappedPacketInFlying(packet.getRawPacket());
            if (check) debug(exempt + " " + wrapped.isOnGround() + " " + data.getPositionProcessor().isMathematicallyOnGround());
            if (check && !exempt) {
                if (wrapped.isOnGround() && !data.getPositionProcessor().isMathematicallyOnGround()) {
                    fail("c:" + check + " ,pg:" + wrapped.isOnGround());
                }
            }
        }
    }
}
