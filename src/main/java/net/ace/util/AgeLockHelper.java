package net.ace.util;

import net.minecraft.world.entity.AgeableMob;

public final class AgeLockHelper {
    public static boolean isBabyLock(AgeableMob mob) {
        return mob.getEntityData().get(AgeableDataAccess.BABY_LOCK);
    }
    public static void setBabyLock(AgeableMob mob, boolean lock) {
        mob.getEntityData().set(AgeableDataAccess.BABY_LOCK, lock);
        if (lock) mob.setBaby(true);
    }
}