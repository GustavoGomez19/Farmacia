package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static models.EmployeesDao.ROL_USER;
import models.Suppliers;
import models.SuppliersDao;
import views.SystemView;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    private Suppliers supplier;
    private SuppliersDao supplierDao;
    private SystemView views;

    //Rol
    String rol = ROL_USER;
    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers supplier, SuppliersDao supplierDao, SystemView views) {
        this.supplier = supplier;
        this.supplierDao = supplierDao;
        this.views = views;
        //Botón de registrar proveedor
        this.views.btn_register_supplier.addActionListener(this);
        //Botón para modificar proveedor
        this.views.btn_update_supplier.addActionListener(this);
        //Botón para eliminar proveedor
        this.views.btn_delete_supplier.addActionListener(this);
        //Botón de cancelar
        this.views.btn_cancel_supplier.addActionListener(this);

        //Buscador
        this.views.supplier_table.addMouseListener(this);
        this.views.txt_search_supplier.addKeyListener(this);
        //Poner la tabla en escucha
        this.views.supplier_table.addMouseListener(this);
        //Poner en escucha el botón lateral de proveedores
        this.views.jLabelSuppliers.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_supplier) {
            //Verificar si los campos están vacíos
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.cmb_supplier_city.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //Registrar proveedor
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

                if (supplierDao.registerSupplierQuery(supplier)) {
                    cleanTable();
                    cleanFields();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar proveedor.");
                }
            }
        } else if (e.getSource() == views.btn_update_supplier) {
            //Verificar si los campos están vacíos para modificar el proveedor
            if (views.txt_supplier_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para modificar.");
            } else {
                if (views.txt_supplier_name.getText().equals("")
                        || views.txt_supplier_address.getText().equals("")
                        || views.txt_supplier_telephone.getText().equals("")
                        || views.txt_supplier_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                } else {
                    //Modificar el proveedor
                    supplier.setName(views.txt_supplier_name.getText().trim());
                    supplier.setAddress(views.txt_supplier_address.getText().trim());
                    supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                    supplier.setEmail(views.txt_supplier_email.getText().trim());
                    supplier.setDescription(views.txt_supplier_description.getText().trim());
                    supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());
                    supplier.setId(Integer.parseInt(views.txt_supplier_id.getText()));
                    
                    if (supplierDao.updateSupplierQuery(supplier)) {
                        //Limpiar la table
                        cleanTable();
                        //Limpiar los campos
                        cleanFields();
                        //Listar proveedor
                        listAllSuppliers();
                        views.btn_register_supplier.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos del proveedor modificados con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del provedor");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_supplier) {
            int row = views.supplier_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un proveedor para eliminar");
            } else {
                int id = Integer.parseInt(views.supplier_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Se eliminará el proveedor seleccionado. ¿Desea continuar?");
                if (question == 0 && supplierDao.deleteSupplierQuery(id) != false) {
                    //limpiar tabla
                    cleanTable();
                    //Limpiar campos
                    cleanFields();
                    //Listar proveedores
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado con éxito.");
                }
            }
        } else if (e.getSource() == views.btn_cancel_supplier) {
            cleanFields();
            views.btn_register_supplier.setEnabled(true);
        }
    }

    //Listar proveedores
    public void listAllSuppliers() {
        if (rol.equals("Administrador")) {
            List<Suppliers> list = supplierDao.listSupplierQuery(views.txt_search_supplier.getText());
            model = (DefaultTableModel) views.supplier_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }
            //Pasar todos los datos a la tabla
            views.supplier_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.supplier_table) {
            int row = views.supplier_table.rowAtPoint(e.getPoint());
            views.txt_supplier_id.setText(views.supplier_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.supplier_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.supplier_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.supplier_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.supplier_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.supplier_table.getValueAt(row, 5).toString());
            views.cmb_supplier_city.setSelectedItem(views.supplier_table.getValueAt(row, 6).toString());
            //Deshabilitar botones
            views.btn_register_supplier.setEnabled(false);
            views.txt_supplier_id.setEditable(false);
        } else if (e.getSource() == views.jLabelSuppliers) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(5);
                //Limpiar tabla
                cleanTable();
                //limpiar campos
                cleanFields();
                //Listar proveedores
                listAllSuppliers();
            } else {
                views.jTabbedPane1.setEnabledAt(5, false);
                views.jLabelSuppliers.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No cuentas con privilegios de administrador para ingresar a esta opción.");
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_supplier) {
            //Limpiar la tabla
            cleanTable();
            //Listar el proveedor
            listAllSuppliers();
        }

    }

    //Limpiar los campos del cliente
    public void cleanFields() {
        views.txt_supplier_id.setText("");
        views.txt_supplier_id.setEditable(true);
        views.txt_supplier_name.setText("");
        views.txt_supplier_address.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.txt_supplier_description.setText("");
        views.cmb_supplier_city.setSelectedIndex(0);
    }

    //Limpiar la tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

}
