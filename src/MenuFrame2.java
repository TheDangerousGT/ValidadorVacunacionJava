import javax.swing.*;

public class MenuFrame2 extends JFrame {
    private JPanel panel;
    private JTextArea menuTextArea;

    public MenuFrame2() {
        setTitle("Menú");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);

        panel = new JPanel();
        menuTextArea = new JTextArea(10, 30);
        menuTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(menuTextArea);
        panel.add(scrollPane);

        add(panel);
    }

    public void setMenu(String menu) {
        menuTextArea.setText(menu);
    }
}
