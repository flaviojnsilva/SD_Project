package froggergame.GUI;

import froggergame.server.GameFactoryImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class LoginFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel emailLabel = new JLabel("EMAIL");

    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField emailTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JButton registerButton = new JButton("REGISTER");
    JCheckBox showPassword = new JCheckBox("Show Password");


    public LoginFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        emailLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        emailTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(125, 330, 100, 30);
        registerButton.setBounds(200, 300, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(emailLabel);
        container.add(passwordLabel);
        container.add(emailTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(registerButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        registerButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = emailTextField.getText();
            pwdText = passwordField.getSelectedText();
            if (userText.equalsIgnoreCase("mehtab") && pwdText.equalsIgnoreCase("12345")) {
                JOptionPane.showMessageDialog(this, "froggergame.GUI.Login Successful");
            } else if (!userText.equalsIgnoreCase("mehtab")) {

                JOptionPane.showMessageDialog(this, "User Don't Exist!\nPlease register first.");
            }
            {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }

        }
        if (e.getSource() == resetButton) {
            emailTextField.setText("");
            passwordField.setText("");
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
        if (e.getSource() == registerButton) {
            String userText;
            userText = emailTextField.getText();
            if (userText.equalsIgnoreCase("mehtab")) {
                JOptionPane.showMessageDialog(this, "User already exist!");
            } else {

                GameFactoryImpl gameFactoryImpl = null;
                try {
                    gameFactoryImpl = new GameFactoryImpl();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    String pwdText;
                    userText = emailTextField.getText();
                    pwdText = passwordField.getSelectedText();

                    gameFactoryImpl.register(userText, pwdText);

                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
