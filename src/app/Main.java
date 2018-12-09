package app;

import view.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.GregorianCalendar;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main implements ActionListener{
    
    private GregorianCalendar fecha;
    private FrameMain frame;
    private FramePrestamo framePrestamo;
    private FrameAutor frameAutor;
    private String connectionUrl;
    private Connection conn;
    private Statement stm;
    private ResultSet rs;
    private PreparedStatement prpstm;
    private String sql, strFecha, dev;
    private String[] row;
    
    public Main(){
        fecha = new GregorianCalendar();
        strFecha = fecha.get(1)+"-"+fecha.get(2)+"-"+fecha.get(5);
        fecha.add(GregorianCalendar.DAY_OF_MONTH, 7);
        dev = fecha.get(1)+"-"+fecha.get(2)+"-"+fecha.get(5);
        frame = new FrameMain();
        framePrestamo = new FramePrestamo();
        frameAutor = new FrameAutor();
        connectionUrl = "jdbc:sqlserver://VIRTUALWINSRVR:1433;" +  
            "databaseName=BiblioLeon;user=BiblioAdmin;password=ProyectoIntegrador18;";  
        try {
            conn = DriverManager.getConnection(connectionUrl);
            stm = conn.createStatement();
            System.out.println("Coneccion lista.");
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode()+"\n"+ex.getMessage());
        }
        
        //Se agregan los listeners a los botones
        frame.getBtnAceptarSocio().addActionListener(this);
        frame.getBtnAceptarPrestamo().addActionListener(this);
        frameAutor.getBtnAceptarAutor().addActionListener(this);
        frame.getItmPrestamos().addActionListener((ActionEvent e) -> {
            sql = "SELECT * FROM prestamo ORDER BY fecha_devolucion ASC";
            try {
                rs = stm.executeQuery(sql);
                framePrestamo.setTableData(rs);
                framePrestamo.setVisible(true);
            } catch (SQLException ex) {
            }
        });
        frame.getItmAutores().addActionListener((ActionEvent e) -> {
            sql = "SELECT * FROM autor";
            try{
                rs = stm.executeQuery(sql);
                frameAutor.setTableData(rs);
                frameAutor.setVisible(true);
            }catch(SQLException ex){System.out.println(ex.getMessage());}
        });        
    }
    
    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((javax.swing.JButton)e.getSource() == frame.getBtnAceptarSocio()){
            sql = "INSERT INTO Socio VALUES (?,?,?,?,?,?)";
            try {
                prpstm = conn.prepareStatement(sql);
                prpstm.setString(1, frame.getTxtIDNuevoSocio().getText());
                prpstm.setString(2, frame.getTxtCurp().getText());
                prpstm.setString(3, frame.getTxtDireccion().getText());
                prpstm.setString(4, frame.getTxtTelefono().getText());
                prpstm.setString(5, frame.getTxtNombre().getText());
                prpstm.setString(6, frame.getTxtApellido().getText());
                prpstm.execute();
                JOptionPane.showMessageDialog(null, "Nuevo Usuario registrado con exito", "Exito", JOptionPane.OK_OPTION);
                frame.getTxtIDNuevoSocio().setText("S-0000");
                frame.getTxtCurp().setText("");
                frame.getTxtDireccion().setText("");
                frame.getTxtTelefono().setText("");
                frame.getTxtNombre().setText("");
                frame.getTxtApellido().setText("");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage()); 
            }
        }
        else if ((javax.swing.JButton)e.getSource() == frame.getBtnAceptarPrestamo()){
            sql = "INSERT INTO prestamo(numero_socio,id_volumen,fecha_prestamo,fecha_limite) VALUES (?,?,?,?)";
            try{
                prpstm = conn.prepareStatement(sql);
                prpstm.setString(1, frame.getTxtIDSocio().getText());
                prpstm.setString(2, frame.getTxtIDVolumen().getText());
                prpstm.setString(3, strFecha);
                prpstm.setString(4, dev);
                prpstm.execute();
                JOptionPane.showMessageDialog(null, "Nuevo Prestamo registrado con exito", "Exito", JOptionPane.OK_OPTION);
                frame.getTxtIDSocio().setText("S-0000");
                frame.getTxtIDVolumen().setText("V-0000");
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        else if ((javax.swing.JButton) e.getSource() == frameAutor.getBtnAceptarAutor()){
            sql = "INSERT INTO autor VALUES (?,?,?,?)";
            try{
                row = new String[4];
                row[0] = frameAutor.getTxtIDAutor().getText();
                row[1] = frameAutor.getTxtNombre().getText();
                row[2] = frameAutor.getTxtApellido().getText();
                row[3] = frameAutor.getTxtNacionalidad().getText();
                prpstm = conn.prepareStatement(sql);
                for (int i = 1; i < 5; i++) {
                    prpstm.setString(i, row[i-1]);
                }
                prpstm.execute();
                JOptionPane.showMessageDialog(null, "Nuevo autor registrado con exito", "Exito", JOptionPane.OK_OPTION);
                frameAutor.addRow(row);
                frameAutor.getTxtIDAutor().setText("");
                frameAutor.getTxtNombre().setText("");
                frameAutor.getTxtApellido().setText("");
                frameAutor.getTxtNacionalidad().setText("");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}