public class NodoABB {
    String nombre;
    String dpi;
    NodoABB izquierda;
    NodoABB derecha;

    public NodoABB(String nombre, String dpi) {
        this.nombre = nombre;
        this.dpi = dpi;
        this.izquierda = null;
        this.derecha = null;
    }
}
