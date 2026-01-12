package net.ace.util;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public final class AgeableDataAccess {
    /* 只注册一次，全局单例 */
    public static final EntityDataAccessor<Boolean> BABY_LOCK =
            SynchedEntityData.defineId(AgeableMob.class, EntityDataSerializers.BOOLEAN);

    private AgeableDataAccess() {}
}