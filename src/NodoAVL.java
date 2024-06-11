public class NodoAVL {
    String nombre;
    String dpi;
    NodoAVL izquierda;
    NodoAVL derecha;
    int altura;

    public NodoAVL(String nombre, String dpi) {
        this.nombre = nombre;
        this.dpi = dpi;
        this.izquierda = null;
        this.derecha = null;
        this.altura = 1;
    }
}
