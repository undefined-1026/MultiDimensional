package mDimension.entity.ability;

import arc.util.Time;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.abilities.Ability;
import mindustry.gen.PayloadUnit;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;

public class PayloadProduceAbility extends Ability {
    public float produceTime = 60f;
    public UnlockableContent payloadType;
    protected float timer = 0f;

    @Override
    public void update(Unit unit) {
        if (unit instanceof PayloadUnit pu) {

            if (timer >= produceTime) {

                Payload p = null;
                if (payloadType instanceof Block block) {
                    p = new BuildPayload(block, unit.team);
                } else if (payloadType instanceof UnitType unitType) {
                    p = new UnitPayload(unitType.create(unit.team));
                }
                if (canProduceTime(pu, p)) {
                    pu.addPayload(p);
                    timer = 0;
                }
            } else {
                timer += Time.delta;
            }
        }
    }

    boolean canProduceTime(Unit u, Payload payload){
        if(payload == null)return false;
        if(u instanceof PayloadUnit unit){
            return unit.canPickupPayload(payload);
        }else return false;
    }
}
