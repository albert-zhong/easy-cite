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
    JComboBox<String> month = new JComboBox<>(months);

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

        for (JTextField field : fields) { // removes beginning and trailing whitespace in string
            field.setText(field.getText().trim());
        }

        String firstNameText = fields.get(0).getText();
        String miText = fields.get(1).getText();
        String lastNameText = fields.get(2).getText();
        String articleTitleText = fields.get(3).getText();
        String websiteTitleText = fields.get(4).getText();
        String sponsorText = fields.get(5).getText();
        String urlText = fields.get(6).getText();
        String dayText = fields.get(7).getText();
        String yearText = fields.get(8).getText();


        // Create the citation
        if (articleTitleText.equals("") || urlText.equals("") || websiteTitleText.equals("")) {
            citation.setText("Requires at least the article title, the website title, and the url");
        } else if (!isValidYear(yearText)) {
            citation.setText("Improper year");
        } else if(!isValidDay(dayText)) {
            citation.setText("Improper day");
        } else if(!miText.equals("") && !isMiddleInitialValid(miText)) { // if MI exists and is wrong
            citation.setText("Improper MI");
        } else {
            // simplifying day if first digit is 0 (e.g 01 becomes 1)
            if (dayText.charAt(0) == '0') {
                dayText = dayText.substring(1);
            }

            // capitalizing MI
            miText = miText.toUpperCase();

            // Author name
            // IF First, MI, Last are available
            if (!firstNameText.equals("") && !lastNameText.equals("") && !miText.equals("")) {
                finalCitation += lastNameText + ", " + firstNameText + " " + miText + ". ";
            } else if (!firstNameText.equals("") && !lastNameText.equals("")) { // ONLY First Last available
                finalCitation += lastNameText + ", " + firstNameText + ". ";
            }

            // Article title
            finalCitation += "\"" + articleTitleText + ".\" ";

            // Website title
            finalCitation += websiteTitleText + ", ";

            // sponsor
            if (!sponsorText.equals("")) {
                finalCitation += sponsorText + ", ";
            }

            // ONLY year AND month AND day available
            if (!yearText.equals("") || month.getSelectedIndex() != 0 || !dayText.equals("")) {
                finalCitation += dayText + " " + month.getSelectedItem() + " " + yearText + ", ";
            } else if (!yearText.equals("") || month.getSelectedIndex() != 0) { // ONLY year AND month
                finalCitation += month.getSelectedItem() + " " + yearText + ", ";
            } else if (!yearText.equals("")) { // ONLY year
                finalCitation += yearText + ", ";
            }

            // URL
            finalCitation += urlText + ".";

            citation.setText(finalCitation);
        }
    }

    private boolean isValidDay(String stringDay) {
        if (stringDay.length() != 1 && stringDay.length() != 2) { // Day must be 1 digit or 2 digits long
            return false;
        }

        char char1 = stringDay.charAt(0);
        char char2 = stringDay.charAt(1);

        if (!Character.isDigit(char1) || !Character.isDigit(char2)) { // both must be digits
            return false;
        }

        if (Character.getNumericValue(char1) == 0 && Character.getNumericValue(char2) == 0) { // can't both be 0
            return false;
        }

        if (Character.getNumericValue(char1) > 3) {
            return false; // Days must be 0, 1, 2, or 3
        }

        return true; // if it passes all tests, return true
    }

    private boolean isValidYear(String stringYear) {
        if (stringYear.length() > 4) { // Year cannot be greater than 4 chars long. Any programmers in the future
                                        // reading this will have to change it
            return false;
        }

        for (int i=0; i<stringYear.length(); i++) {
            if (!Character.isDigit(stringYear.charAt(i))) {  // if you run into any non-digits, it's not a valid year
                return false;
            }
        }
        return true; // if it passes all tests, return true
    }

    private boolean isMiddleInitialValid(String MI) {
        if (MI.length() != 1) {
            return false;
        }
        if (!Character.isLetter(MI.charAt(0))) {
            return false;
        }
        return true;
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
