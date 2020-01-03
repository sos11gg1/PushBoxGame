package com.example.lib;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

//Class GamePanel
//The main class of the game

class GamePanel extends JPanel implements KeyListener
{
    private static final long serialVersionUID = 1L;

    private char[][] map; //Map state
    private char[][] box;
    private int sX,sY;  //Map size
    private Point player,p; //Point of player
    private int Num=0;  //Number of boxs
    private Point[] boxs; //Points of boxs;
    //private Point[] trg; //Points of target
    //private Point[] path;
    //private int steps;
    private Point[] dir;

    private int size;
    private Image imgfloor;
    private Image imgwall;
    private Image imgplayer;
    private Image imgbox1;
    private Image imgbox2;
    private Image imgtrg;
    private Image imggrass;



    //method
    public GamePanel(char b[][],int w,int h,int size)throws Exception
    {
        sX = b.length;
        sY = b[0].length;
        Num = 0;
        String Filename;

        map =   new char[sX][sY];
        box =   new char[sX][sY];
        player= new Point();
        p = new Point();
        boxs= new Point[sX*sY];

        dir = new Point[4];
        dir[0]= new Point(0,-1); //left
        dir[1]= new Point(-1,0); //up
        dir[2]= new Point(0, 1); //right
        dir[3]= new Point(1, 0); //down

        try
        {
            imgfloor=ImageIO.read(new File("a1.PNG"));//讀取檔案
            imgwall=ImageIO.read(new File("a2.PNG"));
            imgplayer= ImageIO.read(new File("a3.PNG"));
            imgtrg  = ImageIO.read(new File("a4.PNG"));
            imgbox1  = ImageIO.read(new File("a5.PNG"));
            imgbox2  = ImageIO.read(new File("a6.PNG"));
            imggrass = ImageIO.read(new File("a7.PNG"));
        }
        catch(Exception e){

        }


        this.size=size;

        //set Map
        for(int i=0;i<sX;i++){
            for(int j=0;j<sY;j++){
                switch(b[i][j]){
                    case 'P': //Player
                        map[i][j]='.';
                        p.setLocation(i, j);
                        break;
                    case 'X': //Target
                        map[i][j]='X';
                        break;
                    case 'B': //Box
                        map[i][j]='.';
                        boxs[Num++]=new Point(i, j);
                        break;
                    case '#': //Wall
                        map[i][j]='#';
                        break;
                    case '.': //floor
                        map[i][j]='.';
                        break;
                    case 'G': //grass
                        map[i][j]='G';
                        break;
                    default :
                        map[i][j]='.';
                        break;
                }
            }
        }//end set Map

        reset(); //reset Game
    }

    public void reset(){
        player.setLocation(p);
        for(int i=0;i<sX;i++)
            for(int j=0;j<sY;j++)
                box[i][j]=' ';
        for(int i=0;i<Num;i++)
            box[boxs[i].x][boxs[i].y]='B';
        //steps=0;
        repaint();
    }

    public void undo(){

    }

    public boolean judge(){
        int lv=0;
        for(int i=0;i<sX;i++)
            for(int j=0;j<sY;j++)
                if(box[i][j]=='B'&&map[i][j]!='X')
                    return false;

        return true;
    }

    public void paint(Graphics g){
        //draw map
        for(int i=0;i<sX;i++){
            for(int j=0;j<sY;j++){
                switch(map[i][j]){
                    case '.':
                        g.drawImage(imgfloor, j*size, i*size, null);
                        break;
                    case '#':
                        g.drawImage(imgwall, j*size, i*size, null);
                        break;
                    case 'X':
                        g.drawImage(imgfloor, j*size, i*size, null);
                        break;
                    case 'G':
                        g.drawImage(imggrass, j*size, i*size, null);
                        break;
                }
                if(box[i][j]=='B'&&map[i][j]=='X') g.drawImage(imgbox2, j*size, i*size, null);
                else if(box[i][j]=='B') g.drawImage(imgbox1, j*size, i*size, null);
                else if(map[i][j]=='X') g.drawImage(imgtrg, j*size, i*size, null);
            }
        }
        //draw player
        g.drawImage(imgplayer, player.y*size, player.x*size, null);

        g.drawRect(0, 0, sY*size, sX*size);

        //get Focus
        this.requestFocusInWindow(); /* ********* */
    }


    /* methods of KeyListener */

    public void keyPressed(KeyEvent e){
        int x=player.x;
        int y=player.y;
        int k,x0,y0,l=0;

        k=e.getKeyCode()-37;

        if(k<0||k>=4) return;

        x+=dir[k].x;
        y+=dir[k].y;

        if(x<0||x>=sX||y<0||y>=sY) return;
        if(map[x][y]=='#') return;

        if(box[x][y]!='B'&&(map[x][y]=='.'||map[x][y]=='X')){
            player.x=x;
            player.y=y;
        }
        if(box[x][y]=='B'){
            x0=x+dir[k].x;
            y0=y+dir[k].y;
            if(x0<0||y0<0||x0>=sX||y0>=sY) return;
            if(map[x0][y0]!='#'&&box[x0][y0]!='B'){
                box[x][y]=' ';
                box[x0][y0]='B';
                player.x=x;
                player.y=y;
            }
        }

        repaint();


        if(judge()){
            JOptionPane.showMessageDialog(null,"You win!");
            reset();
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

}



//Class PushBox
//Creat JFrame to Play the game





