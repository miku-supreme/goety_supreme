package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class FlungBlockEntity extends Entity {

    private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE =
            SynchedEntityData.defineId(FlungBlockEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<Float> DATA_ANIM_VY =
            SynchedEntityData.defineId(FlungBlockEntity.class, EntityDataSerializers.FLOAT);

    public float animY;
    public float prevAnimY;

    public FlungBlockEntity(EntityType<? extends FlungBlockEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public FlungBlockEntity(Level level, double x, double y, double z, BlockState state, float vy) {
        this(ModEntityTypes.FLUNG_BLOCK.get(), level);
        setBlockState(state);
        setAnimVy(vy);
        setPos(x, y, z);
    }

    @Override
    public void defineSynchedData() {
        entityData.define(DATA_BLOCK_STATE, Blocks.AIR.defaultBlockState());
        entityData.define(DATA_ANIM_VY, 1.0F);
    }

    public BlockState getBlockState() {
        return entityData.get(DATA_BLOCK_STATE);
    }

    public void setBlockState(BlockState state) {
        entityData.set(DATA_BLOCK_STATE, state);
    }

    public float getAnimVy() {
        return entityData.get(DATA_ANIM_VY);
    }

    public void setAnimVy(float value) {
        entityData.set(DATA_ANIM_VY, value);
    }

    @Override
    public void tick() {
        super.tick();
        this.prevAnimY = this.animY;
        this.animY += getAnimVy();
        setAnimVy(getAnimVy() - 0.1F);
        if (this.animY < -0.5F) {
            discard();
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.put("BlockState", NbtUtils.writeBlockState(getBlockState()));
        tag.putFloat("AnimY", this.animY);
        tag.putFloat("PrevAnimY", this.prevAnimY);
        tag.putFloat("AnimVY", getAnimVy());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        BlockState state = NbtUtils.readBlockState(
                level().holderLookup(net.minecraft.core.registries.Registries.BLOCK),
                tag.getCompound("BlockState"));
        setBlockState(state);
        this.animY = tag.getFloat("AnimY");
        this.prevAnimY = tag.getFloat("PrevAnimY");
        setAnimVy(tag.getFloat("AnimVY"));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
