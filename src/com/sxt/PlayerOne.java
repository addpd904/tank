package com.sxt;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerOne extends Tank{
    Boolean left=false;
    Boolean right=false;
    Boolean up=false;
    Boolean down=false;
    public PlayerOne(String img, int x, int y, GamePanel gamePanel, String upImg, String leftImg, String rightImg, String downImg) {
        super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        move();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }

    public void keyPressed(KeyEvent e){
        int key =e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT :
                left=true;
                break;
            case KeyEvent.VK_DOWN:
                down=true;
                break;
            case KeyEvent.VK_RIGHT:
                right=true;
                break;
            case KeyEvent.VK_UP :
                up=true;
                break;
            case KeyEvent.VK_SPACE:
                attack();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        int key =e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT :
                left=false;
            case KeyEvent.VK_DOWN:
                down=false;
            case KeyEvent.VK_RIGHT:
                right=false;
            case KeyEvent.VK_UP :
                up=false;
            default:
                break;
        }
    }

    public void move(){
        if(left){
            leftward();
        }
        else if(right){
            righttward();
        }
        else if(up){
            upward();
        }
        else if(down){
            downward();
        }
    }

}
