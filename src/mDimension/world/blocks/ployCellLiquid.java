package mDimension.world.blocks;


import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.entities.Puddles;
import mindustry.game.EventType;
import mindustry.gen.Puddle;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Liquid;
import mindustry.world.Tile;

import static mindustry.entities.Puddles.maxLiquid;

public class ployCellLiquid extends Liquid {
    public Color colorFrom = Color.white.cpy(), colorTo = Color.white.cpy();
    public int colorStep = -1;
    public int puddleSide = -4;
    public int cellSide = -4;
    public float puddleRange = -1;
    public float cellRange = -1;
    public float puddleRotation = 0;
    public float cellRotation = 0;
    public float puddleRad = 8f;
    public float cellRad = 3.8f;
    public int cells = 6;

    public @Nullable Liquid spreadTarget;
    public float maxSpread = 0.75f, spreadConversion = 1.2f, spreadDamage = 0.11f, removeScaling = 0.25f;

    public ployCellLiquid(String name, Color color){
        super(name, color);
    }
    public ployCellLiquid(String name, Color color,int cellSide){
        super(name, color);
        this.cellSide = cellSide;
    }
    public ployCellLiquid(String name, Color color,int cellSide,int puddleSide){
        super(name, color);
        this.cellSide = cellSide;
        this.puddleSide = puddleSide;
    }

    public ployCellLiquid(String name){
        super(name);
    }

    @Override
    public void update(Puddle puddle){
        if(!Vars.state.rules.fire) return;

        if(spreadTarget != null){
            float scaling = Mathf.pow(Mathf.clamp(puddle.amount / maxLiquid), 2f);
            boolean reacted = false;

            for(var point : Geometry.d4c){
                Tile tile = puddle.tile.nearby(point);
                if(tile != null && tile.build != null && tile.build.liquids != null && tile.build.liquids.get(spreadTarget) > 0.0001f){
                    float amount = Math.min(tile.build.liquids.get(spreadTarget), maxSpread * Time.delta * scaling);
                    tile.build.liquids.remove(spreadTarget, amount * removeScaling);
                    Puddles.deposit(tile, this, amount * spreadConversion);
                    reacted = true;
                }
            }

            //damage thing it is on
            if(spreadDamage > 0 && puddle.tile.build != null && puddle.tile.build.liquids != null && puddle.tile.build.liquids.get(spreadTarget) > 0.0001f){
                reacted = true;

                //spread in 4 adjacent directions around thing it is on
                float amountSpread = Math.min(puddle.tile.build.liquids.get(spreadTarget) * spreadConversion, maxSpread * Time.delta) / 2f;
                for(var dir : Geometry.d4){
                    Tile other = puddle.tile.nearby(dir);
                    if(other != null){
                        Puddles.deposit(puddle.tile, other, puddle.liquid, amountSpread);
                    }
                }

                puddle.tile.build.damage(spreadDamage * Time.delta * scaling);
            }

            //spread to nearby puddles
            for(var point : Geometry.d4){
                Tile tile = puddle.tile.nearby(point);
                if(tile != null){
                    var other = Puddles.get(tile);
                    if(other != null && other.liquid == spreadTarget){
                        //TODO looks somewhat buggy when outputs are occurring
                        float amount = Math.min(other.amount, Math.max(maxSpread * Time.delta * scaling, other.amount * 0.25f * scaling));
                        other.amount -= amount;
                        puddle.amount += amount;
                        reacted = true;
                        if(other.amount <= maxLiquid / 3f){
                            other.remove();
                            Puddles.deposit(tile, puddle.tile, this, Math.max(amount, maxLiquid / 3f));
                        }
                    }
                }
            }

            if(reacted && this == Liquids.neoplasm){
                Events.fire(EventType.Trigger.neoplasmReact);
            }
        }
    }

    @Override
    public float react(Liquid other, float amount, Tile tile, float x, float y){
        if(other == spreadTarget){
            return amount;
        }
        return 0f;
    }

    @Override
    public void drawPuddle(Puddle puddle){
        float amount = puddle.amount, x = puddle.x, y = puddle.y;
        float f = Mathf.clamp(amount / (maxLiquid / 1.5f));
        float smag = puddle.tile.floor().isLiquid ? 0.8f : 0f, sscl = 25f;

        Draw.color(Tmp.c1.set(color).shiftValue(-0.05f));
        rand.setSeed(id);
        float puddleRotate = (puddleRange>0?rand.range(puddleRange) - puddleRange / 2:0)+puddleRotation;
        if(puddleSide >0) {
            Fill.poly(
                    x + Mathf.sin(Time.time + id * 532, sscl, smag),
                    y + Mathf.sin(Time.time + id * 53, sscl, smag),
                    puddleSide,
                    f * puddleRad,
                    puddleRotation
            );
        }else{
            Fill.circle(
                    x + Mathf.sin(Time.time + id * 532, sscl, smag),
                    y + Mathf.sin(Time.time + id * 53, sscl, smag),
                    f * puddleRad
            );
        }

        float lengthP = f * 6f;
        for(int i = 0; i < 3; i++){
            Tmp.v1.trns(rand.random(360f), rand.random(lengthP));
            float vx = x + Tmp.v1.x, vy = y + Tmp.v1.y;
            if(puddleSide >0){
                Fill.poly(
                        vx + Mathf.sin(Time.time + i * 532, sscl, smag),
                        vy + Mathf.sin(Time.time + i * 53, sscl, smag),
                        puddleSide,
                        f * puddleRad*0.625f,
                        puddleRotate);
            }else {
                Fill.circle(
                        vx + Mathf.sin(Time.time + i * 532, sscl, smag),
                        vy + Mathf.sin(Time.time + i * 53, sscl, smag),
                        f * puddleRad*0.625f);
            }
        }

        Draw.color();

        if(lightColor.a > 0.001f && f > 0){
            Drawf.light(x, y, 30f * f, lightColor, color.a * f * 0.8f);
        }

        if(cells<0)return;
        float baseLayer = puddle.tile != null && puddle.tile.block().solid || puddle.tile.build != null ? Layer.blockOver : Layer.debris - 0.5f;

        int id = puddle.id;
        float length = Math.max(f, 0.3f) * 9f;

        rand.setSeed(id);
        for(int i = 0; i < cells; i++){
            Draw.z(baseLayer + i/1000f + (id % 100) / 10000f);
            Tmp.v1.trns(rand.random(360f), rand.random(length));
            float vx = x + Tmp.v1.x, vy = y + Tmp.v1.y;

            Draw.color(colorFrom, colorTo, colorStep>0?(float)(rand.range(colorStep) /colorStep):rand.random(1f));
            float cellRealRad = cellRad * f * rand.random(0.35f, 1f) * Mathf.absin(Time.time + ((i + id) % 60) * 54, 75f * rand.random(1f, 2f), 1f);
            if(cellSide >0){
                float cellRotate = (cellRange>0?rand.range(cellRange) - cellRange/2:0) + cellRotation;
                Fill.poly(
                        vx + Mathf.sin(Time.time + i * 532, sscl, smag),
                        vy + Mathf.sin(Time.time + i * 53, sscl, smag),
                        cellSide,
                        cellRealRad,
                        cellRotate);
            }else {
                Fill.circle(
                        vx + Mathf.sin(Time.time + i * 532, sscl, smag),
                        vy + Mathf.sin(Time.time + i * 53, sscl, smag),
                        cellRealRad);
            }
        }

        Draw.color();
    }
}