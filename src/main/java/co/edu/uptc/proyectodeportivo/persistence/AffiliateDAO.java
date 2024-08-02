package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Affiliate;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

public class AffiliateDAO implements InterfaceDAO<Affiliate> {
    private ConnectionString connectionString=new ConnectionString("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.2.12");
    @Override
    public List<Affiliate> getAll() {
        try(MongoClient mongoClient =  MongoClients.create(connectionString)){
         MongoDatabase database = mongoClient.getDatabase("db_affiliates");
         MongoCollection<Document> collection=database.getCollection("affiliates");
         FindIterable<Document> affiliates = collection.find();

          
        }
        return List.of();
    }

    @Override
    public Affiliate save(Affiliate object) {
        return null;
    }

    @Override
    public Affiliate findById(Integer id) {
        return null;
    }

    @Override
    public Affiliate delete(Integer id) {
        return null;
    }

    @Override
    public Affiliate update(Affiliate object, Integer id) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
