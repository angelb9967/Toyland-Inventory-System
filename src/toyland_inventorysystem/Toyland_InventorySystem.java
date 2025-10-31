package toyland_inventorysystem;

import java.util.ArrayList;

public class Toyland_InventorySystem {

    /* Problems: 
        0. Gawing global variables 
        1. Hindi gumagana ng maayos yung search bar 
        2. Kulang ng search bar sa inventory-in and inventory-out 
        3. Kailangan may access si employee sa View Inventory 
        4. Dapat lahat may validations 
        5. Dapat may number of attempts sa paglogin
        6. --
    
    */
    private static String goTo_AccountCenter_of = "";
    private static int totalItems = 0;
    private static int lowStockItems = 0;
    private static int outofStockItems = 0;
    private static String bestSeller = "None";
    private static int highestSales = 0;
    
    // USER ACC - to display on the View Logs and Dashboard 
    private static String UserID = "";
    private static String UserAccName = "";
    private static String UserAccType = "";
    
     // VIEW LOGS 
    private static ArrayList<String> logsAccDte = new ArrayList<>();
    private static ArrayList<String> logsAccNme = new ArrayList<>();
    private static ArrayList<String> logsAccID = new ArrayList<>();
    private static ArrayList<String> logsAccTpe = new ArrayList<>();
    private static ArrayList<String> logsAccAct = new ArrayList<>();
    private static ArrayList<String> logsAccTme = new ArrayList<>();
    
    // EMPLOYEE ACCOUNT INFORMATION   
    private static ArrayList<String> EmpAccID = new ArrayList<>();
    private static ArrayList<String> EmpName = new ArrayList<>();
    private static ArrayList<String> EmpUsername = new ArrayList<>();
    private static ArrayList<String> EmpPassword = new ArrayList<>();
    
    // ADMINISTRATOR ACCOUNT INFORMATION 
    private static ArrayList<String> AdminAccID = new ArrayList<>();
    private static ArrayList<String> AdminName = new ArrayList<>();
    private static ArrayList<String> AdminUsername = new ArrayList<>();
    private static ArrayList<String> AdminPassword = new ArrayList<>();
    
    // TOP SELLING PRODUCTS 
    public static ArrayList<String> fastItem = new ArrayList<>(); // top selling products: items 
    public static ArrayList<String> fastSales = new ArrayList<>(); // top selling products: # of sales of each item
    
    // TRANSACTION IN HISTORY
    private static ArrayList<String> inTransaction_barCode = new ArrayList<>();
    private static ArrayList<String> inTransaction_itemName = new ArrayList<>();
    private static ArrayList<String> inTransaction_date = new ArrayList<>();
    private static ArrayList<String> inTransaction_qty = new ArrayList<>();
    private static ArrayList<String> inTransaction_supplier = new ArrayList<>();
    private static ArrayList<String> inTransaction_accountDetails = new ArrayList<>();
    
    // TRANSACTION OUT HISTORY 
    public static ArrayList<String> outTransaction_customerName = new ArrayList<>();
    public static ArrayList<String> outTransaction_barCode = new ArrayList<>();
    public static ArrayList<String> outTransaction_date = new ArrayList<>();
    public static ArrayList<String> outTransaction_qty = new ArrayList<>();
    public static ArrayList<String> outTransaction_destination = new ArrayList<>();
    public static ArrayList<String> outTransaction_purpose = new ArrayList<>();
    public static ArrayList<String> outTransaction_accountDetails = new ArrayList<>();
    
    // PRODUCT INFORMATION 
    public static ArrayList<String> barCode = new ArrayList<>();
    public static ArrayList<String> itemName = new ArrayList<>();
    public static ArrayList<String> itemType = new ArrayList<>();
    public static ArrayList<String> unitPrice = new ArrayList<>();
    public static ArrayList<String> retailPrice = new ArrayList<>();
    public static ArrayList<String> description = new ArrayList<>();
    public static ArrayList<String> status = new ArrayList<>();
    public static ArrayList<String> stocks = new ArrayList<>();

    public static int getTotalItems() {
        return totalItems;
    }

    public static void setTotalItems(int totalItems) {
        Toyland_InventorySystem.totalItems = totalItems;
    }

    public static int getLowStockItems() {
        return lowStockItems;
    }

    public static void setLowStockItems(int lowStockItems) {
        Toyland_InventorySystem.lowStockItems = lowStockItems;
    }

    public static String getGoTo_AccountCenter_of() {
        return goTo_AccountCenter_of;
    }

    public static void setGoTo_AccountCenter_of(String goTo_AccountCenter_of) {
        Toyland_InventorySystem.goTo_AccountCenter_of = goTo_AccountCenter_of;
    }

    public static int getOutofStockItems() {
        return outofStockItems;
    }

    public static void setOutofStockItems(int outofStockItems) {
        Toyland_InventorySystem.outofStockItems = outofStockItems;
    }

