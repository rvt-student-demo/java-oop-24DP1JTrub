package rvt;
import javax.swing.JFrame;

public class UserInterface {
    private JFrame window;

    public UserInterface() {
        initialize();
    }
    public void initialize() {
        window = new JFrame("Todo App");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(1024,768);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
    }
    public void show() {
        window.setVisible(true);
    }
}