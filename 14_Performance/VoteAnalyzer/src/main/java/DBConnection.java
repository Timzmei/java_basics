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
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY name_date(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public static void setCommitFalse() throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void setCommit(PreparedStatement stmt) throws SQLException {
        connection.commit();
        stmt.clearBatch();
    }


    public static void executeMultiInsert() throws SQLException {
        System.out.println("Загрузка в БД...");
        String sql = "INSERT INTO voter_count(name, birthDate, `count`)" +
                "VALUES" + insertQuery.toString() +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1";

        DBConnection.getConnection().createStatement().execute(sql);
        System.out.println("Окончание загрузки в БД...");

    }

    public static void countVoter(String name, String birthDay) throws SQLException
    {
        birthDay = birthDay.replace('.', '-');

        boolean isStart = insertQuery.length() == 0;
        insertQuery.append((isStart ? "" : ",") + "('" + name + "', '" + birthDay + "', 1)");

        if (insertQuery.length() > 10000000) {
            executeMultiInsert();
            insertQuery.setLength(0);
        }

//        PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO voter_count(name, birthDate, `count`)" +
//                "VALUES(?, ?, 1)" +
//                "ON DUPLICATE KEY UPDATE `count`=`count` + 1");
//
//        stmt.setString(1, name);
//        stmt.setString(2, birthDay);
//
//        stmt.executeUpdate();
//
//        stmt.close();






//        String sql = "INSERT INTO voter_count(name, birthDate, `count`)" +
//                "VALUES('" + name + "', '" + birthDay + "', 1)" +
//                "ON DUPLICATE KEY UPDATE `count`=`count` + 1";
//
//        DBConnection.getConnection().createStatement().execute(sql);


        int contactId = -1;

        // Вторым параметром передаем массив полей, значниея которых нам нужны
//        PreparedStatement stmt = con.prepareStatement("INSERT INTO jc_contact (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)", new String[] {"contact_id"});





//        String sql = "SELECT id FROM voter_count WHERE birthDate='" + birthDay + "' AND name='" + name + "'";
//        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
//        if(!rs.next())
//        {
//            DBConnection.getConnection().createStatement()
//                    .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
//                            name + "', '" + birthDay + "', 1)");
//        }
//        else {
//            Integer id = rs.getInt("id");
//            DBConnection.getConnection().createStatement()
//                    .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
//        }
//        rs.close();
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
