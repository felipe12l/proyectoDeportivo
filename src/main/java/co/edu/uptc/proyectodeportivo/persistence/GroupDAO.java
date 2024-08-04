package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Group;
import co.edu.uptc.proyectodeportivo.model.Group;
import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO implements InterfaceDAO<Group> {
    private Gson gson;
    private ConnectionString connectionString;
    @Override
    public List<Group> getAll() {
        List<Group> Groups = new ArrayList<>();
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("db_project");
            MongoCollection<Document> collection=database.getCollection("groups");
            FindIterable<Document> documents = collection.find();
            for (Document doc : documents) {
                Group Group=gson.fromJson(doc.toJson(), Group.class);
                Groups.add(Group);
            }

        }
        return Groups;
    }

    @Override
    public Group save(Group object) {
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("db_project");
            MongoCollection<Document> collection=database.getCollection("groups");
            String obj=gson.toJson(object);
            Document document = Document.parse(obj);
            collection.insertOne(document);
            return object;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Group findById(String id) {
        return getAll().stream().filter(m-> m.getId().compareTo(id)==0).findFirst().orElse(null);
    }

    @Override
    public Group delete(String id) {
        Group a=findById(id);
        if(a!=null){
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase database = mongoClient.getDatabase("db_project");
                MongoCollection<Document> collection = database.getCollection("groups");
                FindIterable<Document> documents = collection.find();
                Document filter = new Document("id", id);

                // Llama al método deleteOne para borrar el documento que coincide con el filtro
                DeleteResult result = collection.deleteOne(filter);


                return result.getDeletedCount() > 0?a:null;
            }}
        return null;
    }

    @Override
    public Group update(Group object, String id) {
        // Encontrar el documento actual en la base de datos usando el id
        Group existingGroup = findById(id);
        if (existingGroup != null) {
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase database = mongoClient.getDatabase("db_project");
                MongoCollection<Document> collection = database.getCollection("groups");

                // Convertir el nuevo objeto Group en un Document
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
