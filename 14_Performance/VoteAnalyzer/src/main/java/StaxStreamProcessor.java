
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

        PreparedStatement stmt = DBConnection.getConnection().prepareStatement("INSERT INTO voter_count(name, birthDate, `count`)" +
                "VALUES(?, ?, 1)" +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1");
        DBConnection.setCommitFalse();
        final int batchSize = 500000;
        int count = 0;

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT &&
                    "voter".equals(reader.getLocalName())) {

                stmt.setString(1, reader.getAttributeValue(null, "name"));
                stmt.setString(2, reader.getAttributeValue(null, "birthDay"));

                stmt.addBatch();

                if(++count % batchSize == 0) {
                    stmt.executeBatch();
                    DBConnection.setCommit(stmt);
                    count = 0;
                }
            }

        }
        stmt.executeBatch();
        DBConnection.setCommit(stmt);
        stmt.close();
    }

}
