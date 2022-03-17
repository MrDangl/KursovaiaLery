
import java.sql.*;
import java.util.ArrayList;

public class Storage {
    static Connection conn = null;


    static public void loadData() {

        try {
            String url = "jdbc:sqlite:BD.db";
            conn = DriverManager.getConnection(url);
            System.out.println("connection succsessfull");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    static public ArrayList<Warehouse> getStorWarehouse()
        {
            ArrayList<Warehouse> warehouseList = new ArrayList<>();
            String QUERY = "SELECT id, numWarehouse, nameWarehouse, ProfileWarehouse, adressWarehouse FROM Warehouse ";
            try
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);
                while (rs.next())
                {
                    Warehouse ware = new Warehouse(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                    ware.setId(rs.getInt(1));
                    warehouseList.add(ware);
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }


            return warehouseList;
        }
        static public ArrayList<Com> getStorCom()
                {
                    ArrayList<Com> comList = new ArrayList<>();
                    String QUERY = "SELECT id, nameCom, mailCom, profileCom, fioCom, adressCom FROM Company ";
                    try
                    {
                        Integer[] integ = {1,2,3};
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(QUERY);
                        while (rs.next())
                        {
                            Com com = new Com(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                            com.setId(rs.getInt(1));
                            comList.add(com);
                        }
                    }
                    catch (SQLException e)
                    {
                        System.out.println(e.getMessage());
                    }


                    return comList;
                }

}
