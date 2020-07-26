import java.sql.*;
import java.util.Properties;

public class Database {

    String conUrl = "jdbc:mysql://localhost:3306/schedular";
    String username = "root";
    String password  = "root";

    Connection con;

    public Database () {

    }

    public Connection connect () {
        if ( con == null ) {
            try {
                con = DriverManager.getConnection(conUrl, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return con;
    }

    public void disconnect() {

        if ( con != null ) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



    public boolean checkIfPosted(String query) throws SQLException {

        boolean alreadyPosted = false;

        ResultSet rs;

        Database database = new Database();

        database.connect();
        Statement statement = database.con.createStatement();

        rs = statement.executeQuery(query);

        while (rs.next()) {

            if (rs.getInt(1) > 0) {
                alreadyPosted = true;
            }

        }

        statement.close();
        database.disconnect();

        return alreadyPosted;
    }

}
