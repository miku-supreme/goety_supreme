package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.items.magic.MagicFocus;
import cn.blockforge.generated.goetysupreme.config.NewFocusSummonConfig;
import cn.blockforge.generated.goetysupreme.spell.IronTeethFocus;
import cn.blockforge.generated.goetysupreme.entity.IronTeethServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.ServantSummonSpell;

public final class IronTeethFocus extends MagicFocus {

    public IronTeethFocus() {
        super(new ServantSummonSpell(NewFocusSummonConfig.IRON_TEETH, SpellType.GEOMANCY,
                ServantSummonSpell.SoundProfile.MINISTROUS,
                level -> ModEntityTypes.IRON_TEETH_SERVANT.get().create(level),
                entity -> entity instanceof IronTeethServant));
    }
}
