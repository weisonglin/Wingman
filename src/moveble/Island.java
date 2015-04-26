package moveble;

import java.awt.Image;
//import java.awt.image.ImageObserver;
import java.util.Random;

public class Island extends moveble{


    public Island(Image img, int x, int y, int speed, Random gen) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.gen = gen;
        this.show=true;
    }

    public void update(int h, int w) {
        y += speed;
        if (y >= h) {
            y = -100;
            x = Math.abs(gen.nextInt() % (w - 30));
        }
    }

}
