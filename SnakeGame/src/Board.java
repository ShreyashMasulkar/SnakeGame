import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int BoardH = 400;
    int BoardW = 400;
    int Max_Dots = 1600;
    int Dot_Size = 10;
    int Dots;
    int x[] = new int[Max_Dots];
    int y[] = new int[Max_Dots];
    int apple_x;
    int apple_y;
    Image body, head, apple;
    Timer timer;
    int Delay = 200;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame = true;

        Board(){
            TAdapter tAdapter = new TAdapter();
            addKeyListener(tAdapter);
            setFocusable(true);
            setPreferredSize(new Dimension(BoardW, BoardH));
            setBackground(Color.BLACK);
            initGame();
            loadImages();


    }
    public void initGame(){
            Dots = 3;
            x[0] = 250;
            y[0] = 250;
            for(int i =0;i<Dots;i++){
                x[i] = x[0]+Dot_Size*i;
                y[i] = y[0];
            }
           locateApple();
            timer = new Timer(Delay, this);
            timer.start();


    }
    public void loadImages(){
            ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
            body = bodyIcon.getImage();
            ImageIcon headIcon = new ImageIcon("src/resources/head.png");
            head = headIcon.getImage();
            ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
            apple = appleIcon.getImage();
    }
    @Override
    public void paintComponent(Graphics g){
            super.paintComponent(g);
            Drawing(g);
    }


    public void Drawing(Graphics g)
    {
           if(inGame)
           {
               g.drawImage(apple, apple_x, apple_y, this);
               for(int i=0;i<Dots;i++){
                   if(i==0)
                   {
                       g.drawImage(head, x[0], y[0], this);
                   }
                   else
                       g.drawImage(body, x[i], y[i], this);
               }
           }
           else {
               gameOver(g);
               timer.stop();
           }
    }

    public void locateApple(){
        apple_x = ((int)(Math.random()*39))*Dot_Size;
        apple_y = ((int)(Math.random()*39))*Dot_Size;
    }
    public void checkCollision(){
            for(int i =0;i<Dots;i++){
                if(i>=4&&x[0]==x[i]&&y[0]==y[i]){
                    inGame = false;
                }
            }
            if(x[0]<0)
            {
                inGame = false;
            }
            if(x[0]>=BoardW)
            {
                inGame = false;
            }
            if(y[0]<0)
            {
                inGame = false;
            }
            if(y[0]>=BoardH)
            {
                inGame = false;
            }
    }

public void gameOver(Graphics g){
            String msg = "Game Over";
            int score = (Dots - 3)*100;
            String scoremsg = "Score:"+Integer.toString(score);
            Font small = new Font("Times New Roman", Font.BOLD, 14);
            FontMetrics fontMetrics = getFontMetrics(small);
            g.setColor(Color.WHITE);
            g.drawString(msg, (BoardW-fontMetrics.stringWidth(msg))/2, BoardH/4);
            g.drawString(scoremsg, (BoardW-fontMetrics.stringWidth(msg))/2, 3*(BoardH)/4);

}

    @Override
    public void actionPerformed(ActionEvent actionEvent){
            if(inGame)
            {
                eatapple();
                checkCollision();
                move();
            }
            repaint();

    }
    public void move(){
            for(int i=Dots-1;i>0;i--)
            {
                x[i] = x[i-1];
                y[i] = y[i-1];
            }
            if(leftDirection)
            {
                x[0]-=Dot_Size;
            }
            if(rightDirection)
            {
                x[0]+=Dot_Size;
            }
            if(upDirection)
            {
                y[0]-=Dot_Size;
            }
            if(downDirection)
            {
                y[0]+=Dot_Size;
            }
    }
    public void eatapple(){
            if(apple_x == x[0] && apple_y == y[0])
            {
                Dots++;
                locateApple();
            }

    }


    private class TAdapter extends KeyAdapter{
            @Override
        public void keyPressed(KeyEvent keyEvent){
                int key = keyEvent.getKeyCode();
                if(key==keyEvent.VK_LEFT&&!rightDirection){
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                }
                if(key==keyEvent.VK_RIGHT&&!leftDirection){
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                }
                if(key==keyEvent.VK_UP&&!downDirection){
                    leftDirection = false;
                    upDirection = true;
                    rightDirection = false;
                }
                if(key==keyEvent.VK_DOWN&&!upDirection){
                    leftDirection = false;
                    rightDirection = false;
                    downDirection = true;
                }
            }
    }
}
