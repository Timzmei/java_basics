
import javax.persistence.Cacheable;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;


public class StaxStreamProcessor implements AutoCloseable {

    private final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    public StaxStreamProcessor(InputStream is) throws XMLStreamException {

        reader = FACTORY.createXMLStreamReader(is);

    }

    @Override
    public  void close() {

    }

    public void startElement() throws XMLStreamException, SQLException {

        BatchUploader batchUploader = new BatchUploader();

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT &&
                    "voter".equals(reader.getLocalName())) {
//                System.out.println(++count);
                String name = reader.getAttributeValue(null, "name");
                String birthDay = reader.getAttributeValue(null, "birthDay");

                batchUploader.addVoter(name, birthDay);
            }

        }
        batchUploader.uploadBatch(batchUploader.getBatchSize());
        batchUploader.closeLoading();
    }


}
