package database;

import FMPGM.p134.Product134;
import PostgreSQL.CSVRow;

import java.sql.*;
import java.util.*;

public class DatabaseConnection {

    private final String url = "jdbc:postgresql://localhost/csv";
    private final String user = "postgres";
    private final String password = "26071993";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch ( SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }



    public long insertRow(CSVRow csvRow){
        String SQL = "INSERT INTO DBBFFFFP(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL) "
                                 + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        long id =0;


        try(Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
        ) {
            pstmt.setString(1,csvRow.getA());
            pstmt.setString(2,csvRow.getB());
            pstmt.setString(3,csvRow.getC());
            pstmt.setString(4,csvRow.getD());
            pstmt.setString(5,csvRow.getE());
            pstmt.setString(6,csvRow.getF());
            pstmt.setString(7,csvRow.getG());
            pstmt.setString(8,csvRow.getH());
            pstmt.setString(9,csvRow.getI());
            pstmt.setString(10,csvRow.getJ());
            pstmt.setString(11,csvRow.getK());
            pstmt.setString(12,csvRow.getL());
            pstmt.setString(13,csvRow.getM());
            pstmt.setString(14,csvRow.getN());
            pstmt.setString(15,csvRow.getO());
            pstmt.setString(16,csvRow.getP());
            pstmt.setString(17,csvRow.getQ());
            pstmt.setString(18,csvRow.getR());
            pstmt.setString(19,csvRow.getS());
            pstmt.setString(20,csvRow.getT());
            pstmt.setString(21,csvRow.getU());
            pstmt.setString(22,csvRow.getV());
            pstmt.setString(23,csvRow.getW());
            pstmt.setString(24,csvRow.getX());
            pstmt.setString(25,csvRow.getY());
            pstmt.setString(26,csvRow.getZ());
            pstmt.setString(27,csvRow.getAA());
            pstmt.setString(28,csvRow.getAB());
            pstmt.setString(29,csvRow.getAC());
            pstmt.setString(30,csvRow.getAD());
            pstmt.setString(31,csvRow.getAE());
            pstmt.setString(32,csvRow.getAF());
            pstmt.setString(33,csvRow.getAG());
            pstmt.setString(34,csvRow.getAH());
            pstmt.setString(35,csvRow.getAI());
            pstmt.setString(36,csvRow.getAJ());
            pstmt.setString(37,csvRow.getAK());
            pstmt.setString(38,csvRow.getAL());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return  id;
    }

    public int csvInsertRows(List<CSVRow> list){
        String SQL = "INSERT INTO DBBFFFFP(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        int count = 0;

        try (
                Connection conn = connect();
                PreparedStatement statement = conn.prepareStatement(SQL);) {


            for (CSVRow csvRow : list) {

                statement.setString(1,csvRow.getA());
                statement.setString(2,csvRow.getB());
                statement.setString(3,csvRow.getC());
                statement.setString(4,csvRow.getD());
                statement.setString(5,csvRow.getE());
                statement.setString(6,csvRow.getF());
                statement.setString(7,csvRow.getG());
                statement.setString(8,csvRow.getH());
                statement.setString(9,csvRow.getI());
                statement.setString(10,csvRow.getJ());
                statement.setString(11,csvRow.getK());
                statement.setString(12,csvRow.getL());
                statement.setString(13,csvRow.getM());
                statement.setString(14,csvRow.getN());
                statement.setString(15,csvRow.getO());
                statement.setString(16,csvRow.getP());
                statement.setString(17,csvRow.getQ());
                statement.setString(18,csvRow.getR());
                statement.setString(19,csvRow.getS());
                statement.setString(20,csvRow.getT());
                statement.setString(21,csvRow.getU());
                statement.setString(22,csvRow.getV());
                statement.setString(23,csvRow.getW());
                statement.setString(24,csvRow.getX());
                statement.setString(25,csvRow.getY());
                statement.setString(26,csvRow.getZ());
                statement.setString(27,csvRow.getAA());
                statement.setString(28,csvRow.getAB());
                statement.setString(29,csvRow.getAC());
                statement.setString(30,csvRow.getAD());
                statement.setString(31,csvRow.getAE());
                statement.setString(32,csvRow.getAF());
                statement.setString(33,csvRow.getAG());
                statement.setString(34,csvRow.getAH());
                statement.setString(35,csvRow.getAI());
                statement.setString(36,csvRow.getAJ());
                statement.setString(37,csvRow.getAK());
                statement.setString(38,csvRow.getAL());

                statement.addBatch();
                count++;
                // execute every 100 rows or less
                if (count % 100 == 0 || count == list.size()) {
                    statement.executeBatch();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return count;

    }

    public List<CSVRow> csvSearchW(String Wdata,String Snotdata){

        String SQL = "SELECT  * "
                + " FROM DBBFFFFP "
                + " WHERE W = ? "
                + " AND NOT S = ? ";
        List<CSVRow> csvRowList = new ArrayList<CSVRow>();




        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, Wdata);
            pstmt.setString(2, Snotdata);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CSVRow csvRow = new CSVRow();

                csvRow.setID(rs.getInt(1));
                csvRow.setA(rs.getString(2));
                csvRow.setB(rs.getString(3));
                csvRow.setC(rs.getString(4));
                csvRow.setD(rs.getString(5));
                csvRow.setE(rs.getString(6));
                csvRow.setF(rs.getString(7));
                csvRow.setG(rs.getString(8));
                csvRow.setH(rs.getString(9));
                csvRow.setI(rs.getString(10));
                csvRow.setJ(rs.getString(11));
                csvRow.setK(rs.getString(12));
                csvRow.setL(rs.getString(13));
                csvRow.setM(rs.getString(14));
                csvRow.setN(rs.getString(15));
                csvRow.setO(rs.getString(16));
                csvRow.setP(rs.getString(17));
                csvRow.setQ(rs.getString(18));
                csvRow.setR(rs.getString(19));
                csvRow.setS(rs.getString(20));
                csvRow.setT(rs.getString(21));
                csvRow.setU(rs.getString(22));
                csvRow.setV(rs.getString(23));
                csvRow.setW(rs.getString(24));
                csvRow.setX(rs.getString(25));
                csvRow.setY(rs.getString(26));
                csvRow.setZ(rs.getString(27));
                csvRow.setAA(rs.getString(28));
                csvRow.setAB(rs.getString(29));
                csvRow.setAC(rs.getString(30));
                csvRow.setAD(rs.getString(31));
                csvRow.setAE(rs.getString(32));
                csvRow.setAF(rs.getString(33));
                csvRow.setAG(rs.getString(34));
                csvRow.setAH(rs.getString(35));
                csvRow.setAI(rs.getString(36));
                csvRow.setAJ(rs.getString(37));
                csvRow.setAK(rs.getString(38));
                csvRow.setAL(rs.getString(39));


                csvRowList.add(csvRow);
            }



            return csvRowList;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return csvRowList;
    }

    public List<Product134> csvP134SearchW(String Wdata, String Snotdata){

        String SQL = "SELECT  * "
                + " FROM DBBFFFFP "
                + " WHERE W = ? "
                + " AND NOT S = ? ";
        List<Product134> product134List = new ArrayList<Product134>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, Wdata);
            pstmt.setString(2, Snotdata);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product134 product134 = new Product134(rs.getString(34),"",rs.getString(27),rs.getString(4),rs.getString(22),rs.getString(18),rs.getString(19),rs.getString(22));
                product134List.add(product134);
            }

            return product134List;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return product134List;
    }

