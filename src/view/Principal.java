package view;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import layout.WrapLayout;

/**
 *
 * @author Eduardo Raygoza
 */
public class Principal extends JFrame{
    
    public JTextField txtUsuario, txtVolumen, txtCurp, txtDir, txtTel, txtNombre, txtApellido;
    public JButton btnAceptarPrestamo, btnAceptarUsuario;
    public JMenuBar menuBar;
    public JMenuItem itmPrestamo, itmVol, itmAutor;
    
    private JPanel pnlPrestamo, pnlUsuario;
    
    public Principal(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Biblioteca Leon");
        setSize(800, 600);
        setLayout(new WrapLayout());
        
        //BARRA DE MENU
        menuBar = new JMenuBar();
        itmPrestamo = new JMenuItem("Prestamos");
        itmVol = new JMenuItem("Volumenes");
        itmAutor = new JMenuItem("Autores");
        menuBar.add(itmPrestamo);
        menuBar.add(itmVol);
        menuBar.add(itmAutor);
        setJMenuBar(menuBar);
        
        //PANEL DE NUEVO PRESTAMO
        pnlPrestamo = new JPanel();
        pnlPrestamo.setSize(800, 300);
        
        txtUsuario = new JTextField(10);
        txtVolumen = new JTextField(10);
        btnAceptarPrestamo = new JButton("Aceptar");
        
        pnlPrestamo.add(new JLabel("PRESTAMO:       "));
        pnlPrestamo.add(new JLabel("ID usuario"));
        pnlPrestamo.add(txtUsuario);
        pnlPrestamo.add(new JLabel("Volumen"));
        pnlPrestamo.add(txtVolumen);
        pnlPrestamo.add(btnAceptarPrestamo);
        
        //PANEL DE NUEVO USUARIO
        pnlUsuario = new JPanel();
        pnlUsuario.setLayout(new WrapLayout());
        
        txtCurp = new JTextField(20);
        txtDir = new JTextField(20);
        txtTel = new JTextField(20);
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        btnAceptarUsuario = new JButton("Aceptar");
        
        pnlUsuario.add(new JLabel("NUEVO USUARIO:       "));
        pnlUsuario.add(new JLabel("CURP:"));
        pnlUsuario.add(txtCurp);
        pnlUsuario.add(new JLabel("Direccion:"));
        pnlUsuario.add(txtDir);
        pnlUsuario.add(new JLabel("Telefono:"));
        pnlUsuario.add(txtTel);
        pnlUsuario.add(new JLabel("Nombre:"));
        pnlUsuario.add(txtNombre);
        pnlUsuario.add(new JLabel("Apellido:"));
        pnlUsuario.add(txtApellido);
        pnlUsuario.add(btnAceptarUsuario);
        
        
        
        add(pnlPrestamo);
        add(pnlUsuario);
        setVisible(true);
    }
    
}
