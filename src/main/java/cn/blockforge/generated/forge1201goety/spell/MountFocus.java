package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.common.items.magic.MagicFocus;
import cn.blockforge.generated.goetysupreme.spell.MountFocus;
import cn.blockforge.generated.goetysupreme.spell.MountSummonSpell;

public final class MountFocus extends MagicFocus {

    public MountFocus(MountSummonSpell.Kind kind) {
        super(new MountSummonSpell(kind));
    }
}
