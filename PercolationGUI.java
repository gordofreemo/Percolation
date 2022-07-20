import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PercolationGUI extends JPanel implements ActionListener {
    private JFrame frame = new JFrame();
    private Percolation myPerc;
    Timer myTimer;
    private int screenWidth;
    private int screenHeight;
    private int blockSize;
    private int n;
    PercolationGUI(int n) {
        this.n = n;
        screenHeight = 700;
        screenWidth = 700;
        blockSize = screenHeight/n;

        myPerc = new Percolation(n);
        myPerc.open(10,10);
        myTimer = new Timer(1,this);
        myTimer.start();



        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
    }

    @Override
    public void paintComponent(Graphics g){
        int[][] loopGrid = myPerc.getGrid();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(loopGrid[i][j] == 0)
                    g.setColor(Color.BLACK);
                else if (loopGrid[i][j] == 1)
                    g.setColor(Color.white);
                else if (loopGrid[i][j] == 2)
                    g.setColor(Color.BLUE);
                g.fillRect(j*blockSize,i*blockSize,blockSize,blockSize);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int num1 = (int)(Math.random() * n)+1;
        int num2 = (int)(Math.random() * n)+1;

        while(myPerc.isOpen(num1,num2)) {
            num1 = (int)(Math.random() * n)+1;
            num2 = (int)(Math.random() * n)+1;
        }

        myPerc.open(num1,num2);
        repaint();
        if(myPerc.percolates()) {
            myPerc.updateFull();
            System.out.println(myPerc);
            repaint();
            myTimer.stop();
            JOptionPane.showMessageDialog(frame, "The System Percolated");
        }
    }

    public static void main(String[] args) {
        new PercolationGUI(100);
    }
}
