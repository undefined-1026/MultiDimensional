package mDimension.entity;

import arc.audio.Sound;
import mindustry.gen.Sounds;

import java.util.Arrays;

public class MultiSound extends Sound {
    public Sound[] sounds;
    public float[][] multiParameter;
    public MultiSound(Sound... sounds){
        this.sounds = sounds;
        this.multiParameter = new float[sounds.length][2];
        Arrays.fill(multiParameter,new float[]{1,1});
    }
    public MultiSound(Object... pitAndSounds){
        if(pitAndSounds.length%2!=0){
            this.sounds = new Sound[]{Sounds.none};
            this.multiParameter = new float[][]{{1,1}};
            return;
        }
        this.sounds = new Sound[pitAndSounds.length/2];
        this.multiParameter = new float[pitAndSounds.length/2][2];
        for(int i=0;i<pitAndSounds.length;i++){
            if(i%2 == 0&&pitAndSounds[i] instanceof Sound sound){
                sounds[i/2] = sound;
            }else if(i%2 != 0&&pitAndSounds[i] instanceof float[] f){
                multiParameter[(i-1)/2] = f;
            };
        }
    }
    @Override
    public int at(float x, float y, float pitch, float volume, boolean checkFrame){
        for(int i = 0;i<sounds.length;i++){
            sounds[i].at(x,y,pitch*multiParameter[i][0],volume*multiParameter[i][1],checkFrame);
        }
        return -1;
    }
}
