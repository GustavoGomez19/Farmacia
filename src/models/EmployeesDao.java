package models;

import java.util.List;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class EmployeesDao {

    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Variables para enviar datos entre interfaces
    public static int ID_USER = 0;
    public static String FULL_NAME_USER = "";
    public static String USER_NAME_USER = "";
    public static String ADDRESS_USER = "";
    public static String TELEPHONE_USER = "";
    public static String EMAIL_USER = "";
    public static String ROL_USER = "";

    //Método del login
    public Employees loginQuery(String user, String password) {
        String query = "SELECT * FROM employees WHERE user_name = ? AND password = ?";
        Employees employee = new Employees();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            //Enviar parámetros
            pst.setString(1, user);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                ID_USER = employee.getId();
                employee.setFull_name(rs.getString("full_name"));
                FULL_NAME_USER = employee.getFull_name();
                employee.setUser_name(rs.getNString("user_name"));
                USER_NAME_USER = employee.getUser_name();
                employee.setAddress(rs.getNString("address"));
                ADDRESS_USER = employee.getAddress();
                employee.setTelephone(rs.getString("telephone"));
                TELEPHONE_USER = employee.getTelephone();
                employee.setEmail(rs.getString("email"));
                EMAIL_USER = employee.getEmail();
                employee.setRol(rs.getString("rol"));
                ROL_USER = employee.getRol();

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener al empleado " + e);
        }
        return employee;
    }

    //Método para registrar empleado
    public boolean registerEmployeeQuery(Employees employee) {
        String query = "INSERT INTO employees(id, full_name, user_name, address, telephone, email, password, rol, created, updated) VALUES(?,?,?,?,?,?,?,?,?,?)";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFull_name());
            pst.setString(3, employee.getUser_name());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, dateTime);
            pst.setTimestamp(10, dateTime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar empleado." + e);
            return false;
        }
    }

    //Método para listar empleado
    public List listEmployeesQuery(String value) {
        List<Employees> list_employees = new ArrayList();
        String query = "SELECT * FROM employees ORDER BY rol ASC";
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_employee);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Employees employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUser_name(rs.getString("user_name"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
                list_employees.add(employee);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_employees;
    }

    //Método para modificar empleado
    public boolean updateEmployeeQuery(Employees employee) {
        String query = "UPDATE employees SET full_name = ?, user_name = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ? WHERE id = ?";
        Timestamp dateTime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUser_name());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, dateTime);
            pst.setInt(8, employee.getId());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los dato del empleado." + e);
            return false;
        }
    }

    //Método para eliminar empleado
    public boolean deleteEmployeeQuery(int id) {
        String query = "DELETE FROM employees WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar un empleado que tenga relación con otra tabla.");
            return false;
        }
    }

    //Método para cambiar la contraseña
    public boolean updateEmployeePaswword(Employees employee) {
        String query = "UPDATE employees SET password = ? WHERE user_name = '" + USER_NAME_USER + "'";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getPassword());
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña. " + e);
            return false;
        }
    }
}
