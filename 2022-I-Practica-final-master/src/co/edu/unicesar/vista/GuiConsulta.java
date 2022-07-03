/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicesar.vista;

import co.edu.unicesar.utilidades.Mensaje;
import Excepciones.ExcepcionArchivo;
import co.edu.unicesar.modelo.ListaPublicacion;
import co.edu.unicesar.modelo.Publicacion;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jairo F
 */
public class GuiConsulta extends JDialog {

    private JPanel panelBusqueda, panelPrinicipal, panelOpciones;
    private JScrollPane panelTabla;
    private JLabel lbBuqueda;
    private JButton btnEliminar;
    private JTextField txtBusqueda;
    private JTable tablaRegistros;
    private DefaultTableModel modeloTabla;
    private String titulosTabla[] = {"IDBM", "TITULO", "AUTOR", "AÑO", "COSTO", "Otros"};
    private String datosTabla[][] = {null};
    private Container contenedor;
    private ListaPublicacion modelo;

    public GuiConsulta(Frame frame, boolean bln, int tipo) {
        super(frame, bln);
        if (tipo == 1) {
            this.modelo = new ListaPublicacion();
        } else {
            this.modelo = new ListaPublicacion("");
        }

        this.setTitle("Catalogo Aires - Consulta - Version 1.0");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.iniciarComponentes();
        this.setSize(500, 600);
        //this.pack();
        try {
            List<Publicacion> lista = this.modelo.leer();
            this.actualizarTabla(lista);

        } catch (Exception e) {
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void iniciarComponentes() {
        this.contenedor = this.getContentPane();
        this.contenedor.setLayout(new FlowLayout());

        this.panelPrinicipal = new JPanel();
        this.panelPrinicipal.setLayout(new BorderLayout());

        this.iniciarPanelBusqueda();
        this.iniciarPanelTabla();
        this.iniciarPanelOpciones();

        this.panelPrinicipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.contenedor.add(this.panelPrinicipal);
    }

    private void iniciarPanelBusqueda() {

        this.panelBusqueda = new JPanel();
        this.panelBusqueda.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.lbBuqueda = new JLabel("IBDN: ");
        this.txtBusqueda = new JTextField(15);
        this.txtBusqueda.addKeyListener(new eventoFiltroBusqueda());

        this.panelBusqueda.add(this.lbBuqueda);
        this.panelBusqueda.add(this.txtBusqueda);

        this.panelBusqueda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.panelPrinicipal.add(this.panelBusqueda, BorderLayout.NORTH);

    }

    private void iniciarPanelTabla() {

        this.tablaRegistros = new JTable();
        this.modeloTabla = new DefaultTableModel(this.datosTabla, this.titulosTabla);
        this.tablaRegistros.setModel(this.modeloTabla);

        this.panelTabla = new JScrollPane(this.tablaRegistros);

        this.panelPrinicipal.add(this.panelTabla, BorderLayout.CENTER);

    }

    private void iniciarPanelOpciones() {
        this.panelOpciones = new JPanel();
        this.panelOpciones.setLayout(new FlowLayout(FlowLayout.RIGHT));

        this.btnEliminar = new JButton("Eliminar");
        this.btnEliminar.addActionListener(new eventoClickEliminar());

        this.panelOpciones.add(this.btnEliminar);

        this.panelOpciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.panelPrinicipal.add(this.panelOpciones, BorderLayout.SOUTH);
    }

    public void actualizarTabla(List<Publicacion> lista) {

        this.modeloTabla.setNumRows(0);

        for (Publicacion a : lista) {
            //{"IDBM", "TITULO", "AUTOR", "AÑO", "COSTO", "Otros"};
            String datos[] = {String.valueOf(a.getIdbn()), a.getTitulo(),a.getAutor(), String.valueOf(a.getAnio()), String.valueOf(a.getCosto()), String.valueOf(a.toString())};
            this.modeloTabla.addRow(datos);
        }

    }

    private void filtrarDatos() {

        try {

            String serial;
            List<Publicacion> lista;
            if (this.txtBusqueda.getText().isEmpty()) {
                serial = "";
                lista = this.modelo.leer();
            } else {
                serial = (this.txtBusqueda.getText());
                lista = this.modelo.filtrar(serial);
            }
            this.actualizarTabla(lista);

        } catch (ExcepcionArchivo e) {
        }

    }

    private void eliminarDato() {

        int row = this.tablaRegistros.getSelectedRow();
        String idbn = (String) this.modeloTabla.getValueAt(row, 0);
        try {
            Publicacion eliminado = this.modelo.eliminar(new Publicacion(idbn) {
                @Override
                public String getDataStringFormat() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getStringFormat() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            Mensaje.mostrar(this, "Confirmacion", "Registro eliminadi", JOptionPane.INFORMATION_MESSAGE);
            this.filtrarDatos();

        } catch (Exception e) {

        }

    }

    class eventoFiltroBusqueda implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            filtrarDatos();
        }

    }

    class eventoClickEliminar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            eliminarDato();
        }

    }
}
