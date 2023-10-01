import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    private void addComponents() {
        //search field to search relative location
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("MS Mincho",Font.BOLD, 24));
        searchField.setBounds(15,15,450,40);
        add(searchField);

        //add search button
        

        JButton searchButton = new JButton(addImage("src/Images/search1.png",70,40));
        searchButton.setBounds( 490,15,70,40);
        add(searchButton);


        JLabel cloudyIcon = new JLabel(addImage("src/Images/cloudy.png",300,290));
        cloudyIcon.setBounds(0,100,600,290);
        add(cloudyIcon);

        JLabel temperatureText  = new JLabel("10 C");
        temperatureText.setBounds(0,410,600,40);
        temperatureText.setFont(new Font("Dialog",Font.BOLD,50));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);




    }

    private ImageIcon addImage(String relativePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(relativePath));
            //sacle the image to size
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not load image");
        return null;
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