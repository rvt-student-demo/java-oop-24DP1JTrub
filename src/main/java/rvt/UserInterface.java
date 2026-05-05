package rvt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UserInterface {

    private final TodoList todoList;
    private final DefaultListModel<String> listModel;

    public UserInterface(TodoList todoList) {
        this.todoList = todoList;
        this.listModel = new DefaultListModel<>();
    }

    public void start() {
        SwingUtilities.invokeLater(this::createAndShowGui);
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame("Todo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JTextField taskField = new JTextField(20);
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove Selected");

        JList<String> taskList = new JList<>(listModel);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskField);
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(removeButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String task = taskField.getText().trim();
            if (task.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter a task first.");
                return;
            }

            todoList.add(task);
            listModel.addElement(task);
            taskField.setText("");
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(frame, "Select a task to remove.");
                return;
            }

            todoList.remove(selectedIndex + 1);
            listModel.remove(selectedIndex);
        });

        frame.setVisible(true);
    }
}