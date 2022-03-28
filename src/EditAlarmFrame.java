import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class EditAlarmFrame extends JFrame{

    private JLabel myLabel;
    private JPanel myPanel;
    private JDateChooser myDate;
    private JButton setDateButton;
    private String date;
    private JPanel AlarmPanel;
    public JComboBox<String> comboBoxHour;
    public JComboBox<String> comboBoxMin;
    private JTextField textFieldVol;
    private JComboBox<String> comboBoxTemp;
    private JButton saveButton;
    private JPanel datePanel;
    private int sessionID;

    public EditAlarmFrame(String title){
        super(title);
        setCalendarUI();
        this.setContentPane(myPanel);

        setDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.format(myDate.getDate());
                myLabel.setText(date);
            }
        });

    }

    public JComboBox<String> getComboBoxTemp() {
        return comboBoxTemp;
    }

    public JPanel getMyPanel() {
        return myPanel;
    }

    public JComboBox<String> getComboBoxHour() {
        return comboBoxHour;
    }

    public JComboBox<String> getComboBoxMin() {
        return comboBoxMin;
    }

    public JTextField getTextFieldVol() {
        return textFieldVol;
    }

    public void setCalendarUI() {
        myPanel = new JPanel();
        myPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

        AlarmPanel = new JPanel();
        AlarmPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));

        myPanel.add(AlarmPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        AlarmPanel.setBorder(BorderFactory.createTitledBorder(null, "When and how do I want my coffee?", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

        myDate = new JDateChooser();
        setDateButton = new JButton();
        myDate.setPreferredSize(new Dimension(200,25));

        datePanel = new JPanel();
        datePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        datePanel.add(myDate, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        AlarmPanel.add(datePanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));


        comboBoxHour = new JComboBox<>();
        final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<>();
        for (String s : Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24")) {
            defaultComboBoxModel1.addElement(s);
        }

        comboBoxHour.setModel(defaultComboBoxModel1);
        AlarmPanel.add(comboBoxHour, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Minute");
        AlarmPanel.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Volume (cl)");
        AlarmPanel.add(label2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldVol = new JTextField();
        AlarmPanel.add(textFieldVol, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Temperature (°C)");
        AlarmPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxTemp = new JComboBox<>();
        final DefaultComboBoxModel<String> defaultComboBoxModel2 = new DefaultComboBoxModel<>();
        defaultComboBoxModel2.addElement("COLD");
        defaultComboBoxModel2.addElement("MEDIUM");
        defaultComboBoxModel2.addElement("HOT");
        comboBoxTemp.setModel(defaultComboBoxModel2);
        AlarmPanel.add(comboBoxTemp, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        AlarmPanel.add(saveButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Hour");
        AlarmPanel.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Date");
        AlarmPanel.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        comboBoxMin = new JComboBox<>();
        final DefaultComboBoxModel<String> defaultComboBoxModel3 = new DefaultComboBoxModel<>();
        for (String s : Arrays.asList("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60")) {
            defaultComboBoxModel3.addElement(s);
        }
        comboBoxMin.setModel(defaultComboBoxModel3);
        AlarmPanel.add(comboBoxMin, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

    }

    public JLabel getMyLabel() {
        return myLabel;
    }

    public void setLabel(String newText)
    {
        myLabel.setText(newText);
    }

    public String getDate() {
        return date;
    }

}
