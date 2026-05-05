package mDimension.content;

import arc.struct.Seq;

import static mDimension.content.md_items.*;
import static mDimension.content.md_liquids.*;
import static mDimension.content.md_blocks.*;
import static mindustry.content.Items.*;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;
import mindustry.type.SectorPreset;


public class md_TechTree {
    private static TechTree.TechNode context = null;
    public static Seq<TechTree.TechNode> all = new Seq<>();
    public static Seq<TechTree.TechNode> roots = new Seq<>();

    public static void load(){
        md_Planets.depicilon.techTree = nodeRoot("depicilon",md_blocks.coreSteady,()->{

            node(md_blocks.aluminium_electrolysis_cell,()->{
                node(md_blocks.al_alloy_smelting);

            });

            node(md_blocks.light_duct,()->{
                node(md_blocks.armored_light_duct,()->{
                    node(md_blocks.stack_rail_conveyor);
                });
                node(md_blocks.multiway_unloader);
                node(md_blocks.light_duct_bridge);
            });

            node(beam_bore,()->{
                node(small_impact_drill);
            });

            node(md_blocks.ngm_launch_pad);

            node(fracture,Seq.with(new Objectives.Produce(ti_alloy)),()->{});

            nodeProduce(bauxite,()->{
                nodeProduce(aluminium,()->{
                    nodeProduce(al_alloy,()->{
                        nodeProduce(ti_alloy,()->{
                            nodeProduce(dimension_fluid,()->{
                                nodeProduce(polymorphic_crystal);
                            });
                            nodeProduce(plasma);
                        });
                        nodeProduce(polymer);
                    });
                });

            });
            nodeProduce(titanium);
            nodeProduce(copper);
            nodeProduce(graphite);
            nodeProduce(silicon);
        });
    }

    public static void addToNext(UnlockableContent content,Runnable run){
        context = TechTree.all.find(t->t.content == content);
        run.run();
    }

    public static TechTree.TechNode nodeRoot(String name, UnlockableContent content, Runnable children){
        return nodeRoot(name, content, false, children);
    }

    public static TechTree.TechNode nodeRoot(String name, UnlockableContent content, boolean requireUnlock, Runnable children){
        var root = node(content, content.researchRequirements(), children);
        root.name = name;
        root.requiresUnlock = requireUnlock;
        roots.add(root);
        return root;
    }

    public static TechTree.TechNode node(UnlockableContent content, Runnable children){
        return node(content, content.researchRequirements(), children);
    }

    public static TechTree.TechNode node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        return node(content, requirements, null, children);
    }

    public static TechTree.TechNode node(UnlockableContent content, ItemStack[] requirements, Seq<Objectives.Objective> objectives, Runnable children){
        TechTree.TechNode node = new TechTree.TechNode(context, content, requirements);
        if(objectives != null){
            node.objectives.addAll(objectives);
        }

        //insert missing sector parent dependencies
        if(context != null && context.content instanceof SectorPreset preset && !node.objectives.contains(o -> o instanceof Objectives.SectorComplete sc && sc.preset == preset)){
            node.objectives.insert(0, new Objectives.SectorComplete(preset));
        }

        TechTree.TechNode prev = context;
        context = node;
        children.run();
        context = prev;

        return node;
    }

    public static TechTree.TechNode node(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        return node(content, content.researchRequirements(), objectives, children);
    }

    public static TechTree.TechNode node(UnlockableContent block){
        return node(block, () -> {});
    }

    public static TechTree.TechNode nodeProduce(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        return node(content, content.researchRequirements(), objectives.add(new Objectives.Produce(content)), children);
    }

    public static TechTree.TechNode nodeProduce(UnlockableContent content, Runnable children){
        return nodeProduce(content, new Seq<>(), children);
    }

    public static TechTree.TechNode nodeProduce(UnlockableContent content){
        return nodeProduce(content, new Seq<>(),()->{});
    }
}
