package app;

import view.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
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
    private FrameLibro frameLibro;
    private FrameVolumen frameVol;
    private FrameSocios frameSocios;
    private String connectionUrl;
    private Connection conn;
    private Statement stm;
    private ResultSet rs;
    private PreparedStatement prpstm;
    private String sql, strFecha, dev, idvol;
    private Object[] row;
    private CallableStatement call;
    
    public Main(){
        fecha = new GregorianCalendar();
        strFecha = fecha.get(1)+"-"+(fecha.get(2)+1)+"-"+fecha.get(5);
        fecha.add(GregorianCalendar.DAY_OF_MONTH, 7);
        dev = fecha.get(1)+"-"+(fecha.get(2)+1)+"-"+fecha.get(5);
        frame = new FrameMain();
        framePrestamo = new FramePrestamo();
        frameAutor = new FrameAutor();
        frameLibro = new FrameLibro();
        frameVol = new FrameVolumen();
        frameSocios = new FrameSocios();
        connectionUrl = "jdbc:sqlserver://VIRTUALWINSRVR:1433;" +  
            "databaseName=BiblioLeon;user=BiblioAdmin;password=ProyectoIntegrador18;";  
        try {
            conn = DriverManager.getConnection(connectionUrl);
            stm = conn.createStatement();
            call = conn.prepareCall("{call devolverVolumen(?)}");
            System.out.println("Coneccion lista.");
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode()+"\n"+ex.getMessage());
        }
        
        //Se agregan los listeners a los botones
        frame.getBtnAceptarSocio().addActionListener(this);
        frame.getBtnAceptarPrestamo().addActionListener(this);
        frame.getBtnDevolucion().addActionListener(this);
        frameAutor.getBtnAceptarAutor().addActionListener(this);
        frameLibro.getBtnAgregarLibro().addActionListener(this);
        frameVol.getBtnAgregarVolumen().addActionListener(this);
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
        frame.getItmLibros().addActionListener((ActionEvent e) -> {
            sql = "SELECT a.id_libro, b.id_autor, a.titulo, a.año "
                    + "FROM libro as a JOIN libro_autor as b "
                    + "ON a.id_libro = b.id_libro";
            try{
                rs = stm.executeQuery(sql);
                frameLibro.setTableData(rs);
                frameLibro.setVisible(true);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        });
        frame.getItmVolumenes().addActionListener((ActionEvent e) -> {
            sql = "SELECT * FROM volumen";
            try{
                rs = stm.executeQuery(sql);
                frameVol.setTableData(rs);
                frameVol.setVisible(true);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        });
        frame.getItmSocios().addActionListener((ActionEvent e) -> {
            sql = "SELECT numero_socio, nombre, apellido, CURP, direccion, telefono FROM socio";
            try{
                rs = stm.executeQuery(sql);
                frameSocios.setTableData(rs);
                frameSocios.setVisible(true);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
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
                JOptionPane.showMessageDialog(null, "Nuevo Usuario registrado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
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
                prpstm.setString(2, frame.getTxtVolumen().getText());
                prpstm.setString(3, strFecha);
                prpstm.setString(4, dev);
                prpstm.execute();
                JOptionPane.showMessageDialog(null, "Nuevo Prestamo registrado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                frame.getTxtIDSocio().setText("S-0000");
                frame.getTxtVolumen().setText("V-0000");
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        else if ((javax.swing.JButton) e.getSource() == frameAutor.getBtnAceptarAutor()){
            sql = "INSERT INTO autor VALUES (?,?,?,?)";
            try{
                row = new Object[4];
                row[0] = frameAutor.getTxtIDAutor().getText();
                row[1] = frameAutor.getTxtNombre().getText();
                row[2] = frameAutor.getTxtApellido().getText();
                row[3] = frameAutor.getTxtNacionalidad().getText();
                prpstm = conn.prepareStatement(sql);
                for (int i = 1; i < 5; i++) {
                    prpstm.setObject(i, row[i-1]);
                }
                prpstm.execute();
                JOptionPane.showMessageDialog(null, "Nuevo autor registrado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                frameAutor.addRow(row);
                frameAutor.getTxtIDAutor().setText("");
                frameAutor.getTxtNombre().setText("");
                frameAutor.getTxtApellido().setText("");
                frameAutor.getTxtNacionalidad().setText("");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        else if((javax.swing.JButton) e.getSource() == frameLibro.getBtnAgregarLibro()){
            sql = "INSERT INTO libro VALUES (?,?,?)";
            
            try{
                row = new Object[4];
                prpstm = conn.prepareStatement(sql);
                row[0] = frameLibro.getTxtIDLibro().getText();
                row[1] = frameLibro.getTxtIDAutor().getText();
                row[2] = frameLibro.getTxtTitulo().getText();
                row[3] = frameLibro.getTxtAño().getText();
                prpstm.setObject(1, row[0]);
                prpstm.setObject(2, row[2]);
                prpstm.setObject(3, row[3]);
                prpstm.execute();
                sql = "INSERT INTO libro_autor VALUES (?,?)";
                prpstm = conn.prepareStatement(sql);
                for (int i = 1; i < 3; i++) {
                    prpstm.setObject(i, row[i-1]);
                }
                prpstm.execute();
                JOptionPane.showMessageDialog(null, "Nuevo libro registrado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                frameLibro.addRow(row);
                frameLibro.getTxtIDLibro().setText("");
                frameLibro.getTxtIDAutor().setText("");
                frameLibro.getTxtTitulo().setText("");
                frameLibro.getTxtAño().setText("");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        else if((javax.swing.JButton) e.getSource() == frameVol.getBtnAgregarVolumen()){
            sql = "INSERT INTO volumen VALUES (?,?,?,?,?,?,?,?)";
            try{
                row = new Object[8];
                row[0] = frameVol.getTxtIDVolumen().getText();
                row[1] = frameVol.getTxtIDLibro().getText();
                row[2] = frameVol.getTxtEditorial().getText();
                row[3] = frameVol.getTxtEdicion().getText();
                row[4] = frameVol.getTxtISBN().getText();
                row[5] = frameVol.getTxtAñoEdicion().getText();
                row[6] = frameVol.getCmbEstado().getSelectedIndex();
                row[7] = frameVol.getCmbAdquisicion().getSelectedIndex();
                prpstm = conn.prepareStatement(sql);
                for (int i = 0; i < 6; i++) {
                    prpstm.setObject(i+1, row[i]);
                }
                switch((int)row[6]){
                    case 0: row[6] = "E"; break;
                    case 1: row[6] = "B"; break;
                    case 2: row[6] = "R"; break;
                    case 3: row[6] = "M"; break;
                }
                switch((int)row[7]){
                    case 0: row[7] = "C"; break;
                    case 1: row[7] = "D"; break;
                }
                prpstm.setObject(7, row[6]);
                prpstm.setObject(8, row[7]);
                prpstm.execute();
                JOptionPane.showMessageDialog(null, "Nuevo volumen registrado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                switch((String)row[6]){
                    case "E": row[6] = "Excelente"; break;
                    case "B": row[6] = "Bueno"; break;
                    case "R": row[6] = "Regular"; break;
                    case "M": row[6] = "Malo"; break;
                }
                switch((String)row[7]){
                    case "C": row[7] = "Compra"; break;
                    case "D": row[7] = "Donacion"; break;
                }
                frameVol.addRow(row);
                frameVol.getTxtIDVolumen().setText("");
                frameVol.getTxtIDLibro().setText("");
                frameVol.getTxtEditorial().setText("");
                frameVol.getTxtEdicion().setText("");
                frameVol.getTxtISBN().setText("");
                frameVol.getTxtAñoEdicion().setText("");
                frameVol.getCmbEstado().setSelectedIndex(0);
                frameVol.getCmbAdquisicion().setSelectedIndex(0);
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        else if((javax.swing.JButton) e.getSource() == frame.getBtnDevolucion()){
            //System.out.println(frame.getTxtIDVolumen().getText());
            /*sql = "UPDATE prestamo SET fecha_devolucion = '"+strFecha+"' "
                    + "WHERE (id_volumen = 'V-5613' AND fecha_devolucion IS NULL)";
            try{
                stm.executeUpdate(sql);
                frame.getTxtIDVolumen().setText("");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }*/
            
            try{
                call.setString("volumen", frame.getTxtIDVolumen().getText());
                call.execute();
                JOptionPane.showMessageDialog(null, "Devolucion exitosa", "Exito", JOptionPane.INFORMATION_MESSAGE);
                frame.getTxtIDVolumen().setText("");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            //System.out.println(strFecha);
        }
    }
}