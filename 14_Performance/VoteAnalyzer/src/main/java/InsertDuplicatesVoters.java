import java.sql.*;

public class InsertDuplicatesVoters {



    private Connection conn;

    private final String checkDuplicates = "SELECT name, birthDate, COUNT(*) FROM voter " +
            "GROUP BY name, birthDate HAVING COUNT(*) > 1";


    private final String insertDuplicates = "INSERT INTO voter_count(name, birthDate, count) " +
            checkDuplicates;

    private final String insertDuplicates2 = "INSERT INTO voter_count(name, birthDate, count) " +
            "VALUES(?, ?, ?)";


    public InsertDuplicatesVoters() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public void addDuplicates() throws SQLException {
        conn.createStatement().executeUpdate(insertDuplicates);
//        conn.close();
    }


}
