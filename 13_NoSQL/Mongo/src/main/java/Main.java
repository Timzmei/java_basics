import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Sorts;
import java.util.Arrays;
import org.bson.Document;

import org.bson.BsonDocument;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final String CSV_file= "13_NoSQL/Mongo/src/data/mongo.csv";

    private static MongoCollection<Document> collection;


    public static void main(String[] args) throws IOException{


        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");

        MongoDatabase database = mongoClient.getDatabase("local");

        // Создаем коллекцию
        collection = database.getCollection("TestSkillDemo");

        // Удалим из нее все документы
        collection.drop();

        parseCSV();

        System.out.println("Общее количество студентов в базе: " + collection.countDocuments());

        System.out.println("Kоличество студентов старше 40 лет: " + collection.countDocuments(BsonDocument.parse("{age: {$gt: 40}}")));

        BsonDocument query = BsonDocument.parse("{age: 1}");
        collection.find().sort(query).limit(1).forEach((Consumer<Document>) document -> {
            System.out.println("Имя самого молодого студента: "  + document.getString("name"));
        });


        query = BsonDocument.parse("{age: -1}");
        collection.find().sort(query).limit(1).forEach((Consumer<Document>) document -> {
            System.out.println("Cписок курсов самого старого студента: "  + document.get("CourseList"));
        });


    }

    public static void parseCSV () throws IOException {


        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSV_file));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                String name = csvRecord.get(0);
                int age = Integer.valueOf(csvRecord.get(1));
                List<String> list = Collections.singletonList(csvRecord.get(2));

//                System.out.println(list);
                createMongo(name, age, list);

            }
        }
    }

    public static void createMongo(String name, int age, List list) {

        // Создадим первый документ
        Document firstDocument = new Document()
                .append("name", name)
                .append("age", age)
                .append("CourseList", list);

        // Вставляем документ в коллекцию
        collection.insertOne(firstDocument);



    }




}
