package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Affiliate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AffiliateDAO implements InterfaceDAO<Affiliate> {
    private Gson gson;
    private ConnectionString connectionString;

    public AffiliateDAO() {
        this.connectionString =new ConnectionString("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.2.12");;
        gson=new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<Affiliate> getAll() {
        List<Affiliate> affiliates = new ArrayList<>();
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
         MongoDatabase database = mongoClient.getDatabase("db_affiliates");
         MongoCollection<Document> collection=database.getCollection("affiliates");
         FindIterable<Document> documents = collection.find();
            for (Document doc : documents) {
                Affiliate affiliate=gson.fromJson(doc.toJson(), Affiliate.class);
                affiliates.add(affiliate);
            }
          
        }
        return affiliates;
    }

    @Override
    public Affiliate save(Affiliate object) {
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("db_affiliates");
            MongoCollection<Document> collection=database.getCollection("affiliates");
            String obj=gson.toJson(object);
            Document document = Document.parse(obj);
            collection.insertOne(document);
            return object;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public Affiliate findById(String id) {
       return getAll().stream().filter(m-> m.getId().compareTo(id)==0).findFirst().orElse(null);
    }

    @Override
    public Affiliate delete(String id) {
        Affiliate a=findById(id);
        if(a!=null){
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("db_affiliates");
            MongoCollection<Document> collection = database.getCollection("affiliates");
            FindIterable<Document> documents = collection.find();
            Document filter = new Document("id", id);

            // Llama al método deleteOne para borrar el documento que coincide con el filtro
            DeleteResult result = collection.deleteOne(filter);


            return result.getDeletedCount() > 0?a:null;
        }}
        return null;
    }

    @Override
    public Affiliate update(Affiliate object, String id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
