import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Visualization2 {
    
    JFrame frame;
    MyDrawPanel panel;
    int x = 70;
    int y = 70;
    boolean run = false;
    /*
    public static void main(String[] args) {
        Visualization2 vis = new Visualization2();
        vis.visualize();
    }
    */
    public void visualize() {
        frame = new JFrame("Visualization2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200,100,800,800);
        frame.setVisible(true);
        Container cont = frame.getContentPane();
        
        JButton button = new JButton("Start");
        button.addActionListener(new ButtonListener());
        cont.add(BorderLayout.SOUTH, button);
        
        panel = new MyDrawPanel();
        cont.add(BorderLayout.CENTER, panel);
    }
    
    private class MyDrawPanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0,0,panel.getWidth(),panel.getHeight());
            
            g.setColor(Color.yellow);
            g.fillOval(x,y,40,40);
        }
    }
    
    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new Timer(50, new visListener()).start();
        }
    }
    
    public class visListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            x++;
            y++;
            panel.repaint();
        }
    }
}