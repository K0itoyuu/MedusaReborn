package com.gladurbad.medusa.check.impl.movement.sprint;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import org.bukkit.entity.Player;

@CheckInfo(name = "Sprint (B)",description = "Client Ignored Blindness")
public class SprintB extends Check {

    public SprintB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getActionProcessor().isSprinting()) {
            if (player.hasPotionEffect(darkness)) {
                fail();
                data.getPlayer().setSprinting(false);
            }
        }
    }
}
