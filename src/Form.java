import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Form extends JFrame{
    Panel panel;
    JButton addVisitor;
     Form() {
         panel = new Panel();
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.add(panel);
         this.pack();
         this.setLocationRelativeTo(null);
         this.setVisible(true);

    }
}
