package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Competition;
import co.edu.uptc.proyectodeportivo.model.Competition;
import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDAO implements InterfaceDAO<Competition> {
    private Gson gson;
    private ConnectionString connectionString;
    @Override
    public List<Competition> getAll() {
        List<Competition> Competitions = new ArrayList<>();
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("db_project");
            MongoCollection<Document> collection=database.getCollection("Competitions");
            FindIterable<Document> documents = collection.find();
            for (Document doc : documents) {
                Competition Competition=gson.fromJson(doc.toJson(), Competition.class);
                Competitions.add(Competition);
            }

        }
        return Competitions;
    }

    @Override
    public Competition save(Competition object) {
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("db_project");
            MongoCollection<Document> collection=database.getCollection("Competitions");
            String obj=gson.toJson(object);
            Document document = Document.parse(obj);
            collection.insertOne(document);
            return object;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Competition findById(String id) {
        return getAll().stream().filter(m-> m.getId().compareTo(id)==0).findFirst().orElse(null);
    }

    @Override
    public Competition delete(String id) {
        Competition a=findById(id);
        if(a!=null){
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase database = mongoClient.getDatabase("db_project");
                MongoCollection<Document> collection = database.getCollection("Competitions");
                FindIterable<Document> documents = collection.find();
                Document filter = new Document("id", id);

                // Llama al método deleteOne para borrar el documento que coincide con el filtro
                DeleteResult result = collection.deleteOne(filter);


                return result.getDeletedCount() > 0?a:null;
            }}
        return null;
    }

    @Override
    public Competition update(Competition object, String id) {
        // Encontrar el documento actual en la base de datos usando el id
        Competition existingCompetition = findById(id);
        if (existingCompetition != null) {
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase database = mongoClient.getDatabase("db_project");
                MongoCollection<Document> collection = database.getCollection("Competitions");

                // Convertir el nuevo objeto Competition en un Document
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
