package net.ace.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

public class NbtValueOutputList implements ValueOutput.ValueOutputList {
    private final ListTag list = new ListTag();

    public NbtValueOutputList(CompoundTag parentTag, String listKey) {
    }

    @Override
    public @NotNull ValueOutput addChild() {
        CompoundTag childTag = new CompoundTag();
        this.list.add(childTag);
        return new NbtValueOutput(childTag);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }


    // ✅ 必须实现 discardLast
    @Override
    public void discardLast() {
        if (!this.list.isEmpty()) {
            this.list.removeLast();
        }
    }
}