package com.gladurbad.medusa.check.impl.player.inventory;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Inventory (D)",description = "Checks for moving or rotation in inventory.")
public class InventoryD extends Check {

    public InventoryD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getActionProcessor().isInventory()) {
            if (packet.isRotation() && !packet.isPosLook()) {
                buffer += 1.0;
                if (buffer > 4.0) {
                    fail();
                    buffer = 0;
                }
            }
            if (packet.isPosition()) {
                boolean invalid = !isExempt(ExemptType.FLYING) && data.getVelocityProcessor().getBypassTicks() == 0 &&
                        data.getPositionProcessor().getDeltaXZ() > 0.185 && !data.getPositionProcessor().isTeleporting();
                if (invalid) buffer += 1.0;
                if (buffer > 4.0) {
                    fail("dxz: " + data.getPositionProcessor().getDeltaXZ());
                    data.getPlayer().closeInventory();
                    data.getActionProcessor().setInventory(false);
                    buffer = 0;
                }
            }
        } else {
            buffer = Math.max(buffer - 0.05,0);
        }
    }
}