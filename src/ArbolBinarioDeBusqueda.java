import javax.swing.tree.DefaultMutableTreeNode;

public class ArbolBinarioDeBusqueda {
    private NodoABB raiz;
    private static final int SHIFT = 3; // Shift value for Caesar cipher

    public ArbolBinarioDeBusqueda() {
        this.raiz = null;
    }

    public void insertar(String nombre, String dpi) {
        raiz = insertarRec(raiz, nombre, dpi);
    }

    private NodoABB insertarRec(NodoABB raiz, String nombre, String dpi) {
        if (raiz == null) {
            raiz = new NodoABB(nombre, dpi);
            return raiz;
        }
        if (dpi.compareTo(raiz.dpi) < 0) {
            raiz.izquierda = insertarRec(raiz.izquierda, nombre, dpi);
        } else if (dpi.compareTo(raiz.dpi) > 0) {
            raiz.derecha = insertarRec(raiz.derecha, nombre, dpi);
        }
        return raiz;
    }

    public NodoABB buscar(String dpi) {
        return buscarRec(raiz, dpi);
    }

    private NodoABB buscarRec(NodoABB raiz, String dpi) {
        if (raiz == null || raiz.dpi.equals(dpi)) {
            return raiz;
        }
        if (dpi.compareTo(raiz.dpi) < 0) {
            return buscarRec(raiz.izquierda, dpi);
        }
        return buscarRec(raiz.derecha, dpi);
    }

    public void eliminar(String dpi) {
        raiz = eliminarRec(raiz, dpi);
    }

    private NodoABB eliminarRec(NodoABB raiz, String dpi) {
        if (raiz == null) {
            return raiz;
        }
        if (dpi.compareTo(raiz.dpi) < 0) {
            raiz.izquierda = eliminarRec(raiz.izquierda, dpi);
        } else if (dpi.compareTo(raiz.dpi) > 0) {
            raiz.derecha = eliminarRec(raiz.derecha, dpi);
        } else {
            if (raiz.izquierda == null) {
                return raiz.derecha;
            } else if (raiz.derecha == null) {
                return raiz.izquierda;
            }
            raiz.dpi = minValor(raiz.derecha).dpi;
            raiz.derecha = eliminarRec(raiz.derecha, raiz.dpi);
        }
        return raiz;
    }

    private NodoABB minValor(NodoABB raiz) {
        NodoABB temp = raiz;
        while (temp.izquierda != null) {
            temp = temp.izquierda;
        }
        return temp;
    }

    public DefaultMutableTreeNode getTree() {
        return createTree(raiz);
    }

    private DefaultMutableTreeNode createTree(NodoABB nodo) {
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

    private void encryptNode(NodoABB nodo) {
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

    private void decryptNode(NodoABB nodo) {
        if (nodo != null) {
            nodo.nombre = FileEncryptor.decrypt(nodo.nombre, SHIFT);
            nodo.dpi = FileEncryptor.decrypt(nodo.dpi, SHIFT);
            decryptNode(nodo.izquierda);
            decryptNode(nodo.derecha);
        }
    }

    public String preOrden() {
        StringBuilder resultado = new StringBuilder();
        preOrdenRec(raiz, resultado);
        return resultado.toString();
    }

    private void preOrdenRec(NodoABB nodo, StringBuilder resultado) {
        if (nodo != null) {
            resultado.append(nodo.dpi).append(" ");
            preOrdenRec(nodo.izquierda, resultado);
            preOrdenRec(nodo.derecha, resultado);
        }
    }

    public String inOrden() {
        StringBuilder resultado = new StringBuilder();
        inOrdenRec(raiz, resultado);
        return resultado.toString();
    }

    private void inOrdenRec(NodoABB nodo, StringBuilder resultado) {
        if (nodo != null) {
            inOrdenRec(nodo.izquierda, resultado);
            resultado.append(nodo.dpi).append(" ");
            inOrdenRec(nodo.derecha, resultado);
        }
    }

    public String postOrden() {
        StringBuilder resultado = new StringBuilder();
        postOrdenRec(raiz, resultado);
        return resultado.toString();
    }

    private void postOrdenRec(NodoABB nodo, StringBuilder resultado) {
        if (nodo != null) {
            postOrdenRec(nodo.izquierda, resultado);
            postOrdenRec(nodo.derecha, resultado);
            resultado.append(nodo.dpi).append(" ");
        }
    }
}


class NodoABB {
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
