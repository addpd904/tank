package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {
    Image offScreemImage=null;
    int width= 800;
//    int height=610;
    int height=900;
    Image select= Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
    int y=150;
    //1:单人模式 2：双人模式 3：暂停 4：失败5：游戏胜利
    int state= 0;
    //存取键盘过渡值
    int a =1;
    //绘制次数
    int count=0;
    int enemyCount=0;
    ArrayList<Bullet> bulletList=new ArrayList<Bullet>();
    ArrayList<Bot> botList=new ArrayList<Bot>();
    ArrayList<Bullet> removeList=new ArrayList<Bullet>();
    ArrayList<Tank> playerList=new ArrayList<Tank>();
    ArrayList<Wall> wallList=new ArrayList<Wall>();
    ArrayList<Base> basesList=new ArrayList<Base>();
    ArrayList<Blast> blastList=new ArrayList<Blast>();


    PlayerOne playerOne=new PlayerOne("images/player1/p1tankU.gif",125,510,this,
           "images/player1/p1tankU.gif","images/player1/p1tankL.gif",
            "images/player1/p1tankR.gif","images/player1/p1tankD.gif");
    PlayerTwo playerTwo=new PlayerTwo("images/player2/p2tankU.gif",225,520,this,
            "images/player2/p2tankU.gif","images/player2/p2tankL.gif",
            "images/player2/p2tankR.gif","images/player2/p2tankD.gif");
    Base base=new Base("images/star.gif",375,570,this);


    public void launch(){
        setTitle("坦克大战");
        setSize(width,height);
        setDefaultCloseOperation(3);
        setVisible(true);
        //添加键盘监视器
        this.addKeyListener(new KeyMonitor());
        for (int i=0;i<14;i++){
            wallList.add(new Wall("images/walls.gif",50+i*50,170,this));
        }
        wallList.add(new Wall("images/walls.gif",305,560,this));
        wallList.add(new Wall("images/walls.gif",305,500,this));
        wallList.add(new Wall("images/walls.gif",365,500,this));
        wallList.add(new Wall("images/walls.gif",425,500,this));
        wallList.add(new Wall("images/walls.gif",425,560,this));

        basesList.add(base);

        while (true){
            if(botList.size()==0&&enemyCount>2){
                state=5;
            }
            if(playerList.size()==0&&(state==1||state==2)||basesList.size()==0){
                state=4;
            }
            if (count%20==1&&enemyCount<10&&(state==1||state==2)){
                Random random=new Random();
                int rnum=random.nextInt(800);
                botList.add(new Bot("images/enemy/enemy1U.gif",rnum,300,this,
                        "images/enemy/enemy1U.gif","images/enemy/enemy1L.gif",
                        "images/enemy/enemy1R.gif","images/enemy/enemy1D.gif"));
                enemyCount++;
            }
            repaint();
            try {
                Thread.sleep(50 );
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void paint(Graphics g){
        if (offScreemImage==null){
            offScreemImage=this.createImage(width,height);
        }
        Graphics gImage=offScreemImage.getGraphics();
        gImage.setColor(Color.GRAY);
        gImage.fillRect(0,0,width,height);
        gImage.setColor(Color.BLUE);
        gImage.setFont(new Font("仿宋",Font.BOLD,50));
        if(state==0){
            gImage.drawString("选择游戏模式",220,100);
            gImage.drawString("单人",220,200);
            gImage.drawString("双人",220,300);
//        g.drawImage(select,160,y,height,y,width,y,height,rootPane);
            gImage.drawImage(select,160,y,null);
        }
        else if (state==1||state==2){
            gImage.setFont(new Font("仿宋",Font.BOLD,30));
            gImage.setColor(Color.BLUE);
            gImage.drawString("剩余敌人："+botList.size(),10,50);
            gImage.drawString("游戏开始",220,100);
//            if (state==1){
//                gImage.drawString("单人模式",220,200);
//            }
//            else {
//                gImage.drawString("双人模式",220,200);
//            }
            for (Tank player:playerList){
                player.paintSelf(gImage);
            }
            for (Bullet bullet:bulletList){
                bullet.paintSelf(gImage);
            }
            bulletList.removeAll(removeList);
            for (Bot bot:botList){
                bot.paintSelf(gImage);
            }
            for (Wall wall:wallList){
                wall.paintSelf(gImage);
            }
            for (Base base:basesList){
                base.paintSelf(gImage);
            }
            for (Blast blast:blastList){
                blast.paintSelf(gImage);
            }
            count++;
        }
        else if (state==5){
            gImage.drawString("游戏胜利",220,100);
        }
        else if (state==4){
            gImage.drawString("游戏失败",220,100);
        }
        else if (state==3){
            gImage.drawString("游戏暂停",220,100);
        }
//        g.drawImage(offScreemImage,width,y,state,y,height,y,a,y,rootPane);
        g.drawImage(offScreemImage,0,0,null);
    }

    class KeyMonitor extends KeyAdapter{
        //
        public void keyPressed(KeyEvent e){
            int key =e.getKeyCode();
            switch (key){
                case KeyEvent.VK_1:
                    a=1;
                    y=150;
                    break;
                case KeyEvent.VK_2:
                    a=2;
                    y=250;
                    break;
                case KeyEvent.VK_ENTER:
                    state=a;
                    playerList.add(playerOne);
                    if (state==2){
                        playerList.add(playerTwo);
                        playerTwo.alive=true;
                    }
                    playerOne.alive=true;
                    break;
                case  KeyEvent.VK_P:
                    if (state!=3){
                        a =state;
                        state=3;
                    }
                    else {
                        state=a;
                        if (a==0){
                            a=1;
                        }
                    }
                default:
                    playerOne.keyPressed(e);
                    if (state==2){
                        playerTwo.keyPressed(e);
                    }
            }
        }

        public void keyReleased(KeyEvent e){
            playerOne.keyReleased(e);
            if (state==2){
                playerTwo.keyReleased(e);
            }
        }

    }

    public static void main(String[] args ){
        GamePanel gp=new GamePanel();
        gp.launch();
    }
}
