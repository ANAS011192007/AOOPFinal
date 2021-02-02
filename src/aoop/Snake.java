package aoop;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;
import javax.swing.*;
public class Snake{
    public Snake(String s){
        JFrame f=new JFrame("Snake Game");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setBounds(10,10,905,700);
        f.setVisible(true);
        Game game=new Game(s);
        f.add(game);
    }
    public static void main(String[] args) {

    }
}
class Game extends JPanel implements ActionListener,KeyListener{
    BufferedWriter writer;
    BufferedReader reader;
    int[] snakexpos=new int[750];
    int[] snakeypos=new int[750];
    int start=0,snakelength=3,count=0;
    boolean l=false;boolean r=false;boolean u=false;boolean d=false;
    int objectxpos[]={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int objectypos[]={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    int x=25,y=75,score=0,restart=0;
    ImageIcon points=new ImageIcon("E://sb.png");
    Random n=new Random();
    int xr=n.nextInt(34);
    int yr=n.nextInt(23);
    String names[]=new String[1000];
    int scores[]=new int[1000];
    public String sname;
    public Game(String s){
        sname=s;
       setFocusable(true);
       addKeyListener(this);
       setFocusTraversalKeysEnabled(false);
       Timer time=new Timer(100,this);
       time.start();
    }    
    public String getSname() {
        return sname;
    }
    @Override
    public void paint(Graphics G){
        G.setColor(Color.BLUE);
        G.fillRect(0, 0, 905, 700);
        G.drawRect(24,10,851,55);
        G.setColor(Color.LIGHT_GRAY);
        ImageIcon it=new ImageIcon("E://title.jpg");
        it.paintIcon(this,G,25,11);
        G.drawRect(24,74,851,577);
        G.setColor(Color.BLACK);
        G.fillRect(25,75,850,575);
        G.setColor(Color.BLACK);
        G.setColor(Color.lightGray);
        Font f=new Font("Helvetica",Font.ITALIC,14);
        G.setFont(f);
        G.drawString("Score = "+score,750,50);
        Font f4=new Font("Helvetica",Font.ITALIC,14);
        G.setFont(f4);
        G.drawString("Player = "+getSname(),750,30);
        if(start==0){
            snakexpos[0]=100;
            snakexpos[1]=75;
            snakexpos[2]=50;
            snakeypos[0]=100;
            snakeypos[1]=100;
            snakeypos[2]=100;
        }
        ImageIcon lm,dm,um,sb;
        ImageIcon rm=new ImageIcon("E://rm.png");  
        rm.paintIcon(this,G,snakexpos[0],snakeypos[0]);
        for(int i=0;i<snakelength;i++){
            if(i==0 && l){
                lm=new ImageIcon("E://lm.png");
                lm.paintIcon(this,G,snakexpos[i],snakeypos[i]);          
            }
            if(i==0 && r){
                rm=new ImageIcon("E://rm.png");
             rm.paintIcon(this,G,snakexpos[i],snakeypos[i]);
            }
            if(i==0 && u){
                um=new ImageIcon("E://um.png");
             um.paintIcon(this,G,snakexpos[i],snakeypos[i]);
            }
            if(i==0 && d){
                 dm=new ImageIcon("E://dm.png");
             dm.paintIcon(this,G,snakexpos[i],snakeypos[i]);
            }
            if(i!=0){
                sb=new ImageIcon("E://sb.png");
             sb.paintIcon(this,G,snakexpos[i],snakeypos[i]);
            }
        }
        if(objectxpos[xr]==snakexpos[0]){
            if(objectypos[yr]==snakeypos[0]){
                     snakelength++;
                     xr=n.nextInt(34);
                     yr=n.nextInt(23);
                     score++;
            }
        }
        points.paintIcon(this,G,objectxpos[xr],objectypos[yr]);
        for(int i=1;i<snakelength;i++){
            if(snakexpos[i]==snakexpos[0]){
                if(snakeypos[i]==snakeypos[0]){
                    r=false;
                    l=false;
                    u=false;
                    d=false;
                    restart=1;
                    G.setColor(Color.lightGray);
                    Font f1=new Font("Helvetica",Font.BOLD,50);
                    G.setFont(f1);
                    G.drawString("Game Over!",300,300);
                    G.setColor(Color.lightGray);
                    Font f2=new Font("Helvetica",Font.BOLD,20);
                    G.setFont(f2);
                    G.drawString("Press 'Enter' to try again." ,330,340); 
                    Font f3=new Font("Helvetica",Font.BOLD,20);
                    G.setFont(f3);
                    G.drawString("Press 's' to see scoreboard." ,320,370);
                    names[count]=getSname();
                    scores[count]=score;
                    count++;
                }
            }
        }
        G.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(r){
            for(int i=snakelength-1;i>=0;i--){
                snakeypos[i+1]=snakeypos[i];
            }
            for(int i=snakelength;i>=0;i--){
                if(i!=0){
                    snakexpos[i]=snakexpos[i-1];
                }else{
                snakexpos[i]=snakexpos[i]+25;
            }
                if(snakexpos[i]>850){
                    snakexpos[i]=25;
                }
            }
            repaint();
        }
        else if(l){
            for(int i=snakelength-1;i>=0;i--){
                snakeypos[i+1]=snakeypos[i];
            }
            for(int i=snakelength;i>=0;i--){
                if(i!=0){
                    snakexpos[i]=snakexpos[i-1];
                }else{
                snakexpos[i]=snakexpos[i]-25;
            }
                if(snakexpos[i]<25){
                    snakexpos[i]=850;
                }
            }
            repaint();
        }
        else if(d){
            for(int i=snakelength-1;i>=0;i--){
                snakexpos[i+1]=snakexpos[i];
            }
            for(int i=snakelength;i>=0;i--){
                if(i!=0){
                    snakeypos[i]=snakeypos[i-1];
                }else{
                snakeypos[i]=snakeypos[i]+25;
            }
                if(snakeypos[i]>625){
                    snakeypos[i]=75;
                }
            }
            repaint();
        }
        else if(u){
            for(int i=snakelength-1;i>=0;i--){
                snakexpos[i+1]=snakexpos[i];
            }
            for(int i=snakelength;i>=0;i--){
                if(i!=0){
                    snakeypos[i]=snakeypos[i-1];
                }else{
                snakeypos[i]=snakeypos[i]-25;
            }
                if(snakeypos[i]<75){
                    snakeypos[i]=625;
                }
            }
            repaint();
        }
    }
    @Override
    public void keyTyped(KeyEvent ke) {
      
    }
    @Override
    public void keyPressed(KeyEvent ke) {
          if(ke.getKeyCode()==38 && restart==0){
             start++;
             u=true;
             if(!d){
                 d=false;
             }
             else if(d){
                 u=false;
                 l=true;
             }
             l=false;
             r=false;
        }
        else if(ke.getKeyCode()==40 && restart==0){
             start++;
             d=true;
             if(!u){
                 u=false;
             }
             else if(u){
                 d=false;
                 u=true;
             }
             l=false;
             r=false;
            
        }
        else if(ke.getKeyCode()==37 && restart==0){
             start++;
             l=true;
             if(!r){
                 r=false;
             }
             else if(r){
                 l=false;
                 r=true;
             }
             u=false;
             d=false;
        }
        else if(ke.getKeyCode()==39 && restart==0){
             start++;
             r=true;
             if(!l){
                 l=false;
             }
             else if(l){
                 r=false;
                 l=true;
             }
             u=false;
             d=false;
        }
          if(ke.getKeyCode()==KeyEvent.VK_ENTER && restart==1){
              snakelength=3;
              start=0;
              score=0;
              restart=0;
              repaint();
    }
          if(ke.getKeyChar()=='s'){
                            try{
            Socket socket = new Socket("127.0.0.1", 2111);
            OutputStreamWriter o = new OutputStreamWriter(socket.getOutputStream());
            writer = new BufferedWriter(o);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isr);
            for(int i=0;i<count;i++){
            writer.write("Name");
            writer.newLine();
            writer.write(names[i]);
            writer.newLine();
            writer.flush();
            writer.write("Score");
            writer.newLine();
            writer.write(scores[i]+"");
            writer.newLine();
            writer.flush();
            }
              }catch(IOException e){
              
              }
          }
        }
    
    
    @Override
    public void keyReleased(KeyEvent ke) {
     
    }
}
