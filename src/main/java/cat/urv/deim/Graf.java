package cat.urv.deim;

import cat.urv.deim.exceptions.VertexNoTrobat;
import cat.urv.deim.exceptions.ArestaNoTrobada;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;


public class Graf <K extends Comparable<K>, V extends Comparable<V>, E extends Comparable<E>> implements IGraf<K, V, E>{
    private HashMapIndirecte <K,V> taula;
    private int numArestes;

    public Graf(int dim) {
        taula = new HashMapIndirecte<>(dim);
        numArestes=0;
    }

public void inserirVertex(K key, V value){
    taula.inserir(key, value);
}

 public V consultarVertex(K key) throws VertexNoTrobat {
    try {
        return taula.consultar(key);
    } catch (ElementNoTrobat e) {
        throw new VertexNoTrobat();
    }
 }

 public void esborrarVertex(K key) throws VertexNoTrobat {
    if(!taula.buscar(key)) throw new VertexNoTrobat();
    LlistaNoOrdenada<K> amistats = taula.getAmistats(key);
    LlistaNoOrdenada<K> amistatsAux;

    for (int i=0; i< amistats.numElements(); i++){

        try {
            amistatsAux = taula.getAmistats(amistats.consultar(i));
            amistatsAux.esborrar(key);
            numArestes--;
        } catch (PosicioForaRang e){
            //throw new PosicioForaRang();
        } catch (ElementNoTrobat e) {
            //throw new ElementNoTrobat();
        }
        }
        try {
            taula.esborrar(key);
        } catch (ElementNoTrobat e){
            throw new VertexNoTrobat();
        }
 }

 public boolean esBuida() {
    return taula.numElements() == 0;
 }

 public int numVertex() {
    return taula.numElements();
 }

 public ILlistaGenerica<K> obtenirVertexIDs() {
    return taula.obtenirClaus();
 }


 public void inserirAresta(K v1, K v2 , E pes) throws VertexNoTrobat {
    if (!taula.buscar(v1) || !taula.buscar(v2)) throw new VertexNoTrobat();
        int amistat = (Integer) pes;
        if (existeixAresta(v1, v2)){
            try{
                LlistaNoOrdenada<K> llista = taula.getAmistats(v1);
                llista.esborrar(v2);
                llista = taula.getAmistats(v2);
                llista.esborrar(v1);
                numArestes--;
            }catch(ElementNoTrobat e){throw new VertexNoTrobat();}
        }
        LlistaNoOrdenada<K> llista = taula.getAmistats(v1);
        llista.inserir(v2, amistat);
        llista=taula.getAmistats(v2);
        llista.inserir(v1, amistat);
        numArestes++;
 }

 public void inserirAresta(K v1, K v2) throws VertexNoTrobat {
    if(!taula.buscar(v1) || !taula.buscar(v2)) throw new VertexNoTrobat();
    LlistaNoOrdenada<K> llista = taula.getAmistats(v1);
    llista.inserir(v2);
    llista=taula.getAmistats(v2);
    llista.inserir(v1);
    numArestes++;
 }

 public boolean existeixAresta(K v1, K v2) throws VertexNoTrobat {
    if(!taula.buscar(v1) ||!taula.buscar(v2)) throw new VertexNoTrobat();
    LlistaNoOrdenada<K> llista = taula.getAmistats(v1);
    return llista.existeix(v2);
 }

 public E consultarAresta(K v1, K v2) throws VertexNoTrobat, ArestaNoTrobada {
    if(!taula.buscar(v1) ||!taula.buscar(v2)) throw new VertexNoTrobat();
    if(!existeixAresta(v1, v2)) throw new ArestaNoTrobada();

    LlistaNoOrdenada<K> llista = taula.getAmistats(v1);
    try {
        int pes = llista.getPes(v2);
        E pesAresta = (E) Integer.valueOf(pes);
        return pesAresta;
    } catch (ElementNoTrobat e) {
        throw new ArestaNoTrobada();
    }
 }

 public void esborrarAresta(K v1, K v2) throws VertexNoTrobat, ArestaNoTrobada {
    if(!taula.buscar(v1) ||!taula.buscar(v2)) throw new VertexNoTrobat();
    LlistaNoOrdenada<K> llista = taula.getAmistats(v1);

    try {
        llista.esborrar(v2);
        llista = taula.getAmistats(v2);
        llista.esborrar(v1);
        numArestes--;
    }catch (ElementNoTrobat e){
        throw new ArestaNoTrobada();
    }

 }

 public int numArestes() {
    return numArestes;
 }


 public boolean vertexAillat(K v1) throws VertexNoTrobat {
    if(!taula.buscar(v1)) throw new VertexNoTrobat();
    LlistaNoOrdenada<K> llista = taula.getAmistats(v1);
    if(llista.esBuida()) return true;   //vertex no te veins
    return false;                       //vertex te veins
 }

 public int numVeins(K v1) throws VertexNoTrobat {
    if(!taula.buscar(v1)) throw new VertexNoTrobat();
    LlistaNoOrdenada<K> llista = taula.getAmistats(v1);
    return llista.numElements();
 }

 public ILlistaGenerica<K> obtenirVeins(K v1) throws VertexNoTrobat {
    return taula.getAmistats(v1);
 }

 //OPCIONALS
 public ILlistaGenerica<K> obtenirNodesConnectats(K v1) throws VertexNoTrobat {
 return null;
 }

 public ILlistaGenerica<K> obtenirComponentConnexaMesGran() {
    return null;
 }

}
