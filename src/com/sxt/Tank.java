package com.sxt;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Tank extends GameObject{
    public int width=40;
    public int height=50;
    private int speed=6;
    public Direction direction=Direction.UP;
    private  String upImg;
    private  String leftImg;
    private  String rightImg;
    private  String downImg;
    public boolean alive=false;

    private boolean attackCoolDown=true;
    private int attackCoolDownTime=200;

    public Tank(String img,int x,int y,GamePanel gamePanel,
                String upImg,String leftImg,String rightImg,String downImg){
        super(img,x,y,gamePanel);
        this.upImg=upImg;
        this.leftImg=leftImg;
        this.rightImg=rightImg;
        this.downImg=downImg;
    }

    public void leftward(){
        if (!hitWall(x-speed,y)&&!moveToBoder(x-speed,y)){
            x-=speed;
        }
        setImg(leftImg);
        direction=Direction.LEFT;
    }
    public void righttward(){
        if (!hitWall(x+speed,y)&&!moveToBoder(x+speed,y)){
            x+=speed;
        }
        setImg(rightImg);
        direction=Direction.RIGHT;
    }
    public void upward(){
        if (!hitWall(x,y-speed)&&!moveToBoder(x,y-speed)){
            y-=speed;
        }
        setImg(upImg);
        direction=Direction.UP;
    }
    public void downward(){
        if (!hitWall(x,y+speed)&&!moveToBoder(x,y+speed)){
            y+=speed;
        }
        setImg(downImg);
        direction=Direction.DOWN;
    }

    public void setImg(String img){
        this.img=Toolkit.getDefaultToolkit().getImage(img);
    }

    public void attack(){
        if(attackCoolDown&&alive){
            Point p= this.getHeadPoint();
            Bullet bullet=new Bullet("images/bullet/bulletGreen.gif",p.x,p.y,this.gamePanel,this.direction);
            this.gamePanel.bulletList.add(bullet);
            new AttackCD().start();
        }
    }

    class AttackCD extends Thread{
        public void run(){
            attackCoolDown=false;
            try {
                Thread.sleep(attackCoolDownTime);
            }catch (Exception e){
                e.printStackTrace();
            }
            attackCoolDown=true;
            this.interrupt();
        }
    }

    public Point getHeadPoint(){
        switch (direction){
            case LEFT:
                return new Point(x,y+height/2);
            case RIGHT:
                return new Point(x+width,y+height/2);
            case UP:
                return new Point(x+width/2,y);
            case DOWN:
                return new Point(x+width/2,y+height);
            default:
                return null;
        }
    }

    public boolean hitWall(int x,int y){
        ArrayList<Wall> walls=this.gamePanel.wallList;
        Rectangle next=new Rectangle(x,y,width,height);
        for (Wall wall:walls){
            if (next.intersects(wall.getRec())){
                return true;
            }
        }
        return false;
    }

    public boolean moveToBoder(int x,int y){
        if (x<0||y<0){
            return true;
        }
        else if (x+width>this.gamePanel.getWidth()
                ||y+height>this.gamePanel.getHeight()){
            return true;
        }
        return false;
    }


    @Override
    public abstract void paintSelf(Graphics g);

    @Override
    public abstract Rectangle getRec();
}
