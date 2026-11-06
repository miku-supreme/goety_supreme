package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.CryptNecromancerConversion;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.api.NecromancerFocusHolder;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractNecromancer.class, remap = false)
public abstract class GoetyNecromancerFocusMixin implements NecromancerFocusHolder {

    @Unique
    private Set<String> goetySupreme$necromancerFocusGrants;

    @Unique
    private int goetySupreme$extraSummonMisses;

    @Unique
    private boolean goetySupreme$extraTopUpPending;

    @Unique
    private int goetySupreme$cheatSheets;

    @Override
    public Set<String> goetySupreme$necromancerFocusGrants() {
        if (this.goetySupreme$necromancerFocusGrants == null) {
            this.goetySupreme$necromancerFocusGrants = new HashSet<>();
        }
        return this.goetySupreme$necromancerFocusGrants;
    }

    @Override
    public int goetySupreme$extraSummonMisses() {
        return this.goetySupreme$extraSummonMisses;
    }

    @Override
    public void goetySupreme$setExtraSummonMisses(int misses) {
        this.goetySupreme$extraSummonMisses = Math.max(0, misses);
    }

    @Override
    public boolean goetySupreme$extraTopUpPending() {
        return this.goetySupreme$extraTopUpPending;
    }

    @Override
    public void goetySupreme$setExtraTopUpPending(boolean pending) {
        this.goetySupreme$extraTopUpPending = pending;
    }

    @Override
    public int goetySupreme$cheatSheets() {
        return this.goetySupreme$cheatSheets;
    }

    @Override
    public void goetySupreme$setCheatSheets(int sheets) {
        this.goetySupreme$cheatSheets = Math.max(0,
                Math.min(sheets, FocusSlotRules.MAX_CHEAT_SHEETS));
    }

    @Inject(method = "m_6071_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$grantDeathFocus(Player player, InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> callback) {
        AbstractNecromancer self = (AbstractNecromancer) (Object) this;
        ItemStack stack = player.getItemInHand(hand);
        if (CryptNecromancerConversion.tryConvert(self, player, stack)) {
            callback.setReturnValue(InteractionResult.SUCCESS);
            return;
        }
        if (UniversalSoulJarItem.tryFeed(self, player, stack, hand)) {
            callback.setReturnValue(InteractionResult.SUCCESS);
            return;
        }
        if (NecromancerSummonExtras.tryGrantFocus(self, player, stack)) {
            callback.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "m_7380_", at = @At("TAIL"), require = 0)
    private void goetySupreme$saveDeathFocus(CompoundTag tag, CallbackInfo callback) {
        ListTag list = new ListTag();
        for (String grant : this.goetySupreme$necromancerFocusGrants()) {
            list.add(StringTag.valueOf(grant));
        }
        tag.put("GoetySupremeNecroFocus", list);
        tag.putInt("GoetySupremeNecroMisses", this.goetySupreme$extraSummonMisses);
        tag.putInt(FocusSlotRules.CHEAT_SHEETS_TAG, this.goetySupreme$cheatSheets);
    }

    @Inject(method = "m_7378_", at = @At("TAIL"), require = 0)
    private void goetySupreme$readDeathFocus(CompoundTag tag, CallbackInfo callback) {
        this.goetySupreme$necromancerFocusGrants = null;
        if (tag.contains("GoetySupremeNecroFocus", Tag.TAG_LIST)) {
            ListTag list = tag.getList("GoetySupremeNecroFocus", Tag.TAG_STRING);
            for (int i = 0; i < list.size(); ++i) {
                this.goetySupreme$necromancerFocusGrants().add(list.getString(i));
            }
        }
        this.goetySupreme$extraSummonMisses = Math.max(0, tag.getInt("GoetySupremeNecroMisses"));
        this.goetySupreme$setCheatSheets(tag.getInt(FocusSlotRules.CHEAT_SHEETS_TAG));
    }
}
