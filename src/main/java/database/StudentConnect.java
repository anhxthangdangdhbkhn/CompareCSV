package database;

import java.sql.*;

public class StudentConnect {
    private final String url = "jdbc:postgresql://localhost/learn";
    private final String user = "postgres";
    private final String password = "26071993";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void getStudents(){
        String SQL = "SELECT * FROM STUDENT ";
        try(Connection conn = connect();
            Statement stmt = conn.prepareStatement(SQL);
            ResultSet resultSet = stmt.getResultSet();)
        {
            System.out.println("connected");
            System.out.println("data: " + resultSet.getFetchSize());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public long insertStudent(Student student){
//        String SQL = "INSERT INTO \"STUDENT\"(firstname,lastname,email) "
//                + "VALUES(?,?,?)";
//        long id = 0;
//        try(Connection conn = connect();
//        PreparedStatement pstmt = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS)){
//            pstmt.setString(1,student.getFirstname());
//            pstmt.setString(2,student.getLastname());
//            pstmt.setString(3,student.getEmail());
//
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                // get the ID back
//                try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        id = rs.getLong(1);
//                    }
//                } catch (SQLException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return id;
//    }
}
