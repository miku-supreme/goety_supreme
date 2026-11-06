package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.common.items.magic.MagicFocus;
import cn.blockforge.generated.goetysupreme.spell.SerponFocus;
import cn.blockforge.generated.goetysupreme.spell.SerponSummonSpell;

public final class SerponFocus extends MagicFocus {

    public SerponFocus(SerponSummonSpell.Kind kind) {
        super(new SerponSummonSpell(kind));
    }
}
