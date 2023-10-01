import javax.swing.*;

public class loader extends JFrame {
    loader() {
        //set title
        super("Weather App");

        //close when x is pressed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setLayout(null);

        setResizable(false);
        addComponents();


    }

    public void addComponents() {

    }


    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               new loader().setVisible(true);
           }
       });
    }
}