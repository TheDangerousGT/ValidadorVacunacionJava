import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class InterfazUsuario {
    private final JPanel panel;
    private final JTextField rutaArchivoField;
    private final JTextField cantidadDatosField; // Campo para la cantidad de datos
    private final JButton cargarArchivoButton;
    private final JTextArea resultadosArea;
    private final JButton mostrarMenuButton;
    private final ManejadorDatos manejadorDatos;
    private boolean archivoCargado;

    public InterfazUsuario() {
        panel = new JPanel(new BorderLayout());
        rutaArchivoField = new JTextField(20);
        cantidadDatosField = new JTextField(5); // Inicializar el campo de cantidad de datos
        cargarArchivoButton = new JButton("Cargar Archivo");
        resultadosArea = new JTextArea(20, 40);
        mostrarMenuButton = new JButton("Mostrar Menú");
        archivoCargado = false;

        cargarArchivoButton.addActionListener(e -> {
            try {
                cargarArchivo();
            } catch (IOException ex) {
                Logger.getLogger(InterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        mostrarMenuButton.addActionListener(e -> {
            try {
                mostrarMenu();
            } catch (Exception ex) {
                Logger.getLogger(InterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Ruta del archivo: "));
        inputPanel.add(rutaArchivoField);
        inputPanel.add(new JLabel("Cantidad de datos: ")); // Etiqueta para la cantidad de datos
        inputPanel.add(cantidadDatosField); // Agregar el campo de cantidad de datos
        inputPanel.add(cargarArchivoButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultadosArea), BorderLayout.CENTER);
        panel.add(mostrarMenuButton, BorderLayout.SOUTH);

        manejadorDatos = new ManejadorDatos();
    }

    public JPanel getPanel() {
        return panel;
    }

private void cargarArchivo() throws IOException {
    String rutaArchivo = rutaArchivoField.getText();
    if (rutaArchivo.isEmpty()) {
        JOptionPane.showMessageDialog(panel, "Por favor, ingrese la ruta del archivo.");
        return;
    }

    int cantidadDatos = -1;
    String cantidadDatosStr = cantidadDatosField.getText().trim();
    if (!cantidadDatosStr.isEmpty()) {
        try {
            cantidadDatos = Integer.parseInt(cantidadDatosStr);
            if (cantidadDatos < 1 || cantidadDatos > 3000000) {
                throw new NumberFormatException("Cantidad fuera del rango permitido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Por favor, ingrese un número válido entre 1 y 3,000,000.");
            return;
        }
    }

    String resultado = cargarDatosDesdeArchivo(rutaArchivo, cantidadDatos);
    resultadosArea.setText(resultado);
    archivoCargado = true;
    mostrarMenuButton.setEnabled(true); // Habilitar el botón "Mostrar Menú"
}

private String cargarDatosDesdeArchivo(String rutaArchivo, int cantidadDatos) throws FileNotFoundException, IOException {
    StringBuilder resultado = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        br.readLine(); // Ignorar la primera línea (encabezados)
        String linea;
        int contador = 0;
        while ((linea = br.readLine()) != null && (cantidadDatos == -1 || contador < cantidadDatos)) {
            String[] datos = linea.split("\t"); // Usar tabulación como delimitador
            String nombre = datos[0];
            String dpiStr = datos[1];
            // Aquí se insertan los datos en el árbol o se realizan las operaciones necesarias
            resultado.append("Nombre: ").append(nombre).append("\tDPI: ").append(dpiStr).append("\n");
            contador++;
        }
    }
    return resultado.toString();
}

    private void mostrarMenu() throws Exception {
        if (!archivoCargado) {
            JOptionPane.showMessageDialog(panel, "Por favor, cargue un archivo primero.");
            return;
        }

        String menu = "Menú:\n" +
                "1. Realizar búsqueda\n" +
                "2. Insertar nueva información\n" +
                "3. Actualizar información existente\n" +
                "4. Eliminar información\n" +
                "5. Mostrar árbol binario de búsqueda\n" +
                "6. Mostrar árbol AVL\n" +
                "7. Encriptar archivo\n" +
                "8. Desencriptar archivo\n" +
                "9. Comprimir archivo\n" +
                "10. Descomprimir archivo\n" +
                "11. Salir\n";
        JTextField opcionField = new JTextField(5);
        JOptionPane.showMessageDialog(null, new Object[] {menu, opcionField}, "Seleccione una opción", JOptionPane.PLAIN_MESSAGE);
        String opcion = opcionField.getText().trim();

        switch (opcion) {
            case "1":
                // Realizar búsqueda
                realizarBusqueda();
                break;
            case "2":
                // Insertar nueva información
                insertarInformacion();
                break;
            case "3":
                // Actualizar información existente
                actualizarInformacion();
                break;
            case "4":
                // Eliminar información
                eliminarInformacion();
                break;
            case "5":
                // Mostrar árbol binario de búsqueda
                mostrarABB();
                break;
            case "6":
                // Mostrar árbol AVL
                mostrarAVL();
                break;
            case "7":
                // Encriptar archivo
                encriptarArchivo();
                break;
            case "8":
                // Desencriptar archivo
                desencriptarArchivo();
                break;
            case "9":
                // Comprimir Archivo
                comprimirArchivo();
                break;
            case "10":
                // Descomprimir archivo
                descomprimirArchivo();
                break;
            case "11":
                // Salir
                System.exit(0);
                break;

            default:
                // Opción inválida
                JOptionPane.showMessageDialog(panel, "Opción inválida.");
                break;
        }
    }

private void realizarBusqueda() {
    String dpi = JOptionPane.showInputDialog(panel, "Ingrese el DPI a buscar:");
    NodoABB nodo = manejadorDatos.buscar(dpi);
    if (nodo != null) {
        resultadosArea.append("Información encontrada: \nNombre: " + nodo.nombre + "\nDPI: " + nodo.dpi + "\n");
    } else {
        JOptionPane.showMessageDialog(panel, "No se encontró ningún registro con el DPI: " + dpi);
    }
}


    private void insertarInformacion() throws IOException {
        String nombre = JOptionPane.showInputDialog(panel, "Ingrese el nombre:");
        String dpi = JOptionPane.showInputDialog(panel, "Ingrese el DPI:");
        manejadorDatos.insertar(nombre, dpi);
        resultadosArea.append("Información insertada: \nNombre: " + nombre + "\nDPI: " + dpi + "\n");
    }

private void actualizarInformacion() {
    String dpi = JOptionPane.showInputDialog(panel, "Ingrese el DPI de la información a actualizar:");
    NodoABB nodo = manejadorDatos.buscar(dpi);
    if (nodo != null) {
        String nuevoNombre = JOptionPane.showInputDialog(panel, "Ingrese el nuevo nombre:");
        try {
            manejadorDatos.actualizar(dpi, nuevoNombre);
            resultadosArea.append("Información actualizada: \nNombre: " + nuevoNombre + "\nDPI: " + dpi + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Error al actualizar la información: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(panel, "No se encontró ningún registro con el DPI: " + dpi);
    }
}


    private void eliminarInformacion() throws IOException {
        String dpi = JOptionPane.showInputDialog(panel, "Ingrese el DPI de la información a eliminar:");
        manejadorDatos.eliminar(dpi);
        resultadosArea.append("Registro eliminado con DPI: " + dpi + "\n");
    }

    private void mostrarABB() {
        JTree tree = new JTree(manejadorDatos.getArbolABB());
        JFrame frame = new JFrame("Árbol Binario de Búsqueda");
        frame.add(new JScrollPane(tree));
        
        frame.setSize(300, 400);
        frame.setVisible(true);
    }

    private void mostrarAVL() {
        JTree tree = new JTree(manejadorDatos.getArbolAVL());
        JFrame frame = new JFrame("Árbol AVL");
        frame.add(new JScrollPane(tree));
        frame.setSize(300, 400);
        frame.setVisible(true);
    }

    private void encriptarArchivo() throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        String inputFile = JOptionPane.showInputDialog(panel, "Ingrese el nombre del archivo a encriptar:");
        String outputFile = JOptionPane.showInputDialog(panel, "Ingrese el nombre del archivo encriptado:");
        String secretKey = JOptionPane.showInputDialog(panel, "Ingrese la clave secreta para encriptar:");

        try {
            FileEncryptor.encryptFile(inputFile, outputFile, secretKey);
        } catch (Exception ex) {
            Logger.getLogger(InterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(panel, "Archivo encriptado con éxito.");
    }

    private void desencriptarArchivo() throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        String inputFile = JOptionPane.showInputDialog(panel, "Ingrese el nombre del archivo a desencriptar:");
        String outputFile = JOptionPane.showInputDialog(panel, "Ingrese el nombre del archivo desencriptado");
        String secretKey = JOptionPane.showInputDialog(panel, "Ingrese la clave secreta para desencriptar:");

        try {
            FileEncryptor.decryptFile(inputFile, outputFile, secretKey);
            JOptionPane.showMessageDialog(panel, "Archivo desencriptado con éxito.");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException ex) {
            JOptionPane.showMessageDialog(panel, "Error al desencriptar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(InterfazUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void comprimirArchivo() {
        String inputFile = JOptionPane.showInputDialog(panel, "Ingrese la ruta del archivo a comprimir:");
        String outputPath = JOptionPane.showInputDialog(panel, "Ingrese la ruta donde desea guardar el archivo comprimido:");
        String outputFile = JOptionPane.showInputDialog(panel, "Ingrese el nombre del archivo comprimido:");

        try {
            FileCompressor.compressFile(inputFile, outputPath + File.separator + outputFile);
            JOptionPane.showMessageDialog(panel, "Archivo comprimido con éxito.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Error al comprimir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void descomprimirArchivo() {
        String inputFile = JOptionPane.showInputDialog(panel, "Ingrese el nombre del archivo a descomprimir:");
        String outputPath = JOptionPane.showInputDialog(panel, "Ingrese la ruta donde desea guardar los archivos descomprimidos:");
        try {
            FileCompressor.decompressFile(inputFile, outputPath);
            JOptionPane.showMessageDialog(panel, "Archivo descomprimido con éxito.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Error al descomprimir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
