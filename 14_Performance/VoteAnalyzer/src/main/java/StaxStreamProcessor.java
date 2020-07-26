
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

        PreparedStatement stmt = DBConnection.getConnection().prepareStatement("INSERT INTO voter_count(name, birthDate, `count`)" +
                "VALUES(?, ?, 1)" +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1");
        DBConnection.setCommitFalse();
        final int batchSize = 500000;
        int count = 0;

        int lineCount = 0;


        long start = System.currentTimeMillis();
        long end;
        double resultTime;



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
                    lineCount = lineCount + batchSize;
                    end = System.currentTimeMillis();
                    resultTime =  (double)(end - start) / 1000 / 60;
                    System.out.println(LocalTime.now().toString() + ": Добавлено " + (lineCount) + " строк в базу, " + "время загрузки: " + resultTime + " minutes");

                    stmt.clearBatch();
                    start = end;
                    count = 0;

                }
            }

        }
        stmt.executeBatch();
        DBConnection.setCommit(stmt);
        lineCount = lineCount + batchSize;
        end = System.currentTimeMillis();
        resultTime =  (double)(end - start) / 1000 / 60;
        System.out.println(LocalTime.now().toString() + ": Добавлено " + (lineCount) + " строк в базу, " + "время загрузки: " + resultTime + " minutes");

        stmt.clearBatch();
        stmt.close();
    }

}
