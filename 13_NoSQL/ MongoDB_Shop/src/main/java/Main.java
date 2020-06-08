import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.UnwindOptions;
import org.bson.Document;

import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonParseException;
import org.bson.json.JsonWriter;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

import java.io.IOException;
import static com.mongodb.client.model.Aggregates.*;



public class Main {

//    private static final String CSV_file= "13_NoSQL/Mongo/src/data/mongo.csv";

    private static MongoCollection<Document> collection;
    private static MongoCollection<Document> collection_shop;
    private static MongoCollection<Document> collection_item;

    private static String[] textSplit;
//    private static String regex = "\\d+";


    public static void main(String[] args) throws IOException, JsonParseException {


        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");

        MongoDatabase database = mongoClient.getDatabase("local");

        // Создаем коллекцию
        collection = database.getCollection("TestSkillDemo");
        collection_shop = database.getCollection("ShopListDemo");
        collection_item = database.getCollection("ItemListDemo");


        // Удалим из коллекций все документы
        collection.drop();
        collection_shop.drop();
        collection_item.drop();



        //Вывести описание команд
        printInstructions();

        Scanner scanner = new Scanner(System.in);


        //считываем команду
        try {


            while (true) {
                String text = scanner.nextLine();

                textSplit = text.split("\\s");
                String command = textSplit[0];
                if (command.equals("end")) {
                    break;
                }
                switch (command) {
                    case "ADD_SHOP": // Добавить магазин
                        String shopName = textSplit[1];
                        addShop(shopName);
                        break;
                    case "ADD_ITEM": // Добавить товар
                        String itemName = textSplit[1];
                        int priceItem = Integer.parseInt(textSplit[2]);
                        addItem(itemName, priceItem);
                        break;
                    case "SET_ITEM": // Выставить товар в магазин
                        itemName = textSplit[1];
                        shopName = textSplit[2];
                        setItem(itemName, shopName);
                        break;
                    case "STATISTICS": // Вывести статистику по магазинам
                        showStatistics();
                        break;
                    case "?":
                        printInstructions();
                        break;
                    default:
                        System.out.println("команда не распознана");
                        printInstructions();
                        break;
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    // Чтобы не забыть команды
    public static void printInstructions(){

        System.out.println("— Команда добавления магазина\n* ADD_SHOP имя_магазина - сначала идет название команды, а потом имя магазина, всегда одно слово, без пробелов");
        System.out.println("Например ADD_SHOP Девяточка\n");

        System.out.println("— Команда добавления товара\n* ADD_ITEM наименование_товара стоимость - сначала идет название команды, а потом название товара, всегда одно слово, " +
                "без пробелов, затем целое число — цена товара в рублях");
        System.out.println("Например ADD_ITEM Вафли 54\n");

        System.out.println("— Команда добавления товара в магазин\n* SET_ITEM наименование_товара имя_магазина - сначала идет название команды, а потом название товара, затем название магазина");
        System.out.println("Например SET_ITEM Вафли Девяточка\n");

        System.out.println("— Команда получения информации о товарах во всех магазинах\n" +
                "STATISTICS");

        System.out.println("— Выход\n" +
                "end\n\n");
    }


    public static void addShop (String shopName) {
        ArrayList<String> itemList = new ArrayList<>();
        Document shopDocument = new Document()
                .append("name", shopName)
                .append("itemList", itemList);

        if ((collection_shop.find(BsonDocument.parse("{name: {$eq: \"" + shopName + "\"}}")).first() == null)){
            collection_shop.insertOne(shopDocument);
        }
        else {
            System.out.println("Магазин " + shopName + " уже имеется. Придумайте новое название магазина");
        }
    }


    public static void addItem(String itemName, int priceItem) {
        Document itemDocument = new Document()
                .append("name", itemName)
                .append("price", priceItem);

        if ((collection_item.find(BsonDocument.parse("{name: {$eq: \"" + itemName + "\"}}")).first() == null)){
            collection_item.insertOne(itemDocument);
        }
        else {
            System.out.println("Товар " + itemName + " уже имеется. Придумайте новый товар");
        }
    }

    public static void setItem(String itemName, String shopName){
        BsonDocument queryItem = BsonDocument.parse("{name: {$eq: \"" + itemName + "\"}}");
        BsonDocument queryShop = BsonDocument.parse("{name: {$eq: \"" + shopName + "\"}}");
        try {
            if (!(collection_item.find(queryItem).first() == null) && !(collection_shop.find(queryShop).first() == null)) {
                if (!((collection_shop.find(queryShop).first().getList("itemList", String.class).size() == 0))) {
                    collection_shop.findOneAndUpdate(queryShop, BsonDocument.parse("{$addToSet: {itemList: \"" + itemName + "\"}}"));
                }
                else {
                    collection_shop.findOneAndUpdate(queryShop, BsonDocument.parse("{$set: {itemList: [\"" + itemName + "\"]}}"));
                }
            }
            else {
                System.out.println("магазин: " + collection_shop.find(queryShop).first() + ", товар: " + collection_item.find(queryItem).first());
                System.out.println("Товар или магазин не найден");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void showStatistics(){
        // Общее количество товаров

        System.out.println("\n\nОбщее количество товаров в каждом магазине\n");

        collection_shop.aggregate(Arrays.asList(unwind("$itemList"), sortByCount("$name"))).forEach((Consumer<Document>) document -> {
            System.out.println("Наименование магазина: " + document.get("_id") + " - количество товара: " + document.get("count"));
        });

        // Среднюю цену товара

        System.out.println("\n\nСредняя цена товара в каждом магазине:\n");

        collection_shop.aggregate(Arrays.asList(
                lookup("ItemListDemo", "itemList", "name", "priceItem"),
                unwind("$priceItem"),
                group("$name", Accumulators.avg("avgPriceItem","$priceItem.price"))
        )).forEach((Consumer<Document>) document -> System.out.println("Наименование магазина: " + document.get("_id") + " - Средняя цена товара: " + document.get("avgPriceItem")));

        // Самый дорогой и самый дешевый товар

        System.out.println("\n\nСамый дорогой и самый дешевый товар в каждом магазине:\n");

        collection_shop.aggregate(Arrays.asList(
                lookup("ItemListDemo", "itemList", "name", "priceItem"),
                unwind("$priceItem"),
                sort(new Document("name", 1)
                        .append("priceItem.price", 1)),
                group("$name", Arrays.asList(
                        Accumulators.first("minNameItem", "$priceItem.name"),
                        Accumulators.last("maxNameItem", "$priceItem.name"),
                        Accumulators.min("minPriceItem", "$priceItem.price"),
                        Accumulators.max("maxPriceItem", "$priceItem.price")
                ))
        )).forEach((Consumer<Document>) document ->
                System.out.println("Наименование магазина: " + document.get("_id") + "\n\t " +
                        "- Cамый дешевый товар: " + document.get("minNameItem") + " - " + document.get("minPriceItem") + " руб." + "\n\t " +
                        "- Cамый дорогой товар: " + document.get("maxNameItem") + " - " + document.get("maxPriceItem") + " руб."));




        // Количество товаров, дешевле 100 рублей.
        System.out.println("\n\nКоличество товаров, дешевле 100 рублей в каждом магазине:\n");


        collection_shop.aggregate(Arrays.asList(
                lookup("ItemListDemo", "itemList", "name", "priceItem"),
                unwind("$priceItem"),
                match(new Document("priceItem.price",new Document("$lt", 100))),
                addFields(new Field<>("count", 1)),
                group("$name",Accumulators.sum("count", "$count"))
        )).forEach((Consumer<Document>) document ->
                System.out.println("Наименование магазина: " + document.get("_id") + "\n\t " +
                        "- Количество товаров, дешевле 100 рублей: " + document.get("count")));


    }

}
