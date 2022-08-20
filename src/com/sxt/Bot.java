package com.sxt;
import java.awt.*;
import java.util.Random;

public class Bot extends Tank{
    public Bot(String img, int x, int y, GamePanel gamePanel, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }
    int moveTimes=0;

    public Direction getRandDirection(){
        Random r=new Random();
        int a=r.nextInt(4);
        switch (a){
            case 0:
                return Direction.LEFT;
            case 1:
                return  Direction.RIGHT;
            case 2:
                return Direction.UP;
            case 3:
                return Direction.DOWN;
            default:
                return null;
        }
    }

    public void go(){
        attack();
        if(moveTimes>=20){
            direction=getRandDirection();
            moveTimes=0;
        }else {
            moveTimes++;
        }
        switch (direction){
            case LEFT :
                leftward();
                break;
            case  RIGHT:
                righttward();
                break;
            case UP :
                upward();
                break;
            case DOWN:
                downward();
                break;
        }

    }

    public void attack(){
            Point p= this.getHeadPoint();
            Random r=new Random();
            int rnum=r.nextInt(100);
            if (rnum<3){
                this.gamePanel.bulletList.add(new EnemyBuffet("images/bullet/bulletYellow.gif",p.x,p.y,this.gamePanel,this.direction));
            }
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        go();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }

}
