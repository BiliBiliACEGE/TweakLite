package net.ace.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.stream.Stream;

public class NbtValueOutput implements ValueOutput {
    private final CompoundTag tag;

    public NbtValueOutput(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public void putString(String key, String value) {
        this.tag.putString(key, value);
    }

    @Override
    public void putIntArray(String string, int[] is) {

    }

    @Override
    public ValueOutput child(String string) {
        return null;
    }

    @Override
    public void putDouble(String key, double value) {
        this.tag.putDouble(key, value);
    }

    @Override
    public void putShort(String key, short value) {
        this.tag.putShort(key, value);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        this.tag.putBoolean(key, value);
    }

    @Override
    public void putInt(String key, int value) {
        this.tag.putInt(key, value);
    }

    @Override
    public <T> void store(String key, Codec<T> codec, T value) {
        DataResult<Tag> result = codec.encodeStart(NbtOps.INSTANCE, value);
        result.result().ifPresent(tag -> this.tag.put(key, tag));
    }

    // ✅ 实现 store(MapCodec<T>, T) - 使用 codec() 获取普通 Codec
    @Override
    public <T> void store(MapCodec<T> codec, T value) {
        Codec<T> regularCodec = codec.codec();
        DataResult<Tag> result = regularCodec.encodeStart(NbtOps.INSTANCE, value);
        result.result().ifPresent(tag -> {
            if (tag instanceof CompoundTag compoundTag) {
                this.tag.merge(compoundTag);
            }
        });
    }

    @Override
    public void putLong(String key, long value) {
        this.tag.putLong(key, value);
    }

    @Override
    public void putByte(String key, byte value) {
        this.tag.putByte(key, value);
    }

    @Override
    public void putFloat(String key, float value) {
        this.tag.putFloat(key, value);
    }

    @Override
    public ValueOutputList childrenList(String key) {
        return new NbtValueOutputList(this.tag, key);
    }

    @Override
    public <T> TypedOutputList<T> list(String string, Codec<T> codec) {
        return null;
    }

    @Override
    public void discard(String string) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public <T> void storeNullable(String key, Codec<T> codec, T value) {
        if (value != null) {
            store(key, codec, value);
        }
    }
}