import java.sql.*;

public class DBConnection
{
    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "kol97szc";

    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                    "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter");

                connection.createStatement().execute("CREATE TABLE voter(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
//                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id))");

                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");

                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id))");


//                connection.createStatement().execute("CREATE TABLE voter_count(" +
//                        "id INT NOT NULL AUTO_INCREMENT, " +
//                        "name TINYTEXT NOT NULL, " +
//                        "birthDate DATE NOT NULL, " +
//                        "`count` INT NOT NULL, " +
//                        "PRIMARY KEY(id), " +
//                        "UNIQUE KEY name_date(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public static void disableAutoCommit() throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void setCommit(PreparedStatement stmt) throws SQLException {
        connection.commit();
        stmt.clearBatch();
    }

    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
