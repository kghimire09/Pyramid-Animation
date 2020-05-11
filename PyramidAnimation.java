//PyramidAnimation: Make a version of your Pyramind program that draws one row of the pyramid at a time pausing for 50 ms
//        between rows. After the last row is drawn rows are removed until no rows are shown, pausing for 50 ms between rows.
//        Then the whole process repeats.

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PyramidAnimation extends JFrame {
    private final DrawingPanel myPanel=new DrawingPanel();

    public static class DrawingPanel extends JPanel {
        private Rectangle Rect;
        private int ROWS=25;
        private int Base=25;
        private double height,width;
        public int rowsToDraw=0;
        public DrawingPanel(){
            setBackground(Color.white);
            setForeground(Color.blue);
        }
        @Override
        protected void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            rectangleDrawing(graphic);
            bricksDrawing(graphic);
        }
        public void rectangleDrawing(Graphics graphic){
            Rect=new Rectangle((int)(getWidth()*0.1),(int)(getHeight()*0.1),(int)(getWidth()*0.8),(int)(getHeight()*0.8));
            width=(float)Rect.getWidth()/Base;
            height=(float)Rect.getHeight()/ROWS;
            graphic.fillRect((int)(getWidth()*0.1),(int)(getHeight()*0.1),(int)(getWidth()*0.8),(int)(getHeight()*0.8));
        }
        public void bricksDrawing(Graphics graphic){
            Rect=new Rectangle((int)(getWidth()*0.1),(int)(getHeight()*0.1),(int)(getWidth()*0.8),(int)(getHeight()*0.8));
            Graphics2D brick = (Graphics2D)graphic.create();
            double x = getWidth()*0.1;
            double y = getHeight()*0.9-height;
            for (int i=1;i<=rowsToDraw;i++){
                for (int j=Base;j>=i;j--){
                    brick.setColor(Color.ORANGE);
                    Rectangle2D.Double rect = new Rectangle2D.Double(x,y,width,height);
                    brick.fill(rect);
                    brick.setColor(Color.RED);
                    Rectangle2D.Double shape = new Rectangle2D.Double(x,y,width,height);
                    brick.draw(shape);
                    x+=width;
                }
                y-=height;
                x=getWidth()*0.1+i*width*0.5;
            }
        }
        private int delta=1;
        private void nextRow(){
            rowsToDraw+=delta;
            if (rowsToDraw>ROWS||rowsToDraw<0){
                delta*=-1;
            }
            repaint();
            try {
                Thread.sleep(50);
            }catch (InterruptedException ignored){
            }
        }
    }
    public PyramidAnimation(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        add(myPanel);
    }
    public static void main(String[] args){
        PyramidAnimation pyramidAnimation = new PyramidAnimation();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                pyramidAnimation.setVisible(true);
            }
        });
        while (true){
            pyramidAnimation.myPanel.nextRow();

        }
    }

}
