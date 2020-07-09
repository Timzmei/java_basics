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
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class StaxStreamProcessor implements AutoCloseable {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    private Voter voter;
    private SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private HashMap<Voter, Integer> voterCounts;
    private HashMap<Integer, WorkTime> voteStationWorkTimes;

    public StaxStreamProcessor(InputStream is) throws XMLStreamException {

        reader = FACTORY.createXMLStreamReader(is);

        voterCounts = new HashMap<>();
        voteStationWorkTimes = new HashMap<>();
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

            }
        }
    }

    public void startElement() throws XMLStreamException, ParseException {
        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLEvent.START_ELEMENT &&
                    "voter".equals(reader.getLocalName())) {
                Date birthDay = birthDayFormat.parse(reader.getAttributeValue(null, "birthDay"));
                voter = new Voter(reader.getAttributeValue(null, "name"), birthDay.getTime());
            }
            else  if (event == XMLEvent.START_ELEMENT && voter != null && "visit".equals(reader.getLocalName())) {
                int count = voterCounts.getOrDefault(voter, 0);
                voterCounts.put(voter, count + 1);


                Integer station = Integer.parseInt(reader.getAttributeValue(null, "station"));
                Date time = visitDateFormat.parse(reader.getAttributeValue(null, "time"));
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



    public void printDuplicatedVoters(){
        for (Voter voter : voterCounts.keySet()){
            int count = voterCounts.get(voter);
            if(count > 1){
                System.out.println(voter.toString() + " - " + count);
            }
        }
    }


}
