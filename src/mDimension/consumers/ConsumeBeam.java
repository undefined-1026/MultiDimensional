package mDimension.consumers;

import arc.Core;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mDimension.meta.md_StatValues;
import mDimension.type.Beam;
import mDimension.type.md_LaserData;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;
import mindustry.world.meta.Stat;
import mindustry.world.meta.Stats;

import java.util.Objects;

public class ConsumeBeam extends Consume {
    public Seq<md_LaserData> lasers = new Seq<>();

    public float maxWavelength = -1;
    public float minWavelength = -1;

    public float requiredPower = 10f;
    //由于aunke使用的min，所以改这个调用没有
    public float maxEfficiency = 1f;

    public Beam inputBeam = null;
    public int maxSize = 256;

    public float cachePower = 0f;
    public ObjectMap<Building,LaserModule> laserDataMap = new ObjectMap<>();

    public ConsumeBeam(){}
    public ConsumeBeam(float requiredPower){
        this.requiredPower = requiredPower;
    }
    public ConsumeBeam(float requiredPower, float minWavelength, float maxWavelength){
        this.requiredPower = requiredPower;
        this.minWavelength = minWavelength;
        this.maxWavelength = maxWavelength;
    }
    public ConsumeBeam(float requiredPower, Beam beam){
        this.requiredPower = requiredPower;
        this.inputBeam = beam;
    }
    @Override
    public void apply(Block block) {
        //region Bar
        block.addBar("laserpower",e->
            new Bar(
                    ()-> Core.bundle.format("bar.laserpower", getLaserPower(e)),
                    ()->Color.white,
                    ()->getLaserPower(e)/requiredPower
            )
        );
        //endregion
    }

    public static Seq<ConsumeBeam> getLaserConsume(Block block){
        Seq<ConsumeBeam> consumeLasers = new Seq<>();
        for(Consume c:block.consumers){
            if(c instanceof ConsumeBeam cl){
                consumeLasers.add(cl);
            }
        }
        return consumeLasers;
    }

    @Override
    public float efficiency(Building b) {
        Items.copper.description = "";
        for(ObjectMap.Entry<Building,LaserModule> e:laserDataMap){
            Items.copper.description+="\n key"+e.key + "val:"+e.value;
        }
        if (b.dead) {
            laserDataMap.remove(b);
            return 0;
        }
        if (laserDataMap.get(b) == null) {
            laserDataMap.put(b, new LaserModule());
        }
        float power = 0;
        for (md_LaserData laserData : laserDataMap.get(b).laserDatas) {
            if (
                    (minWavelength < 0 || minWavelength <= laserData.wavelengthLevel) &&
                            (maxWavelength < 0 || laserData.wavelengthLevel <= maxWavelength) &&
                            (inputBeam == null || inputBeam.name.equals(laserData.beam))
            ) {

                power += laserData.power;
            }
        }
        laserDataMap.get(b).laserDatas.clear();
        laserDataMap.get(b).power = power;
        return Math.min(maxEfficiency, power/requiredPower);
    }

    public float getLaserPower(Building b){
        if(b.dead){
            laserDataMap.remove(b);
            return 0;
        }
        if(laserDataMap.get(b)==null) {
            laserDataMap.put(b,new LaserModule());
            return 0;
        }
        return laserDataMap.get(b).power;

    }
    @Override
    public void display(Stats stats){
        stats.add(booster ? Stat.booster : Stat.input,t->{
            if(inputBeam == null){
                t.add(md_StatValues.BeamStack(requiredPower,minWavelength,maxWavelength,true));
            }else{
                t.add(md_StatValues.BeamStack(inputBeam,requiredPower,false));
            }
        });
    }

}
