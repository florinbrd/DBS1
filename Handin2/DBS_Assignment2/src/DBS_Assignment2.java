import java.sql.*;
import java.util.ArrayList;


public class DBS_Assignment2 {
    public static void main(String[] args) {
        String driver = "org.postgresql.Driver";

        String url = "jdbc:postgresql://localhost:5432/postgres";

        String user = "postgres";
        String pw = "***********"; //insert your own password

        Connection connection = null;

        //Loading JDBC driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, user, pw);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //setupDB(connection); -- > to initialize DB if not existent
        insertCustomer(connection);
        selectCustomer(connection);
        //selectAndOrderCustomerData(connection); -- > select and order by customerNo
    }

  /*  private static void selectAndOrderCustomerData(Connection connection) {
        try {
            String querysql2 = "SELECT * FROM  \"Snow School\".Customer " + "ORDER BY customerNo;";

            PreparedStatement queryCustomerStatement = connection.prepareStatement(querysql2);
            ResultSet resultSet = queryCustomerStatement.executeQuery();

            ArrayList<Object[]> results = new ArrayList<Object[]>();
            while (resultSet.next()) {
                Object[] row = new Object[resultSet.getMetaData().getColumnCount()];

                for (int i = 0; i < row.length; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                results.add(row);
            }
            resultSet.close();
            queryCustomerStatement.close();

            for (int i = 0; i < results.size(); i++) {
                Object[] row = results.get(i);
                int customerNo = Integer.parseInt(row[0].toString());
                String fName = row[1].toString();
                String lName = row[2].toString();
                int age = Integer.parseInt(row[3].toString());
                String  phoneNumber = row[4].toString();
                String eMail = row[5].toString();
                String address = row[6].toString();

                System.out.println(customerNo + " " + fName + " " + lName + " (" + phoneNumber
                        + ") - " + age + " - " + " - " + eMail + " - " + address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
    private static void selectCustomer(Connection connection) {
        try {
            String querysql = "SELECT * FROM \"Snow school\".Customer ;";
            PreparedStatement queryCustomerStatement = connection.prepareStatement(querysql);
            ArrayList<Object[]> results;
            try (ResultSet resultSet = queryCustomerStatement.executeQuery()) {

                results = new ArrayList<Object[]>();

                while (resultSet.next()) {
                    Object[] row = new Object[resultSet.getMetaData().getColumnCount()];

                    for (int i = 0; i < row.length; i++) {
                        row[i] = resultSet.getObject(i + 1);
                    }
                    results.add(row);
                }
            }
            queryCustomerStatement.close();

            for (int i = 0; i < results.size(); i++) {
                Object[] row = results.get(i);
                int retrievedCustomerNo = Integer.parseInt(row[0].toString());
                String retrievedFirstName = row[1].toString();
                String retrievedLastName = row[2].toString();
                String retrievedAge = row[3].toString();
                int retrievedPhoneNumber = Integer.parseInt(row[4].toString());
                String retrievedEmail = row[5].toString();
                String retrievedAddress = row[6].toString();


                System.out.println("----- " + " Customer NO: " + retrievedCustomerNo + " ----- " + " First Name: " + retrievedFirstName + " " + " ----- " + " Last Name: " + retrievedLastName + " ----- "
                        + " Age: " + retrievedAge + " ----- " + " Phone number: " + retrievedPhoneNumber + " ----- " +
                        " Email: " + retrievedEmail + " ----- " + " Address: " + retrievedAddress);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertCustomer(Connection connection) {
        String preparedSql = " INSERT INTO \"Snow school\".Customer VALUES(?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(preparedSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (preparedStatement != null) {
            addCustomerToDatabase(preparedStatement, 3223, "Zeke ", "Qwert", "50444782",
                    25, "ZZ@top.com", "St. Michel");
        }
    }

    //DB Setup
   /* private static void setupDB(Connection connection) {
         String sql = "CREATE SCHEMA IF NOT EXISTS \"Snow School\";";
         try {
             Statement statement = connection.createStatement();
             statement.execute(sql);
             System.out.println("Schema created");
         } catch (SQLException e) {
             e.printStackTrace();
         }

         sql = "CREATE TABLE IF NOT EXISTS \"Snow School\".Customer("
                 + "  customerNo integer NOT NULL PRIMARY KEY,"
                 + "  fName varchar(10) NOT NULL,"
                 + "  lName varchar(15) NOT NULL,"
                 + "  phoneNumber varchar (13) NOT NULL,"
                 + " age integer  NOT NULL,"
                 + " eMail varchar (30) NOT NULL,"
                 + " address varchar (35) NOT NULL," +
                 "constraint customerPK primary key (customerNo)" + ");";

         try {
             Statement statement = connection.createStatement();
             statement.execute(sql);
             System.out.println("Table created");
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return;
     }
*/
    private static void addCustomerToDatabase(PreparedStatement customerStatement, int customerNo, String fName, String lName, String phoneNumber, int age, String eMail, String address) {
        try {
            customerStatement.setInt(1, customerNo);
            customerStatement.setString(2, fName);
            customerStatement.setString(3, lName);
            customerStatement.setString(4, phoneNumber);
            customerStatement.setInt(5, age);
            customerStatement.setString(6, eMail);
            customerStatement.setString(7, address);
            customerStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