    public int csvDeleteAllTable(){
       // String SQL = "TRUNCATE TABLE DBBFFFFP;";

        String SQL = "TRUNCATE TABLE DBBFFFFP RESTART IDENTITY CASCADE;";
        // TRUNCATE sch.mytable RESTART IDENTITY CASCADE;

        Statement stmt;
        int deletedRows =1;

        try (Connection conn = connect();){
            stmt = conn.createStatement();
            deletedRows=stmt.executeUpdate(SQL);
            System.out.println("deletedRows: " + deletedRows);
            if(deletedRows>0){
                System.out.println("Deleted All Rows In The Table Successfully...");
            }else{
                System.out.println("Table already empty.");
            }
            System.out.println("Delete all " + deletedRows);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deletedRows;

    }

    public  int companyInsertColumns(List listCompany){

        String SQL = "INSERT INTO Company(name) "
                + "VALUES(?)";
        int count = 0;
        try (
                Connection conn = connect();
                PreparedStatement statement = conn.prepareStatement(SQL);) {


            for(int i= 0;i<listCompany.size();i++){
                statement.setString(1,listCompany.get(i).toString());
                statement.addBatch();
                count++;
                if (count % 100 == 0 || count == listCompany.size()) {
                    statement.executeBatch();
                }
             //   System.out.println(i +" :" + listCompany.get(i));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
        return  count;

    }

    public void companyDeleteAllTable(){
        String SQL = "TRUNCATE TABLE Company RESTART IDENTITY CASCADE; ";

        Statement stmt;


        try (Connection conn = connect();){
            stmt = conn.createStatement();
            int deletedRows=stmt.executeUpdate(SQL);
            System.out.println("deletedRows " + deletedRows);
            if(deletedRows>0){
                System.out.println("Deleted All Rows In The Table Successfully...");
            }else{
                System.out.println("Table already empty.");
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int companyDeleteRow(int id){
        String SQL = "DELETE FROM Company WHERE id = ?";

        int affectedrows = 0; // tra ve so dong bi anh huong
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }

    public List<String> companyTable(){

        String SQL = "SELECT  * "
                + " FROM Company ";
        List<String> listCompany = new ArrayList<String>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
             ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name="";

                name = rs.getString("name").toString();
               // System.out.println("Name :" + name);
                listCompany.add(name);
            }

            return listCompany;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listCompany;

    }












}
