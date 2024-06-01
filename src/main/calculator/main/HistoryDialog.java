package main;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HistoryDialog extends JDialog {
    public HistoryDialog(JFrame parent, ArrayList<Pair> pairs) {
        super(parent, "Історія", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Create table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Функція");
        model.addColumn("Похідна");

        // Populate table model
        for (Pair pair : pairs) {
            model.addRow(new Object[]{pair.input, pair.output});
        }

        // Create table with the model
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Create close button
        JButton closeButton = new JButton("Закрити");
        closeButton.addActionListener(e -> dispose());

        JButton clearButton = new JButton("Очистити");
        clearButton.addActionListener(e -> {
            model.setRowCount(0);
            pairs.clear();
        });

        // Add close button to the dialog
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Make dialog visible
        setVisible(true);
    }
}
