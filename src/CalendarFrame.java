import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class CalendarFrame extends JFrame{

    private JLabel myLabel;
    private JPanel myPanel;
    private JDateChooser myDate;
    private JButton setDateButton;
    private String date;

    public CalendarFrame(String title){
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

    public void setCalendarUI() {
        myPanel = new JPanel();
        myLabel = new JLabel();
        myDate = new JDateChooser();
        setDateButton = new JButton();
        myLabel.setText("Select date");
        setDateButton.setText("Set Date");
        myPanel.add(myLabel);
        myPanel.add(myDate);
        myPanel.add(setDateButton);
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

/*    public static void main(String[] args) {
        CalendarFrame ui= new CalendarFrame("Set date");
        ui.setVisible(true);
        ui.pack();
    }*/
}
