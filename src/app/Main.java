package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import view.Principal;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main implements ActionListener{
    public static void main(String[] args) {
//        String connectionUrl = "jdbc:sqlserver://VIRTUALWINSRVR:1433;" +  
//   "databaseName=BiblioLeon;user=BiblioAdmin;password=ProyectoIntegrador18;";  
//        try{
//            //Estableciendo la coneccion a la BDD
//            System.out.println("Estableciendo coneccion...");
//            Connection con = DriverManager.getConnection(connectionUrl);
//            System.out.println("Coneccion establecida");
//            
//            //Ejecutando un Query
//            Statement stm = con.createStatement();
//            String sql = "SELECT * FROM Producto";
//            ResultSet rs = stm.executeQuery(sql);
//            
//            //Extrayendo informacion del ResultSet
//            while(rs.next()){
//                String id = rs.getString("id_prod");
//                String nombre = rs.getString("nombre_prod");
//                System.out.println("ID: "+id+" Nombre: "+nombre);
//            }
//            con.close();
//            stm.close();
//            rs.close();
//        }
//        catch(SQLException e){
//            System.out.println("Error no: "+e.getErrorCode()+" \n"+e.getMessage());
//        }

        new Principal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}