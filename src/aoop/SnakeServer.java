package aoop;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class SnakeServer {
    public static void main(String[] args) {   
     try {
            ServerSocket server=new ServerSocket(2111);
            System.out.println("Waiting for client");
            ArrayList<SnakeServerHandle>player=new ArrayList<>();
            while(true){
                try {
                    Socket socket = server.accept();
                    System.out.println("connected");
                    SnakeServerHandle handle = new SnakeServerHandle(socket, player);
                    player.add(handle);
                    Thread t = new Thread(handle);
                    t.start();
                     new SnakeFile();
                     new SnakeLeaderBoard();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class SnakeServerHandle implements Runnable{
     BufferedWriter writer;
     BufferedReader reader;
     ArrayList<SnakeServerHandle>player;
     public SnakeServerHandle(Socket socket,ArrayList<SnakeServerHandle>player){
         try {
             OutputStreamWriter osw=new OutputStreamWriter(socket.getOutputStream());
             InputStreamReader isr=new InputStreamReader(socket.getInputStream());
             writer=new BufferedWriter(osw);
             reader=new BufferedReader(isr);
             this.player= player;
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    @Override
    public void run() {
        try { 
            String read;
            String names[]=new String[1000];
            String scores[]=new String[1000];
            int n=0;
            while((read=reader.readLine())!=null){
     if(read.equals("Name")){
      String name=reader.readLine();
      names[n]=name;
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("E:\\SnakeLeaderBoard.txt", true)));
      if(n==0)
            out.print(names[n]);
            out.close();
         System.out.println(name);
     }
     if(read.equals("Score")){
      String score=reader.readLine();
      scores[n]=score;
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("E:\\SnakeLeaderBoard.txt", true)));
            out.println(" "+scores[n]); 
            out.close();
                
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    }
 class SnakeFile {
    SnakeFile(){
       try{
        ArrayList<String> names = new ArrayList<String>(); 
        ArrayList<String> scores = new ArrayList<String>(); 
        int flag;
        File file=new File("E:\\SnakeLeaderBoard.txt");
        BufferedReader r= new BufferedReader(new FileReader(file));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("E:\\SnakeScoreBoard.txt")));
        String st;
        while((st=r.readLine())!=null){
        String split[]=st.split(" ");
        names.add(split[0]);
        scores.add(split[1]);
        }
        for(int j=0;j<scores.size();j++){
        int max=Integer.parseInt(scores.get(0));
        flag=0;
        for(int i=0;i<scores.size();i++){
        if(max<Integer.parseInt(scores.get(i))){
            max=Integer.parseInt(scores.get(i));
            flag=i;
        }
        }
        out.println(names.get(flag)+" "+scores.get(flag));
        String a=names.get(flag);
        for(int i=0;i<scores.size();i++){
            if(a.contains(names.get(i))){
                names.remove(i);
                scores.remove(i);
                i--;
            }
        }
        j--;
        }
             out.close();
             r.close();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
 }
 class SnakeLeaderBoard {
    public SnakeLeaderBoard(){     
   JFrame frame = new JFrame("Scoreboard");
   frame.setSize(850,695);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setVisible(true);
   frame.setLayout(null);
   frame.getContentPane().setBackground(Color.black);
        JLabel[] l=new JLabel[20];
   JLabel img=readImage("E://title.jpg",850,54);
   img.setBounds(0,0,850,54);
   frame.add(img);
   JLabel s=new JLabel("LEADERBOARD");
   Font f1=new Font("Helvetica",Font.BOLD,50);
   s.setFont(f1);
   s.setForeground(Color.blue);
   s.setBounds(230,60,400,100);
   JLabel n=new JLabel("Name");
   Font f2=new Font("Helvetica",Font.BOLD,30);
   n.setFont(f2);
   n.setForeground(Color.blue);
   n.setBounds(90,160,100,30);
   JLabel sc=new JLabel("Score");
   Font f3=new Font("Helvetica",Font.BOLD,30);
   sc.setFont(f3);
   sc.setForeground(Color.blue);
   sc.setBounds(600,160,100,30);
   frame.add(s);
   frame.add(n);
   frame.add(sc);
   try{
       File file=new File("E:\\SnakeScoreBoard.txt");
       BufferedReader r= new BufferedReader(new FileReader(file));
       String st;
       int i=0,y=210;
        while((st=r.readLine())!=null){
        String split[]=st.split(" ");
        l[i]=new JLabel(split[0]);
        Font f4=new Font("Helvetica",Font.BOLD,30);
        l[i].setFont(f4);
        l[i].setBounds(90,y,250,30);
        l[i].setForeground(Color.blue);
        frame.add(l[i]);
        i++;
        l[i]=new JLabel(split[1]);
        Font f5=new Font("Helvetica",Font.BOLD,30);
        l[i].setFont(f5);
        l[i].setBounds(630,y,250,30);
        l[i].setForeground(Color.blue);
        frame.add(l[i]);
        i++;
        y+=30;   
        }
        frame.repaint();
   }catch(Exception e){
       
   }
   frame.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               int x = e.getX() - 8;
               int y = e.getY() - 31;
               System.out.println(x + " " + y);
           }
       });
   }
    JLabel readImage(String imagePath, int width, int height){
       try {
           BufferedImage image = ImageIO.read(new File(imagePath));

           Image imageScaled = image.getScaledInstance(width, height,
                   Image.SCALE_SMOOTH);

           return new JLabel(new ImageIcon(imageScaled));
       }
       catch (IOException e){
           e.printStackTrace();
       }
       return null;
   }
 }