package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.common.items.magic.MagicFocus;
import cn.blockforge.generated.goetysupreme.spell.SedimentFocus;
import cn.blockforge.generated.goetysupreme.spell.SedimentSummonSpell;

public class SedimentFocus extends MagicFocus {

    public SedimentFocus(SedimentSummonSpell.Kind kind) {
        super(new SedimentSummonSpell(kind));
    }
}
