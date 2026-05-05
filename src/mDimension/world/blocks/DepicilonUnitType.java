package mDimension.world.blocks;

import mDimension.content.md_items;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.world.meta.Env;

public class DepicilonUnitType extends UnitType {
    public DepicilonUnitType(String name){
        super(name);
        constructor = UnitEntity::create;
        outlineColor = Pal.darkOutline;
        envDisabled = Env.space;
        ammoType = new ItemAmmoType(md_items.aluminium);
        researchCostMultiplier = 7.5f;
    }
}
