import javax.swing.tree.DefaultMutableTreeNode;

public class ArbolAVL {
    private NodoAVL raiz;
    private static final int SHIFT = 3; // Shift value for Caesar cipher

    public ArbolAVL() {
        this.raiz = null;
    }

    public void insertar(String nombre, String dpi) {
        raiz = insertarRec(raiz, nombre, dpi);
    }

    private NodoAVL insertarRec(NodoAVL nodo, String nombre, String dpi) {
        if (nodo == null) {
            return new NodoAVL(nombre, dpi);
        }

        if (dpi.compareTo(nodo.dpi) < 0) {
            nodo.izquierda = insertarRec(nodo.izquierda, nombre, dpi);
        } else if (dpi.compareTo(nodo.dpi) > 0) {
            nodo.derecha = insertarRec(nodo.derecha, nombre, dpi);
        } else {
            return nodo;
        }

        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));

        int balance = getBalance(nodo);

        if (balance > 1 && dpi.compareTo(nodo.izquierda.dpi) < 0) {
            return rotarDerecha(nodo);
        }

        if (balance < -1 && dpi.compareTo(nodo.derecha.dpi) > 0) {
            return rotarIzquierda(nodo);
        }

        if (balance > 1 && dpi.compareTo(nodo.izquierda.dpi) > 0) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        if (balance < -1 && dpi.compareTo(nodo.derecha.dpi) < 0) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    private int getBalance(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierda) - altura(nodo.derecha);
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL T2 = x.derecha;

        x.derecha = y;
        y.izquierda = T2;

        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;

        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL T2 = y.izquierda;

        y.izquierda = x;
        x.derecha = T2;

        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;

        return y;
    }

    public DefaultMutableTreeNode getTree() {
        return createTree(raiz);
    }

    private DefaultMutableTreeNode createTree(NodoAVL nodo) {
        if (nodo == null) {
            return null;
        }
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(nodo.nombre + " (" + nodo.dpi + ")");
        DefaultMutableTreeNode leftChild = createTree(nodo.izquierda);
        DefaultMutableTreeNode rightChild = createTree(nodo.derecha);
        if (leftChild != null) {
            treeNode.add(leftChild);
        }
        if (rightChild != null) {
            treeNode.add(rightChild);
        }
        return treeNode;
    }

    public void encryptTree() {
        encryptNode(raiz);
    }

    private void encryptNode(NodoAVL nodo) {
        if (nodo != null) {
            nodo.nombre = FileEncryptor.encrypt(nodo.nombre, SHIFT);
            nodo.dpi = FileEncryptor.encrypt(nodo.dpi, SHIFT);
            encryptNode(nodo.izquierda);
            encryptNode(nodo.derecha);
        }
    }

    public void decryptTree() {
        decryptNode(raiz);
    }

    private void decryptNode(NodoAVL nodo) {
        if (nodo != null) {
            nodo.nombre = FileEncryptor.decrypt(nodo.nombre, SHIFT);
            nodo.dpi = FileEncryptor.decrypt(nodo.dpi, SHIFT);
            decryptNode(nodo.izquierda);
            decryptNode(nodo.derecha);
        }
    }
}

class NodoAVL {
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

