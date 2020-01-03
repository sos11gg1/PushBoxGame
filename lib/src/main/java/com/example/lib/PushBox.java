package com.example.lib;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PushBox extends JFrame
{
    private static final long serialVersionUID = 1L;

    private JPanel top;
    private GamePanel center;
    private JPanel bottom;

    private JButton breset; //Button of reset
    private JButton bundo; //Button of undo

    private int width,height,size;
    private int sX,sY;

    //method of constitution
    public PushBox(char[][] b)throws Exception{
        super("PushBox Game");

        //set Window
        size=35;
        sX=b.length;
        sY=b[0].length;
        width=size*sY+10;
        height=size*sX+100;

        top     = new JPanel();
        center  = new GamePanel(b,width,height-100,size); //Panel of Game
        bottom  = new JPanel();
        breset = new JButton("Reset");
        bundo = new JButton("←Undo");

        //add Listener
        center.addKeyListener(center);
        breset.addActionListener(new ResetListener());
        bundo.addActionListener(new UndoListener());

        top.add(breset);
        //top.add(bundo);
        add(top,"North");
        add(center,"Center");
        add(bottom,"South");

        setSize(width,height);
        setVisible(true);
    }

    //classes of Listener
    class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            center.reset();
        }
    }
    class UndoListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            center.undo();
        }
    }

    //method main
    public static void main(String[] args)throws Exception{
        char[][] b=new char[12][13];

        b[0]="G#######GGGGG".toCharArray();
        b[1]="G#..#..#GGGGG".toCharArray();
        b[2]="G#.BB..#GGGGG".toCharArray();
        b[3]="G#..#B.######".toCharArray();
        b[4]="##..#.###XXX#".toCharArray();
        b[5]="#..#.B..#..X#".toCharArray();
        b[6]="#.B.B.B....X#".toCharArray();
        b[7]="#..#.B..#..X#".toCharArray();
        b[8]="##..#.###XXX#".toCharArray();
        b[9]="G#...B.######".toCharArray();
        b[10]="G#..#P.#GGGGG".toCharArray();
        b[11]="G#######GGGGG".toCharArray();


        PushBox p=new PushBox(b);
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
