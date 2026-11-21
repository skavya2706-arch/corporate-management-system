package gui;


import com.toedter.calendar.JDateChooser;
import dao.EmployeeDAO;
import model.LeaveRequest;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.util.Calendar;

public class LeaveApplicationForm extends JFrame {

    public LeaveApplicationForm(int empId) {

        setTitle("Leave Application");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        EmployeeDAO employeeDAO = new EmployeeDAO();

        // ================= FROM DATE =================

        JLabel lblFromDate = new JLabel("From Date");
        lblFromDate.setBounds(40, 120, 100, 25);
        add(lblFromDate);

        JDateChooser fromDateChooser = new JDateChooser();
        fromDateChooser.setBounds(170, 120, 200, 25);
        add(fromDateChooser);

        // ================= TO DATE =================

        JLabel lblToDate = new JLabel("To Date");
        lblToDate.setBounds(40, 170, 100, 25);
        add(lblToDate);

        JDateChooser toDateChooser = new JDateChooser();
        toDateChooser.setBounds(170, 170, 200, 25);
        add(toDateChooser);

        // ================= LEAVE DAYS =================

        JLabel lblLeaveDays = new JLabel("Leave Days");
        lblLeaveDays.setBounds(40, 220, 100, 25);
        add(lblLeaveDays);

        JTextField txtLeaveDays = new JTextField();
        txtLeaveDays.setBounds(170, 220, 80, 25);
        txtLeaveDays.setEditable(false);
        add(txtLeaveDays);

        // ================= REASON =================

        JLabel lblReason = new JLabel("Reason");
        lblReason.setBounds(40, 270, 100, 25);
        add(lblReason);

        JTextArea txtReason = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(txtReason);
        scrollPane.setBounds(170, 270, 250, 100);
        add(scrollPane);

        // ================= BUTTONS =================

        JButton btnApply = new JButton("Apply");
        btnApply.setBounds(120, 400, 100, 35);
        add(btnApply);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(260, 400, 100, 35);
        add(btnCancel);

        // ================= CALCULATE WORKING DAYS =================

        PropertyChangeListener calculateLeaveDays = evt -> {

            if (fromDateChooser.getDate() != null &&
                    toDateChooser.getDate() != null) {

                Calendar start = Calendar.getInstance();
                start.setTime(fromDateChooser.getDate());

                Calendar end = Calendar.getInstance();
                end.setTime(toDateChooser.getDate());

                int leaveDays = 0;

                while (!start.after(end)) {

                    int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);

                    // Count only Monday to Friday
                    if (dayOfWeek != Calendar.SATURDAY &&
                            dayOfWeek != Calendar.SUNDAY) {

                        leaveDays++;
                    }

                    start.add(Calendar.DAY_OF_MONTH, 1);
                }

                txtLeaveDays.setText(String.valueOf(leaveDays));

            } else {

                txtLeaveDays.setText("");

            }
        };

        fromDateChooser.addPropertyChangeListener(
                "date", calculateLeaveDays);

        toDateChooser.addPropertyChangeListener(
                "date", calculateLeaveDays);

        // ================= APPLY BUTTON =================

        btnApply.addActionListener(e -> {

            // ---------- FROM DATE VALIDATION ----------

            if (fromDateChooser.getDate() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select From Date."
                );

                return;
            }

            // ---------- TO DATE VALIDATION ----------

            if (toDateChooser.getDate() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select To Date."
                );

                return;
            }

            // Convert dates to SQL Date

            Date currentDate = new Date(System.currentTimeMillis());

            Date fromDate =
                    new Date(fromDateChooser.getDate().getTime());

            Date toDate =
                    new Date(toDateChooser.getDate().getTime());

            // ---------- FROM DATE CANNOT BE PAST ----------

            if (fromDate.before(currentDate)) {

                JOptionPane.showMessageDialog(
                        this,
                        "From Date must be today or a future date."
                );

                return;
            }

            // ---------- TO DATE CANNOT BE BEFORE FROM DATE ----------

            if (toDate.before(fromDate)) {

                JOptionPane.showMessageDialog(
                        this,
                        "To Date cannot be before From Date."
                );

                return;
            }

            // ---------- REASON VALIDATION ----------

            String reason = txtReason.getText().trim();

            if (reason.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the reason."
                );

