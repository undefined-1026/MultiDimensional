package mDimension.content;

import annotations.Annotations.*;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mDimension.entity.ability.PatienceAbility;
import mDimension.entity.ability.SprintAbility;
import mDimension.entity.bullet.BallLightningBulletType;
import mDimension.tool.Drawff;
import mDimension.type.DepicilonUnitType;
import mDimension.type.md_Fx;
import mDimension.type.weapons.OverdriveWeapon;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.EnergyFieldAbility;
import mindustry.entities.abilities.ShieldRegenFieldAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.ContinuousFlameBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static mDimension.type.md_Fx.polyStarExplosion;
import static mindustry.Vars.tilesize;

import static mDimension.content.md_blocks.modname;
public class md_UnitTypes {
    public static @EntityDef({Unitc.class, ElevationMovec.class}) UnitType shimmer;
    public static @EntityDef({Unitc.class, Payloadc.class}) UnitType primitive ,burst;

    public static void load(){
        shimmer = new DepicilonUnitType("shimmer"){{
            constructor = ElevationMoveUnit::create;

            hovering = true;
            canDrown = false;
            shadowElevation = 0.1f;
            softShadowScl = 0.7f;

            drag = 0.08f;
            speed = 1.8f;
            rotateSpeed = 6f;

            accel = 0.07f;

            health = 850f;
            armor = 2f;
            hitSize = 11f;

            engineSize = 0;
            itemCapacity = 15;
            setEnginesMirror(
                    new UnitEngine(17f/4f,-14f/4f,2.5f,-45f)
            );

            useEngineElevation = false;
            researchCostMultiplier = 0f;
            moveSound = Sounds.loopExtract;
            moveSoundVolume = 0.25f;
            moveSoundPitchMin = 0.7f;
            moveSoundPitchMax = 1.5f;
            weapons.add(new Weapon("shimmer-weapon"){{
                range = 15.5f;
                alwaysContinuous = true;
                mirror = false;
                top = false;
                shootY = 4f;
                x = y = 0f;
                shootSound = Sounds.none;
                activeSound = Sounds.shootSublimate;
                activeSoundVolume = 1.5f;
                bullet = new ContinuousFlameBulletType(){{
                    damage = 40f;
                    length = 9f;
                    width = 4.5f;
                    ammoMultiplier = 1.2f;
                    knockback = 1f;
                    pierceCap = 2;
                    buildingDamageMultiplier = 0.3f;

                    hitColor = flareColor = Color.valueOf("FFDB78");
                    flareLength = 7f;
                    flareWidth = 3f;
                    colors = new Color[]{
                            Color.valueOf("DB7D42").a(0.45f),
                            Color.valueOf("E8AC58").a(0.65f),
                            Color.valueOf("FFDB78").a(0.85f),
                            Color.white};
                }};
            }});
        }};
        primitive = new DepicilonUnitType("primitive"){{
            constructor = PayloadUnit::create;
            coreUnitDock = true;
            controller = u -> new BuilderAI(true, 400f);
            isEnemy = false;
            envDisabled = 0;

            range = 80f;
            faceTarget = true;
            targetPriority = -2;
            lowAltitude = true;
            mineWalls = true;
            mineFloor = true;
            mineHardnessScaling = true;
            flying = true;
            mineSpeed = 4f;
            mineTier = 3;
            buildSpeed = 1f;
            drag = 0.08f;
            speed = 4.8f;
            rotateSpeed = 6f;
            accel = 0.08f;
            itemCapacity = 40;
            health = 200;
            armor = 2f;
            hitSize = 10f;

            engineSize = 10/4f;
            engineOffset = 22/4f;
            setEnginesMirror(
                    new UnitEngine(18 / 4f, 4 / 4f, 10/4f, -45)
            );

            payloadCapacity = 2 * 2 * tilesize * tilesize;
            pickupUnits = true;
            vulnerableWithPayloads = true;

            fogRadius = 0f;
            targetable = false;
            hittable = false;

            weapons.add(
                    new OverdriveWeapon(modname+"primitive-weapon1"){{
                        lockedRange = 30*8f;
                        lockedTime = 50f;
                        reload = 30f;
                        extraDuration = 300f;
                        aimChangeSpeed = 4.5f;



                        y = -4/4f;
                        x = 0;
                        shootY = 7/4f;
                        mirror = false;
                        top = false;
                    }}
            );
            clipSize = 32*8f;
        }};
        burst = new DepicilonUnitType("burst"){{

            softShadowScl = 0.75f;
            constructor = PayloadUnit::create;
            envDisabled = 0;
            payloadCapacity = 3 * 3 * tilesize * tilesize;
            pickupUnits = true;
            vulnerableWithPayloads = true;
            itemOffsetY = 4f;

            range = 160f;
            faceTarget = false;
            targetPriority = 0;
            lowAltitude = false;
            mineWalls = false;
            mineFloor = false;
            mineHardnessScaling = false;
            flying = true;
            drag = 0.05f;
            speed = 1.3f;
            rotateSpeed = 2.8f;
            accel = 0.08f;
            hitSize = 40f;

            autoDropBombs = true;
            //because main weapon reload is long
            circleTarget = false;

            trailLength = 15;
            trailScl = 25/32f;
            waveTrailY = 0;
            waveTrailX = 0;

            engineOffset = 55/4f;
            engineSize = 30/4f;


            loopSoundVolume = 0.85f;
            loopSound = Sounds.loopHover;

            health = 8000;
            armor = 6;
            abilities.addAll(
                    new PatienceAbility(0.6f){{
                        heatColor = Color.valueOf("DEB660");
                        speed = 1.8f;
                        health = 2;
                        armor = 20f;
                        damage = 1.4f;
                        drawHeat = true;
                        effectColor = Color.valueOf("FFE096");
                        effect = md_Fx.triangle.layer(Layer.flyingUnit+1f);
                        effectChance = 0.25f;
                    }},
                    new ShieldRegenFieldAbility(15,1200,75,40)
            );

            weapons.add(
                    new Weapon(){{
                        shootSound = Sounds.shootMissilePlasma;
                        soundPitchMax = 1.2f;
                        soundPitchMin = 0.8f;
                        shootY = 0;
                        x = 0;
                        y = 15/4f;
                        mirror = false;
                        shootCone = 360f;
                        reload = 180f;
                        bullet = new BallLightningBulletType(){{
                            shootEffect = md_Fx.polyStarExplosion(45,4,50,6,45,false);
                            overflow = false;
                            shockAmount = 3;
                            shockLimit = 1;
                            shockCooldown = 12f;
                            shockDamage = 150f;
                            shockRange = 120f;

                            shockEffect =md_Fx.chainLightningBig;
                            sprite = "large-orb";
                            width = height = 25f;

                            maxRange = 8*8f;
                            ignoreRotation = true;

                            backColor =hitColor= Color.valueOf("FFF1B0");
                            shockColor =Color.valueOf("FFE56E");
                                    frontColor = Color.white;
                            mixColorTo = Color.white;

                            hitSound = Sounds.explosionQuad;
                            hitSoundVolume = 0.9f;

                            shootCone = 180f;
                            ejectEffect = Fx.none;
                            hitShake = 4f;

                            collidesAir = false;

                            lifetime = 70f;
                            despawnHit = true;
                            hitSound = Sounds.explosionReactor;
                            hitSoundVolume = 0.65f;

                            despawnEffect = new MultiEffect(
                                    md_Fx.Mulitpleslash(40f,10,hitColor,64f,5f,70f),
                                    md_Fx.brokenWaveColor(32f,56,16f,3f,18,0.7f,3f),
                                    md_Fx.brokenWaveColor(38f,45,13f,3f,12,0.45f,4f),
                                    md_Fx.polyStarExplosion(30f,3,120,8,0,true),
                                    md_Fx.polyStarExplosion(30f,3,75,6,60,true)
                            );
                            hitEffect = Fx.none;
                            keepVelocity = false;
                            spin = 2f;

                            width = height =

                            speed = 0f;
                            collides = false;

                            splashDamage = 600;
                            splashDamageRadius = 100f;
                            damage = splashDamage * 0.7f;
                            fragBullets = 10;
                            fragLifeMax = 1.2f;fragLifeMin = 0.8f;
                            fragVelocityMin = 1f;fragVelocityMax = 1.1f;
                            fragBullet = new BasicBulletType(36*8/60f,70){{
                                sprite = "missile-large";
                                backSprite = "missile-large";
                                width = 10f;
                                height = 14f;
                                trailLength = 5;
                                trailWidth = 3f;
                                lifetime = 27f;
                                homingPower = 0.12f;
                                homingRange = 30*8f;
                                frontColor = trailColor = backColor = hitColor = Color.valueOf("FFF1B0");
                                despawnEffect = hitEffect = md_Fx.Mulitpleslash(20,2,hitColor,16,3f,4f);
                            }};
                        }

                            @Override
                            public void draw(Bullet b) {
                                super.draw(b);

                                float fin = (float) ((Math.sqrt(b.fin())-b.fin())*4f);
                                Draw.color(Color.valueOf("FFD57A"));
                                if(fin>0.05f){
                                    Drawff.prominence(b.id,b.x,b.y,20,12,fin*45,fin*12f,fin*10,6);
                                }
                                Fill.circle(b.x,b.y,fin*10);
                                for(int o: Mathf.zeroOne) {
                                    Draw.color(Color.valueOf("FFD57A"),Color.white,o*0.95f);
                                    float scl = 1-o*0.4f;
                                    for (int i = 0; i < 3; i++) {
                                        Drawf.tri(b.x, b.y, scl*8 *fin, scl*80 *fin, i * 120 + Time.time*1.2f);
                                    }
                                }
                                Fill.circle(b.x,b.y,fin*6);
                                Draw.reset();
                            }
                        };
                    }
                        @Override
                        public void draw(Unit unit, WeaponMount mount) {
                            super.draw(unit, mount);
                            if(!unit.isValid())return;
                            float fin = (reload-mount.reload)/reload;
                            Draw.z(Layer.bullet+1f);
                            Tmp.v2.set(x,y).rotate(unit.rotation()-90);
                            Draw.color(Color.valueOf("FFD57A"));
                            Fill.circle(unit.x +Tmp.v2.x,unit.y+Tmp.v2.y,fin*4.5f);
                            if(fin>0.1f)Drawff.prominence(
                                    unit.id,
                                    unit.x +Tmp.v2.x,unit.y+Tmp.v2.y,
                                    15,10,fin*5f,
                                    fin*5.5f,fin*4.5f,6);
                            Draw.color(Color.valueOf("FFFBEB"));
                            Fill.circle(unit.x +Tmp.v2.x,unit.y+Tmp.v2.y,fin*2.3f);

                            Draw.reset();
                        }
                    },
                    new Weapon(modname+"burst-weapon"){{
                        shootSound = Sounds.shootReign;
                        soundPitchMin = 0.7f;
                        soundPitchMax = 1.3f;
                        controllable = false;
                        autoTarget = true;
                        x = 16;
                        y = 4.8f;
                        mirror = true;
                        layerOffset = -20;
                        shoot = new ShootBarrel(){{
                            barrels = new float[]{
                                    2,0,10,
                                    1,0,0,
                                    -1,0,0,
                                    2,0,-10
                            };
                            shotDelay = 2f;
                            shots = 8;
                        }};
                        parts.add(new RegionPart("-blade"){{
                            heatProgress = PartProgress.warmup;
                            progress = PartProgress.warmup.blend(PartProgress.recoil, 0.15f);
                            heatColor = Color.valueOf("FFAA50");
                            x = 0 / 4f;
                            y = 0f;
                            moveRot = -25f;
                            moveY = -1f;
                            moveX = -1f;
                            under = true;
                            mirror = true;
                        }});
                        reload = 40f;
                        shootCone = 30f;
                        shootY = 4f;
                        rotateSpeed = 240/60f;
                        rotate = true;
                        rotationLimit = 361;
                        bullet = new BasicBulletType(22*8/60f,35,modname+"acicular-bullet"){{
                            homingPower = 0.02f;
                            lightningColor = backColor = trailColor = hitColor = Color.valueOf("FFE3A6");
                            lightningDamage = 20f;
                            lightning = 1;
                            lightningLength = 2;
                            lightningCone = 15f;
                            lifetime = 50;
                            maxRange = 16*8f;

                            width = 11;
                            height = 13f;
                            pierce = true;
                            pierceCap = 2;

                            trailWidth = 1.5f;
                            trailLength = 8;
                            weaveScale = 5f;
                            weaveMag = 1.8f;
                        }};
                    }}
            );
            setEnginesMirror(
                    new UnitEngine(93/4f,-16/4f,25/4f,-60f),
                    new UnitEngine(105/4f,-55/4f,16/4f,-45f),
                    new UnitEngine(76/4f,-65/4f,16/4f,-45f)
            );
        }
            @Override
            public void drawItems(Unit unit) {
                float z = Draw.z();
                Draw.z(Layer.flyingUnit+1);
                super.drawItems(unit);
                Draw.z(z);
            }
        };
    }



}
