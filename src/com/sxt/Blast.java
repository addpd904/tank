package com.sxt;

import java.awt.*;

public class Blast extends GameObject{
    static Image[] imgs=new  Image[1];
    int explodeCount=0;
    static {
        imgs[0]=Toolkit.getDefaultToolkit().getImage("images/blast.png");
    }
    public Blast(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void paintSelf(Graphics g) {
        if(explodeCount<1) {
            g.drawImage(imgs[explodeCount], x, y, gamePanel);
            explodeCount++;
        }

    }

    @Override
    public Rectangle getRec() {
        return null;
    }
}
