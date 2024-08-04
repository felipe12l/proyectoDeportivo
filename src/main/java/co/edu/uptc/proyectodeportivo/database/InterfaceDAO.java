package co.edu.uptc.proyectodeportivo.database;

import com.mongodb.ConnectionString;

import java.io.Closeable;
import java.util.List;

public interface InterfaceDAO<T> extends Closeable {


    List<T> getAll();

    T findById(Integer id);

    T save(T object);

    T delete(Integer id);

    T update(T object, Integer id);


}
