/*
 * Creates sqlite connection and sqlite utility methods
 * 
 */
package com.mgeiger.fitnesseteststore;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Administrator
 */
class FitnesseTestStore {

    private static Connection connection;

    public void setConnection() {       
        connection = null;
        
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            //Statement statement = connection.createStatement();
            //statement.setQueryTimeout(30);  // set timeout to 30 sec.
        /*
         *  TODO: move out to readme
         * --------------------------------------------------------
            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
            System.out.println("id = " + rs.getInt("id"));
            }
            statement.executeUpdate("drop table if exists fitnesse_tests");
            statement.executeUpdate("create table fitnesse_tests (id integer, name string, application_id integer)");
            statement.executeUpdate("insert into fitnesse_tests values(1, 'test_one', 1)");
            statement.executeUpdate("insert into fitnesse_tests values(2, 'test_two', 2)");
            ResultSet rstwo = statement.executeQuery("select * from fitnesse_tests");
            while (rstwo.next()) {
            // read the result set
            System.out.print("name = " + rstwo.getString("name"));
            System.out.print(" id = " + rstwo.getInt("id"));
            System.out.println(" application_id = " + rstwo.getInt("application_id"));
            }
        * --------------------------------------------------------
        */
        } catch (SQLException e) {
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        // parse fitness tests from Xebium project
        FetchFitnesseTests testListObj = new FetchFitnesseTests();
        String dir = "c:\\<FITNESSE TESTS DIR>\\";
        File currentDir = new File(dir);
        List tests = FetchFitnesseTests.generateList(currentDir);

        try {
            FitnesseTestStore dbh = new FitnesseTestStore();
            Connection connect = dbh.getConnection();
            dbh.setConnection();
            Statement statement = connection.createStatement();
            /*
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists xebium_test");
            statement.executeUpdate("create table xebium_test (id integer, test_path string, application_id integer, created_at TIMESTAMP)");

            int t = 0; // counter
            DateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (t < tests.size()) {
                statement.executeUpdate("insert into xebium_test values(" + t + ", '" + tests.get(t) + "', NULL, current_timestamp)");
                ++t;
            }
            * 
            */

            ResultSet rs = statement.executeQuery("select * from xebium_test");
            while (rs.next()) {
                // read the result set
                System.out.println("test_path = " + rs.getInt("id"));
                System.out.println("test_path = " + rs.getString("test_path"));
                System.out.println("created_at = " + rs.getString("created_at"));
            }

            dbh.closeConnection();

        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }

        System.out.println("Number of tests: " + tests.size());
    }
}
