package com.gladurbad.medusa.manager;

import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.check.impl.combat.autoblock.AutoBlockA;
import com.gladurbad.medusa.check.impl.combat.autoblock.AutoBlockB;
import com.gladurbad.medusa.check.impl.combat.killaura.KillAuraF;
import com.gladurbad.medusa.check.impl.combat.killaura.*;
import com.gladurbad.medusa.check.impl.combat.reach.ReachA;
import com.gladurbad.medusa.check.impl.combat.velocity.VelocityA;
import com.gladurbad.medusa.check.impl.combat.velocity.VelocityB;
import com.gladurbad.medusa.check.impl.movement.fly.*;
import com.gladurbad.medusa.check.impl.movement.jesus.JesusA;
import com.gladurbad.medusa.check.impl.movement.jesus.JesusB;
import com.gladurbad.medusa.check.impl.movement.jesus.JesusC;
import com.gladurbad.medusa.check.impl.movement.noslow.*;
import com.gladurbad.medusa.check.impl.movement.sprint.*;
import com.gladurbad.medusa.check.impl.movement.speed.*;
import com.gladurbad.medusa.check.impl.player.client.*;
import com.gladurbad.medusa.check.impl.player.inventory.*;
import com.gladurbad.medusa.check.impl.player.protocol.*;
import com.gladurbad.medusa.check.impl.player.rotation.RotationA;
import com.gladurbad.medusa.check.impl.player.rotation.RotationB;
import com.gladurbad.medusa.check.impl.player.rotation.RotationC;
import com.gladurbad.medusa.check.impl.player.scaffold.ScaffoldA;
import com.gladurbad.medusa.check.impl.player.scaffold.ScaffoldB;
import com.gladurbad.medusa.check.impl.player.scaffold.ScaffoldC;
import com.gladurbad.medusa.config.Config;
import com.gladurbad.medusa.data.PlayerData;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public final class CheckManager {

    public static final Class<?>[] CHECKS = new Class[] {
            ScaffoldA.class,
            ScaffoldB.class,
            ScaffoldC.class,

            VelocityA.class,
            VelocityB.class,

            NoSlowA.class,
            NoSlowB.class,

            ClientA.class,
            ClientB.class,
            ClientC.class,

            FlyA.class,
            FlyB.class,
            FlyC.class,
            FlyD.class,
            FlyE.class,
            FlyF.class,
            FlyG.class,

            SpeedA.class,
            SpeedB.class,
            SpeedC.class,

            SprintA.class,
            SprintB.class,
            SprintC.class,

            JesusA.class,
            JesusB.class,
            JesusC.class,

            AutoBlockA.class,
            AutoBlockB.class,

            KillAuraA.class,
            KillAuraB.class,
            KillAuraC.class,
            KillAuraD.class,
            KillAuraE.class,
            KillAuraF.class,
            KillAuraG.class,
            KillAuraH.class,

            InventoryA.class,
            InventoryB.class,
            InventoryC.class,
            InventoryD.class,

            ProtocolA.class,
            ProtocolB.class,
            ProtocolC.class,
            ProtocolD.class,
            ProtocolE.class,
            ProtocolF.class,
            ProtocolG.class,
            ProtocolH.class,
            ProtocolI.class,
            ProtocolJ.class,
            //ProtocolK.class,
            ProtocolL.class,
            RotationA.class,
            RotationB.class,
            RotationC.class,
    };

    private static final List<Constructor<?>> CONSTRUCTORS = new ArrayList<>();

    public static List<Check> loadChecks(final PlayerData data) {
        final List<Check> checkList = new ArrayList<>();
        for (Constructor<?> constructor : CONSTRUCTORS) {
            try {
                checkList.add((Check) constructor.newInstance(data));
            } catch (Exception exception) {
                System.err.println("Failed to load checks for " + data.getPlayer().getName());
                exception.printStackTrace();
            }
        }
        return checkList;
    }

    public static void setup() {
        for (Class<?> clazz : CHECKS) {
            if (Config.ENABLED_CHECKS.contains(clazz.getSimpleName())) {
                try {
                    CONSTRUCTORS.add(clazz.getConstructor(PlayerData.class));
                } catch (NoSuchMethodException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}

