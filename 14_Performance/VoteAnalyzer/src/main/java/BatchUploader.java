import java.io.BufferedReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;

public class BatchUploader {

    private PreparedStatement stmt;
//    private final String statementInsertVoters = "INSERT INTO voter_count(name, birthDate, `count`)" +
//            "VALUES(?, ?, 1)" +
//            "ON DUPLICATE KEY UPDATE `count`=`count` + 1";

    private final String statementInsertVoters = "INSERT INTO voter(name, birthDate)" +
            "VALUES(?, ?)";



    private final int maxBatchSize = 500000;



    private int batchSize;

    public BatchUploader() throws SQLException {

        stmt = DBConnection.getConnection().prepareStatement(statementInsertVoters);
        DBConnection.disableAutoCommit();
        batchSize = 0;

    }

    public void addVoter(String name, String birthDay) throws SQLException {


        stmt.setString(1, name);
        stmt.setString(2, birthDay);

        stmt.addBatch();
        ++batchSize;

//        System.out.println(stmt.getFetchSize());
        if(getBatchSize() > maxBatchSize) {
            uploadBatch(getBatchSize());
            batchSize = 0;
        }

    }

    public void uploadBatch(int batchSize) throws SQLException {
        long start = System.currentTimeMillis();
        System.out.print(LocalTime.now().toString() + " - время загрузки партии из " + batchSize + " строк: ");
        stmt.executeBatch();
        DBConnection.setCommit(stmt);
        long end = System.currentTimeMillis();
        System.out.println(getMinutes(start, end) + " minutes");
        stmt.clearBatch();
    }

    public void closeLoading() throws SQLException {
        stmt.close();
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize() {
        ++batchSize;
    }

    public double getMinutes(long start, long end){
        return (double) (end - start) / 1000 / 60;
    }

}
