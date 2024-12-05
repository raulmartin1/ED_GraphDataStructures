package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.VertexNoTrobat;
import cat.urv.deim.exceptions.ArestaNoTrobada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class GrafPersones {

    // Metodes per a guardar persones
    private Graf<Integer, Persona, Integer> graf;

    public GrafPersones(int dim){
        graf = new Graf<Integer, Persona, Integer>(dim);
    }

    public GrafPersones(int dim, String f1 ,String f2) {
        graf = new Graf<Integer, Persona, Integer>(dim);
        try{
            BufferedReader persones = new BufferedReader(new FileReader(f1));
            String linia = persones.readLine();
            while (linia != null){
                String[] parts = linia.split(",");
                int id = Integer.parseInt(parts[0]);
                int edat = Integer.parseInt(parts[1]);
                int alsada = Integer.parseInt(parts[4]);
                int pes = Integer.parseInt(parts[5]);
                Persona p = new Persona(id, edat, parts[2], parts[3], alsada, pes);
                graf.inserirVertex(id, p);
                linia = persones.readLine();
            }
            persones.close();
            if (f2 != null){
                BufferedReader amistats = new BufferedReader(new FileReader(f2));
                linia = amistats.readLine();
                while (linia != null){
                    String[] parts = linia.split(",");
                    int id1 = Integer.parseInt(parts[0]);
                    int id2 = Integer.parseInt(parts[1]);
                    int pes = Integer.parseInt(parts[2]);
                    graf.inserirAresta(id1, id2, pes);
                    linia = amistats.readLine();
                }
                amistats.close();
            }
        }catch (IOException e){
            System.out.println("Error en el tractament del fitxer");
        }catch (VertexNoTrobat e){
            System.out.println("No s'ha trobat la persona que pertany a la relació");
        }
    }


    public void inserirPersona(Persona p) {
        graf.inserirVertex(p.getId_persona(), p);
    }

    public Persona consultarPersona(int id) throws ElementNoTrobat {
        try {
            return graf.consultarVertex(id);
        }catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public void esborrarPersona(int id) throws ElementNoTrobat {
        try {
            graf.esborrarVertex(id);
        }catch(VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public int numPersones() {
        return graf.numVertex();
    }

    public boolean esBuida() {
        return numPersones() == 0;
    }

    public ILlistaGenerica<Integer> obtenirPersonesIDs() {
        return graf.obtenirVertexIDs();
    }

    // Metodes per a guardar amistats

    public void inserirAmistat(Persona p1, Persona p2) throws ElementNoTrobat  {
        try {
            graf.inserirAresta(p1.getId_persona(), p2.getId_persona());
        }catch(VertexNoTrobat e){
            throw new ElementNoTrobat();
        }
    }

    public void inserirAmistat(Persona p1, Persona p2, int intensitat) throws ElementNoTrobat  {
        try {
            graf.inserirAresta(p1.getId_persona(), p2.getId_persona(), intensitat);
        }catch(VertexNoTrobat e){
            throw new ElementNoTrobat();
        }
    }

    public void esborrarAmistat(Persona p1, Persona p2) throws ElementNoTrobat  {
    try {
        graf.esborrarAresta(p1.getId_persona(), p2.getId_persona());
    }catch (VertexNoTrobat e){
        throw new ElementNoTrobat();
    }catch (ArestaNoTrobada e) {
        throw new ElementNoTrobat();
    }
    }

    public boolean existeixAmistat(Persona p1, Persona p2) throws ElementNoTrobat {
        try {
            return graf.existeixAresta(p1.getId_persona(), p2.getId_persona());
        } catch (VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    public int intensitatAmistat(Persona p1, Persona p2) throws ElementNoTrobat {
        try {
            return graf.consultarAresta(p1.getId_persona(), p2.getId_persona());
        }catch (VertexNoTrobat e){
            throw new ElementNoTrobat();
        }catch(ArestaNoTrobada e){
            throw new ElementNoTrobat();
        }
    }

    public int numAmistats() {
        return graf.numArestes();
    }


    public int numAmistats(Persona p) throws ElementNoTrobat {
        try {
            return graf.numVeins(p.getId_persona());
        }catch(VertexNoTrobat e){
            throw new ElementNoTrobat();
        }
    }

    public boolean teAmistats(Persona p) {
        try {
            return graf.numVeins(p.getId_persona()) != 0;
        }catch (VertexNoTrobat e) {
            return false;
        }
    }


    public ILlistaGenerica<Integer> obtenirAmistats(Persona p) throws ElementNoTrobat {
        try{
            return graf.obtenirVeins(p.getId_persona());
        }catch(VertexNoTrobat e) {
            throw new ElementNoTrobat();
        }
    }

    // Aquest metode busca totes les persones del grup d'amistats de p que tenen alguna connexio amb p
    // ja sigui directament, o be perque son amics d'amics, o amics de amics de amics, etc.
    // Retorna una llista amb els ID de les persones del grup
    public ILlistaGenerica<Integer> obtenirGrupAmistats(Persona p) throws ElementNoTrobat {
    return null;
    }

    // Aquest metode busca el grup d'amistats mes gran del graf, es a dir, el que te major nombre
    // de persones que estan connectades entre si. Retorna una llista amb els ID de les persones del grup
    public ILlistaGenerica<Integer> obtenirGrupAmistatsMesGran() {
    return null;
    }

}
