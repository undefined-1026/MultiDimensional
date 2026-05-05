package mDimension.world.blocks;

import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class DumpStorageBlock extends StorageBlock {
    public float dumpTime = 6f;
    private int itemTotal = 1;
    public DumpStorageBlock(String name){
        super(name);
        canOverdrive = true;
        update = true;

    }

    @Override
    public boolean outputsItems() {
        return true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.speed,60f/dumpTime, StatUnit.itemsSecond);
    }

    @Override
    public void init() {
        super.init();
        itemTotal = Vars.content.items().size;
    }

    public class DumpStorageBlockBuild extends StorageBuild{

        float process = 0f;
        int index = 0;

        @Override
        public boolean canDump(Building to, Item item) {
            if(
                    to instanceof StorageBuild
                    || to instanceof CoreBlock.CoreBuild
            ){
                return false;
            }
            return true;
        }

        @Override
        public void updateTile() {
            if(linkedCore == null){
                if(process>=dumpTime){
                    process%=dumpTime;
                    for(int i = 0;i<itemTotal;i++){
                        Item item = Vars.content.item(index);
                        index++;
                        index%=itemTotal;
                        if(this.items.has(item)){
                            if(dump(item))break;
                        }
                    }

                }else{
                    process+=Time.delta * timeScale;
                }
            }
        }
    }
}
