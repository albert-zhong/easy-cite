package sh.surge.albert.easycite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame
                 implements ActionListener{

    private JTextArea citation;

    private ArrayList<JTextField> fields = new ArrayList<>();

    private String[] months = {"", "Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
    JComboBox month = new JComboBox(months);

    public GUI() {
        super("easy-cite");
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        createLabelAndField(panel, new String[]{"First name", "MI", "Last name"}, new int[]{10,2,10}); // 0, 1, 2
        createLabelAndField(panel, "Article title", 40); // 3
        createLabelAndField(panel, "Website title", 40); // 4
        createLabelAndField(panel, "Sponsor", 40); // 5
        createLabelAndField(panel, "URL", 40); // 6

        // DATE PANEL
        JPanel datePanel = new JPanel();

        JLabel monthLabel = new JLabel("Month:");
        datePanel.add(monthLabel);
        datePanel.add(month);

        JLabel dayLabel = new JLabel("Day:");
        datePanel.add(dayLabel);
        fields.add(new JTextField(4)); // DAY is 7
        datePanel.add(fields.get(fields.size() - 1));

        JLabel yearLabel = new JLabel("Year:");
        datePanel.add(yearLabel);
        fields.add(new JTextField(4)); // YEAR is 8
        datePanel.add(fields.get(fields.size() - 1));
        panel.add(datePanel);

        JButton createCitation = new JButton("Create citation");
        panel.add(createCitation);
        createCitation.addActionListener(this);

        citation = new JTextArea("Citation here", 20, 20);
        citation.setEditable(false);
        panel.add(citation);

        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String finalCitation = "";

        if (fields.get(3).getText().equals("") || fields.get(6).getText().equals("") || fields.get(4).getText().equals("")) {
            citation.setText("Requires at least the article title, the website title, and the url");
        } else {

            // Author name
            // IF First, MI, Last are available
            if (!fields.get(0).getText().equals("") && !fields.get(2).getText().equals("") && !fields.get(1).getText().equals("")) {
                finalCitation += fields.get(2).getText() + ", " + fields.get(0).getText() + " " + fields.get(1).getText() + ". ";
            } else if (!fields.get(0).getText().equals("") && !fields.get(2).getText().equals("")) { // ONLY First Last available
                finalCitation += fields.get(2).getText() + ", " + fields.get(0).getText() + ". ";
            }

            // Article title

            finalCitation += "\"" + fields.get(3).getText() + ".\" ";

            // Website title
            finalCitation += fields.get(4).getText() + ", ";

            // sponsor
            if (!fields.get(5).getText().equals("")) {
                finalCitation += fields.get(5).getText() + ", ";
            }

            // ONLY year AND month AND day available
            if (!fields.get(8).getText().equals("") || month.getSelectedIndex() != 0 || !fields.get(7).getText().equals("")) {
                finalCitation += fields.get(7).getText() + " " + month.getSelectedItem() + " " + fields.get(8).getText() + ", ";
            } else if (!fields.get(8).getText().equals("") || month.getSelectedIndex() != 0) { // ONLY year AND month
                finalCitation += month.getSelectedItem() + " " + fields.get(8).getText() + ", ";
            } else if (!fields.get(8).getText().equals("")) { // ONLY year
                finalCitation += fields.get(8).getText() + ", ";
            }

            // URL
            finalCitation += fields.get(6).getText() + ".";

            citation.setText(finalCitation);
        }
    }


    private void createLabelAndField(Container container, String text, int column) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(text + ":");

        fields.add(new JTextField(column));

        panel.add(label);
        panel.add(fields.get(fields.size() - 1));
        container.add(panel);
    }

    private void createLabelAndField(Container container, String[] texts, int[] columns) {
        JPanel panel = new JPanel();

        for (int i=0; i<texts.length; i++) {
            JLabel label = new JLabel(texts[i] + ":");
            panel.add(label);

            fields.add(new JTextField(columns[i]));
            panel.add(fields.get(fields.size() - 1));
        }
        container.add(panel);
    }

}

