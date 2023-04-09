package com.gladurbad.medusa.manager;

import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.check.impl.combat.aim.AimA;
import com.gladurbad.medusa.check.impl.combat.aim.AimB;
import com.gladurbad.medusa.check.impl.combat.autoblock.*;
import com.gladurbad.medusa.check.impl.combat.critical.*;
import com.gladurbad.medusa.check.impl.combat.killaura.*;
import com.gladurbad.medusa.check.impl.combat.velocity.*;
import com.gladurbad.medusa.check.impl.movement.fly.*;
import com.gladurbad.medusa.check.impl.movement.groundspoof.GroundSpoofA;
import com.gladurbad.medusa.check.impl.movement.groundspoof.GroundSpoofB;
import com.gladurbad.medusa.check.impl.movement.jesus.*;
import com.gladurbad.medusa.check.impl.movement.motion.*;
import com.gladurbad.medusa.check.impl.movement.noslow.*;
import com.gladurbad.medusa.check.impl.movement.sprint.*;
import com.gladurbad.medusa.check.impl.movement.speed.*;
import com.gladurbad.medusa.check.impl.player.client.*;
import com.gladurbad.medusa.check.impl.player.impossible.*;
import com.gladurbad.medusa.check.impl.player.inventory.*;
import com.gladurbad.medusa.check.impl.player.protocol.*;
import com.gladurbad.medusa.check.impl.player.rotation.*;
import com.gladurbad.medusa.check.impl.player.scaffold.*;
import com.gladurbad.medusa.config.Config;
import com.gladurbad.medusa.data.PlayerData;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public final class CheckManager {

    public static final Class<?>[] CHECKS = new Class[] {
            AimA.class,
            AimB.class,

            GroundSpoofA.class,
            GroundSpoofB.class,

            CriticalA.class,

            MotionA.class,
            MotionB.class,

            ImpossibleA.class,

            ScaffoldA.class,
            ScaffoldB.class,
            ScaffoldC.class,
            ScaffoldD.class,

            VelocityA.class,
            VelocityB.class,

            NoSlowA.class,
            NoSlowB.class,

            ClientA.class,
            ClientB.class,
            ClientC.class,
            ClientD.class,
            ClientE.class,
            ClientF.class,

            FlyA.class,
            FlyB.class,
            FlyC.class,

            SpeedA.class,
            SpeedB.class,
            SpeedC.class,
            SpeedD.class,

            SprintA.class,
            SprintB.class,
            SprintC.class,

            JesusA.class,
            JesusB.class,
            JesusC.class,

            AutoBlockA.class,
            AutoBlockB.class,
            AutoBlockC.class,

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
            ProtocolK.class,
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
                Check check = (Check) constructor.newInstance(data);
                checkList.add(check);
            } catch (Exception exception) {
                System.err.println("Failed to load checks for " + data.getPlayer().getName());
                exception.printStackTrace();
            }
        }
        return checkList;
    }

    public static void setup() {
        CONSTRUCTORS.clear();
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

