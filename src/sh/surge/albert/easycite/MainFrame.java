package sh.surge.albert.easycite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame(String title){
        super(title);

        // Set layout manager
        setLayout(new BorderLayout());

        // Create Swing component
        JTextField authorFirstName = new JTextField(32);
        JTextField authorMiddleInitial = new JTextField(2);
        JTextField authorLastName = new JTextField(32);
        JTextField sourceTitle = new JTextField(2);
        JTextField
        JButton button = new JButton("Click me");

        // Add Swing components to content pane
        Container c = getContentPane();
        c.add(textArea, BorderLayout.CENTER);
        c.add(button, BorderLayout.SOUTH);

        // Add behavior
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("Fuck you");
            }
        });
    }

}
