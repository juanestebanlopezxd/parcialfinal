/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import Excepciones.ExcepcionArchivo;
import Excepciones.ExcepcionEscritura;
import Excepciones.ExcepcionLectura;
import java.util.List;
import co.edu.unicesar.modelo.Publicacion;

/**
 *
 * @author Jairo F
 */
public interface IPublicacionCrud {
    void registrar(Publicacion a) throws ExcepcionArchivo;
    List<Publicacion> leer() throws ExcepcionArchivo;
    Publicacion buscar(Publicacion a)throws ExcepcionArchivo;
    Publicacion eliminar(Publicacion a)throws ExcepcionArchivo;
    List<Publicacion> filtrar(String serial) throws ExcepcionArchivo;
    
    
}
