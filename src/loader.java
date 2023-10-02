import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class loader extends JFrame {
    loader() {
        //set title
        super("Weather App");

        //close when x is pressed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(590, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addComponents();

    }

    private void addComponents() {
        //search field to search relative location
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("MS Mincho", Font.BOLD, 24));
        searchField.setBounds(15, 15, 450, 40);
        add(searchField);

        //add search button

        ImageIcon icon1 = new ImageIcon(loader.class.getResource("/search1.png"));

        // Optionally, scale the image to your desired size
        Image scaledImage1 = icon1.getImage().getScaledInstance(70, 40, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(scaledImage1);

        JButton searchButton = new JButton(icon1);
        searchButton.setBounds(490, 15, 70, 40);

       
        searchButton.setBorderPainted(false);
        add(searchButton);

        JLabel city = new JLabel();
        city.setFont(new Font("Dialog", Font.BOLD, 20));
        city.setBounds(100, 100, 100, 100);
        add(city);




        ImageIcon icon = new ImageIcon(loader.class.getResource("/cloudy.png"));

        // Optionally, scale the image to your desired size
        Image scaledImage = icon.getImage().getScaledInstance(300, 290, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JLabel cloudyIcon = new JLabel(icon);
        cloudyIcon.setBounds(-10, 90, 600, 290);
        add(cloudyIcon);

        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(-10, 400, 600, 40);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 40));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);


        JLabel weatherDescription = new JLabel("Sunny");
        weatherDescription.setBounds(-10, 430, 600, 60);
        weatherDescription.setFont(new Font("Dialog", Font.PLAIN, 40));
        weatherDescription.setHorizontalAlignment(SwingConstants.CENTER);
        add((weatherDescription));


        ImageIcon icon11 = new ImageIcon(loader.class.getResource("/humid.png"));

        // Optionally, scale the image to your desired size
        Image scaledImage11 = icon11.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        icon11 = new ImageIcon(scaledImage11);


        JLabel humidityImage = new JLabel(icon11);
        humidityImage.setBounds(60, 550, 60, 60);
        add(humidityImage);

        JLabel humidTxt = new JLabel("<html> <b> Humidity </b> 100%</html>");
        humidTxt.setFont(new Font("Dialog", Font.PLAIN, 20));
        humidTxt.setBounds(135, 555, 100, 60);
        add(humidTxt);


        ImageIcon icon111 = new ImageIcon(loader.class.getResource("/windIcon.png"));

        // Optionally, scale the image to your desired size
        Image scaledImage111 = icon111.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        icon111 = new ImageIcon(scaledImage111);


        JLabel windSpeedIcon = new JLabel(icon111);
        windSpeedIcon.setBounds(340, 550, 60, 60);
        add(windSpeedIcon);




        JLabel windspeedtxt = new JLabel("<html> <b> Windspeed </b> 5.2km/h</html>");
        windspeedtxt.setFont(new Font("Dialog", Font.PLAIN, 20));
        windspeedtxt.setBounds(415, 555, 120, 60);
        add(windspeedtxt);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    JSONObject data = backEndLogic.getWeatherData(searchField.getText().toString());

                    temperatureText.setText(data.get("temperature").toString());
                    windspeedtxt.setText("<html> <b> Windspeed </b>" + data.get("windspeed").toString() + "km/h</html>");
                    humidTxt.setText("<html> <b> Humidity </b>" + data.get("humidity") + "%</html>");
                    weatherDescription.setText(data.get("weather_condition").toString());

                    if (data.get("weather_condition").equals("Clear")) {

                        ImageIcon icon = new ImageIcon(loader.class.getResource("/sunny.png"));

                        // Optionally, scale the image to your desired size
                        Image scaledImage = icon.getImage().getScaledInstance(300, 290, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaledImage);
                        cloudyIcon.setIcon(icon);




                    } else if (data.get("weather_condition").equals("Cloudy")) {
                        ImageIcon icon = new ImageIcon(loader.class.getResource("/cloudy.png"));

                        // Optionally, scale the image to your desired size
                        Image scaledImage = icon.getImage().getScaledInstance(300, 290, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaledImage);
                        cloudyIcon.setIcon(icon);

                    } else if (data.get("weather_condition").equals("Rain")) {
                        ImageIcon icon = new ImageIcon(loader.class.getResource("/rain.png"));

                        // Optionally, scale the image to your desired size
                        Image scaledImage = icon.getImage().getScaledInstance(300, 290, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaledImage);
                        cloudyIcon.setIcon(icon);

                    } else if (data.get("weather_condition").equals("Snow")) {
                        ImageIcon icon = new ImageIcon(loader.class.getResource("/snow.png"));

                        // Optionally, scale the image to your desired size
                        Image scaledImage = icon.getImage().getScaledInstance(300, 290, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaledImage);
                        cloudyIcon.setIcon(icon);

                    }

                } catch (Exception a) {
                    JOptionPane.showMessageDialog(new JOptionPane(), "Enter Valid Location");
                }

            }
        });


        //add wind speed and direction.


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

                loader a  = new loader();
                a.setUndecorated(true);
                a.setVisible(true);
                //a.setBackground(new Color(0, 0, 0, 0));

               // a.setOpacity(1.0f);

            }
        });
    }
}