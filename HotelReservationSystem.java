package codeAlpha;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class HotelSystem {
    private static Map<String, String> userMap = new HashMap<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();

    public HotelSystem() {
        // Adding some rooms for demonstration
        for (int i = 1; i <= 5; i++) {
            rooms.add(new Room(100 + i, "Single", 100.0, true));
            rooms.add(new Room(200 + i, "Double", 150.0, true));
            rooms.add(new Room(300 + i, "Suite", 250.0, true));
        }

        // Adding default users
        userMap.put("admin", "admin123");
    }

    public boolean authenticateUser(String username, String password) {
        return userMap.containsKey(username) && userMap.get(username).equals(password);
    }

    public boolean registerUser(String username, String password) {
        System.out.println(username);
        if (userMap.containsKey(username)) {
            return false;
        }
        userMap.put(username, password);
        return true;
    }
    // returning rooms hence rooms is of private
    public static List<Room> getRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
                availableRooms.add(room);
        }
        return availableRooms;
    }
    //book room method
    public boolean bookRoom(String customerName, int roomNo, String checkInTime, String checkOutTime,
            double amountPaid) {
        for (Room room : rooms) {
            if (room.getRoomNo() == roomNo && room.isAvailable()) {
                room.setAvailable(false);
                customers.add(new Customer(customerName, roomNo, checkInTime, checkOutTime, amountPaid));
                return true;
            }
        }
        return false;
    }

    public List<Customer> getCurrentCustomers() {
        return customers;
    }
}

class HotelReservationSystem extends Frame implements ActionListener {
    private HotelSystem hotelSystem = new HotelSystem();
    private CardLayout cardLayout = new CardLayout();

    // Components for login
    private TextField loginUsernameField, loginPasswordField;
    private Label loginStatusLabel;

    // Components for registration
    private TextField registerUsernameField, registerPasswordField;
    private Label registerStatusLabel;

    // Components for booking
    private TextField customerNameField, roomNoField, checkInTimeField, checkOutTimeField, amountPaidField;
    private Label bookingStatusLabel;

    // Components for displaying customers
    private TextArea customersTextArea;

    public HotelReservationSystem() {
        setTitle("Hotel Reservation System");
        setSize(600, 400); // Increased window size
        setLayout(cardLayout);

        // Login Panel
        Panel loginPanel = new Panel(new GridLayout(4, 2));
        loginUsernameField = new TextField();
        loginPasswordField = new TextField();
        loginPasswordField.setEchoChar('*');
        loginStatusLabel = new Label();
        Button loginButton = new Button("Login");
        Button goToRegisterButton = new Button("Register");
        loginPanel.add(new Label("Username:"));
        loginPanel.add(loginUsernameField);
        loginPanel.add(new Label("Password:"));
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginButton);
        loginPanel.add(goToRegisterButton);
        loginPanel.add(loginStatusLabel);
        loginButton.addActionListener(this);
        goToRegisterButton.addActionListener(this);
        add("Login", loginPanel);

        // Register Panel
        Panel registerPanel = new Panel(new GridLayout(4, 2));
        registerUsernameField = new TextField();
        registerPasswordField = new TextField();
        registerPasswordField.setEchoChar('*');
        registerStatusLabel = new Label();
        Button registerButton = new Button("Register");
        Button goToLoginButton = new Button("Back to Login");
        registerPanel.add(new Label("New Username:"));
        registerPanel.add(registerUsernameField);
        registerPanel.add(new Label("New Password:"));
        registerPanel.add(registerPasswordField);
        registerPanel.add(registerButton);
        registerPanel.add(goToLoginButton);
        registerPanel.add(registerStatusLabel);
        registerButton.addActionListener(this);
        goToLoginButton.addActionListener(this);
        add("Register", registerPanel);

        // Main Menu Panel
        Panel mainMenuPanel = new Panel(new GridLayout(4, 1));
        Button searchRoomsButton = new Button("Search Rooms");
        Button bookRoomButton = new Button("Book Room");
        Button showCustomersButton = new Button("Show Customers");
        Button logoutButton = new Button("Logout");
        mainMenuPanel.add(searchRoomsButton);
        mainMenuPanel.add(bookRoomButton);
        mainMenuPanel.add(showCustomersButton);
        mainMenuPanel.add(logoutButton);
        searchRoomsButton.addActionListener(this);
        bookRoomButton.addActionListener(this);
        showCustomersButton.addActionListener(this);
        logoutButton.addActionListener(this);
        add("MainMenu", mainMenuPanel);

