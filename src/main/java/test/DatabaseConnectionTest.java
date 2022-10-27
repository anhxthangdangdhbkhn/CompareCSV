package test;

import database.DatabaseConnection;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        DatabaseConnection connect = new DatabaseConnection();
        System.out.println("Xoa row 50: "+ connect.companyDeleteRow(50));
    }
}
