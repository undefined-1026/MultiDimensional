package mDimension.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Item;

public class md_items {
    public static Item
            bauxite,aluminium,al_alloy,ti_alloy,polymer,carbon_fibre,polymorphic_crystal,plasma,light_ceramic;

    public static void load(){
        bauxite = new Item("bauxite",Color.valueOf("ffebd0")){{
            hardness = 0;
            cost = 0.5f;
            alwaysUnlocked = true;
        }};
        aluminium = new Item("aluminium",Color.valueOf("ffeded")){{
            healthScaling = 0.3f;
            hardness = 1;
            cost = 0.4f;
            alwaysUnlocked = true;
        }};
        al_alloy = new Item("al-alloy",Color.valueOf("ededff")){{
            healthScaling = 0.5f;
            hardness = 2;
            cost = 0.7f;
            alwaysUnlocked = true;
        }};
        ti_alloy = new Item("ti-alloy",Color.valueOf("d8d8ff")){{
            healthScaling = 0.8f;
            hardness = 3;
            cost = 2f;
            alwaysUnlocked = true;
        }};
        polymer = new Item("polymer",Color.valueOf("FFE399")){{
            flammability = 0.6f;
            cost = 0.3f;
            hardness = 0;
            alwaysUnlocked = true;
        }};
        carbon_fibre = new Item("carbon-fibre",Color.valueOf("303030")){{
            flammability = 0.2f;
            cost = 1.5f;
            hardness = 1;
            alwaysUnlocked = true;
        }};
        polymorphic_crystal = new Item("polymorphic-crystal",Color.valueOf("F8D09E")){{
            frames = 6;
            frameTime = 20;
            transitionFrames = 64;
            charge = 1.5f;
            explosiveness =2.73f;
            radioactivity = 3.25f;
            cost = 3.5f;
            hardness = 2;
            alwaysUnlocked = true;

        }};
        plasma = new Item("plasma",Color.valueOf("ADB0FF")){{
            frames = 2;
            frameTime = 8;
            transitionFrames = 16;
            charge = 2.5f;
            explosiveness = 2f;
            cost = 3f;
            hardness  = 0;
            alwaysUnlocked = true;
        }};
        light_ceramic = new Item("light-ceramic",Color.valueOf("EFFF87")){{
            frames = 2;
            frameTime = 3;
            transitionFrames = 25;
            charge = 0.8f;
            explosiveness = 0.8f;
            cost = 2.5f;
            hardness  = 1;
            alwaysUnlocked = true;
        }};
    }
}
