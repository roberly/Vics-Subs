//package Database;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import address.model.Person;
//
//public class DBoperation {
//
//
//	 /*
//     * Update address book ...
//     */
//    public static Statement addressBookUpdate(DBConnection database, Person temp) throws SQLException {
//        Connection connection = database.getConnection();
//        Statement statement = connection.createStatement();
//
//
//        String update_command = "update person set dbfirstName" + " = '" + temp.getFirstName() + "'," + "dblastName" + " = '" + temp.getLastName() +
//        		  "'" + ", dbstreet = '" + temp.getStreet() +"'" + ", dbpostalCode = " + temp.getPostalCode() + "," + "dbcity = '" + temp.getCity() + "'" + ", salary = '" + temp.getSalary() + "'" + " where dbpersonID = " + temp.getId() + ";";
//
//        statement.executeUpdate(update_command);
//        return statement;
//    }
//
//    public static Statement addressBookInsert(DBConnection database, Person temp) throws SQLException {
//        Connection connection = database.getConnection();
//        Statement statement = connection.createStatement();
//
//
//        String insert_command = "insert into person values (" + "'" + temp.getId() + "'" + "," + "'" + temp.getFirstName() + "'" + "," + "'" + temp.getLastName() + "'" + "," + "'" + temp.getStreet() + "'" + "," + "'" + temp.getPostalCode() + "'" + "," + "'" + temp.getCity() + "'" + "," + "'" + temp.getSalary() + "'" + ");";
//
//        statement.executeUpdate(insert_command);
//        return statement;
//    }
//
//    /**
//     * delete record from address book ...
//     */
//    public static Statement addressBookDeleteRecordPerson(DBConnection database, Connection connection, Person temp) throws SQLException {
//        connection = database.getConnection();
//        Statement statement = connection.createStatement();
//
//        String del_command = "delete from person where dbpersonID = " + temp.getId();
//        statement.executeUpdate(del_command);
//        return statement;
//    }
//}