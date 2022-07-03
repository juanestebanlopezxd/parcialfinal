/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import Excepciones.ExcepcionArchivo;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import co.edu.unicesar.modelo.Publicacion;

/**
 *
 * @author Jairo F
 */
public class ImpArchivoObjeto implements IPublicacionCrud {

    private File archivo;
    private FileInputStream modoLectura;
    private FileOutputStream modoEscritura;

    public ImpArchivoObjeto() {
        this("Aires.obj");
    }

    public ImpArchivoObjeto(String path) {
        this.archivo = new File(path);
    }

    private void guardar(List<Publicacion> lista) throws ExcepcionArchivo {
        ObjectOutputStream oos = null;
        try {
            this.modoEscritura = new FileOutputStream(this.archivo);
            oos = new ObjectOutputStream(this.modoEscritura);
            oos.writeObject(lista);
            oos.close();

        } catch (FileNotFoundException e) {
            throw new ExcepcionArchivo("El Archivo de escritura no existe, o no se puede abrir");
        } catch (SecurityException e) {
            throw new ExcepcionArchivo("No tiene permiso de escritura sobre el archivo");
        } catch (IOException e) {
            throw new ExcepcionArchivo("Error al escribir en el archivo");
        } catch (NullPointerException e) {
            throw new ExcepcionArchivo("Los datos de la lista son null");
        }

    }

    private List<Publicacion> cargarArchivo() throws ExcepcionArchivo {

        ObjectInputStream ois = null;

        if (!this.archivo.exists()) {
            return new ArrayList<Publicacion>();
        }
        try {
            this.modoLectura = new FileInputStream(this.archivo);
            ois = new ObjectInputStream(this.modoLectura);
            List<Publicacion> lista = (List<Publicacion>) ois.readObject();
            ois.close();
            return lista;

        } catch (FileNotFoundException e) {
            throw new ExcepcionArchivo("El Archivo de lectura no existe, o no se puede abrir");
        } catch (SecurityException e) {
            throw new ExcepcionArchivo("No tiene permiso de lectura sobre el archivo");
        } catch (StreamCorruptedException e) {
            throw new ExcepcionArchivo("Error con los datos de flujo de cierre del objeto");
        } catch (IOException e) {
            throw new ExcepcionArchivo("Error al leer en el archivo");
        } catch (NullPointerException e) {
            throw new ExcepcionArchivo("El archivo a leer es null");
        }
        catch(ClassNotFoundException e){
            throw new ExcepcionArchivo("No existe la claase definida para el objeto leido");
        }

    }

    @Override
    public void registrar(Publicacion a) throws ExcepcionArchivo {
            List<Publicacion> lista = this.cargarArchivo();
            lista.add(a);
            this.guardar(lista);
    }

    @Override
    public List<Publicacion> leer() throws ExcepcionArchivo {
          return this.cargarArchivo();
    }

    @Override
    public Publicacion buscar(Publicacion a) throws ExcepcionArchivo {
        List<Publicacion> lista = this.cargarArchivo();
        Publicacion buscado=null;
        for(Publicacion i: lista){
            if(i.getIdbn().equals(a.getIdbn())){
                buscado=i;
                break;
            }
        }
        return buscado;
    }

    @Override
    public Publicacion eliminar(Publicacion a) throws ExcepcionArchivo {
        List<Publicacion> lista = this.cargarArchivo();
        Publicacion eliminar=null;
        Iterator<Publicacion>i = lista.iterator();
        
        while(i.hasNext()){
            Publicacion aux= i.next();
            if(aux.getIdbn().equals(a.getIdbn())){
                eliminar=aux;
                i.remove();
            }
        }
        
        this.guardar(lista);
        
        return eliminar;
        
    }

    /**
     * @return the archivo
     */
    public File getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the modoLectura
     */
    public FileInputStream getModoLectura() {
        return modoLectura;
    }

    /**
     * @param modoLectura the modoLectura to set
     */
    public void setModoLectura(FileInputStream modoLectura) {
        this.modoLectura = modoLectura;
    }

    /**
     * @return the modoEscritura
     */
    public FileOutputStream getModoEscritura() {
        return modoEscritura;
    }

    /**
     * @param modoEscritura the modoEscritura to set
     */
    public void setModoEscritura(FileOutputStream modoEscritura) {
        this.modoEscritura = modoEscritura;
    }
    
     public List<Publicacion> filtrar(String serial) throws ExcepcionArchivo{
         List<Publicacion> lista = this.cargarArchivo();
         List<Publicacion> listaFiltrada = new ArrayList();
         for(Publicacion a: lista){
             String serialLista = String.valueOf(a.getIdbn());
             String serialFiltrada = String.valueOf(serial);
             if(serialLista.contains(serialFiltrada)){
                 listaFiltrada.add(a);
             }
         }
         return listaFiltrada;         
     }

}