    public static String getBestSeller() {
        return bestSeller;
    }

    public static void setBestSeller(String bestSeller) {
        Toyland_InventorySystem.bestSeller = bestSeller;
    }

    public static int getHighestSales() {
        return highestSales;
    }

    public static void setHighestSales(int highestSales) {
        Toyland_InventorySystem.highestSales = highestSales;
    }

    public static String getUserID() {
        return UserID;
    }

    public static void setUserID(String UserID) {
        Toyland_InventorySystem.UserID = UserID;
    }

    public static String getUserAccName() {
        return UserAccName;
    }

    public static void setUserAccName(String UserAccName) {
        Toyland_InventorySystem.UserAccName = UserAccName;
    }

    public static String getUserAccType() {
        return UserAccType;
    }

    public static void setUserAccType(String UserAccType) {
        Toyland_InventorySystem.UserAccType = UserAccType;
    }

    public static ArrayList<String> getLogsAccDte() {
        return logsAccDte;
    }

    public static void setLogsAccDte(ArrayList<String> logsAccDte) {
        Toyland_InventorySystem.logsAccDte = logsAccDte;
    }

    public static void addLogsAccDte(String logsAccDte) {
        Toyland_InventorySystem.logsAccDte.add(logsAccDte);
    }

    public static ArrayList<String> getLogsAccNme() {
        return logsAccNme;
    }

    public static void setLogsAccNme(ArrayList<String> logsAccNme) {
        Toyland_InventorySystem.logsAccNme = logsAccNme;
    }

    public static void addLogsAccNme(String logsAccNme) {
        Toyland_InventorySystem.logsAccNme.add(logsAccNme);
    }

    public static ArrayList<String> getLogsAccID() {
        return logsAccID;
    }

    public static void setLogsAccID(ArrayList<String> logsAccID) {
        Toyland_InventorySystem.logsAccID = logsAccID;
    }

    public static void addLogsAccID(String logsAccID) {
        Toyland_InventorySystem.logsAccID.add(logsAccID);
    }

    public static ArrayList<String> getLogsAccTpe() {
        return logsAccTpe;
    }

    public static void setLogsAccTpe(ArrayList<String> logsAccTpe) {
        Toyland_InventorySystem.logsAccTpe = logsAccTpe;
    }

    public static void addLogsAccTpe(String logsAccTpe) {
        Toyland_InventorySystem.logsAccTpe.add(logsAccTpe);
    }

    public static ArrayList<String> getLogsAccAct() {
        return logsAccAct;
    }

    public static void setLogsAccAct(ArrayList<String> logsAccAct) {
        Toyland_InventorySystem.logsAccAct = logsAccAct;
    }

    public static void addLogsAccAct(String logsAccAct) {
        Toyland_InventorySystem.logsAccAct.add(logsAccAct);
    }

    public static ArrayList<String> getLogsAccTme() {
        return logsAccTme;
    }

    public static void setLogsAccTme(ArrayList<String> logsAccTme) {
        Toyland_InventorySystem.logsAccTme = logsAccTme;
    }

    public static void addLogsAccTme(String logsAccTme) {
        Toyland_InventorySystem.logsAccTme.add(logsAccTme);
    }

    public static ArrayList<String> getEmpAccID() {
        return EmpAccID;
    }

    public static void setEmpAccID(int index, String value) {
        Toyland_InventorySystem.EmpAccID.set(index, value);
    }

    public static void addEmpAccID(String EmpAccID) {
        Toyland_InventorySystem.EmpAccID.add(EmpAccID);
    }

    public static void removeEmpAccID(int index) {
        Toyland_InventorySystem.EmpAccID.remove(index);
    }

    public static ArrayList<String> getEmpName() {
        return EmpName;
    }

    public static void setEmpName(int index, String value) {
        Toyland_InventorySystem.EmpName.set(index, value);
    }

    public static void addEmpName(String EmpName) {
        Toyland_InventorySystem.EmpName.add(EmpName);
    }

    public static void removeEmpName(int index) {
        Toyland_InventorySystem.EmpName.remove(index);
    }

    public static ArrayList<String> getEmpUsername() {
        return EmpUsername;
    }

    public static void setEmpUsername(int index, String value) {
        Toyland_InventorySystem.EmpUsername.set(index, value);
    }

    public static void addEmpUsername(String EmpUsername) {
        Toyland_InventorySystem.EmpUsername.add(EmpUsername);
    }

    public static void removeEmpUsername(int index) {
        Toyland_InventorySystem.EmpUsername.remove(index);
    }

    public static ArrayList<String> getEmpPassword() {
        return EmpPassword;
    }

    public static void setEmpPassword(int index, String value) {
        Toyland_InventorySystem.EmpPassword.set(index, value);
    }

