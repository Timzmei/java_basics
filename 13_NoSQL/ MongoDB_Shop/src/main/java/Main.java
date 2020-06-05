import com.mongodb.client.*;
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

        addShop("magnit");
        addShop("pyatiorochka");

        addItem("apple", 52);
        addItem("fruit", 34);
        addItem("water", 68);
        addItem("egg", 123);
        addItem("bread", 12);

        setItem("apple", "magnit");
        setItem("fruit", "magnit");
        setItem("water", "magnit");
        setItem("egg", "magnit");
        setItem("bread", "magnit");

        setItem("apple", "pyatiorochka");
        setItem("fruit", "pyatiorochka");
        setItem("bread", "pyatiorochka");

        showStatistics();

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
                    case "ADD_SHOP":
                        String shopName = textSplit[1];
                        addShop(shopName);
                        break;
                    case "ADD_ITEM":
                        String itemName = textSplit[1];
                        int priceItem = Integer.parseInt(textSplit[2]);
                        addItem(itemName, priceItem);
                        break;
                    case "SET_ITEM":
                        itemName = textSplit[1];
                        shopName = textSplit[2];
                        setItem(itemName, shopName);
                        break;
                    case "STATISTICS":
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
                "end");
    }

    public static void addShop (String shopName) {
        // Создаем магазин
        ArrayList<String> itemList = new ArrayList<>();
        Document shopDocument = new Document()
                .append("name", shopName)
                .append("itemList", itemList);

        // Вставляем магазин в коллекцию

        if ((collection_shop.find(BsonDocument.parse("{name: {$eq: \"" + shopName + "\"}}")).first() == null)){
            collection_shop.insertOne(shopDocument);
            System.out.println(collection_shop.find(BsonDocument.parse("{name: {$eq: \"" + shopName + "\"}}")).first());
        }
        else {
            System.out.println("Магазин " + shopName + " уже имеется. Придумайте новое название магазина");
        }




    }


    public static void addItem(String itemName, int priceItem) {
        // Создаем товар
        Document itemDocument = new Document()
                .append("name", itemName)
                .append("price", priceItem);


        if ((collection_item.find(BsonDocument.parse("{name: {$eq: \"" + itemName + "\"}}")).first() == null)){
            collection_item.insertOne(itemDocument);
            System.out.println(collection_item.find(BsonDocument.parse("{name: {$eq: \"" + itemName + "\"}}")).first());
        }
        else {
            System.out.println("Товар " + itemName + " уже имеется. Придумайте новый товар");
        }

        // Вставляем товар в коллекцию

    }

    public static void setItem(String itemName, String shopName){
        BsonDocument queryItem = BsonDocument.parse("{name: {$eq: \"" + itemName + "\"}}");
        BsonDocument queryShop = BsonDocument.parse("{name: {$eq: \"" + shopName + "\"}}");
        ArrayList<String> oldShopItemList = new ArrayList<>();
        ArrayList<String> newShopItemList = new ArrayList<>();

        try {
            if (!(collection_item.find(queryItem).first() == null) && !(collection_shop.find(queryShop).first() == null)) {

                System.out.println(collection_shop.find(queryShop).first());

                if (!((collection_shop.find(queryShop).first().getList("itemList", Arrays.class).size() == 0))) {


                    oldShopItemList.addAll(((ArrayList<String>)collection_shop.find(queryShop).first().get("itemList")));
                    newShopItemList.addAll(oldShopItemList);
                    newShopItemList.add(itemName);

                    collection_shop.findOneAndUpdate(queryShop, BsonDocument.parse("{$set: {itemList: \"" + newShopItemList + "\"}}"));
//                    System.out.println(collection_shop.find(queryShop).first());
                }
                else {

                    newShopItemList.add(itemName);

                    collection_shop.findOneAndUpdate(queryShop, convertnewShopItemList));


                    collection_shop.findOneAndUpdate(queryShop, BsonDocument.parse("{$set: {itemList: \"" + newShopItemList + "\"}}"));
//                    System.out.println(collection_shop.find(queryShop).first());

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

        Bson lookup = new Document("$lookup",
                new Document("from", "collection_item")
                        .append("localField", "itemList")
                        .append("foreignField", "price")
                        .append("as", "look_price"));

        List<Bson> filters = new ArrayList<>();
        filters.add(lookup);

        AggregateIterable<Document> it = collection_shop.aggregate(filters);

        for (Document row : it) {
            System.out.println(row.toJson());
        }

//        System.out.println(collection_shop.aggregate(Arrays.asList(match())));


//        Bson lookup = new Document("$lookup",
//                new Document("from", "coll_two")
//                        .append("localField", "foreign_id")
//                        .append("foreignField", "_id")
//                        .append("as", "look_coll"));
//
//        Bson match = new Document("$match",
//                new Document("look_coll.actif", true));
//
//        List<Bson> filters = new ArrayList<>();
//        filters.add(lookup);
//        filters.add(match);
//
//        AggregateIterable<Document> it = db.getCollection("coll_one").aggregate(filters);
//
//        for (Document row : it) {
//            System.out.println(row.toJson());
//        }

    }

}
