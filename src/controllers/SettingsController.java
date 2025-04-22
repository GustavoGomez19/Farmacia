package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import models.EmployeesDao;
import views.SystemView;

public class SettingsController implements MouseListener {

    private SystemView views;

    public SettingsController(SystemView views) {
        this.views = views;
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelSales.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        Profile();
    }
    
    //Asignar el perfíl del usuario
    public void Profile(){
        this.views.txt_id_profile.setText("" + EmployeesDao.ID_USER);
        this.views.txt_name_profile.setText(EmployeesDao.FULL_NAME_USER);
        this.views.txt_address_profile.setText(EmployeesDao.ADDRESS_USER);
        this.views.txt_telephone_profile.setText(EmployeesDao.TELEPHONE_USER);
        this.views.txt_email_profile.setText(EmployeesDao.EMAIL_USER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == views.jLabelProducts) {
            views.jPanelProducts.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelPurchases) {
            views.jPanelPurchases.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelSales){
            views.jPanelSales.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelCustomers) {
            views.jPanelCustomers.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelEmployees) {
            views.jPanelEmploys.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelSuppliers) {
            views.jPanelSuppliers.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelReports) {
            views.jPanelReports.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelCategories) {
            views.jPanelCategories.setBackground(new Color(100, 105, 100));
        } else if (e.getSource() == views.jLabelSettings) {
            views.jPanelSettings.setBackground(new Color(100, 105, 100));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == views.jLabelProducts) {
            views.jPanelProducts.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelPurchases) {
            views.jPanelPurchases.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSales) {
            views.jPanelSales.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelCustomers) {
            views.jPanelCustomers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelEmployees) {
            views.jPanelEmploys.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSuppliers) {
            views.jPanelSuppliers.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelCategories) {
            views.jPanelCategories.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelReports) {
            views.jPanelReports.setBackground(new Color(18, 45, 61));
        } else if (e.getSource() == views.jLabelSettings) {
            views.jPanelSettings.setBackground(new Color(18, 45, 61));
        }
    }

}
