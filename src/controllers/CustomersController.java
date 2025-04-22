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
import models.Customers;
import models.CustomersDao;
import views.SystemView;

public class CustomersController implements ActionListener, MouseListener, KeyListener {

    private Customers customer;
    private CustomersDao customerDao;
    private SystemView views;

    DefaultTableModel model = new DefaultTableModel();

    public CustomersController(Customers customer, CustomersDao customersDao, SystemView views) {
        this.customer = customer;
        this.customerDao = customersDao;
        this.views = views;
        //Botón de registrar cliente
        this.views.btn_register_customer.addActionListener(this);
        //Botón de modificar cliente
        this.views.btn_update_customer.addActionListener(this);
        //Botón de eliminar cliente
        this.views.btn_delete_customer.addActionListener(this);
        //Botón de cancelar
        this.views.btn_cancel_customer.addActionListener(this);
        
        //Buscador
        this.views.customer_table.addMouseListener(this);
        this.views.txt_search_customer.addKeyListener(this);
        //Poner en escucha opción clientes menú lateral
        this.views.jLabelCustomers.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_customer) {
            //Verificar si los campos están vacíos para registrar los datos del cliente
            if (views.txt_customer_id.getText().equals("")
                    || views.txt_customer_fullname.getText().equals("")
                    || views.txt_customer_address.getText().equals("")
                    || views.txt_customer_telephone.getText().equals("")
                    || views.txt_customer_email.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            } else {
                //Registrar cliente
                customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setFullName(views.txt_customer_fullname.getText().trim());
                customer.setAddress(views.txt_customer_address.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());

                if (customerDao.registerCustomerQuery(customer)) {
                    cleanTable();
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente registrado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el cliente.");
                }
            }
        } else if (e.getSource() == views.btn_update_customer) {
            //Verificar si los campos están vacíos para modificar datos del cliente
            if (views.txt_customer_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar.");
            } else {
                if (views.txt_customer_id.getText().equals("")
                        || views.txt_customer_fullname.getText().equals("")
                        || views.txt_customer_address.getText().equals("")
                        || views.txt_customer_telephone.getText().equals("")
                        || views.txt_customer_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                } else {
                    //Modificar datos del cliente
                    customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                    customer.setFullName(views.txt_customer_fullname.getText().trim());
                    customer.setAddress(views.txt_customer_address.getText().trim());
                    customer.setTelephone(views.txt_customer_telephone.getText().trim());
                    customer.setEmail(views.txt_customer_email.getText().trim());
                    
                    if (customerDao.updateCustomerQuery(customer)) {
                        cleanTable();
                        cleanFields();
                        listAllCustomers();
                        views.btn_register_customer.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos del cliente modificados con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del cliente.");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_customer) {
            //Eliminar cliente
            int row = views.customer_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debese seleccionar un cliente para eliminar.");
            } else {
                int id = Integer.parseInt(views.customer_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Se eliminará el cliente seleccionado. ¿Desea continuar?");
                
                if (question == 0 && customerDao.deleteCustomerQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_customer.setEnabled(true);
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_customer){ 
            //Funcionalidad botón de cancelar
            views.btn_register_customer.setEnabled(true);
            cleanFields();
        }
    }

    //Listar y buscar clientes
    public void listAllCustomers() {
        List<Customers> list = customerDao.listCustomerQuery(views.txt_search_customer.getText());

        model = (DefaultTableModel) views.customer_table.getModel();

        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFullName();
            row[2] = list.get(i).getTelephone();
            row[3] = list.get(i).getAddress();
            row[4] = list.get(i).getEmail();
            model.addRow(row);
        }
        views.customer_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.customer_table) {
            int row = views.customer_table.rowAtPoint(e.getPoint());
            views.txt_customer_id.setText(views.customer_table.getValueAt(row, 0).toString());
            views.txt_customer_fullname.setText(views.customer_table.getValueAt(row, 1).toString());
            views.txt_customer_address.setText(views.customer_table.getValueAt(row, 2).toString());
            views.txt_customer_telephone.setText(views.customer_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customer_table.getValueAt(row, 4).toString());
            //Deshabilitar botones
            views.btn_register_customer.setEnabled(false);
            views.txt_customer_id.setEditable(false);
        } else if (e.getSource() == views.jLabelCustomers) {
            //Funcionalidad opción clientes menú lateral
            views.jTabbedPane1.setSelectedIndex(3);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar todos los clientes
            listAllCustomers();
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
        if (e.getSource() == views.txt_search_customer) {
            //Limpiar la tabla
            cleanTable();
            //Listar los clientes
            listAllCustomers();
        }
    }
    
    //Limpiar los campos del cliente
    public void cleanFields() {
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

}