                return;
            }

            // ---------- CALCULATE WORKING DAYS ----------

            Calendar start = Calendar.getInstance();
            start.setTime(fromDate);

            Calendar end = Calendar.getInstance();
            end.setTime(toDate);

            int leaveDays = 0;

            while (!start.after(end)) {

                int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);

                // Saturday and Sunday are not counted
                if (dayOfWeek != Calendar.SATURDAY &&
                        dayOfWeek != Calendar.SUNDAY) {

                    leaveDays++;
                }

                start.add(Calendar.DAY_OF_MONTH, 1);
            }

            txtLeaveDays.setText(String.valueOf(leaveDays));

            // ---------- WEEKEND ONLY VALIDATION ----------

            if (leaveDays == 0) {

                System.out.println(
                        "\n========== LEAVE DETAILS =========="
                );

                System.out.println("Leaves Asked      : 0");
                System.out.println("Total Leaves      : 20");
                System.out.println("Remaining Leaves  : 20");
                System.out.println("Leave Status      : REJECTED");

                System.out.println(
                        "==================================="
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Leave rejected!\n" +
                                "The selected dates contain only weekends."
                );

                return;
            }

            // ================= LEAVE BALANCE =================

            int totalLeaves = 20;
            int leavesTaken = employeeDAO.getApprovedLeaveDays(empId);
            int remainingBeforeLeave = totalLeaves - leavesTaken;
            int remainingLeaves = remainingBeforeLeave - leaveDays;
            // Make sure the value cannot become negative
            if (leavesTaken < 0) {
                leavesTaken = 0;
            }



            if (remainingBeforeLeave < 0) {
                remainingBeforeLeave = 0;
            }

            // ---------- ALL LEAVES EXHAUSTED ----------

            if (remainingBeforeLeave == 0) {

                System.out.println(
                        "\n========== LEAVE DETAILS =========="
                );

                System.out.println(
                        "Leaves Asked      : " + leaveDays
                );

                System.out.println(
                        "Total Leaves      : " + totalLeaves
                );

                System.out.println(
                        "Remaining Leaves  : 0"
                );

                System.out.println(
                        "Leave Status      : REJECTED"
                );

                System.out.println(
                        "==================================="
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Leave Rejected!\n" +
                                "You have exhausted all 20 leaves."
                );

                return;
            }

            // ---------- REQUEST GREATER THAN REMAINING ----------

            if (leaveDays > remainingBeforeLeave) {

                System.out.println(
                        "\n========== LEAVE DETAILS =========="
                );

                System.out.println(
                        "Leaves Asked      : " + leaveDays
                );

                System.out.println(
                        "Total Leaves      : " + totalLeaves
                );

                System.out.println(
                        "Remaining Leaves  : " + remainingBeforeLeave
                );

                System.out.println(
                        "Leave Status      : REJECTED"
                );

                System.out.println(
                        "==================================="
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Leave Rejected!\n" +
                                "Leaves Remaining: "
                                + remainingBeforeLeave +
                                "\nLeaves Requested: "
                                + leaveDays
                );

                return;
            }

            // ================= CONSOLE OUTPUT =================

            System.out.println("\n========== LEAVE DETAILS ==========");
            System.out.println("Leaves Asked      : " + leaveDays);
            System.out.println("Total Leaves      : " + totalLeaves);
            System.out.println("Leaves Taken      : " + (leavesTaken + leaveDays));
            System.out.println("Remaining Leaves  : " + remainingLeaves);
            System.out.println("Leave Status      : PENDING");
            System.out.println("===================================");

            // ================= CREATE LEAVE REQUEST =================

            LeaveRequest leaveRequest =
                    new LeaveRequest();

            leaveRequest.setEmpId(empId);
            leaveRequest.setFromDate(fromDate);
            leaveRequest.setToDate(toDate);
            leaveRequest.setLeaveDays(leaveDays);
            leaveRequest.setReason(reason);

            // ================= APPLY LEAVE =================

            boolean result =
                    employeeDAO.applyLeave(leaveRequest);

            if (result) {

                JOptionPane.showMessageDialog(
                        this,
                        "Leave Applied Successfully."
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Apply Leave."
                );
            }
        });

        // ================= CANCEL =================

        btnCancel.addActionListener(e -> dispose());

        setVisible(true);
    }
}
