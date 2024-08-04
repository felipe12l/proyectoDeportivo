package co.edu.uptc.proyectodeportivo.database;

import co.edu.uptc.proyectodeportivo.logic.Discipline;
import co.edu.uptc.proyectodeportivo.logic.User;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements InterfaceDAO<User> {

    //Si la base de datos está en otra máquina colocar la IP de esa maquina
    private ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("KKKDeportiveClub");
            MongoCollection<Document> collection = mongoDatabase.getCollection("affiliates");
            FindIterable<Document> iterable = collection.find();

            for (Document document : iterable) {
                int id = document.getObjectId("_id").hashCode(); // Convert `_id` to int
                String name = document.getString("name");
                String lastName = document.getString("lastName");
                int age = document.getInteger("age");
                String disciplineName = document.getString("discipline");
                String isGroup = document.getString("isGroup");
                Discipline discipline = new Discipline(disciplineName, Boolean.parseBoolean(isGroup));
                User user = new User(id, name, lastName, age, discipline);

                // If the competitions are stored as a list of strings in MongoDB
                List<String> competitions = (List<String>) document.get("competitions");
                if (competitions != null) {
                    user.setCompetitions(competitions);
                }

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(Integer id) {
        User user = null;
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("KKKDeportiveClub");
            MongoCollection<Document> collection = mongoDatabase.getCollection("affiliates");
            Document query = new Document("_id", id);
            Document document = collection.find(query).first();

            if (document != null) {
                int userId = document.getObjectId("_id").hashCode();
                String name = document.getString("name");
                String lastName = document.getString("lastName");
                int age = document.getInteger("age");
                String disciplineName = document.getString("discipline");
                String isGroup = document.getString("isGroup");
                Discipline discipline = new Discipline(disciplineName, Boolean.parseBoolean(isGroup));
                user = new User(userId, name, lastName, age, discipline);

                // If the competitions are stored as a list of strings in MongoDB
                List<String> competitions = (List<String>) document.get("competitions");
                if (competitions != null) {
                    user.setCompetitions(competitions);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User save(User object) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("KKKDeportiveClub");
            MongoCollection<Document> collection = mongoDatabase.getCollection("affiliates");

            Document document = new Document("name", object.getName())
                    .append("lastName", object.getLastName())
                    .append("age", object.getAge())
                    .append("discipline", object.getDiscipline().getName())
                    .append("competitions", object.getCompetitions());

            collection.insertOne(document);

            // Assuming you want to return the saved user with an ID set
            int id = document.getObjectId("_id").hashCode(); // Convert `_id` to int
            object.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public User delete(Integer id) {
        User user = findById(id);
        if (user != null) {
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase mongoDatabase = mongoClient.getDatabase("KKKDeportiveClub");
                MongoCollection<Document> collection = mongoDatabase.getCollection("affiliates");
                Document query = new Document("_id", id);
                collection.deleteOne(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User update(User object, Integer id) {
        User user = findById(id);
        if (user != null) {
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase mongoDatabase = mongoClient.getDatabase("KKKDeportiveClub");
                MongoCollection<Document> collection = mongoDatabase.getCollection("affiliates");

                Document updateFields = new Document("name", object.getName())
                        .append("lastName", object.getLastName())
                        .append("age", object.getAge())
                        .append("discipline", object.getDiscipline().getName())
                        .append("competitions", object.getCompetitions());

                Document updateQuery = new Document("$set", updateFields);
                Document query = new Document("_id", id);
                collection.updateOne(query, updateQuery);

                user = findById(id); // Return updated user
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void close() throws IOException {
        // Implement any necessary cleanup
    }
}
