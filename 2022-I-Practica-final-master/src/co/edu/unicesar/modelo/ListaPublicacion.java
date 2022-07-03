/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicesar.modelo;

import Excepciones.ExcepcionArchivo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.IPublicacionCrud;
import persistencia.ImpArchivoObjeto;
import persistencia.ImpArchivoTexto;

/**
 *
 * @author JAIRO
 */
public class ListaPublicacion implements IPublicacionCrud {
    
    private IPublicacionCrud registroPublicaciones ;
    
    //Costructor para la persistencia por archivo de texto
    public ListaPublicacion() {
        registroPublicaciones = new ImpArchivoObjeto();
    }

    //Costructor para la persistencia por archivo de objetos
    public ListaPublicacion(String dato) {
         registroPublicaciones = new ImpArchivoTexto();
    }
    
    
    @Override
    public void registrar(Publicacion p) {
        
        try {
            this.registroPublicaciones.registrar(p);
        } catch (ExcepcionArchivo ex) {
            Logger.getLogger(ListaPublicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<Publicacion> leer() {
        
        try {
            return this.registroPublicaciones.leer();
        } catch (ExcepcionArchivo ex) {
            Logger.getLogger(ListaPublicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Publicacion buscar(Publicacion p) {
        
        try {
            return this.registroPublicaciones.buscar(p);
        } catch (ExcepcionArchivo ex) {
            Logger.getLogger(ListaPublicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Publicacion eliminar(Publicacion p) {
        
        try {
            return this.registroPublicaciones.eliminar(p);
        } catch (ExcepcionArchivo ex) {
            Logger.getLogger(ListaPublicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    @Override
    public List<Publicacion> filtrar(String i) throws ExcepcionArchivo {
        return this.registroPublicaciones.filtrar(i);
    }
    
}
