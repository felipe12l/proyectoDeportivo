package co.edu.uptc.proyectodeportivo.persistence;

import java.io.Closeable;
import java.util.List;

public interface InterfaceDAO<T> extends Closeable {
    /**
     * devuelve todos los objetos del tipo correspondiente
     * @return List<T>
     */
    List<T> getAll();

    /**
     * guarda el objeto
     * @param object
     * @return
     */
    T save(T object);

    /**
     * halla el objeto
     * @param id
     * @return
     */
    T findById(Integer id);

    /**
     * borra el objeta
     * @param id
     * @return
     */
    T delete(Integer id);

    /**
     * actualiza el objeto
     * @param object
     * @param id
     * @return
     */
    T update(T object, Integer id);

}
