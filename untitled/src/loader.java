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

        JButton searchButton = new JButton(addImage("src/Images/search1.png", 70, 40));
        searchButton.setBounds(490, 15, 70, 40);
        add(searchButton);

        JLabel city = new JLabel();
        city.setFont(new Font("Dialog", Font.BOLD, 20));
        city.setBounds(100, 100, 100, 100);
        add(city);


        JLabel cloudyIcon = new JLabel(addImage("src/Images/cloudy.png", 300, 290));
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

        JLabel humidityImage = new JLabel(addImage("src/Images/humid.png", 60, 60));
        humidityImage.setBounds(60, 550, 60, 60);
        add(humidityImage);

        JLabel humidTxt = new JLabel("<html> <b> Humidity </b> 100%</html>");
        humidTxt.setFont(new Font("Dialog", Font.PLAIN, 20));
        humidTxt.setBounds(135, 555, 100, 60);
        add(humidTxt);

        JLabel windSpeedIcon = new JLabel(addImage("src/Images/windIcon.png", 50, 50));
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
                        cloudyIcon.setIcon(addImage("src/Images/sunny.png", 300, 290));

                    } else if (data.get("weather_condition").equals("Cloudy")) {
                        cloudyIcon.setIcon(addImage("src/Images/cloudy.png", 300, 290));

                    } else if (data.get("weather_condition").equals("Rain")) {
                        cloudyIcon.setIcon(addImage("src/Images/rain.png", 300, 290));

                    } else if (data.get("weather_condition").equals("Snow")) {
                        cloudyIcon.setIcon(addImage("src/Images/snow.png", 300, 290));

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
                new loader().setVisible(true);

            }
        });
    }
}