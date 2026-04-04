package mDimension.consumers;

import arc.struct.Seq;
import mDimension.type.md_LaserData;

//只是一个数据类，用于统计建筑接收的总有效激光功率和接收的激光数据
public class LaserModule {
    public Seq<md_LaserData> laserDatas = new Seq<>();
    public float power = 0;
    public LaserModule(){}

    @Override
    public String toString() {
        return ""+power;
    }
}
