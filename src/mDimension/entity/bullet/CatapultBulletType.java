package mDimension.entity.bullet;

import arc.math.Mathf;
import arc.struct.FloatSeq;
import arc.struct.Seq;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;

public class CatapultBulletType extends BasicBulletType {

    public float catapultRange = 40f;


    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        super.hitEntity(b, entity, health);
        Seq<Healthc> all = new Seq<>(6);
        if(b.type.pierce){

            Units.nearbyEnemies(b.team, b.x, b.y, catapultRange, h -> {
                if (!b.collided.contains(h.id()) )all.add(h);
            });
            if (all.size == 0) return;
            Healthc h = null;
            for(Healthc healthc:all){
                if(h == null) {
                    h = healthc;
                }else if(Mathf.len2(b.x-h.x(),b.y-h.y()) > Mathf.len2(b.x-healthc.x(),b.y-healthc.y())){
                    h = healthc;
                }

            }
            if(h == null)return;
            float angle = Mathf.angle(h.x() - b.x, h.y() - b.y);
            b.rotation(angle);

        }
    }
}
