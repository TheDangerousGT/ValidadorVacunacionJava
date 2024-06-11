import javax.swing.*;

public class ValidadorVacunacion {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Validador de Vacunación");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            InterfazUsuario interfazUsuario = new InterfazUsuario();
            frame.getContentPane().add(interfazUsuario.getPanel());
            frame.pack();
            frame.setVisible(true);
        });
    }
}
