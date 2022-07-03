/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import Excepciones.ExcepcionArchivo;
import Excepciones.ExcepcionEscritura;
import Excepciones.ExcepcionLectura;
import co.edu.unicesar.modelo.AudioLibro;
import co.edu.unicesar.modelo.Libro;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import co.edu.unicesar.modelo.Publicacion;

/**
 *
 * @author Jairo F
 */
public class ImpArchivoTexto implements IPublicacionCrud {

    private File archivo;
    private FileWriter modoEscritura;
    private Scanner modoLectura;

    public ImpArchivoTexto() {
        this("Aires.inv");
    }

    public ImpArchivoTexto(String path) {
        this.archivo = new File(path);
    }

    @Override
    public void registrar(Publicacion a) throws ExcepcionArchivo {
        PrintWriter pw = null;
        try {

            this.modoEscritura = new FileWriter(this.archivo, true);
            pw = new PrintWriter(this.modoEscritura);
            pw.println(a.getDataStringFormat());

        } catch (IOException e) {
            throw new ExcepcionArchivo("Error al abrir o crear archivo en modo escritura");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
    
    private Publicacion cargar(String data[]){
        
        String idbn = data[1];
        String titulo = data[2];
        String autor = data[3];
        int anio = Integer.valueOf(data[4]);
        double costo=Double.valueOf(data[5]);
        
        if(data[0].equals("L")){
            int nPaginas=Integer.valueOf(data[6]);
            int edicion= Integer.valueOf(data[7]);
            return new Libro(nPaginas, edicion, idbn, titulo, autor, anio, costo);
        }else{
            double duracion=Double.valueOf(data[6]);
            double peso=Double.valueOf(data[7]);
            String formato=data[8];
            return new AudioLibro(duracion, peso, formato, idbn, titulo, autor, anio, costo);
        }
    }

    @Override
    public List<Publicacion> leer() throws ExcepcionArchivo {
        List<Publicacion> lista;
        try {
            this.modoLectura = new Scanner(this.archivo);
            lista = new ArrayList();
            while(this.modoLectura.hasNext()){
                String[] data = this.modoLectura.nextLine().split(";");
                Publicacion a = this.cargar(data);
                lista.add(a);
            }
            return lista;
        } catch (FileNotFoundException e) {
            throw new ExcepcionArchivo("Error al leer archivo en modo lectura");
           
        }
        finally{
            if(this.modoLectura!=null)
                this.modoLectura.close();
        }

    }

    @Override
    public Publicacion buscar(Publicacion a) throws ExcepcionArchivo {
        Publicacion  buscado = null;
        try {
            this.modoLectura = new Scanner(this.archivo);
            while(this.modoLectura.hasNext()){
                String[] data = this.modoLectura.nextLine().split(";");
                buscado = this.cargar(data);
                if(buscado.getIdbn().equals(a.getIdbn())){
                    return buscado;
                }    
            }
            return null;
        } catch (FileNotFoundException e) {
            throw new ExcepcionArchivo("Error al buscar archivo en modo lectura");
        }
        finally{
            if(this.modoLectura!=null)
                this.modoLectura.close();
        }
    }
    
    private void renombrarArchivo(File tmp) throws IOException{
        if(!tmp.exists())
            tmp.createNewFile();
        
        if(!this.archivo.delete())
            throw new IOException("Error al eliminar el archivo principal");
        
        if(!tmp.renameTo(this.archivo))
            throw new IOException("Error al renombrar el archivo tmp");
        
    }

    @Override
    public Publicacion eliminar(Publicacion a) throws ExcepcionArchivo{
        Publicacion eliminado = null;
        ImpArchivoTexto archivoTemporal = new ImpArchivoTexto("Aires.tmp");
        try {
            this.modoLectura = new Scanner(this.archivo);
            while(this.modoLectura.hasNext()){
                String[] data = this.modoLectura.nextLine().split(";");
                Publicacion aux = this.cargar(data);
                if(aux.getIdbn().equals(a.getIdbn())){
                    eliminado=aux;
                }    
                else{
                   archivoTemporal.registrar(aux);
                }
            }
            this.modoLectura.close();
            this.renombrarArchivo(archivoTemporal.archivo);
            return eliminado;
        } catch (FileNotFoundException e) {
            throw new ExcepcionArchivo("Error al buscar archivo en modo lectura");
        }
        catch(IOException e){
            throw new ExcepcionArchivo(e.getMessage());
        }
        finally{
            if(this.modoLectura!=null)
                this.modoLectura.close();
        }
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
     * @return the modoEscritura
     */
    public FileWriter getModoEscritura() {
        return modoEscritura;
    }

    /**
     * @param modoEscritura the modoEscritura to set
     */
    public void setModoEscritura(FileWriter modoEscritura) {
        this.modoEscritura = modoEscritura;
    }

    /**
     * @return the modoLectura
     */
    public Scanner getModoLectura() {
        return modoLectura;
    }

    /**
     * @param modoLectura the modoLectura to set
     */
    public void setModoLectura(Scanner modoLectura) {
        this.modoLectura = modoLectura;
    }

    @Override
    public List<Publicacion> filtrar(String serial) throws ExcepcionArchivo {
         List<Publicacion> lista = this.leer();
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
