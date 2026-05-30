package mDimension.world.data;

/**激光数据类*/
public class BeamData {
    public int length;
    public int wavelengthLevel;
    public float power = 10f;
    public String beam = "";
    public BeamData(Beam l){
        this.length = l.lenght;
        this.wavelengthLevel = l.energyLevel;
        this.beam = l.name;
    }

    public BeamData(Beam l, float power){
        this.length = l.lenght;
        this.wavelengthLevel = l.energyLevel;
        this.power = power;
        this.beam = l.name;
    }

    public void setPower(float power){
        this.power = power;
    }
}
