package demo.tasks;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by wdalo on 29-9-2015.
 */
class Main {
    private JPanel mainPanel;
    private JButton openButton;
    private JButton saveButton;
    private JButton addTaskButton;
    private JButton removeTaskButton;
    private JTable tableTasks;
    private JButton editTaskButton;
    private JButton showGraphButton;
    private JButton newButton;
    private JButton saveAsButton;
    private JLabel tasksLabel;
    private final TaskTableModel taskTableModel;
    private final Main main;
    private File file;
    private final Tasks tasks;

    private Main() {

        tasks = new Tasks();
        main = this;

        taskTableModel = new TaskTableModel(tasks.getTasks());
        tableTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTasks.setModel(taskTableModel);

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddEditTask addEditTask = new AddEditTask(tasks.getTasks(), main, null);
                addEditTask.setTitle("Add Task");
                addEditTask.setSize(480, 340);
                addEditTask.setResizable(false);
                addEditTask.setVisible(true);
            }
        });
        removeTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    if (tableTasks.getSelectedRow() > -1) {
                        taskTableModel.removeTask(tableTasks.getSelectedRow());
                        UpdateTable();

                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Please select a task you want to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    ShowEmptyError();
                }
            }
        });
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    Graph graph = new Graph(tasks.getTasks());
                    graph.setTitle("Graph");
                    graph.setSize(480, 340);
                    graph.setResizable(false);
                    graph.setVisible(true);
                } else {
                    ShowEmptyError();
                }
            }
        });
        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    if (tableTasks.getSelectedRow() > -1) {
                        AddEditTask addEditTask = new AddEditTask(tasks.getTasks(), main, tableTasks.getSelectedRow());
                        addEditTask.setTitle("Edit Task");
                        addEditTask.setSize(480, 340);
                        addEditTask.setResizable(false);
                        addEditTask.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Please select a task you want to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    ShowEmptyError();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    if (file != null) {
                        tasks.createXML(String.valueOf(file));
                        tasksLabel.setText("Tasks (" + file + "):");
                    } else {
                        SaveAs();
                    }

                } else {
                    ShowEmptyError();
                }
            }
        });
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Show dialog and open the file.
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Open tasks");

                FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("XML Files", "XML");
                jFileChooser.setFileFilter(fileNameExtensionFilter);

                int userSelection = jFileChooser.showSaveDialog(mainPanel);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    if (jFileChooser.getSelectedFile() != null) {
                        tasks.readXML(jFileChooser.getSelectedFile());

                        file = jFileChooser.getSelectedFile();
                        tasksLabel.setText("Tasks (" + file + "):");

                        UpdateTable();
                    }
                }
            }
        });
        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    SaveAs();
                } else {
                    ShowEmptyError();
                }
            }
        });
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasks.getTasks().clear();
                file = null;
                tasksLabel.setText("Tasks:");

                UpdateTable();
            }
        });
    }

    /**
     * Display error message when there are no tasks.
     */
    private void ShowEmptyError() {
        JOptionPane.showMessageDialog(new JFrame(), "Please add one or more tasks.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show Save As dialog.
     */
    private void SaveAs() {

        // Show dialog and save the task to a file.
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Save tasks");

        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("XML Files", "XML");
        jFileChooser.setFileFilter(fileNameExtensionFilter);

        int userSelection = jFileChooser.showSaveDialog(mainPanel);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String fileName = String.valueOf(jFileChooser.getSelectedFile());

            if (fileName != null) {

                if (!fileName.endsWith(".xml"))
                    fileName = fileName + ".xml";

                tasks.createXML(fileName);

                file = new File(fileName);
                tasksLabel.setText("Tasks (" + file + "):");
            }
        }
    }

    /**
     * Update tasks in table.
     */
    public void UpdateTable() {
        taskTableModel.fireTableDataChanged();
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Tasks");
        frame.setContentPane(new Main().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(746, 340);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 8, new Insets(4, 4, 4, 4), -1, -1));
        openButton = new JButton();
        openButton.setText("Open...");
        mainPanel.add(openButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        mainPanel.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addTaskButton = new JButton();
        addTaskButton.setText("Add task");
        mainPanel.add(addTaskButton, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeTaskButton = new JButton();
        removeTaskButton.setText("Remove task");
        mainPanel.add(removeTaskButton, new com.intellij.uiDesigner.core.GridConstraints(2, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 8, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableTasks = new JTable();
        scrollPane1.setViewportView(tableTasks);
        editTaskButton = new JButton();
        editTaskButton.setText("Edit task");
        mainPanel.add(editTaskButton, new com.intellij.uiDesigner.core.GridConstraints(2, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showGraphButton = new JButton();
        showGraphButton.setText("Show graph");
        mainPanel.add(showGraphButton, new com.intellij.uiDesigner.core.GridConstraints(2, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newButton = new JButton();
        newButton.setText("New");
        mainPanel.add(newButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tasksLabel = new JLabel();
        tasksLabel.setText("Tasks:");
        mainPanel.add(tasksLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 8, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveAsButton = new JButton();
        saveAsButton.setText("Save As...");
        mainPanel.add(saveAsButton, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tasksLabel.setLabelFor(scrollPane1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
