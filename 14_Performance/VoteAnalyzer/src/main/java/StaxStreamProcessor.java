import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class StaxStreamProcessor implements AutoCloseable {

    private final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    private Voter voter;
    private final SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private HashMap<Voter, Integer> voterCounts;
    private HashMap<Integer, WorkTime> voteStationWorkTimes;

    public StaxStreamProcessor(InputStream is) throws XMLStreamException {

        reader = FACTORY.createXMLStreamReader(is);

//        voterCounts = new HashMap<>();
//        voteStationWorkTimes = new HashMap<>();
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    @Override
    public  void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException ex) {
                SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//
//
//        Session session = sessionFactory.openSession();
            }
        }
    }

    public void startElement() throws XMLStreamException, ParseException, SQLException {

//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure("14_Performance/VoteAnalyzer/src/main/resources/hibernate.cfg.xml").build();
//
//        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
//        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
//
        PreparedStatement stmt = DBConnection.getConnection().prepareStatement("INSERT INTO voter_count(name, birthDate, `count`)" +
                "VALUES(?, ?, 1)" +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1");
        DBConnection.setCommitFalse();
        final int batchSize = 500000;
        int count = 0;
        int countCommit = 0;

        long start = System.currentTimeMillis();
        long end;
        long start1 = System.currentTimeMillis();
        long end1;



        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT &&
                    "voter".equals(reader.getLocalName())) {
//                Date birthDay = birthDayFormat.parse(reader.getAttributeValue(null, "birthDay"));
//                Learn learn = new Learn();
//                learn.setBirthDate(birthDay);
//                learn.setName(reader.getAttributeValue(null, "name"));
//                session.save(learn);
//                session.beginTransaction().commit();
//                DBConnection.countVoter(reader.getAttributeValue(null, "name"), reader.getAttributeValue(null, "birthDay"));

                stmt.setString(1, reader.getAttributeValue(null, "name"));
                stmt.setString(2, reader.getAttributeValue(null, "birthDay"));
//                stmt.executeUpdate();

                stmt.addBatch();
//                System.out.println("addBatch: " + (++count));
//
//
//                end1 = System.currentTimeMillis();
//                System.out.println("Parsing Duration: " + (end1 - start1) + " ms");
//                start1 = end1;

                if(++count % batchSize == 0) {
                    stmt.executeBatch();
                    DBConnection.setCommit(stmt);

                    System.out.println(++countCommit);


//                    end = System.currentTimeMillis();
//                    System.out.println("Parsing Duration: " + (end - start)/1000 + " s");
//                    start = end;


                    count = 0;
                }

//                count++;
//                System.out.println(count);
//                voter = new Voter(reader.getAttributeValue(null, "name"), birthDay.getTime());
            }
//            else  if (event == XMLEvent.START_ELEMENT && voter != null && "visit".equals(reader.getLocalName())) {
//                int count = voterCounts.getOrDefault(voter, 0);
////                voterCounts.put(voter, count + 1);
//
//
//                Integer station = Integer.parseInt(reader.getAttributeValue(null, "station"));
//                Date time = visitDateFormat.parse(reader.getAttributeValue(null, "time"));
//                WorkTime workTime = voteStationWorkTimes.get(station);
//                if(workTime == null)
//                {
//                    workTime = new WorkTime();
//                    voteStationWorkTimes.put(station, workTime);
//                }
//                workTime.addVisitTime(time.getTime());
//
//            }
        }
        stmt.executeBatch();
        DBConnection.setCommit(stmt);
        stmt.close();


//        DBConnection.executeMultiInsert();
//        session.close();
//        sessionFactory.close();
//        DBConnection.executeMultiInsert();

    }



}
