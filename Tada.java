/**
 * Tada
 */

import java.io.*;
import javax.swing.*;

public class Tada {

    public static void main(String[] args) {
        JFrame frame
            = new JFrame(); // creating instance of JFrame
 
        JButton button = new JButton(
            " GFG WebSite Click"); // creating instance of
                                   // JButton
        button.setBounds(
            150, 200, 220,
            50); // x axis, y axis, width, height
 
        frame.add(button); // adding button in JFrame
 
        frame.setSize(500, 600); // 400 width and 500 height
        frame.setLayout(null); // using no layout managers
        frame.setVisible(true); // making the frame visible
    }
}