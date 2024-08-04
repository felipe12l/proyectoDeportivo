package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Discipline;
import co.edu.uptc.proyectodeportivo.model.Discipline;
import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDAO implements InterfaceDAO<Discipline> {
    private Gson gson;
    private ConnectionString connectionString;
    @Override
    public List<Discipline> getAll() {
        List<Discipline> Disciplines = new ArrayList<>();
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("db_project");
            MongoCollection<Document> collection=database.getCollection("disciplines");
            FindIterable<Document> documents = collection.find();
            for (Document doc : documents) {
                Discipline Discipline=gson.fromJson(doc.toJson(), Discipline.class);
                Disciplines.add(Discipline);
            }

        }
        return Disciplines;
    }

    @Override
    public Discipline save(Discipline object) {
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("db_project");
            MongoCollection<Document> collection=database.getCollection("disciplines");
            String obj=gson.toJson(object);
            Document document = Document.parse(obj);
            collection.insertOne(document);
            return object;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Discipline findById(String id) {
        return getAll().stream().filter(m-> m.getId().compareTo(id)==0).findFirst().orElse(null);
    }

    @Override
    public Discipline delete(String id) {
        Discipline a=findById(id);
        if(a!=null){
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase database = mongoClient.getDatabase("db_project");
                MongoCollection<Document> collection = database.getCollection("disciplines");
                FindIterable<Document> documents = collection.find();
                Document filter = new Document("id", id);

                // Llama al método deleteOne para borrar el documento que coincide con el filtro
                DeleteResult result = collection.deleteOne(filter);


                return result.getDeletedCount() > 0?a:null;
            }}
        return null;
    }

    @Override
    public Discipline update(Discipline object, String id) {
        // Encontrar el documento actual en la base de datos usando el id
        Discipline existingDiscipline = findById(id);
        if (existingDiscipline != null) {
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase database = mongoClient.getDatabase("db_project");
                MongoCollection<Document> collection = database.getCollection("disciplines");

                // Convertir el nuevo objeto Discipline en un Document
                String jsonString = gson.toJson(object);
                Document updatedDocument = Document.parse(jsonString);

                // Crear un filtro para encontrar el documento a actualizar
                Document filter = new Document("id", id);

                // Actualizar el documento en la colección
                collection.replaceOne(filter, updatedDocument);

                // Retornar el objeto actualizado
                return object;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }



    @Override
    public void close() throws IOException {

    }
}
