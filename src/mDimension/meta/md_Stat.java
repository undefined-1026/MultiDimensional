package mDimension.meta;

import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

public class md_Stat{
    public static Stat
            percentageDamage,percentageReply,armorAdditional,armorMultiplier,percentageShieldDamage,overdrive,maxEffectThreshold,recipes
            ,energyLevel;
    public static void load(){
        percentageDamage = new Stat("percentageDamage");
        percentageReply = new Stat("percentageReply");
        armorAdditional = new Stat("armorAdditional");
        armorMultiplier = new Stat("armorMultiplier");
        percentageShieldDamage = new Stat("percentageShieldDamage");
        overdrive = new Stat("overdrive", StatCat.function);
        maxEffectThreshold = new Stat("maxEffectThreshold",StatCat.function);
        recipes = new Stat("recipes",StatCat.crafting);
        energyLevel = new Stat("energylevel");

    }
}
