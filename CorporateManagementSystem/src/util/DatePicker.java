package util;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class DatePicker {

    public static Date pickDate(String title) {

        final Date[] selectedDate = new Date[1];

        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new FlowLayout());

        JDateChooser chooser = new JDateChooser();
        chooser.setDate(new java.util.Date());

        JButton button = new JButton("OK");

        dialog.add(chooser);
        dialog.add(button);

        button.addActionListener(e -> {

            java.util.Date utilDate = chooser.getDate();

            if (utilDate != null) {

                selectedDate[0] = new Date(utilDate.getTime());

            }

            dialog.dispose();

        });

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        // Give focus back to the console
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {
        }

        return selectedDate[0];
    }
}