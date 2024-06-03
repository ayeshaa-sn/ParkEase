import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;


public class Main {

    public static JTextField twoWheelerField;
    public static JTextField fourWheelerField;
    public static List<Vehicle>vehicles=new ArrayList<>();
    public static JTextArea displayArea;
    private static HashMap<String, String> users = new HashMap<>();

    public static List<Ticket> tickets = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createMainFrame());


    }

    private static void createMainFrame() {
        ImageIcon image = new ImageIcon("darkparking.jpeg");
        Border border = BorderFactory.createLineBorder(Color.darkGray, 3);
        JLabel label = new JLabel(image);
        label.setText("PARKEASE");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(Color.gray);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 90));
        label.setIconTextGap(-150);
        label.setBackground(Color.darkGray);
        label.setOpaque(true);
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton button = new JButton("REGISTER");
        JButton b1 = new JButton("LOGIN");
        button.setBounds(300, 190, 150, 50);
        b1.setBounds(300, 190 + 90, 150, 50);
        button.setFocusable(false);
        button.setFont(new Font("Times new Roman", Font.BOLD, 23));
        button.setBackground(Color.lightGray);
        button.setBorder(new LineBorder(Color.darkGray, 5));

        b1.setFocusable(false);
        b1.setFont(new Font("Times new Roman", Font.BOLD, 23));
        b1.setBackground(Color.lightGray);
        b1.setBorder(new LineBorder(Color.DARK_GRAY, 5));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Parking Management Software");
        frame.setSize(752, 498);
        frame.setResizable(false);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        label.add(button);
        label.add(b1);
        frame.add(label);
        frame.validate();
        frame.pack();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRegistrationFrame();
            }
        });

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //frame.dispose();
                createLoginFrame();
            }
        });

        frame.setVisible(true);
    }

    private static void createRegistrationFrame() {
        // Set UI properties
        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));

        JFrame frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        centerPanel.setBackground(Color.LIGHT_GRAY); // Set background color

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordText = new JPasswordField();

        centerPanel.add(userLabel);
        centerPanel.add(userText);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordText);
        centerPanel.add(confirmPasswordLabel);
        centerPanel.add(confirmPasswordText);

        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(120, 40)); // Set button size

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Add top padding
        buttonPanel.add(registerButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());

                if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                    if (!users.containsKey(username)) {
                        if (password.equals(confirmPassword)) {
                            users.put(username, password);
                            saveUsers();
                            JOptionPane.showMessageDialog(frame, "Registration Successful!");
                            userText.setText("");
                            passwordText.setText("");
                            confirmPasswordText.setText("");
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Username already exists.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter all fields.");
                }
            }
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center registration frame on screen
    }
    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.txt"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createLoginFrame() {
        loadUsers();
        // Set UI properties
        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        centerPanel.setBackground(Color.LIGHT_GRAY); // Set background color

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();

        centerPanel.add(userLabel);
        centerPanel.add(userText);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 40)); // Set button size

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Add top padding
        buttonPanel.add(loginButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                if (users.containsKey(username) && users.get(username).equals(password)) {
                    createInitializationPage();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
                frame.dispose();
            }
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center login frame on screen
    }
    private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.txt"))) {
            users = (HashMap<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, which is fine if no users have been registered yet.
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void createInitializationPage() {
        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));

        JFrame frame = new JFrame("Parking Slot Initializer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setPreferredSize(new Dimension(450, 250));

        JLabel titleLabel = new JLabel("Admin,Initialize the number of parking slots");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel twoWheelerLabel = new JLabel("Two-Wheeler Slots:");
        twoWheelerField = new JTextField();
        JLabel fourWheelerLabel = new JLabel("Four-Wheeler Slots:");
        fourWheelerField = new JTextField();

        inputPanel.add(twoWheelerLabel);
        inputPanel.add(twoWheelerField);
        inputPanel.add(fourWheelerLabel);
        inputPanel.add(fourWheelerField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String twoWheelerSlots = twoWheelerField.getText();
                String fourWheelerSlots = fourWheelerField.getText();
                ParkingLot parkingLot1 = ParkingLot.getParkingLot();


                // Validate input
                try {
                    int twoWheelerCount = Integer.parseInt(twoWheelerSlots);
                    int fourWheelerCount = Integer.parseInt(fourWheelerSlots);
                    parkingLot1.initializeParkingSlots(twoWheelerCount, fourWheelerCount);
                    JOptionPane.showMessageDialog(frame, "Two-wheeler slots: " + twoWheelerCount +
                            "\nFour-wheeler slots: " + fourWheelerCount);
                    createAdminHomepage();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for slots.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    private static void createAdminHomepage() {
        JFrame frame = new JFrame("Admin Homepage");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        frame.add(welcomeLabel, BorderLayout.NORTH);

        // Center Panel with Buttons
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        centerPanel.setBackground(Color.LIGHT_GRAY); // Set background color

        // Create Buttons
        JButton availableParkingButton = new JButton("Dashboard");
        JButton parkingEntryButton = new JButton("Vehicle Entry");
        parkingEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openVehicleEntryPage();
            }
        });
        JButton inVehiclesListButton = new JButton("In Vehicles List");
        inVehiclesListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayInVehiclesList();
            }
        });

        JButton removeVehicleButton = new JButton("Remove Vehicle");
        removeVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRemoveVehiclePage();
            }
        });
        JButton outVehicleListButton = new JButton("Out Vehicle List");
        outVehicleListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOutVehicleListPage();
            }
        });

        // Set Button Styles
        JButton[] buttons = {
                availableParkingButton, parkingEntryButton, inVehiclesListButton, removeVehicleButton, outVehicleListButton
        };
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(200, 50)); // Set button size
            button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
        }


        centerPanel.add(availableParkingButton);
        centerPanel.add(parkingEntryButton);
        centerPanel.add(inVehiclesListButton);
        centerPanel.add(removeVehicleButton);
        centerPanel.add(outVehicleListButton);

        frame.add(centerPanel, BorderLayout.CENTER);


        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center admin homepage on screen

        availableParkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParkingLot parkingLot1 = ParkingLot.getParkingLot();
                JFrame frame = new JFrame("Parking space details");
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new GridLayout(4, 4));

                JButton b1 = new JButton("Total number of Two Wheelers slot:" + twoWheelerField.getText());
                b1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                JButton b2 = new JButton("Total number of Four wheelers slot:" + fourWheelerField.getText());
                b2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                JButton b3 = new JButton("Number of parked two wheelers vehicle:" + ParkingLot.getParkingLot().twoWheelersParkedCounter);
                b3.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                JButton b4 = new JButton("Number of parked four wheelers vehicle:" + parkingLot1.fourWheelersParkedCounter);
                b4.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                JButton b5 = new JButton("Number of available two wheelers slot:" + (Integer.parseInt(twoWheelerField.getText()) - (ParkingLot.getParkingLot().twoWheelersParkedCounter)));
                b5.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                JButton b6 = new JButton("Number of available four wheelers slot:" + (Integer.parseInt(fourWheelerField.getText()) - (ParkingLot.getParkingLot().fourWheelersParkedCounter)));
                b6.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                JButton b7=new JButton("Total revenue from Two Wheelers Vehicles:"+ParkingLot.getParkingLot().twCost);
                JButton b8=new JButton("Total revenue from Four Wheelers Vehicles:"+ParkingLot.getParkingLot().fwCost);
                frame.add(b1);
                frame.add(b2);
                frame.add(b3);
                frame.add(b4);
                frame.add(b5);
                frame.add(b6);
                frame.add(b7);
                frame.add(b8);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
    public static void openVehicleEntryPage(){

        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        JFrame frame = new JFrame("Vehicle Entry Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout(10, 10));
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create the labels and text fields
        JLabel plateNumberLabel = new JLabel("Plate Number:");
        JTextField plateNumberField = new JTextField(20);

        JLabel vehicleSizeLabel = new JLabel("Vehicle Size:");
        JComboBox<VehicleSize> vehicleSizeComboBox = new JComboBox<>(VehicleSize.values());

        JLabel colorLabel = new JLabel("Vehicle's Color:");
        JTextField colorField = new JTextField(20);

        JLabel contactNumberLabel = new JLabel("Owner's Contact No:");
        JTextField contactNumberField = new JTextField(20);


        JButton parkButton = new JButton("Park");


        parkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String plateNumber = plateNumberField.getText();
                    VehicleSize vehicleSize = (VehicleSize) vehicleSizeComboBox.getSelectedItem();
                    String color = colorField.getText();
                    String contactNumber = contactNumberField.getText();

                    if (plateNumber.isEmpty() || vehicleSize == null || color.isEmpty() || contactNumber.isEmpty()) {
                        throw new IllegalArgumentException("All fields must be filled out.");
                    }

                    if (!contactNumber.matches("\\d{11}")) {
                        throw new IllegalArgumentException("Contact number must be a 11-digit number.");
                    }


                    Vehicle vehicle=new Vehicle(plateNumber,color,vehicleSize,contactNumber);

                    Ticket ticket = ParkingLot.getParkingLot().park(vehicle);
                    tickets.add(ticket);

                    saveTicket(ticket,"InVehiclesList.txt");



                    JDialog dialog = new JDialog(frame, "Vehicle Parked", true);
                    dialog.setLayout(new BorderLayout(10, 10));
                    dialog.setSize(300, 150);

                    JLabel messageLabel = new JLabel("Vehicle is parked.", JLabel.CENTER);
                    JButton getTicketButton = new JButton("Get Ticket");
                    getTicketButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(frame,
                                    ticket.toString(),
                                    "Vehicle Ticket",
                                    JOptionPane.INFORMATION_MESSAGE);
                            dialog.dispose();
                            frame.dispose();
                        }

                    });

                    dialog.add(messageLabel, BorderLayout.CENTER);
                    dialog.add(getTicketButton, BorderLayout.SOUTH);

                    dialog.setLocationRelativeTo(frame);
                    dialog.setVisible(true);


                } catch (IllegalArgumentException ex) {
                    // Display error message
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (ParkingFullException pe){
                    JOptionPane.showMessageDialog(frame,pe.getMessage());
                    frame.dispose();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(plateNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(plateNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(vehicleSizeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(vehicleSizeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(colorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(colorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(contactNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(contactNumberField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(parkButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set frame visibility
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    public static void displayInVehiclesList(){
        JFrame listFrame = new JFrame("IN-Vehicles List");
        listFrame.setSize(400, 300);
        listFrame.setLocationRelativeTo(null);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder message = new StringBuilder("IN-Vehicles List:\n");

        // Load and display the list of parked vehicles
        List<Ticket> tickets = loadTickets("InVehiclesList.txt");

        for (Ticket ticket : tickets) {
            message.append(ticket.toString()).append("\n\n");
        }
        textArea.setText(message.toString());

        listFrame.add(new JScrollPane(textArea));
        listFrame.setVisible(true);
    }

    /*public static Ticket parkVehicle(String plateNumber, VehicleSize vehicleSize, String color, String contactNumber,String date) throws ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();


        return new Ticket();
    }*/

    // Method to save a ticket to the file
    public static void saveTicket(Ticket ticket, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(ticket.toString());
            writer.println("----------");  // separator between tickets
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Ticket> loadTickets(String filename) {
        List<Ticket> tickets=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder ticketBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {

                ticketBuilder.append(line).append("\n");
            }
            //System.out.println("d   "+ticketBuilder);
            if (!ticketBuilder.isEmpty()) {
                System.out.println("abcd");
                tickets = parseTicket(ticketBuilder.toString());
            }



        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public static List<Ticket> parseTicket(String ticketString) {
        String[] lines = ticketString.split("\n");

        List<Ticket> tickets = new ArrayList<>();

        for(int i=0 ; i< lines.length ; i+=6) {
            int slotNumber = Integer.parseInt(lines[i].split(":")[1]);
            String plateNumber = lines[i+1].split(":")[1];
            String color = lines[i+2].split(":")[1];
            String d = lines[i+3].split(":")[1];
            VehicleSize vehicleSize = VehicleSize.valueOf(lines[i+4].split(":")[1]);


            Ticket t = new Ticket(slotNumber, plateNumber, color, d, vehicleSize);
            tickets.add(t);


        }


        return tickets;


    }

    public static void openRemoveVehiclePage() {
        JFrame frame = new JFrame("Vehicle Removal");
        frame.setSize(400, 170);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3, 3));
        JLabel label=new JLabel("Enter Vehicle Number:");
        JTextField vehicleField=new JTextField();
        JLabel label1=new JLabel("Enter Vehicle Size:");
        JComboBox<VehicleSize> vehicleSizeComboBox = new JComboBox<>(VehicleSize.values());

        JPanel gapPanel=new JPanel();
        JPanel buttonPanel=new JPanel();
        JButton removeButton=new JButton("REMOVE");
        buttonPanel.add(removeButton);

        inputPanel.add(label);
        inputPanel.add(vehicleField);
        inputPanel.add(label1);
        inputPanel.add(vehicleSizeComboBox);


        // Initially hide the input panel;
        frame.add(inputPanel);
        frame.add(gapPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);

        // Action listener for the "Remove" button

        // Action listener for the "Submit" button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    if (vehicleField == null || vehicleSizeComboBox.getSelectedItem()==null) {
                        throw new IllegalArgumentException("Vehicle Number must be submitted");
                    }
                    String vehicleNumber = vehicleField.getText();
                    VehicleSize vehicleSize = (VehicleSize) vehicleSizeComboBox.getSelectedItem();


                    Receipt receipt=ParkingLot.getParkingLot().unPark(vehicleNumber,vehicleSize);
                    JDialog dialog = new JDialog(frame, "Vehicle Removed", true);
                    dialog.dispose();
                    frame.dispose();
                    JDialog rdialog = new JDialog(frame, "Vehicle Removed", true);
                    rdialog.setLayout(new BorderLayout(10, 10));
                    rdialog.setSize(300, 150);

                    JLabel messageLabel = new JLabel("Vehicle is removed.", JLabel.CENTER);
                    JButton getReceiptButton = new JButton("Get Receipt");
                    getReceiptButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(frame,
                                    receipt.toString(),
                                    "Vehicle Receipt",
                                    JOptionPane.INFORMATION_MESSAGE);
                            rdialog.dispose();
                            frame.dispose();
                        }

                    });

                    rdialog.add(messageLabel, BorderLayout.CENTER);
                    rdialog.add(getReceiptButton, BorderLayout.SOUTH);

                    rdialog.setLocationRelativeTo(frame);
                    rdialog.setVisible(true);

                } catch (InvalidVehicleNumberException ex) {
                    JOptionPane.showMessageDialog(frame,ex.getMessage());

                } catch (ParseException | FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        // Set the frame to be visible
        frame.setVisible(true);
    }
    public static void openOutVehicleListPage(){
        JFrame listFrame = new JFrame("Out-Vehicles List");
        listFrame.setSize(400, 300);
        listFrame.setLocationRelativeTo(null);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder message = new StringBuilder("Out-Vehicles List:\n");

        // Load and display the list of parked vehicles
        List<Ticket> tickets = loadTickets("OutVehiclesList.txt");

        for (Ticket ticket : tickets) {
            message.append(ticket.toString()).append("\n\n");
        }
        textArea.setText(message.toString());

        listFrame.add(new JScrollPane(textArea));
        listFrame.setVisible(true);
    }
}
