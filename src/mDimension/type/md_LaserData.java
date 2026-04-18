package mDimension.type;

/**激光数据类*/
public class md_LaserData {
    public int length;
    public int wavelengthLevel;
    public float power = 10f;
    public String beam = "";
    public md_LaserData(Beam l){
        this.length = l.lenght;
        this.wavelengthLevel = l.energyLevel;
        this.beam = l.name;
    }

    public md_LaserData(Beam l,float power){
        this.length = l.lenght;
        this.wavelengthLevel = l.energyLevel;
        this.power = power;
        this.beam = l.name;
    }

    public void setPower(float power){
        this.power = power;
    }
}
