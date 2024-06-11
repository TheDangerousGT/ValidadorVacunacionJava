import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class ManejadorDatos {
    private ArbolBinarioDeBusqueda abb;
    private ArbolAVL avl;
    private String rutaArchivo;

    
        public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
        
    public ManejadorDatos() {
        this.abb = new ArbolBinarioDeBusqueda();
        this.avl = new ArbolAVL();
    }
    public void insertar(String nombre, String dpi) {
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            System.out.println("La ruta del archivo es inválida.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            bw.write(nombre + "\t" + dpi + "\n");
            // Si necesitas hacer algo más después de escribir en el archivo, puedes hacerlo aquí
        } catch (IOException e) {
            // Manejo de la excepción
            e.printStackTrace(); // Imprime la traza de la pila del error
            // También puedes imprimir un mensaje personalizado
            System.out.println("Error al intentar escribir en el archivo: " + e.getMessage());
        }
    }

    public void eliminar(String dpi) throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\t");
                if (!datos[1].equals(dpi)) {
                    lineas.add(linea);
                }
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (String linea : lineas) {
                bw.write(linea + "\n");
            }
        }
        abb.eliminar(dpi);
    }

    public void actualizar(String dpi, String nuevoNombre) throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\t");
                if (datos[1].equals(dpi)) {
                    lineas.add(nuevoNombre + "\t" + dpi);
                } else {
                    lineas.add(linea);
                }
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (String linea : lineas) {
                bw.write(linea + "\n");
            }
        }
        abb.eliminar(dpi);
        abb.insertar(nuevoNombre, dpi);
    }

    public NodoABB buscar(String dpi) {
        return abb.buscar(dpi);
    }

    public DefaultMutableTreeNode getArbolABB() {
        return abb.getTree();
    }

    public DefaultMutableTreeNode getArbolAVL() {
        return avl.getTree();
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


}
