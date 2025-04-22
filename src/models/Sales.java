
package models;

public class Sales {
    private int id;
    private String sale_date;
    private double total_to_pay;
    private int customer_id;
    private String customer_name;
    private int employee_id;
    private String employee_name;
    
    public Sales(){
        
    }
    
    public Sales(int id, String sale_date, double total_to_pay, int customer_id, String customer_name, int employee_id, String employee_name){
        this.id = id;
        this.sale_date = sale_date;
        this.total_to_pay = total_to_pay;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
    }
    
    public int getId (){
        return id;
    }
    
    public void setId (int id) {
        this.id = id;
    }
    
    public String getSaleDate () {
        return sale_date;
    }
    
    public void setSaleDate(String sale_date) {
        this.sale_date = sale_date;
    }
    
    public double getTotalToPay () {
        return total_to_pay;
    }
    
    public void setTotalToPay (double total_to_pay) {
        this.total_to_pay = total_to_pay;
    }
    
    public int getCustomerId () {
        return customer_id;
    }
    
    public void setCustomerId (int customer_id) {
        this.customer_id = customer_id;
    }
    
    public String getCustomerName () {
        return customer_name;
    }
    
    public void setCustomerName (String customer_name) {
        this.customer_name = customer_name;
    }
    
    public int getEmployeeId () {
        return employee_id;
    }
    
    public void setEmployeeId(int employee_id) {
        this.employee_id = employee_id;
    }
    
    public String getEmployeeName () {
        return employee_name;
    }
    
    public void setEmployeeName (String employee_name) {
        this.employee_name = employee_name;
    }
}
