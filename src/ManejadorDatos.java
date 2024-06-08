import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManejadorDatos {
    private ArbolBinarioDeBusqueda abb;
    private ArbolAVL avl;

    public ManejadorDatos() {
        this.abb = new ArbolBinarioDeBusqueda();
        this.avl = new ArbolAVL();
    }

    public String cargarArchivo(String rutaArchivo, int cantidadDatos) {
        StringBuilder resultado = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Ignorar la primera línea (encabezados)
            String linea;
            int contador = 0;
            while ((linea = br.readLine()) != null && contador < cantidadDatos) {
                String[] datos = linea.split("\t"); // Usar tabulación como delimitador
                String nombre = datos[0];
                String dpiStr = datos[1];
                abb.insertar(nombre, dpiStr);
                avl.insertar(nombre, dpiStr);
                resultado.append("Nombre: ").append(nombre).append("\tDPI: ").append(dpiStr).append("\n");
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al cargar el archivo: " + e.getMessage();
        }
        return resultado.toString();
    }

    public DefaultMutableTreeNode getArbolABB() {
        return abb.getTree();
    }

    public DefaultMutableTreeNode getArbolAVL() {
        return avl.getTree();
    }

}
