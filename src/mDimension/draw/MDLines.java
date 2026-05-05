package mDimension.draw;

import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;

public class MDLines extends Lines {

    public static void line2(TextureRegion region, float x, float y, float color1, float x2, float y2, float color2, boolean cap){
        float hstroke = getStroke()/2f;
        float len = Mathf.len(x2 - x, y2 - y);
        float diffx = (x2 - x) / len * hstroke, diffy = (y2 - y) / len * hstroke;

        if(cap){
            Fill.quad(
                    region,

                    x - diffx - diffy,
                    y - diffy + diffx,
                    color1,

                    x - diffx + diffy,
                    y - diffy - diffx,
                    color1,

                    x2 + diffx + diffy,
                    y2 + diffy - diffx,
                    color2,

                    x2 + diffx - diffy,
                    y2 + diffy + diffx,
                    color2
            );
        }else{
            Fill.quad(
                    region,

                    x - diffy,
                    y + diffx,
                    color1,

                    x + diffy,
                    y - diffx,
                    color1,

                    x2 + diffy,
                    y2 - diffx,
                    color2,

                    x2 - diffy,
                    y2 + diffx,
                    color2

            );
        }

    }
}