    public static void addEmpPassword(String EmpPassword) {
        Toyland_InventorySystem.EmpPassword.add(EmpPassword);
    }

    public static void removeEmpPassword(int index) {
        Toyland_InventorySystem.EmpPassword.remove(index);
    }

    public static ArrayList<String> getAdminAccID() {
        return AdminAccID;
    }

    public static void setAdminAccID(int index, String value) {
        Toyland_InventorySystem.AdminAccID.set(index, value);
    }

    public static void addAdminAccID(String AdminAccID) {
        Toyland_InventorySystem.AdminAccID.add(AdminAccID);
    }

    public static void removeAdminAccID(int index) {
        Toyland_InventorySystem.AdminAccID.remove(index);
    }

    public static ArrayList<String> getAdminName() {
        return AdminName;
    }

    public static void setAdminName(int index, String value) {
        Toyland_InventorySystem.AdminName.set(index, value);
    }

    public static void addAdminName(String AdminName) {
        Toyland_InventorySystem.AdminName.add(AdminName);
    }

    public static void removeAdminName(int index) {
        Toyland_InventorySystem.AdminName.remove(index);
    }

    public static ArrayList<String> getAdminUsername() {
        return AdminUsername;
    }

    public static void setAdminUsername(int index, String value) {
        Toyland_InventorySystem.AdminUsername.set(index, value);
    }

    public static void addAdminUsername(String AdminUsername) {
        Toyland_InventorySystem.AdminUsername.add(AdminUsername);
    }

    public static void removeAdminUsername(int index) {
        Toyland_InventorySystem.AdminUsername.remove(index);
    }

    public static ArrayList<String> getAdminPassword() {
        return AdminPassword;
    }

    public static void setAdminPassword(int index, String value) {
        Toyland_InventorySystem.AdminPassword.set(index, value);
    }  
    
    public static void addAdminPassword(String AdminPassword) {
        Toyland_InventorySystem.AdminPassword.add(AdminPassword);
    }

    public static void removeAdminPassword(int index) {
        Toyland_InventorySystem.AdminPassword.remove(index);
    }

    public static ArrayList<String> getInTransaction_barCode() {
        return inTransaction_barCode;
    }

    public static void setInTransaction_barCode(ArrayList<String> inTransaction_barCode) {
        Toyland_InventorySystem.inTransaction_barCode = inTransaction_barCode;
    }
    
    public static void addInTransaction_barCode(String inTransaction_barCode) {
        Toyland_InventorySystem.inTransaction_barCode.add(inTransaction_barCode);
    }

    public static ArrayList<String> getInTransaction_itemName() {
        return inTransaction_itemName;
    }

    public static void setInTransaction_itemName(ArrayList<String> inTransaction_itemName) {
        Toyland_InventorySystem.inTransaction_itemName = inTransaction_itemName;
    }
    
    public static void addInTransaction_itemName(String inTransaction_itemName) {
        Toyland_InventorySystem.inTransaction_itemName.add(inTransaction_itemName);
    }
    
    public static ArrayList<String> getInTransaction_date() {
        return inTransaction_date;
    }

    public static void setInTransaction_date(ArrayList<String> inTransaction_date) {
        Toyland_InventorySystem.inTransaction_date = inTransaction_date;
    }
    
    public static void addInTransaction_date(String inTransaction_date) {
        Toyland_InventorySystem.inTransaction_date.add(inTransaction_date);
    }

    public static ArrayList<String> getInTransaction_qty() {
        return inTransaction_qty;
    }

    public static void setInTransaction_qty(ArrayList<String> inTransaction_qty) {
        Toyland_InventorySystem.inTransaction_qty = inTransaction_qty;
    }
    
     public static void addInTransaction_qty(String inTransaction_qty) {
        Toyland_InventorySystem.inTransaction_qty.add(inTransaction_qty);
    }

    public static ArrayList<String> getInTransaction_supplier() {
        return inTransaction_supplier;
    }

    public static void setInTransaction_supplier(ArrayList<String> inTransaction_supplier) {
        Toyland_InventorySystem.inTransaction_supplier = inTransaction_supplier;
    }
    
    public static void addInTransaction_supplier(String inTransaction_supplier) {
        Toyland_InventorySystem.inTransaction_supplier.add(inTransaction_supplier);
    }

    public static ArrayList<String> getInTransaction_accountDetails() {
        return inTransaction_accountDetails;
    }

    public static void setInTransaction_accountDetails(ArrayList<String> inTransaction_accountDetails) {
        Toyland_InventorySystem.inTransaction_accountDetails = inTransaction_accountDetails;
    }
    
    public static void addInTransaction_accountDetails(String inTransaction_accountDetails) {
        Toyland_InventorySystem.inTransaction_accountDetails.add(inTransaction_accountDetails);
    }
    
    
    
public static void main(String[] args) {
        // TODO code application logic here
    }
}