        // Book Room Panel
        Panel bookRoomPanel = new Panel(new GridLayout(5, 1));
        customerNameField = new TextField();
        roomNoField = new TextField();
        checkInTimeField = new TextField();
        checkOutTimeField = new TextField();
        amountPaidField = new TextField();
        bookingStatusLabel = new Label();
        Panel customerPanel = new Panel(new GridLayout(5, 2));
        customerPanel.add(new Label("Name:"));
        customerPanel.add(customerNameField);
        customerPanel.add(new Label("Room No.:"));
        customerPanel.add(roomNoField);
        customerPanel.add(new Label("Check-In Time:"));
        customerPanel.add(checkInTimeField);
        customerPanel.add(new Label("Check-Out Time:"));
        customerPanel.add(checkOutTimeField);
        customerPanel.add(new Label("Amount Paid:"));
        customerPanel.add(amountPaidField);
        bookRoomPanel.add(customerPanel);
        Button bookButton = new Button("Book");
        Button backToMainMenuButton = new Button("Back to Main Menu");
        bookRoomPanel.add(bookButton);
        bookRoomPanel.add(backToMainMenuButton);
        bookRoomPanel.add(bookingStatusLabel);
        bookButton.addActionListener(this);
        backToMainMenuButton.addActionListener(this);
        add("BookRoom", bookRoomPanel);

        // Show Customers Panel
        Panel showCustomersPanel = new Panel(new BorderLayout());
        customersTextArea = new TextArea();
        Button backFromShowCustomersButton = new Button("Back to Main Menu");
        showCustomersPanel.add(new JScrollPane(customersTextArea), BorderLayout.CENTER);
        showCustomersPanel.add(backFromShowCustomersButton, BorderLayout.SOUTH);
        backFromShowCustomersButton.addActionListener(this);
        add("ShowCustomers", showCustomersPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("Login")) {
            String username = loginUsernameField.getText();
            String password = loginPasswordField.getText();
            if (hotelSystem.authenticateUser(username, password)) {
                loginStatusLabel.setText("Login Successful");
                cardLayout.show(this, "MainMenu");
            } else {
                loginStatusLabel.setText("Invalid Credentials");
            }
        } else if (actionCommand.equals("Register")) {
            String username = loginUsernameField.getText();
            String password = loginPasswordField.getText();
            if (hotelSystem.registerUser(username, password)) {
                loginStatusLabel.setText("Registration Successful. Please login.");
                cardLayout.show(this, "Login");
            } else {
                registerStatusLabel.setText("Username already exists");
            }
        } else if (actionCommand.equals("Back to Login")) {
            cardLayout.show(this, "Login");
        } else if (actionCommand.equals("Search Rooms")) {
            StringBuilder roomList = new StringBuilder();
            List<Room> allrooms=HotelSystem.getRooms();
            for (Room room : allrooms) {
                roomList.append(room).append("\n");
            }
            JTextArea textArea = new JTextArea(roomList.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Available Rooms", JOptionPane.INFORMATION_MESSAGE);
        } else if (actionCommand.equals("Book Room")) {
            cardLayout.show(this, "BookRoom");
        } else if (actionCommand.equals("Book")) {
            String customerName = customerNameField.getText();
            int roomNo = Integer.parseInt(roomNoField.getText());
            String checkInTime = checkInTimeField.getText();
            String checkOutTime = checkOutTimeField.getText();
            double amountPaid = Double.parseDouble(amountPaidField.getText());
            if (hotelSystem.bookRoom(customerName, roomNo, checkInTime, checkOutTime, amountPaid)) {
                bookingStatusLabel.setText("Booking Successful");
            } else {
                bookingStatusLabel.setText("Room not available or invalid room number");
            }
        } else if (actionCommand.equals("Show Customers")) {
            List<Customer> currentCustomers = hotelSystem.getCurrentCustomers();
            StringBuilder customerList = new StringBuilder();
            for (Customer customer : currentCustomers) {
                customerList.append(customer).append("\n");
            }
            customersTextArea.setText(customerList.toString());
            cardLayout.show(this, "ShowCustomers");
        } else if (actionCommand.equals("Back to Main Menu")) {
            cardLayout.show(this, "MainMenu");
        } else if (actionCommand.equals("Logout")) {
            cardLayout.show(this, "Login");
        }
    }

    public static void main(String[] args) {
        new HotelReservationSystem();
    }
}
