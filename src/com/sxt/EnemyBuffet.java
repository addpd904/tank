package com.sxt;

import java.awt.*;
import java.util.ArrayList;

public class EnemyBuffet extends Bullet{
    public EnemyBuffet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }

    public void hitPlayer(){
        ArrayList<Tank> players=this.gamePanel.playerList;
        for (Tank player:players){
            if (this.getRec().intersects(player.getRec())){
                this.gamePanel.blastList.add(new Blast("",player.x-14,player.y-14,this.gamePanel));
                this.gamePanel.playerList.remove(player);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        go();
        hitPlayer();
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
