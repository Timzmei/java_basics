import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import org.bson.BsonDocument;

import java.util.function.Consumer;

import java.io.IOException;


public class Main {

    private static final String CSV_file= "13_NoSQL/Mongo/src/data/mongo.csv";

    private static MongoCollection<Document> collection;
    public static Integer age;


    public static void main(String[] args) throws IOException{


        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");

        MongoDatabase database = mongoClient.getDatabase("local");

        // Создаем коллекцию
        collection = database.getCollection("TestSkillDemo");

        // Удалим из нее все документы
        collection.drop();



        System.out.println("Общее количество студентов в базе: " + collection.countDocuments());

        System.out.println("Kоличество студентов старше 40 лет: " + collection.countDocuments(BsonDocument.parse("{age: {$gt: 40}}")));


        BsonDocument query = BsonDocument.parse("{age: 1}");
        System.out.println("Имя самого молодого студента: ");
        collection.find().sort(query).limit(1).forEach((Consumer<Document>) document -> {
//            System.out.println("Имя самого молодого студента: "  + document.getString("name"));
            age = document.getInteger("age");
        });
        collection.find().sort(query).forEach((Consumer<Document>) document -> {
            if (document.getInteger("age") == age) {
                System.out.println(document.getString("name") + ", возраст: " + document.getInteger("age"));
            }
        });


        System.out.println("Cписок курсов самого старого студента: ");
        query = BsonDocument.parse("{age: -1}");
        collection.find().sort(query).limit(1).forEach((Consumer<Document>) document -> {
            age = document.getInteger("age");
//            System.out.println("Cписок курсов самого старого студента: "  + document.get("CourseList"));
        });

        collection.find().sort(query).forEach((Consumer<Document>) document -> {
            if (document.getInteger("age") == age) {
                System.out.println(document.getString("name") + ", возраст: " + document.getInteger("age") + ", курсы: " + document.get("CourseList"));
            }
        });

    }


}
