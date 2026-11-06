package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.items.magic.MagicFocus;
import cn.blockforge.generated.goetysupreme.config.NewFocusSummonConfig;
import cn.blockforge.generated.goetysupreme.spell.BlazingSpiritFocus;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.ServantSummonSpell;

public final class BlazingSpiritFocus extends MagicFocus {

    public BlazingSpiritFocus() {
        super(new ServantSummonSpell(NewFocusSummonConfig.BLAZING_SPIRIT, SpellType.NETHER,
                ServantSummonSpell.SoundProfile.BLAZING,
                level -> ModEntityTypes.FLAMOAR_SERVANT.get().create(level),
                entity -> entity instanceof FlamoarServant,
                ServantSummonSpell.StaffCountMode.CHANCE_DOUBLE));
    }
}
