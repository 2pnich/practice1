import javax.swing.*;

public class Form extends JFrame{
    Panel panel;
     Form() {
         panel = new Panel();
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.add(panel);
         this.pack();
         this.setLocationRelativeTo(null);
         this.setVisible(true);

    }
}
