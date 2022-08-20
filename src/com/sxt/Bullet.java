package com.sxt;

import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject {
    int width=10;
    int height=10;
    int speed =7;
    Direction direction;

    public Bullet(String img, int x, int y, GamePanel gamePanel,Direction direction) {
        super(img, x, y, gamePanel);
        this.direction=direction;
    }

    public void leftward(){
        x-=speed;
    }
    public void righttward(){
        x+=speed;
    }
    public void upward(){
        y-=speed;
    }
    public void downward(){
        y+=speed;
    }

    public void go(){
        switch (direction){
            case LEFT :
                leftward();
                break;
            case  RIGHT:
                righttward();
                break;
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
        }
        hitWall();
        moveToBorder();
        hitBase();
    }

    public void hitBot(){
        ArrayList<Bot> bots=this.gamePanel.botList;
        for (Bot bot:bots){
            if (this.getRec().intersects(bot.getRec())){
                this.gamePanel.blastList.add(new Blast("",bot.x-14,bot.y-14,this.gamePanel));
                this.gamePanel.botList.remove(bot);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    public void hitWall(){
        ArrayList<Wall> walls=this.gamePanel.wallList;
        for (Wall wall:walls){
            if (this.getRec().intersects(wall.getRec())){
                this.gamePanel.wallList.remove(wall);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    public void hitBase(){
        ArrayList<Base> bases=this.gamePanel.basesList;
        for (Base base:bases){
            if (this.getRec().intersects(base.getRec())){
                this.gamePanel.basesList.remove(base);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    public void moveToBorder(){
        if (x<0||y<0){
            this.gamePanel.removeList.add(this);
        }
        else if (x+width>this.gamePanel.getWidth()
                ||y+height>this.gamePanel.getHeight()){
            this.gamePanel.removeList.add(this);
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        go();
        hitBot();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
