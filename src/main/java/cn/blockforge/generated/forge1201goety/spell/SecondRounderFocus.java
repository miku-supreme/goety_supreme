package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.common.items.magic.MagicFocus;
import cn.blockforge.generated.goetysupreme.spell.SecondRounderFocus;
import cn.blockforge.generated.goetysupreme.spell.SecondRounderSummonSpell;

public final class SecondRounderFocus extends MagicFocus {

    public SecondRounderFocus(SecondRounderSummonSpell.Kind kind) {
        super(new SecondRounderSummonSpell(kind));
    }
}
