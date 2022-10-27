package database;

import java.sql.*;

public class MyConnect {
    private final String url = "jdbc:postgresql://localhost/dvdrental";
    private final String user = "postgres";
    private final String password = "26071993";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void getActors() {

        String SQL = "SELECT actor_id,first_name, last_name FROM actor";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            // display actor information
            displayActor(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getActorCount() {
        String SQL = "SELECT count(*) FROM actor";
        int count = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return count;
    }

    private void displayActor(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("actor_id") + "\t"
                    + rs.getString("first_name") + "\t"
                    + rs.getString("last_name"));

        }
    }

    public void findActorByID(int actorID) {
        String SQL = "SELECT actor_id,first_name,last_name "
                + "FROM actor "
                + "WHERE actor_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, actorID);
            ResultSet rs = pstmt.executeQuery();
            displayActor(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int deleteActor(int id) {
        String SQL = "DELETE FROM actor WHERE actor_id = ?";

        int affectedrows = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

}
