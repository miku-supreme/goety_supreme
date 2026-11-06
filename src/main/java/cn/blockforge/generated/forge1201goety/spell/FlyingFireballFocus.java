package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.items.magic.MagicFocus;
import cn.blockforge.generated.goetysupreme.config.NewFocusSummonConfig;
import cn.blockforge.generated.goetysupreme.spell.FlyingFireballFocus;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.ServantSummonSpell;

public final class FlyingFireballFocus extends MagicFocus {

    public FlyingFireballFocus() {
        super(new ServantSummonSpell(NewFocusSummonConfig.FIREBALL, SpellType.NETHER,
                ServantSummonSpell.SoundProfile.BLAZING,
                level -> ModEntityTypes.FLYING_FIREBALL_SERVANT.get().create(level),
                entity -> entity instanceof FlyingFireballServant));
    }
}
