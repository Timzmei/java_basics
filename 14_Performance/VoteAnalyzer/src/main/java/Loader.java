import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Loader
{
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
    private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

    public static void main(String[] args) throws Exception
    {


        String fileName = "14_Performance/VoteAnalyzer/res/data-18M.xml";


        long start = System.currentTimeMillis();

//
//
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        SAXParser parser = factory.newSAXParser();
//        XMLHandler handler = new XMLHandler();
//        parser.parse(new File(fileName), handler);
//
//
//
//        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
//        System.out.println("SAXParser - " + usage);
//
//        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get("14_Performance/VoteAnalyzer/res/data-1572M.xml")))) {
            XMLStreamReader reader = processor.getReader();
            processor.startElement();
//            processor.printDuplicatedVoters();
        }
//
//
//
//
//        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
//        System.out.println("StAXParser - " + usage);
//
//
//        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();


//        parseFile(fileName);
        System.out.println("Parsing Duration: " + ((System.currentTimeMillis() - start))/1000 + " s");

        DBConnection.printVoterCounts();



    }

    private static void parseFile(String fileName) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));

        findEqualVoters(doc);
//        fixWorkTimes(doc);
    }

    private static void findEqualVoters(Document doc) throws Exception
    {
        NodeList voters = doc.getElementsByTagName("voter");
        int votersCount = voters.getLength();
        for(int i = 0; i < votersCount; i++)
        {
            Node node = voters.item(i);
            NamedNodeMap attributes = node.getAttributes();

            String name = attributes.getNamedItem("name").getNodeValue();
            String birthDay = attributes.getNamedItem("birthDay").getNodeValue();
//            Date birthDay = birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue());

            DBConnection.countVoter(name, birthDay);
//            System.out.println(i);
//            Voter voter = new Voter(name, birthDay.getTime());
//            Integer count = voterCounts.get(voter);
//            voterCounts.put(voter, count == null ? 1 : count + 1);
        }
        DBConnection.executeMultiInsert();
    }

    private static void fixWorkTimes(Document doc) throws Exception
    {
        NodeList visits = doc.getElementsByTagName("visit");
        int visitCount = visits.getLength();
        for(int i = 0; i < visitCount; i++)
        {
            Node node = visits.item(i);
            NamedNodeMap attributes = node.getAttributes();

            Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
            Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
            WorkTime workTime = voteStationWorkTimes.get(station);
            if(workTime == null)
            {
                workTime = new WorkTime();
                voteStationWorkTimes.put(station, workTime);
            }
            workTime.addVisitTime(time.getTime());
        }
    }
}