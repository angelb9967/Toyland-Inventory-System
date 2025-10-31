package toyland_inventorysystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class menuFrame extends javax.swing.JFrame {

    public menuFrame() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        showTime();
        editHeaders();
        showDate_and_Day();
        updateData_for_OverallInventory();

        Toyland_InventorySystem.addLogsAccAct("SIGN IN");
        Toyland_InventorySystem.addLogsAccDte(jLabel77.getText());
        jLabel79.setText(Toyland_InventorySystem.getUserAccName());
        jLabel80.setText(Toyland_InventorySystem.getUserID());
        getCurrentTime();
        updateData_For_Dashboard();
        updateData_For_ViewLogs();
        updateData_For_TopSelling_Products();

        // DISABLE AND ENABLE ACCESS BASED ON ACCOUNT TYPE 
        switch (Toyland_InventorySystem.getUserAccType()) {
            case "Employee":
                jTextField12.setText(jLabel77.getText());
                jTextField18.setText(jLabel77.getText());
                jPanel14.setBackground(new java.awt.Color(151, 79, 255));
                jPanel18.setBackground(new java.awt.Color(255, 222, 89));
                jLabel3.setFont(new Font("SansSerif", Font.PLAIN, 22));
                jLabel12.setFont(new Font("SansSerif", Font.BOLD, 22));
                jLabel4.setIcon(new ImageIcon("menuFrame - Dashboard_W.png"));
                jLabel13.setIcon(new ImageIcon("menuFrame - InventoryIn_B.png"));
                jLabel3.setEnabled(false);
                jLabel4.setEnabled(false);
                jLabel10.setEnabled(false);
                jLabel11.setEnabled(false);
                jLabel18.setEnabled(false);
                jLabel19.setEnabled(false);
                jLabel5.setEnabled(false);
                jLabel7.setEnabled(false);
                jPanel14.setEnabled(false); // DASHBOARD 
                jPanel17.setEnabled(false); // VIEW INVENTORY
                jPanel21.setEnabled(false); // VIEW LOGS 
                jPanel15.setEnabled(false); // MANAGE ACCOUNTS
                jTabbedPane1.setSelectedIndex(2);
                break;

            case "Admin":
                jLabel5.setEnabled(false);
                jLabel7.setEnabled(false);
                jPanel15.setEnabled(false); // MANAGE ACCOUNTS
                break;

            default: // SUPER ADMIN
                jLabel3.setEnabled(true);
                jLabel4.setEnabled(true);
                jLabel10.setEnabled(true);
                jLabel11.setEnabled(true);
                jLabel18.setEnabled(true);
                jLabel19.setEnabled(true);
                jLabel15.setEnabled(true);
                jLabel17.setEnabled(true);
                jPanel14.setEnabled(true); // DASHBOARD
                jPanel17.setEnabled(true); // VIEW INVENTORY
                jPanel21.setEnabled(true); // VIEW LOGS
                jPanel15.setEnabled(true); // MANAGE ACCOUNTS
        }
    }

    private void updateData_For_TopSelling_Products() {
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0);

        ArrayList<String> rearrangedFastItem = new ArrayList<>();
        ArrayList<String> rearrangedFastSales = new ArrayList<>();

        // TO POPULATE THE TEMPORARY LISTS WITH REARRANGED SALES AND ITEMS
        for (int i = 0; i < Toyland_InventorySystem.fastSales.size(); i++) {
            int maxIndex = i;
            int maxValue = Integer.MIN_VALUE;
            for (int j = i; j < Toyland_InventorySystem.fastSales.size(); j++) {
                int currentSale = Integer.parseInt(Toyland_InventorySystem.fastSales.get(j));
                if (currentSale > maxValue) {
                    maxValue = currentSale;
                    maxIndex = j;
                }
            }
            // TO SWAP THE SALES AND ITEMS AT THE CURRENT INDEX AND THE MAXIMUM INDEX
            Collections.swap(Toyland_InventorySystem.fastSales, i, maxIndex);
            Collections.swap(Toyland_InventorySystem.fastItem, i, maxIndex);

            // TO ADD THE REARRANGED SALES AND ITEMS TO THE TEMPORARY LISTS
            rearrangedFastItem.add(Toyland_InventorySystem.fastItem.get(i));
            rearrangedFastSales.add(Toyland_InventorySystem.fastSales.get(i));
        }

        int ctr = 1;
        for (int i = 0; i < rearrangedFastItem.size(); i++) {
            String rank = Integer.toString(ctr);
            String itemName = rearrangedFastItem.get(i);
            String itemsSold = rearrangedFastSales.get(i);
            Object[] row = {rank, itemName, itemsSold};
            model.addRow(row);
            ctr++;
        }
    }

    private void updateData_for_OverallInventory() {
        int size = Toyland_InventorySystem.barCode.size();
        for (int i = 0; i < size;) {
            String barcode = Toyland_InventorySystem.barCode.get(i);
            String name = Toyland_InventorySystem.itemName.get(i);
            String type = Toyland_InventorySystem.itemType.get(i);
            String UPrice = Toyland_InventorySystem.unitPrice.get(i);
            String RPrice = Toyland_InventorySystem.retailPrice.get(i);
            String status = Toyland_InventorySystem.status.get(i);
            String stocks = Toyland_InventorySystem.stocks.get(i);

            Object[] row = {barcode, name, type, UPrice, RPrice, status, stocks};
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.addRow(row);
            i++;
        }
    }

    private void updateData_For_ViewLogs() {
        int size = Toyland_InventorySystem.getLogsAccNme().size();
        for (int i = 0; i < size; i++) {
            String name = Toyland_InventorySystem.getLogsAccNme().get(i);
            String accID = Toyland_InventorySystem.getLogsAccID().get(i);
            String accType = Toyland_InventorySystem.getLogsAccTpe().get(i);
            String tme = Toyland_InventorySystem.getLogsAccTme().get(i);
            String actvty = Toyland_InventorySystem.getLogsAccAct().get(i);
            String dte = Toyland_InventorySystem.getLogsAccDte().get(i);

            Object[] row = {dte, name, accID, accType, actvty, tme};
            DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.addRow(row);
        }
    }

    private void clearFields_In_ViewInventory() {
        for (JTextField textField : new JTextField[]{jTextField24, jTextField25, jTextField26, jTextField27, jTextField28}) {
            textField.setText("");
        }
        jTextArea4.setText("");
        jTable3.clearSelection();
    }

    private void clearFields_In_InventoryIN() {
        for (JTextField textField : new JTextField[]{jTextField2, jTextField13, jTextField7}) {
            textField.setText("");
        }
    }

    private void clearProductInfo_In_InventoryIN() {
        for (JTextField textField : new JTextField[]{jTextField15, jTextField30, jTextField31, jTextField32, jTextField29}) {
            textField.setText("");
        }
        jLabel49.setText("");
        jLabel121.setIcon(new ImageIcon("Blank_ProductInfo.png"));
        jTextArea1.setText("");
    }

    private void clearFields_In_InventoryOUT() {
        for (JTextField textField : new JTextField[]{jTextField14, jTextField16, jTextField19, jTextField20}) {
            textField.setText("");
        }
        jTextArea2.setText("");
    }

    private void clearProductInfo_In_InventoryOUT() {
        for (JTextField textField : new JTextField[]{jTextField37, jTextField36, jTextField35, jTextField34, jTextField33}) {
            textField.setText("");
        }
        jLabel112.setText("");
        jLabel122.setIcon(new ImageIcon("Blank_ProductInfo.png"));
        jTextArea5.setText("");
    }

    private void clearFields_In_CreateProduct() {
        for (JTextField textField : new JTextField[]{jTextField17, jTextField21, jTextField22, jTextField23}) {
            textField.setText("");
        }
        jTextArea3.setText("");
        jComboBox3.setSelectedIndex(-1);
    }

    private void showExample_In_InventoryIN() {
        jTextField2.setText("512345000107");
        jTextField2.setEditable(false);
        jTextField2.setEnabled(false);
        jTextField12.setText("Jan 1 2023");
        jTextField12.setEditable(false);
        jTextField12.setEnabled(false);
        jTextField13.setText("100");
        jTextField13.setEditable(false);
        jTextField13.setEnabled(false);
        jTextField7.setText("Joyful Creations");
        jTextField7.setEditable(false);
        jTextField7.setEnabled(false);
        jLabel60.setEnabled(false);
        jLabel61.setEnabled(false);
    }

    private void showExample_In_InventoryOut() {
        jTextField14.setText("512345000107");
        jTextField14.setEditable(false);
        jTextField14.setEnabled(false);
        jTextField16.setText("DC Comics Batman The Joker 12 inch Action Figure");
        jTextField16.setEditable(false);
        jTextField16.setEnabled(false);
        jTextField18.setText("Jan 1 2023");
        jTextField18.setEditable(false);
        jTextField18.setEnabled(false);
        jTextField19.setText("10");
        jTextField19.setEditable(false);
        jTextField19.setEnabled(false);
        jTextArea2.setText("Sales");
        jTextArea2.setEditable(false);
        jTextArea2.setEnabled(false);
        jTextField20.setText("N/A [Customers]");
        jTextField20.setEditable(false);
        jTextField20.setEnabled(false);
        jLabel51.setEnabled(false);
        jLabel52.setEnabled(false);
    }

    private void showExample_In_CreateProducts() {
        jTextField17.setText("512345000107");
        jTextField17.setEditable(false);
        jTextField17.setEnabled(false);
        jTextField21.setText("DC Comics Batman The Joker 12 inch Action Figure");
        jTextField21.setEditable(false);
        jTextField21.setEnabled(false);
        jComboBox3.setEnabled(false);
        jTextField22.setText("500.00");
        jTextField22.setEditable(false);
        jTextField22.setEnabled(false);
        jTextField23.setText("600.00");
        jTextField23.setEditable(false);
        jTextField23.setEnabled(false);
        jTextArea3.setText("12-inch action figure that can pose into a variety of dynamic action poses.");
        jTextArea3.setEditable(false);
        jTextArea3.setEnabled(false);
        jComboBox3.setSelectedIndex(0);
    }

    private void editHeaders() {
        JTable[] tables = {jTable3, jTable4, jTable5, jTable6};
        Font headerFont = new Font("SansSerif", Font.BOLD, 14);
        for (JTable table : tables) {
            table.getTableHeader().setFont(headerFont);
        }
    }

    private void showTime() {
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
                String formattedTime = timeFormat.format(new Date());
                jLabel50.setText(formattedTime);
            }
        });
        timer.start();
    }

    private void getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        String formattedTime = timeFormat.format(currentTime);
        Toyland_InventorySystem.addLogsAccTme(formattedTime);
    }

    private void showDate_and_Day() {
        Date d = new Date();
        SimpleDateFormat date = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        jLabel77.setText(date.format(d));
        jLabel78.setText(day.format(d));
    }

    private void updateData_For_Dashboard() {
        Toyland_InventorySystem.setLowStockItems(0);
        Toyland_InventorySystem.setOutofStockItems(0);
        DefaultTableModel model1 = (DefaultTableModel) jTable3.getModel();
        int rowCount = model1.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            Object cellValue = model1.getValueAt(row, 5);
            if (cellValue.equals("Out of Stock")) {
                Toyland_InventorySystem.setOutofStockItems(Toyland_InventorySystem.getOutofStockItems() + 1);
            }
            if (cellValue.equals("Low Stock")) {
                Toyland_InventorySystem.setLowStockItems(Toyland_InventorySystem.getLowStockItems() + 1);
            }
        }

        Toyland_InventorySystem.setTotalItems(rowCount);
        jLabel22.setText(String.valueOf(rowCount));
        jLabel26.setText(String.valueOf(Toyland_InventorySystem.getLowStockItems()));
        jLabel37.setText(String.valueOf(Toyland_InventorySystem.getOutofStockItems()));
        jLabel40.setText(Toyland_InventorySystem.getBestSeller());
        jLabel44.setText(String.valueOf(Toyland_InventorySystem.getHighestSales()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLayeredPane18 = new javax.swing.JLayeredPane();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLayeredPane19 = new javax.swing.JLayeredPane();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        jLabel29 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLayeredPane13 = new javax.swing.JLayeredPane();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLayeredPane12 = new javax.swing.JLayeredPane();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLayeredPane15 = new javax.swing.JLayeredPane();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLayeredPane14 = new javax.swing.JLayeredPane();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel48 = new javax.swing.JPanel();
        jTextField11 = new javax.swing.JTextField();
        jPanel68 = new javax.swing.JPanel();
        jTextField24 = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jPanel69 = new javax.swing.JPanel();
        jTextField25 = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        jPanel70 = new javax.swing.JPanel();
        jTextField26 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jPanel71 = new javax.swing.JPanel();
        jTextField27 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jPanel72 = new javax.swing.JPanel();
        jTextField28 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel97 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        jLabel56 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jTextField10 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jTextField12 = new javax.swing.JTextField();
        jPanel42 = new javax.swing.JPanel();
        jTextField13 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jPanel47 = new javax.swing.JPanel();
        jPanel75 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jPanel76 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel31 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jLabel121 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jPanel73 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jPanel82 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        jLabel32 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jPanel53 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jPanel55 = new javax.swing.JPanel();
        jTextField18 = new javax.swing.JTextField();
        jPanel57 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel73 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jTextField20 = new javax.swing.JTextField();
        jPanel61 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel78 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jTextField37 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jPanel74 = new javax.swing.JPanel();
        jPanel81 = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jPanel83 = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        jPanel84 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLayeredPane8 = new javax.swing.JLayeredPane();
        jLabel33 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jPanel56 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jPanel64 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jPanel65 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel84 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel86 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jPanel66 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLayeredPane9 = new javax.swing.JLayeredPane();
        jLabel34 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        jLayeredPane16 = new javax.swing.JLayeredPane();
        jLabel74 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jPanel63 = new javax.swing.JPanel();
        jLayeredPane17 = new javax.swing.JLayeredPane();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLayeredPane10 = new javax.swing.JLayeredPane();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLayeredPane11 = new javax.swing.JLayeredPane();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel85 = new javax.swing.JPanel();
        jTextField38 = new javax.swing.JTextField();
        jPanel86 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jPanel14 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(255, 222, 89));

        jLabel50.setFont(new java.awt.Font("SansSerif", 0, 70)); // NOI18N
        jLabel50.setText("00:00:00 AM");

        jLabel77.setFont(new java.awt.Font("SansSerif", 0, 32)); // NOI18N
        jLabel77.setText("January 1, 2023");
        jLabel77.setBounds(10, 10, 260, 30);
        jLayeredPane18.add(jLabel77, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel78.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel78.setText("Monday");
        jLabel78.setBounds(10, 40, 360, 40);
        jLayeredPane18.add(jLabel78, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel79.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
        jLabel79.setText("Super Admin");
        jLabel79.setBounds(10, 10, 380, 35);
        jLayeredPane19.add(jLabel79, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel80.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel80.setText("SuperAdmin#23-1");
        jLabel80.setBounds(10, 40, 370, 38);
        jLayeredPane19.add(jLabel80, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel50)
                .addGap(43, 43, 43)
                .addComponent(jLayeredPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 346, Short.MAX_VALUE)
                .addComponent(jLayeredPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLayeredPane18))
                    .addComponent(jLayeredPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel10.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 120));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel29.setText("Dashboard");
        jLabel29.setBounds(40, 50, 367, 89);
        jLayeredPane4.add(jLabel29, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel13.setBackground(new java.awt.Color(0, 160, 200));

        jLabel27.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Out of Stock");
        jLabel27.setBounds(0, 20, 160, 30);
        jLayeredPane13.add(jLabel27, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel28.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Products");
        jLabel28.setBounds(0, 40, 120, 30);
        jLayeredPane13.add(jLabel28, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel37.setFont(new java.awt.Font("SansSerif", 1, 80)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("0");
        jLabel37.setBounds(0, 60, 150, 100);
        jLayeredPane13.add(jLabel37, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dashboard - OutOfStock.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel39)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLayeredPane13)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 8, Short.MAX_VALUE)
                        .addComponent(jLabel39)
                        .addGap(23, 23, 23))))
        );

        jPanel13.setBounds(760, 140, 340, 220);
        jLayeredPane4.add(jPanel13, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel23.setBackground(new java.awt.Color(8, 60, 100));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total");
        jLabel1.setBounds(0, 20, 72, 30);
        jLayeredPane3.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Products");
        jLabel2.setBounds(0, 40, 120, 30);
        jLayeredPane3.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 80)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("0");
        jLabel22.setBounds(0, 60, 150, 100);
        jLayeredPane3.add(jLabel22, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dashboard - ToyBundle.png"))); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane3))
                .addGap(29, 29, 29))
        );

        jPanel23.setBounds(40, 140, 340, 220);
        jLayeredPane4.add(jPanel23, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel24.setBackground(new java.awt.Color(197, 11, 77));

        jLabel24.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Low Stock");
        jLabel24.setBounds(0, 20, 130, 30);
        jLayeredPane12.add(jLabel24, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel25.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Products");
        jLabel25.setBounds(0, 40, 120, 30);
        jLayeredPane12.add(jLabel25, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel26.setFont(new java.awt.Font("SansSerif", 1, 80)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("0");
        jLabel26.setBounds(0, 60, 150, 100);
        jLayeredPane12.add(jLabel26, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dashboard - Warning.png"))); // NOI18N

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addContainerGap(32, Short.MAX_VALUE)
                        .addComponent(jLabel38))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLayeredPane12)))
                .addGap(31, 31, 31))
        );

        jPanel24.setBounds(400, 140, 340, 220);
        jLayeredPane4.add(jPanel24, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel25.setBackground(new java.awt.Color(151, 79, 255));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel25.setBounds(350, 660, 750, 20);
        jLayeredPane4.add(jPanel25, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel26.setBackground(new java.awt.Color(255, 222, 89));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Number of Items Sold");
        jLabel44.setBounds(0, 70, 260, 40);
        jLayeredPane15.add(jLabel44, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel45.setText("Best Seller Product");
        jLabel45.setBounds(0, 0, 290, 60);
        jLayeredPane15.add(jLabel45, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel46.setText("Items Sold");
        jLabel46.setBounds(0, 30, 180, 50);
        jLayeredPane15.add(jLabel46, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dashboard - Sales.png"))); // NOI18N

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLayeredPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel26.setBounds(580, 420, 520, 220);
        jLayeredPane4.add(jPanel26, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel27.setBackground(new java.awt.Color(255, 222, 89));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dashboard - BestSelling.png"))); // NOI18N

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Item Name");
        jLabel40.setBounds(0, 80, 180, 40);
        jLayeredPane14.add(jLabel40, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel41.setText("Best Selling");
        jLabel41.setBounds(0, 10, 170, 60);
        jLayeredPane14.add(jLabel41, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel42.setText("Product");
        jLabel42.setBounds(0, 40, 110, 50);
        jLayeredPane14.add(jLabel42, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(0, 17, Short.MAX_VALUE)
                        .addComponent(jLayeredPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel27.setBounds(40, 420, 520, 220);
        jLayeredPane4.add(jPanel27, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel28.setBackground(new java.awt.Color(151, 79, 255));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 990, Short.MAX_VALUE)
        );

        jPanel28.setBounds(1140, 20, 20, 990);
        jLayeredPane4.add(jPanel28, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel30.setBackground(new java.awt.Color(167, 114, 242));
        jPanel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goTo_inventoryOut_Transactions(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel30MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel30MouseExited(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dashboard - InventoryOut_Trans.png"))); // NOI18N

        jPanel36.setBackground(new java.awt.Color(255, 222, 89));

        jLabel55.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("<html> ‎‎Inventory Out<br>Transactions</html> ");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel55)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel16)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel30.setBounds(1180, 520, 320, 450);
        jLayeredPane4.add(jPanel30, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel31.setBackground(new java.awt.Color(151, 79, 255));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel31.setBounds(40, 380, 1060, 20);
        jLayeredPane4.add(jPanel31, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel40.setBackground(new java.awt.Color(167, 114, 242));
        jPanel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goTo_inventoryIn_Transactions(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel40MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel40MouseExited(evt);
            }
        });

        jPanel33.setBackground(new java.awt.Color(255, 222, 89));

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("<html> ‎‎Inventory In<br>Transactions</html> ");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
        );

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Dashboard - InventoryIn_Trans.png"))); // NOI18N

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel54)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel54)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel40.setBounds(1180, 50, 320, 440);
        jLayeredPane4.add(jPanel40, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel85.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        jLabel85.setText("Top Selling Products");
        jLabel85.setBounds(50, 650, 300, 40);
        jLayeredPane4.add(jLabel85, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTable6.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rank", "Item Name", "Items Sold"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.setRowHeight(24);
        jTable6.setSelectionBackground(new java.awt.Color(151, 79, 255));
        jTable6.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable6.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jTable6);
        jTable6.getColumnModel().getColumn(0).setResizable(false);
        jTable6.getColumnModel().getColumn(1).setResizable(false);
        jTable6.getColumnModel().getColumn(2).setResizable(false);

        jScrollPane8.setBounds(40, 700, 1060, 270);
        jLayeredPane4.add(jScrollPane8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab8", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel30.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel30.setText("View Inventory");
        jLabel30.setBounds(40, 50, 510, 89);
        jLayeredPane5.add(jLabel30, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 51));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTable3.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bar Code", "Item Name", "Item Type", "Unit Price", "Retail Price", "Status", "Available Stocks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setRowHeight(24);
        jTable3.setSelectionBackground(new java.awt.Color(167, 114, 242));
        jTable3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewInventory_ProductInfo(evt);
            }
        });
        jScrollPane5.setViewportView(jTable3);
        jTable3.getColumnModel().getColumn(0).setResizable(false);
        jTable3.getColumnModel().getColumn(1).setResizable(false);
        jTable3.getColumnModel().getColumn(2).setResizable(false);
        jTable3.getColumnModel().getColumn(3).setResizable(false);
        jTable3.getColumnModel().getColumn(4).setResizable(false);
        jTable3.getColumnModel().getColumn(5).setResizable(false);
        jTable3.getColumnModel().getColumn(6).setResizable(false);

        jScrollPane5.setBounds(40, 140, 1440, 460);
        jLayeredPane5.add(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel48.setBackground(new java.awt.Color(151, 79, 255));

        jTextField11.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewInventory_SearchBar_MouseClicked(evt);
            }
        });
        jTextField11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                viewInventory_SearchBar_FocusLost(evt);
            }
        });
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                viewInventory_SearchBar_KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                viewInventory_SearchBar(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel48.setBounds(560, 80, 720, 50);
        jLayeredPane5.add(jPanel48, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel68.setBackground(new java.awt.Color(0, 0, 0));
        jPanel68.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField24.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField24.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField24, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel68.setBounds(50, 810, 280, 40);
        jLayeredPane5.add(jPanel68, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel105.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel105.setText("Item Name:");
        jLabel105.setBounds(50, 790, 120, 24);
        jLayeredPane5.add(jLabel105, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel69.setBackground(new java.awt.Color(0, 0, 0));
        jPanel69.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField25.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField25.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField25, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel69Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel69.setBounds(50, 720, 280, 40);
        jLayeredPane5.add(jPanel69, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel106.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel106.setText("Bar Code:");
        jLabel106.setBounds(50, 700, 120, 24);
        jLayeredPane5.add(jLabel106, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel70.setBackground(new java.awt.Color(0, 0, 0));
        jPanel70.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField26.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField26.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField26, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel70.setBounds(50, 910, 280, 40);
        jLayeredPane5.add(jPanel70, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel107.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel107.setText("Item Type:");
        jLabel107.setBounds(50, 880, 120, 30);
        jLayeredPane5.add(jLabel107, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel71.setBackground(new java.awt.Color(0, 0, 0));
        jPanel71.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField27.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField27.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField27, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel71Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel71.setBounds(360, 720, 280, 40);
        jLayeredPane5.add(jPanel71, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel108.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel108.setText("Unit Price:");
        jLabel108.setBounds(360, 700, 120, 24);
        jLayeredPane5.add(jLabel108, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel72.setBackground(new java.awt.Color(0, 0, 0));
        jPanel72.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField28.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField28.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField28, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel72Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel72.setBounds(360, 810, 280, 40);
        jLayeredPane5.add(jPanel72, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel109.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel109.setText("Retail Price:");
        jLabel109.setBounds(360, 790, 120, 24);
        jLayeredPane5.add(jLabel109, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel110.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel110.setText("Description:");
        jLabel110.setBounds(670, 690, 130, 24);
        jLayeredPane5.add(jLabel110, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane10.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N

        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane10.setViewportView(jTextArea4);

        jScrollPane10.setBounds(670, 710, 420, 250);
        jLayeredPane5.add(jScrollPane10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel97.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel97.setText("Product Information");
        jLabel97.setBounds(40, 620, 470, 50);
        jLayeredPane5.add(jLabel97, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel37.setBackground(new java.awt.Color(151, 79, 255));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel37.setBounds(520, 620, 960, 40);
        jLayeredPane5.add(jPanel37, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel38.setBackground(new java.awt.Color(255, 222, 89));

        jLabel120.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setText("CLEAR");
        jLabel120.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewInventory_Clear_SearchBar(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel38.setBounds(1300, 80, 180, 50);
        jLayeredPane5.add(jPanel38, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab1", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel56.setText("Bar Code:*");
        jLabel56.setBounds(40, 140, 280, 24);
        jLayeredPane6.add(jLabel56, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inventoryIn_Barcode_Validation(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inventoryIn_ProductInfo(evt);
            }
        });
        jTextField2.setBounds(40, 160, 430, 40);
        jLayeredPane6.add(jTextField2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel34.setBackground(new java.awt.Color(0, 0, 0));
        jPanel34.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel34.setBounds(40, 160, 430, 40);
        jLayeredPane6.add(jPanel34, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel35.setBackground(new java.awt.Color(255, 222, 89));
        jPanel35.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel35.setBounds(40, 160, 270, 40);
        jLayeredPane6.add(jPanel35, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel39.setBackground(new java.awt.Color(151, 79, 255));
        jPanel39.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel39.setBounds(40, 160, 270, 40);
        jLayeredPane6.add(jPanel39, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField10.setBackground(new java.awt.Color(241, 241, 241));
        jTextField10.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jTextField10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField10.setBounds(50, 160, 260, 40);
        jLayeredPane6.add(jTextField10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel58.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel58.setText("Date:");
        jLabel58.setBounds(40, 220, 140, 24);
        jLayeredPane6.add(jLabel58, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel41.setBackground(new java.awt.Color(0, 0, 0));
        jPanel41.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField12.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel41.setBounds(40, 240, 430, 40);
        jLayeredPane6.add(jPanel41, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel42.setBackground(new java.awt.Color(0, 0, 0));
        jPanel42.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField13.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField13.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inventoryIn_Quantity_Validation(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField13, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField13, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel42.setBounds(40, 410, 270, 40);
        jLayeredPane6.add(jPanel42, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel59.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel59.setText("Quantity:*");
        jLabel59.setBounds(40, 390, 210, 24);
        jLayeredPane6.add(jLabel59, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel43.setBackground(new java.awt.Color(255, 222, 89));

        jLabel61.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("+");
        jLabel61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryIn_IncrementQuantity(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel43.setBounds(400, 410, 70, 40);
        jLayeredPane6.add(jPanel43, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel45.setBackground(new java.awt.Color(255, 222, 89));

        jLabel60.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("-");
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryIn_DecrementQuantity(evt);
            }
        });

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel45.setBounds(320, 410, 70, 40);
        jLayeredPane6.add(jPanel45, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel64.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel64.setText("Supplier:*");
        jLabel64.setBounds(40, 310, 140, 24);
        jLayeredPane6.add(jLabel64, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inventoryIn_Supplier_Validation(evt);
            }
        });
        jTextField7.setBounds(40, 330, 430, 40);
        jLayeredPane6.add(jTextField7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel47.setBackground(new java.awt.Color(0, 0, 0));
        jPanel47.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel47.setBounds(40, 330, 430, 40);
        jLayeredPane6.add(jPanel47, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel75.setBackground(new java.awt.Color(255, 222, 89));

        jLabel88.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel88.setText("ADD");
        jLabel88.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryIn_ADD(evt);
            }
        });

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPanel75.setBounds(30, 810, 370, 70);
        jLayeredPane6.add(jPanel75, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel76.setBackground(new java.awt.Color(255, 222, 89));

        jLabel89.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel89.setText("CANCEL");
        jLabel89.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryIn_CANCEL(evt);
            }
        });

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel89, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPanel76.setBounds(420, 810, 380, 70);
        jLayeredPane6.add(jPanel76, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel77.setBackground(new java.awt.Color(255, 222, 89));

        jLabel90.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setText("SAVE");
        jLabel90.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryIn_SAVE(evt);
            }
        });

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel90, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPanel77.setBounds(30, 900, 770, 70);
        jLayeredPane6.add(jPanel77, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel48.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel48.setText("Inventory In");
        jLabel48.setBounds(40, 50, 393, 89);
        jLayeredPane6.add(jLabel48, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel49.setBackground(new java.awt.Color(151, 79, 255));

        jLabel62.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel62.setText("Description: ");

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(jTextArea1);
        jTextArea1.setEditable(false);

        jLabel31.setFont(new java.awt.Font("SansSerif", 1, 55)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Product Information");

        jPanel44.setBackground(new java.awt.Color(151, 79, 255));

        jLabel121.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Blank_ProductInfo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel121)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel46.setBackground(new java.awt.Color(255, 222, 89));

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel54.setBackground(new java.awt.Color(151, 79, 255));

        jLabel99.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("Barcode:");

        jTextField15.setEditable(false);
        jTextField15.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jLabel98.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel98.setText("Item Name:");

        jTextField29.setEditable(false);
        jTextField29.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField29.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jLabel57.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel57.setText("Item Type:");

        jTextField30.setEditable(false);
        jTextField30.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField30.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jLabel65.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel65.setText("Unit Price:");

        jTextField31.setEditable(false);
        jTextField31.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField31.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jLabel63.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel63.setText("Retail Price:");

        jTextField32.setEditable(false);
        jTextField32.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField32.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65)
                            .addComponent(jLabel63))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField32, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                            .addComponent(jTextField29)))
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 24, Short.MAX_VALUE))
                            .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField15)
                            .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField15, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField30, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField31, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField32, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField29, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(34, 34, 34))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel62)
                            .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel49.setBounds(850, 20, 670, 990);
        jLayeredPane6.add(jPanel49, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel73.setBackground(new java.awt.Color(255, 222, 89));

        jLabel49.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setToolTipText("");

        jPanel82.setBackground(new java.awt.Color(151, 79, 255));

        jLabel111.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel111.setText("<html> ‎ ‎‎  ‎‎ ‎‎ ‎‎   ‎‎ Current<br>Available Stocks</html>\n");
        jLabel111.setToolTipText("");

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel82Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel73Layout.createSequentialGroup()
                .addComponent(jPanel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel73.setBounds(500, 160, 300, 290);
        jLayeredPane6.add(jPanel73, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel32.setText("Inventory Out");
        jLabel32.setBounds(40, 50, 460, 89);
        jLayeredPane7.add(jLabel32, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel66.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel66.setText("Bar Code:*");
        jLabel66.setBounds(40, 140, 120, 24);
        jLayeredPane7.add(jLabel66, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inventoryOut_Barcode_Validation(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inventoryOut_ProductInfo(evt);
            }
        });
        jTextField14.setBounds(40, 160, 430, 40);
        jLayeredPane7.add(jTextField14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel53.setBackground(new java.awt.Color(0, 0, 0));
        jPanel53.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel53.setBounds(40, 160, 430, 40);
        jLayeredPane7.add(jPanel53, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel68.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel68.setText("Customer Name:*");
        jLabel68.setBounds(40, 220, 170, 24);
        jLayeredPane7.add(jLabel68, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField16.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inventoryOut_CustomerName_Validation(evt);
            }
        });
        jTextField16.setBounds(40, 240, 430, 40);
        jLayeredPane7.add(jTextField16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel55.setBackground(new java.awt.Color(0, 0, 0));
        jPanel55.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel55.setBounds(40, 240, 430, 40);
        jLayeredPane7.add(jPanel55, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField18.setEditable(false);
        jTextField18.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField18.setBounds(40, 330, 430, 40);
        jLayeredPane7.add(jTextField18, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel57.setBackground(new java.awt.Color(0, 0, 0));
        jPanel57.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel57.setBounds(40, 330, 430, 40);
        jLayeredPane7.add(jPanel57, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel70.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel70.setText("Date:");
        jLabel70.setBounds(40, 310, 140, 24);
        jLayeredPane7.add(jLabel70, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel71.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel71.setText("Quantity:*");
        jLabel71.setBounds(40, 390, 250, 24);
        jLayeredPane7.add(jLabel71, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel58.setBackground(new java.awt.Color(0, 0, 0));
        jPanel58.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField19.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inventoryOut_Quantity_Validation(evt);
            }
        });

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField19, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel58.setBounds(40, 410, 270, 40);
        jLayeredPane7.add(jPanel58, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel72.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel72.setText("Purpose:                                                     (optional)");
        jLabel72.setBounds(40, 560, 430, 24);
        jLayeredPane7.add(jLabel72, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane4.setViewportView(jTextArea2);

        jScrollPane4.setBounds(40, 580, 430, 130);
        jLayeredPane7.add(jScrollPane4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel73.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel73.setText("Destination / Address:*");
        jLabel73.setBounds(40, 480, 300, 24);
        jLayeredPane7.add(jLabel73, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel59.setBackground(new java.awt.Color(0, 0, 0));
        jPanel59.setPreferredSize(new java.awt.Dimension(280, 15));

        jTextField20.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField20, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel59.setBounds(40, 500, 430, 40);
        jLayeredPane7.add(jPanel59, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel61.setBackground(new java.awt.Color(255, 222, 89));

        jLabel51.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("+");
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryOut_Increment(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel61.setBounds(400, 410, 70, 40);
        jLayeredPane7.add(jPanel61, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel60.setBackground(new java.awt.Color(255, 222, 89));

        jLabel52.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("-");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryOut_Decrement(evt);
            }
        });

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel60Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel60.setBounds(320, 410, 70, 40);
        jLayeredPane7.add(jPanel60, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel78.setBackground(new java.awt.Color(255, 222, 89));

        jLabel91.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("ADD");
        jLabel91.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryOut_ADD(evt);
            }
        });

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel78.setBounds(30, 810, 370, 70);
        jLayeredPane7.add(jPanel78, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel79.setBackground(new java.awt.Color(255, 222, 89));

        jLabel92.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("SAVE");
        jLabel92.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryOut_SAVE(evt);
            }
        });

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPanel79.setBounds(30, 900, 770, 70);
        jLayeredPane7.add(jPanel79, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel80.setBackground(new java.awt.Color(255, 222, 89));

        jLabel93.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("CANCEL");
        jLabel93.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryOut_CANCEL(evt);
            }
        });

        javax.swing.GroupLayout jPanel80Layout = new javax.swing.GroupLayout(jPanel80);
        jPanel80.setLayout(jPanel80Layout);
        jPanel80Layout.setHorizontalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        jPanel80Layout.setVerticalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel93, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPanel80.setBounds(420, 810, 380, 70);
        jLayeredPane7.add(jPanel80, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel50.setBackground(new java.awt.Color(151, 79, 255));
        jPanel50.setPreferredSize(new java.awt.Dimension(670, 1000));

        jLabel67.setFont(new java.awt.Font("SansSerif", 1, 55)); // NOI18N
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("Product Information");

        jTextField33.setEditable(false);
        jTextField33.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField33.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField33.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jTextField34.setEditable(false);
        jTextField34.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField34.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField34.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jTextField35.setEditable(false);
        jTextField35.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField35.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField35.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jTextField36.setEditable(false);
        jTextField36.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField36.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField36.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jTextField37.setEditable(false);
        jTextField37.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextField37.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField37.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));

        jLabel69.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel69.setText("Retail Price:");

        jLabel100.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel100.setText("Description: ");

        jPanel74.setBackground(new java.awt.Color(151, 79, 255));

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );

        jPanel81.setBackground(new java.awt.Color(255, 222, 89));

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jLabel102.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel102.setText("Item Type:");

        jLabel101.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel101.setText("Unit Price:");

        jLabel122.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Blank_ProductInfo.png"))); // NOI18N

        jLabel103.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel103.setText("Barcode:");

        jLabel104.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel104.setText("Item Name:");

        jScrollPane2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextArea5.setColumns(20);
        jTextArea5.setEditable(false);
        jTextArea5.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setWrapStyleWord(true);
        jTextArea5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(jTextArea5);
        jTextArea1.setEditable(false);

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel100)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField34)
                                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField37)
                                        .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField35, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jLabel100)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel50.setBounds(850, 20, 670, 990);
        jLayeredPane7.add(jPanel50, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel83.setBackground(new java.awt.Color(255, 222, 89));

        jLabel112.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.setToolTipText("");

        jPanel84.setBackground(new java.awt.Color(151, 79, 255));

        jLabel113.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel113.setText("<html> ‎ ‎‎  ‎‎ ‎‎ ‎‎   ‎‎ Current<br>Available Stocks</html>\n");
        jLabel113.setToolTipText("");

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel84Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addComponent(jPanel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel83.setBounds(500, 160, 300, 290);
        jLayeredPane7.add(jPanel83, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel33.setText("Create Product");
        jLabel33.setBounds(40, 50, 770, 89);
        jLayeredPane8.add(jLabel33, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField17.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                createProduct_Barcode_Validation(evt);
            }
        });
        jTextField17.setBounds(40, 160, 420, 40);
        jLayeredPane8.add(jTextField17, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel56.setBackground(new java.awt.Color(0, 0, 0));
        jPanel56.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel56.setBounds(40, 160, 420, 40);
        jLayeredPane8.add(jPanel56, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel81.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel81.setText("Bar Code:*");
        jLabel81.setBounds(40, 140, 120, 24);
        jLayeredPane8.add(jLabel81, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField21.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField21.setBounds(40, 230, 420, 40);
        jLayeredPane8.add(jTextField21, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel64.setBackground(new java.awt.Color(0, 0, 0));
        jPanel64.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel64.setBounds(40, 230, 420, 40);
        jLayeredPane8.add(jPanel64, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel82.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel82.setText("Item Name:*");
        jLabel82.setBounds(40, 210, 120, 24);
        jLayeredPane8.add(jLabel82, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel83.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel83.setText("Unit Price:*");
        jLabel83.setBounds(40, 390, 200, 24);
        jLayeredPane8.add(jLabel83, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField22.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                createProduct_UnitPrice_Validation(evt);
            }
        });
        jTextField22.setBounds(40, 410, 420, 40);
        jLayeredPane8.add(jTextField22, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel65.setBackground(new java.awt.Color(0, 0, 0));
        jPanel65.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel65.setBounds(40, 410, 420, 40);
        jLayeredPane8.add(jPanel65, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox3.setBackground(new java.awt.Color(255, 222, 89));
        jComboBox3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Action Figures", "Vehicles", "Combat Weapons & Accessories", "Dolls and Accessories", "Plush Toys", "Blocks & Puzzles", "Reading & Writing", "Electronic Learning", "Consoles & Accessories", "Indoor Games", "Arts & Crafts", "Musical Toys", "Costumes", "Career Toys", "Junior Sports", "Water Toys & Accessories", "Bikes & Trikes", "Wagons & Rockers", "Skates & Scooters", "Rechargeable Cars" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jComboBox3.setBounds(40, 300, 420, 40);
        jLayeredPane8.add(jComboBox3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel84.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel84.setText("Item Type:*");
        jLabel84.setBounds(40, 280, 120, 24);
        jLayeredPane8.add(jLabel84, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setWrapStyleWord(true);
        jTextArea3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane9.setViewportView(jTextArea3);

        jScrollPane9.setBounds(40, 570, 420, 160);
        jLayeredPane8.add(jScrollPane9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel86.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel86.setText("Description:                                             (optional)");
        jLabel86.setBounds(40, 550, 440, 24);
        jLayeredPane8.add(jLabel86, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel20.setBackground(new java.awt.Color(255, 222, 89));

        jLabel95.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setText("ADD");
        jLabel95.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createProduct_ADD(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanel20.setBounds(40, 790, 200, 80);
        jLayeredPane8.add(jPanel20, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel32.setBackground(new java.awt.Color(255, 222, 89));

        jLabel94.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel94.setText("SAVE");
        jLabel94.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createProduct_SAVE(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanel32.setBounds(40, 890, 420, 80);
        jLayeredPane8.add(jPanel32, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel51.setBackground(new java.awt.Color(255, 222, 89));

        jLabel96.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setText("CANCEL");
        jLabel96.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createProduct_CANCEL(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel96, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel96, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanel51.setBounds(260, 790, 200, 80);
        jLayeredPane8.add(jPanel51, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTextField23.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField23.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                createProduct_RetailPrice_Validation(evt);
            }
        });
        jTextField23.setBounds(40, 490, 420, 40);
        jLayeredPane8.add(jTextField23, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel66.setBackground(new java.awt.Color(0, 0, 0));
        jPanel66.setPreferredSize(new java.awt.Dimension(280, 15));

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel66.setBounds(40, 490, 420, 40);
        jLayeredPane8.add(jPanel66, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel87.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel87.setText("Retail Price:*");
        jLabel87.setBounds(40, 470, 120, 24);
        jLayeredPane8.add(jLabel87, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel52.setBackground(new java.awt.Color(151, 79, 255));

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel52.setBounds(40, 750, 420, 20);
        jLayeredPane8.add(jPanel52, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel67.setBackground(new java.awt.Color(151, 79, 255));

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel67.setBounds(40, 360, 420, 20);
        jLayeredPane8.add(jPanel67, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab4", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel34.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel34.setText("Manage Accounts");
        jLabel34.setBounds(40, 50, 600, 89);
        jLayeredPane9.add(jLabel34, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel62.setBackground(new java.awt.Color(255, 222, 89));
        jPanel62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goToEmp(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        jLabel74.setText("Employee");

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ManageAccounts - Employee.png"))); // NOI18N

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel62Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel53))
                    .addGroup(jPanel62Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel74)))
                .addGap(384, 384, 384)
                .addComponent(jLayeredPane16)
                .addContainerGap())
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel62.setBounds(410, 140, 340, 400);
        jLayeredPane9.add(jPanel62, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel63.setBackground(new java.awt.Color(255, 222, 89));
        jPanel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goToAdmin(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("SansSerif", 1, 28)); // NOI18N
        jLabel75.setText("Administrator");

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ManageAccounts - Administrator.png"))); // NOI18N

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel76)
                    .addComponent(jLabel75))
                .addGap(74, 74, 74)
                .addComponent(jLayeredPane17)
                .addContainerGap())
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel63Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jPanel63.setBounds(40, 140, 340, 400);
        jLayeredPane9.add(jPanel63, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLayeredPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab5", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel35.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel35.setText("View Activity");
        jLabel35.setBounds(40, 50, 530, 89);
        jLayeredPane10.add(jLabel35, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 51));
        jScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTable4.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Account Name", "Account ID", "Account Type", "Activity", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setRowHeight(24);
        jTable4.setSelectionBackground(new java.awt.Color(151, 79, 255));
        jTable4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(jTable4);
        jTable4.getColumnModel().getColumn(0).setResizable(false);
        jTable4.getColumnModel().getColumn(1).setResizable(false);
        jTable4.getColumnModel().getColumn(2).setResizable(false);
        jTable4.getColumnModel().getColumn(3).setResizable(false);
        jTable4.getColumnModel().getColumn(4).setResizable(false);
        jTable4.getColumnModel().getColumn(5).setResizable(false);

        jScrollPane6.setBounds(50, 150, 1310, 820);
        jLayeredPane10.add(jScrollPane6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel29.setBackground(new java.awt.Color(255, 222, 89));

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1330, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );

        jPanel29.setBounds(40, 140, 1330, 830);
        jLayeredPane10.add(jPanel29, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLayeredPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab6", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel36.setFont(new java.awt.Font("SansSerif", 1, 70)); // NOI18N
        jLabel36.setText("View Logs");
        jLabel36.setBounds(40, 50, 350, 89);
        jLayeredPane11.add(jLabel36, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 51));
        jScrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTable5.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Account Name", "Account ID", "Account Type", "Activity", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.setRowHeight(24);
        jTable5.setSelectionBackground(new java.awt.Color(151, 79, 255));
        jTable5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable5);
        jTable5.getColumnModel().getColumn(0).setResizable(false);
        jTable5.getColumnModel().getColumn(1).setResizable(false);
        jTable5.getColumnModel().getColumn(2).setResizable(false);
        jTable5.getColumnModel().getColumn(3).setResizable(false);
        jTable5.getColumnModel().getColumn(4).setResizable(false);
        jTable5.getColumnModel().getColumn(5).setResizable(false);

        jScrollPane7.setBounds(40, 140, 1440, 830);
        jLayeredPane11.add(jScrollPane7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel85.setBackground(new java.awt.Color(151, 79, 255));

        jTextField38.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField38.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        jTextField38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewLogs_SearchBar_MouseClicked(evt);
            }
        });
        jTextField38.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                viewLogs_SearchBar_FocusLost(evt);
            }
        });
        jTextField38.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                viewLogs_SearchBar_KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField38, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField38, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel85.setBounds(430, 70, 850, 50);
        jLayeredPane11.add(jPanel85, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel86.setBackground(new java.awt.Color(255, 222, 89));

        jLabel114.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel114.setText("CLEAR");
        jLabel114.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewLogs_SearchBar_Clear(evt);
            }
        });

        javax.swing.GroupLayout jPanel86Layout = new javax.swing.GroupLayout(jPanel86);
        jPanel86.setLayout(jPanel86Layout);
        jPanel86Layout.setHorizontalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        jPanel86Layout.setVerticalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel86.setBounds(1300, 70, 180, 50);
        jLayeredPane11.add(jPanel86, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLayeredPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 1540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab7", jPanel9);

        jPanel10.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, 1030));

        jPanel11.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1540, 1110));

        jPanel11.setBounds(400, -30, 1540, 1110);
        jLayeredPane1.add(jPanel11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBackground(new java.awt.Color(151, 79, 255));

        jPanel14.setBackground(new java.awt.Color(255, 222, 89));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboard(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel14MouseExited(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 22)); // NOI18N
        jLabel3.setText("Dashboard");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - Dashboard_B.png"))); // NOI18N
        jLabel4.setToolTipText("");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel4)
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        jPanel14.setBounds(0, 210, 400, 90);
        jLayeredPane2.add(jPanel14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel22.setBackground(new java.awt.Color(151, 79, 255));
        jPanel22.setPreferredSize(new java.awt.Dimension(400, 90));
        jPanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signOut(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel22MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel22MouseExited(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel20.setText("Sign Out");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - SignOut_W.png"))); // NOI18N
        jLabel21.setToolTipText("");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel21)
                .addGap(31, 31, 31)
                .addComponent(jLabel20)
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel22.setBounds(0, 840, 400, 90);
        jLayeredPane2.add(jPanel22, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LoginFrame - Logo Picture.png"))); // NOI18N
        jLabel6.setBounds(20, 20, 360, 160);
        jLayeredPane2.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel15.setBackground(new java.awt.Color(151, 79, 255));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageAccounts(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel15MouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel5.setText("Manage Accounts");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - ManageAccounts_W.png"))); // NOI18N
        jLabel7.setToolTipText("");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel7)
                .addGap(31, 31, 31)
                .addComponent(jLabel5)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );

        jPanel15.setBounds(0, 750, 400, 90);
        jLayeredPane2.add(jPanel15, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel16.setBackground(new java.awt.Color(151, 79, 255));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createProduct(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel16MouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel8.setText("Create Product");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - CreateProduct_W.png"))); // NOI18N
        jLabel9.setToolTipText("");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel9)
                .addGap(33, 33, 33)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel16.setBounds(0, 570, 400, 90);
        jLayeredPane2.add(jPanel16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel17.setBackground(new java.awt.Color(151, 79, 255));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewInventory(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel17MouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel10.setText("View Inventory");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - ViewInventory_W.png"))); // NOI18N
        jLabel11.setToolTipText("");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel11)
                .addGap(33, 33, 33)
                .addComponent(jLabel10)
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel17.setBounds(0, 300, 400, 90);
        jLayeredPane2.add(jPanel17, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel18.setBackground(new java.awt.Color(151, 79, 255));
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryIn(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel18MouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel12.setText("Inventory In");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - InventoryIn_W.png"))); // NOI18N
        jLabel13.setToolTipText("");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel13)
                .addGap(36, 36, 36)
                .addComponent(jLabel12)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel18.setBounds(0, 390, 400, 90);
        jLayeredPane2.add(jPanel18, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel19.setBackground(new java.awt.Color(151, 79, 255));
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventoryOut(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel19MouseExited(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel14.setText("Inventory Out");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - InventoryOut_W.png"))); // NOI18N
        jLabel15.setToolTipText("");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel15)
                .addGap(37, 37, 37)
                .addComponent(jLabel14)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel19.setBounds(0, 480, 400, 90);
        jLayeredPane2.add(jPanel19, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel21.setBackground(new java.awt.Color(151, 79, 255));
        jPanel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewLogs(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel21MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel21MouseExited(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 22)); // NOI18N
        jLabel18.setText("View Logs");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuFrame - ViewLogs_W.png"))); // NOI18N
        jLabel19.setToolTipText("");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel18)
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(16, 16, 16))
        );

        jPanel21.setBounds(0, 660, 400, 90);
        jLayeredPane2.add(jPanel21, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE))
        );

        jPanel1.setBounds(0, 0, 400, 1080);
        jLayeredPane1.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1921, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboard
        if (jPanel14.isEnabled()) {
            jPanel14.setBackground(new java.awt.Color(255, 222, 89));
            jPanel17.setBackground(new java.awt.Color(151, 79, 255));
            jPanel18.setBackground(new java.awt.Color(151, 79, 255));
            jPanel19.setBackground(new java.awt.Color(151, 79, 255));
            jPanel16.setBackground(new java.awt.Color(151, 79, 255));
            jPanel15.setBackground(new java.awt.Color(151, 79, 255));
            jPanel21.setBackground(new java.awt.Color(151, 79, 255));
            jPanel22.setBackground(new java.awt.Color(151, 79, 255));
            Font fontPlain22 = new Font("SansSerif", Font.PLAIN, 22);
            Font fontBold22 = new Font("SansSerif", Font.BOLD, 22);
            jLabel3.setFont(fontBold22);
            jLabel10.setFont(fontPlain22);
            jLabel12.setFont(fontPlain22);
            jLabel14.setFont(fontPlain22);
            jLabel8.setFont(fontPlain22);
            jLabel5.setFont(fontPlain22);
            jLabel18.setFont(fontPlain22);
            jLabel20.setFont(fontPlain22);
            String[] imagePaths = {"menuFrame - Dashboard_B.png", "menuFrame - ViewInventory_W.png", "menuFrame - InventoryIn_W.png", "menuFrame - InventoryOut_W.png", "menuFrame - CreateProduct_W.png", "menuFrame - ManageAccounts_W.png", "menuFrame - ViewLogs_W.png", "menuFrame - SignOut_W.png"};
            JLabel[] jLabels = {jLabel4, jLabel11, jLabel13, jLabel15, jLabel9, jLabel7, jLabel19, jLabel21};
            ImageIcon[] icons = new ImageIcon[8];
            for (int i = 0; i < icons.length; i++) {
                icons[i] = new ImageIcon(imagePaths[i]);
                jLabels[i].setIcon(icons[i]);
            }
            updateData_For_Dashboard();
            updateData_For_TopSelling_Products();
            jTabbedPane1.setSelectedIndex(0);
        }
    }//GEN-LAST:event_dashboard

    private void viewInventory(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewInventory
        if (jPanel17.isEnabled()) {
            jPanel14.setBackground(new java.awt.Color(151, 79, 255));
            jPanel17.setBackground(new java.awt.Color(255, 222, 89));
            jPanel18.setBackground(new java.awt.Color(151, 79, 255));
            jPanel19.setBackground(new java.awt.Color(151, 79, 255));
            jPanel16.setBackground(new java.awt.Color(151, 79, 255));
            jPanel15.setBackground(new java.awt.Color(151, 79, 255));
            jPanel21.setBackground(new java.awt.Color(151, 79, 255));
            jPanel22.setBackground(new java.awt.Color(151, 79, 255));
            Font fontPlain22 = new Font("SansSerif", Font.PLAIN, 22);
            Font fontBold22 = new Font("SansSerif", Font.BOLD, 22);
            jLabel3.setFont(fontPlain22);
            jLabel10.setFont(fontBold22);
            jLabel12.setFont(fontPlain22);
            jLabel14.setFont(fontPlain22);
            jLabel8.setFont(fontPlain22);
            jLabel5.setFont(fontPlain22);
            jLabel18.setFont(fontPlain22);
            jLabel20.setFont(fontPlain22);
            String[] imagePaths = {"menuFrame - Dashboard_W.png", "menuFrame - ViewInventory_B.png", "menuFrame - InventoryIn_W.png", "menuFrame - InventoryOut_W.png", "menuFrame - CreateProduct_W.png", "menuFrame - ManageAccounts_W.png", "menuFrame - ViewLogs_W.png", "menuFrame - SignOut_W.png"};
            JLabel[] jLabels = {jLabel4, jLabel11, jLabel13, jLabel15, jLabel9, jLabel7, jLabel19, jLabel21};
            ImageIcon[] icons = new ImageIcon[8];
            for (int i = 0; i < icons.length; i++) {
                icons[i] = new ImageIcon(imagePaths[i]);
                jLabels[i].setIcon(icons[i]);
            }

            jTabbedPane1.setSelectedIndex(1);
            clearFields_In_ViewInventory();
            jLabel30.requestFocus();
            jTextField11.setText("Search Item");
            jTextField11.setForeground(new Color(0x8D8D8D));
            jTextField11.setCaretPosition(0);
            jTextField24.setEditable(false);
            jTextField25.setEditable(false);
            jTextField26.setEditable(false);
            jTextField27.setEditable(false);
            jTextField28.setEditable(false);
            jTextArea4.setEditable(false);
        }
    }//GEN-LAST:event_viewInventory

    private void inventoryIn(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryIn
        jPanel14.setBackground(new java.awt.Color(151, 79, 255));
        jPanel17.setBackground(new java.awt.Color(151, 79, 255));
        jPanel18.setBackground(new java.awt.Color(255, 222, 89));
        jPanel19.setBackground(new java.awt.Color(151, 79, 255));
        jPanel16.setBackground(new java.awt.Color(151, 79, 255));
        jPanel15.setBackground(new java.awt.Color(151, 79, 255));
        jPanel21.setBackground(new java.awt.Color(151, 79, 255));
        jPanel22.setBackground(new java.awt.Color(151, 79, 255));
        Font fontPlain22 = new Font("SansSerif", Font.PLAIN, 22);
        Font fontBold22 = new Font("SansSerif", Font.BOLD, 22);
        jLabel3.setFont(fontPlain22);
        jLabel10.setFont(fontPlain22);
        jLabel12.setFont(fontBold22);
        jLabel14.setFont(fontPlain22);
        jLabel8.setFont(fontPlain22);
        jLabel5.setFont(fontPlain22);
        jLabel18.setFont(fontPlain22);
        jLabel20.setFont(fontPlain22);
        String[] imagePaths = {"menuFrame - Dashboard_W.png", "menuFrame - ViewInventory_W.png", "menuFrame - InventoryIn_B.png", "menuFrame - InventoryOut_W.png", "menuFrame - CreateProduct_W.png", "menuFrame - ManageAccounts_W.png", "menuFrame - ViewLogs_W.png", "menuFrame - SignOut_W.png"};
        JLabel[] jLabels = {jLabel4, jLabel11, jLabel13, jLabel15, jLabel9, jLabel7, jLabel19, jLabel21};
        ImageIcon[] icons = new ImageIcon[8];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageIcon(imagePaths[i]);
            jLabels[i].setIcon(icons[i]);
        }

        jTabbedPane1.setSelectedIndex(2);
        clearFields_In_InventoryIN();
        clearProductInfo_In_InventoryIN();
        showExample_In_InventoryIN();
        jLabel88.setEnabled(true);
        jLabel89.setEnabled(false);
        jLabel90.setEnabled(false);
    }//GEN-LAST:event_inventoryIn

    private void inventoryOut(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryOut
        jPanel14.setBackground(new java.awt.Color(151, 79, 255));
        jPanel17.setBackground(new java.awt.Color(151, 79, 255));
        jPanel18.setBackground(new java.awt.Color(151, 79, 255));
        jPanel19.setBackground(new java.awt.Color(255, 222, 89));
        jPanel16.setBackground(new java.awt.Color(151, 79, 255));
        jPanel15.setBackground(new java.awt.Color(151, 79, 255));
        jPanel21.setBackground(new java.awt.Color(151, 79, 255));
        jPanel22.setBackground(new java.awt.Color(151, 79, 255));
        Font fontPlain22 = new Font("SansSerif", Font.PLAIN, 22);
        Font fontBold22 = new Font("SansSerif", Font.BOLD, 22);
        jLabel3.setFont(fontPlain22);
        jLabel10.setFont(fontPlain22);
        jLabel12.setFont(fontPlain22);
        jLabel14.setFont(fontBold22);
        jLabel8.setFont(fontPlain22);
        jLabel5.setFont(fontPlain22);
        jLabel18.setFont(fontPlain22);
        jLabel20.setFont(fontPlain22);
        String[] imagePaths = {"menuFrame - Dashboard_W.png", "menuFrame - ViewInventory_W.png", "menuFrame - InventoryIn_W.png", "menuFrame - InventoryOut_B.png", "menuFrame - CreateProduct_W.png", "menuFrame - ManageAccounts_W.png", "menuFrame - ViewLogs_W.png", "menuFrame - SignOut_W.png"};
        JLabel[] jLabels = {jLabel4, jLabel11, jLabel13, jLabel15, jLabel9, jLabel7, jLabel19, jLabel21};
        ImageIcon[] icons = new ImageIcon[8];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageIcon(imagePaths[i]);
            jLabels[i].setIcon(icons[i]);
        }

        jTabbedPane1.setSelectedIndex(3);
        clearFields_In_InventoryOUT();
        clearProductInfo_In_InventoryOUT();
        showExample_In_InventoryOut();
        jLabel91.setEnabled(true);
        jLabel92.setEnabled(false);
        jLabel93.setEnabled(false);
    }//GEN-LAST:event_inventoryOut

    private void createProduct(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createProduct
        jPanel14.setBackground(new java.awt.Color(151, 79, 255));
        jPanel17.setBackground(new java.awt.Color(151, 79, 255));
        jPanel18.setBackground(new java.awt.Color(151, 79, 255));
        jPanel19.setBackground(new java.awt.Color(151, 79, 255));
        jPanel16.setBackground(new java.awt.Color(255, 222, 89));
        jPanel15.setBackground(new java.awt.Color(151, 79, 255));
        jPanel21.setBackground(new java.awt.Color(151, 79, 255));
        jPanel22.setBackground(new java.awt.Color(151, 79, 255));
        Font fontPlain22 = new Font("SansSerif", Font.PLAIN, 22);
        Font fontBold22 = new Font("SansSerif", Font.BOLD, 22);
        jLabel3.setFont(fontPlain22);
        jLabel10.setFont(fontPlain22);
        jLabel12.setFont(fontPlain22);
        jLabel14.setFont(fontPlain22);
        jLabel8.setFont(fontBold22);
        jLabel5.setFont(fontPlain22);
        jLabel18.setFont(fontPlain22);
        jLabel20.setFont(fontPlain22);
        String[] imagePaths = {"menuFrame - Dashboard_W.png", "menuFrame - ViewInventory_W.png", "menuFrame - InventoryIn_W.png", "menuFrame - InventoryOut_W.png", "menuFrame - CreateProduct_B.png", "menuFrame - ManageAccounts_W.png", "menuFrame - ViewLogs_W.png", "menuFrame - SignOut_W.png"};
        JLabel[] jLabels = {jLabel4, jLabel11, jLabel13, jLabel15, jLabel9, jLabel7, jLabel19, jLabel21};
        ImageIcon[] icons = new ImageIcon[8];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageIcon(imagePaths[i]);
            jLabels[i].setIcon(icons[i]);
        }

        jTabbedPane1.setSelectedIndex(4);
        showExample_In_CreateProducts();
        jLabel95.setEnabled(true);
        jLabel94.setEnabled(false);
        jLabel96.setEnabled(false);
    }//GEN-LAST:event_createProduct

    private void manageAccounts(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageAccounts
        if (jPanel15.isEnabled()) {
            jPanel14.setBackground(new java.awt.Color(151, 79, 255));
            jPanel17.setBackground(new java.awt.Color(151, 79, 255));
            jPanel18.setBackground(new java.awt.Color(151, 79, 255));
            jPanel19.setBackground(new java.awt.Color(151, 79, 255));
            jPanel16.setBackground(new java.awt.Color(151, 79, 255));
            jPanel15.setBackground(new java.awt.Color(255, 222, 89));
            jPanel21.setBackground(new java.awt.Color(151, 79, 255));
            jPanel22.setBackground(new java.awt.Color(151, 79, 255));
            Font fontPlain22 = new Font("SansSerif", Font.PLAIN, 22);
            Font fontBold22 = new Font("SansSerif", Font.BOLD, 22);
            jLabel3.setFont(fontPlain22);
            jLabel10.setFont(fontPlain22);
            jLabel12.setFont(fontPlain22);
            jLabel14.setFont(fontPlain22);
            jLabel8.setFont(fontPlain22);
            jLabel5.setFont(fontBold22);
            jLabel18.setFont(fontPlain22);
            jLabel20.setFont(fontPlain22);
            String[] imagePaths = {"menuFrame - Dashboard_W.png", "menuFrame - ViewInventory_W.png", "menuFrame - InventoryIn_W.png", "menuFrame - InventoryOut_W.png", "menuFrame - CreateProduct_W.png", "menuFrame - ManageAccounts_B.png", "menuFrame - ViewLogs_W.png", "menuFrame - SignOut_W.png"};
            JLabel[] jLabels = {jLabel4, jLabel11, jLabel13, jLabel15, jLabel9, jLabel7, jLabel19, jLabel21};
            ImageIcon[] icons = new ImageIcon[8];
            for (int i = 0; i < icons.length; i++) {
                icons[i] = new ImageIcon(imagePaths[i]);
                jLabels[i].setIcon(icons[i]);
            }

            jTabbedPane1.setSelectedIndex(5);
        }
    }//GEN-LAST:event_manageAccounts

    private void viewLogs(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewLogs
        if (jPanel21.isEnabled()) {
            jPanel14.setBackground(new java.awt.Color(151, 79, 255));
            jPanel17.setBackground(new java.awt.Color(151, 79, 255));
            jPanel18.setBackground(new java.awt.Color(151, 79, 255));
            jPanel19.setBackground(new java.awt.Color(151, 79, 255));
            jPanel16.setBackground(new java.awt.Color(151, 79, 255));
            jPanel15.setBackground(new java.awt.Color(151, 79, 255));
            jPanel21.setBackground(new java.awt.Color(255, 222, 89));
            jPanel22.setBackground(new java.awt.Color(151, 79, 255));
            Font fontPlain22 = new Font("SansSerif", Font.PLAIN, 22);
            Font fontBold22 = new Font("SansSerif", Font.BOLD, 22);
            jLabel3.setFont(fontPlain22);
            jLabel10.setFont(fontPlain22);
            jLabel12.setFont(fontPlain22);
            jLabel14.setFont(fontPlain22);
            jLabel8.setFont(fontPlain22);
            jLabel5.setFont(fontPlain22);
            jLabel18.setFont(fontBold22);
            jLabel20.setFont(fontPlain22);
            String[] imagePaths = {"menuFrame - Dashboard_W.png", "menuFrame - ViewInventory_W.png", "menuFrame - InventoryIn_W.png", "menuFrame - InventoryOut_W.png", "menuFrame - CreateProduct_W.png", "menuFrame - ManageAccounts_W.png", "menuFrame - ViewLogs_B.png", "menuFrame - SignOut_W.png"};
            JLabel[] jLabels = {jLabel4, jLabel11, jLabel13, jLabel15, jLabel9, jLabel7, jLabel19, jLabel21};
            ImageIcon[] icons = new ImageIcon[8];
            for (int i = 0; i < icons.length; i++) {
                icons[i] = new ImageIcon(imagePaths[i]);
                jLabels[i].setIcon(icons[i]);
            }

            jTabbedPane1.setSelectedIndex(7);
            jLabel36.requestFocus();
            jTextField38.setText("Search Log");
            jTextField38.setForeground(new Color(0x8D8D8D));
            jTextField38.setCaretPosition(0);
        }
    }//GEN-LAST:event_viewLogs

    private void signOut(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signOut
        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to Sign Out?");
        if (reply == JOptionPane.YES_OPTION) {
            Toyland_InventorySystem.addLogsAccDte(jLabel77.getText());
            Toyland_InventorySystem.addLogsAccNme(jLabel79.getText());
            Toyland_InventorySystem.addLogsAccID(jLabel80.getText());
            Toyland_InventorySystem.addLogsAccTpe(Toyland_InventorySystem.getUserAccType());
            Toyland_InventorySystem.addLogsAccAct("SIGN OUT");
            getCurrentTime();

            loginFrame login = new loginFrame();
            login.setVisible(true);
            menuFrame.this.setVisible(false);
        }
    }//GEN-LAST:event_signOut

    private void jPanel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseEntered
        if (!jPanel14.getBackground().equals(new Color(255, 222, 89)) && jPanel14.isEnabled()) {
            jPanel14.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel14MouseEntered

    private void jPanel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseExited
        if (!jPanel14.getBackground().equals(new Color(255, 222, 89)) && jPanel14.isEnabled()) {
            jPanel14.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel14MouseExited

    private void jPanel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseEntered
        if (!jPanel17.getBackground().equals(new Color(255, 222, 89)) && jPanel17.isEnabled()) {
            jPanel17.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel17MouseEntered

    private void jPanel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseExited
        if (!jPanel17.getBackground().equals(new Color(255, 222, 89)) && jPanel17.isEnabled()) {
            jPanel17.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel17MouseExited

    private void jPanel18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseEntered
        if (!jPanel18.getBackground().equals(new Color(255, 222, 89))) {
            jPanel18.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel18MouseEntered

    private void jPanel18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseExited
        if (!jPanel18.getBackground().equals(new Color(255, 222, 89))) {
            jPanel18.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel18MouseExited

    private void jPanel19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseEntered
        if (!jPanel19.getBackground().equals(new Color(255, 222, 89))) {
            jPanel19.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel19MouseEntered

    private void jPanel19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseExited
        if (!jPanel19.getBackground().equals(new Color(255, 222, 89))) {
            jPanel19.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel19MouseExited

    private void jPanel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseEntered
        if (!jPanel16.getBackground().equals(new Color(255, 222, 89))) {
            jPanel16.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel16MouseEntered

    private void jPanel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseExited
        if (!jPanel16.getBackground().equals(new Color(255, 222, 89))) {
            jPanel16.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel16MouseExited

    private void jPanel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseEntered
        if (!jPanel15.getBackground().equals(new Color(255, 222, 89)) && jPanel15.isEnabled()) {
            jPanel15.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel15MouseEntered

    private void jPanel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseExited
        if (!jPanel15.getBackground().equals(new Color(255, 222, 89)) && jPanel15.isEnabled()) {
            jPanel15.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel15MouseExited

    private void jPanel21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseEntered
        if (!jPanel21.getBackground().equals(new Color(255, 222, 89)) && jPanel21.isEnabled()) {
            jPanel21.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel21MouseEntered

    private void jPanel21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseExited
        if (!jPanel21.getBackground().equals(new Color(255, 222, 89)) && jPanel21.isEnabled()) {
            jPanel21.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel21MouseExited

    private void jPanel22MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseEntered
        if (!jPanel22.getBackground().equals(new Color(255, 222, 89))) {
            jPanel22.setBackground(new Color(115, 17, 255));
        }
    }//GEN-LAST:event_jPanel22MouseEntered

    private void jPanel22MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseExited
        if (!jPanel22.getBackground().equals(new Color(255, 222, 89))) {
            jPanel22.setBackground(new Color(151, 79, 255));
        }
    }//GEN-LAST:event_jPanel22MouseExited

    private void goToAdmin(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goToAdmin
        Toyland_InventorySystem.setGoTo_AccountCenter_of("admin");
        accountCenter acc = new accountCenter();
        acc.setVisible(true);
    }//GEN-LAST:event_goToAdmin

    private void goToEmp(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goToEmp
        Toyland_InventorySystem.setGoTo_AccountCenter_of("employee");
        accountCenter acc = new accountCenter();
        acc.setVisible(true);
    }//GEN-LAST:event_goToEmp

    private void inventoryIn_Barcode_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryIn_Barcode_Validation
        // Validation: Numbers only and Maximum of 13 digits 
        String pn = jTextField2.getText();
        int l = pn.length();
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            if (l < 13) {
                jTextField2.setEditable(true);
            } else {
                jTextField2.setEditable(false);
            }
        } else {
            if (Character.isISOControl(c)) {
                jTextField2.setEditable(true);
            } else {
                jTextField2.setEditable(false);
            }
        }
    }//GEN-LAST:event_inventoryIn_Barcode_Validation

    private void inventoryIn_Quantity_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryIn_Quantity_Validation
        // Validation: Numbers only 
        String pn = jTextField13.getText();
        int l = pn.length();
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            if (l < 6) {
                jTextField13.setEditable(true);
            } else {
                jTextField13.setEditable(false);
            }
        } else {
            if (Character.isISOControl(c)) {
                jTextField13.setEditable(true);
            } else {
                jTextField13.setEditable(false);
            }
        }
    }//GEN-LAST:event_inventoryIn_Quantity_Validation

    private void inventoryIn_Supplier_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryIn_Supplier_Validation
        // Validation: Only Letters and Max Characters: 80
        String word = jTextField7.getText();
        int l1 = word.length();
        char c1 = evt.getKeyChar();
        if (Character.isLetter(c1)) {
            if (l1 < 80) {
                jTextField7.setEditable(true);
            } else {
                jTextField7.setEditable(false);
            }
        } else {
            if (Character.isWhitespace(c1) || Character.isISOControl(c1) || evt.getExtendedKeyCode() == KeyEvent.VK_SHIFT) {
                jTextField7.setEditable(true);
            } else {
                jTextField7.setEditable(false);
            }
        }
    }//GEN-LAST:event_inventoryIn_Supplier_Validation

    private void inventoryIn_IncrementQuantity(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryIn_IncrementQuantity
        String numString = jTextField13.getText();
        if (numString.isEmpty() || numString.equals("0")) {
            jTextField13.setText("1");
        } else {
            Integer number = Integer.valueOf(numString);
            number += 1;
            jTextField13.setText(number.toString());
        }
    }//GEN-LAST:event_inventoryIn_IncrementQuantity

    private void inventoryIn_DecrementQuantity(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryIn_DecrementQuantity
        String numString = jTextField13.getText();
        if (numString.isEmpty() || numString.equals("1") || numString.equals("0")) {
            jTextField13.setText("1");
        } else {
            Integer number = Integer.valueOf(numString);
            number -= 1;
            jTextField13.setText(number.toString());
        }
    }//GEN-LAST:event_inventoryIn_DecrementQuantity

    private void inventoryIn_ADD(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryIn_ADD
        if (!jLabel89.isEnabled() && !jLabel90.isEnabled()) {
            jTextField2.setEditable(true);
            jTextField2.setEnabled(true);
            jTextField12.setEnabled(true);
            jTextField13.setEditable(true);
            jTextField13.setEnabled(true);
            jTextField7.setEditable(true);
            jTextField7.setEnabled(true);
            jTextArea1.setEditable(true);
            jTextArea1.setEnabled(true);
            jLabel60.setEnabled(true);
            jLabel61.setEnabled(true);
            jLabel88.setEnabled(false);
            jLabel89.setEnabled(true);
            jLabel90.setEnabled(true);
            clearFields_In_InventoryIN();
            clearProductInfo_In_InventoryIN();
            jTextField12.setText(jLabel77.getText());
        }
    }//GEN-LAST:event_inventoryIn_ADD

    private void inventoryIn_SAVE(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryIn_SAVE
        if (jLabel89.isEnabled()) {
            if (jTextField2.getText().isEmpty() || jTextField12.getText().isEmpty() || jTextField13.getText().isEmpty() || jTextField7.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all the required fields.", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
            } else {
                String barCode = jTextField2.getText();
                String itemName = jTextField30.getText();
                String date = jTextField12.getText();
                String qty = jTextField13.getText();
                String supplier = jTextField7.getText();

                boolean isSameProduct = false;
                String status;
                int q = Integer.parseInt(jTextField13.getText()); // quantity 
                DefaultTableModel view_Inventory = (DefaultTableModel) jTable3.getModel();
                int rowCount = view_Inventory.getRowCount();

                //TO FIND THE ITEM, THEN UPDATE THE AVAILABLE STOCKS AND STATUS 
                for (int row = 0; row < rowCount; row++) {
                    Object cellValue = view_Inventory.getValueAt(row, 0);
                    if (cellValue != null && cellValue.toString().equals(barCode)) {
                        isSameProduct = true;
                        if (q == 0) {
                            JOptionPane.showMessageDialog(null, "You have entered an invalid quantity value.", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                            break;
                        } else {
                            Object qtyVal = view_Inventory.getValueAt(row, 6);
                            int originalValue = Integer.parseInt(qtyVal.toString());
                            int newValue = originalValue + q;
                            if (newValue <= 0) {
                                status = "Out of Stock";
                                view_Inventory.setValueAt(status, row, 5);
                            } else if (newValue <= 20) {
                                status = "Low Stock";
                                view_Inventory.setValueAt(status, row, 5);
                            } else {
                                status = "In Stock";
                                view_Inventory.setValueAt(status, row, 5);
                            }
                            view_Inventory.setValueAt(newValue, row, 6);
                            Toyland_InventorySystem.status.set(row, status);
                            Toyland_InventorySystem.stocks.set(row, String.valueOf(newValue));
                            Toyland_InventorySystem.addInTransaction_barCode(barCode);
                            Toyland_InventorySystem.addInTransaction_itemName(itemName);
                            Toyland_InventorySystem.addInTransaction_qty(qty);
                            Toyland_InventorySystem.addInTransaction_date(date);
                            Toyland_InventorySystem.addInTransaction_supplier(supplier);
                            Toyland_InventorySystem.addInTransaction_accountDetails(Toyland_InventorySystem.getUserID());
                            JOptionPane.showMessageDialog(null, "STOCK SAVED SUCCESSFULLY!");
                            break;
                        }
                    }
                }

                if (isSameProduct == false) {
                    JOptionPane.showMessageDialog(null, "ITEM DOES NOT EXIST", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                }
                clearFields_In_InventoryIN();
                clearProductInfo_In_InventoryIN();
            }
        }
    }//GEN-LAST:event_inventoryIn_SAVE

    private void inventoryIn_CANCEL(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryIn_CANCEL
        if (jLabel90.isEnabled()) {
            clearFields_In_InventoryIN();
            clearProductInfo_In_InventoryIN();
        }
    }//GEN-LAST:event_inventoryIn_CANCEL

    private void inventoryOut_ADD(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryOut_ADD
        if (!jLabel92.isEnabled() && !jLabel93.isEnabled()) {
            jTextField14.setEditable(true);
            jTextField14.setEnabled(true);
            jTextField16.setEditable(true);
            jTextField16.setEnabled(true);
            jTextField18.setEnabled(true);
            jTextField19.setEditable(true);
            jTextField19.setEnabled(true);
            jTextArea2.setEditable(true);
            jTextArea2.setEnabled(true);
            jTextField20.setEditable(true);
            jTextField20.setEnabled(true);
            jLabel51.setEnabled(true);
            jLabel52.setEnabled(true);
            jLabel91.setEnabled(false);
            jLabel92.setEnabled(true);
            jLabel93.setEnabled(true);
            clearFields_In_InventoryOUT();
            clearProductInfo_In_InventoryOUT();
            jTextField18.setText(jLabel77.getText());
        }
    }//GEN-LAST:event_inventoryOut_ADD

    private void inventoryOut_SAVE(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryOut_SAVE
        if (jLabel92.isEnabled()) {
            if (jTextField14.getText().isEmpty() || jTextField16.getText().trim().isEmpty() || jTextField18.getText().isEmpty() || jTextField19.getText().isEmpty() || jTextField20.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all the required fields.", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
            } else {
                String itemName = jTextField36.getText();
                String barCode = jTextField14.getText();
                String customerName = jTextField16.getText();
                String date = jTextField18.getText();
                String qty = jTextField19.getText();
                String destination = jTextField20.getText();
                String purpose = jTextArea2.getText();

                boolean isSameProduct = false;
                String status;
                int quantity = Integer.parseInt(jTextField19.getText());
                DefaultTableModel overall_Inventory = (DefaultTableModel) jTable3.getModel();
                int rowCount = overall_Inventory.getRowCount();

                // TO FIND THE PRODUCT AND SUBTRACT THE QTY TO THE EXISTING STOCK 
                for (int row = 0; row < rowCount; row++) {
                    Object cellValue = overall_Inventory.getValueAt(row, 0);
                    if (cellValue != null && cellValue.toString().equals(barCode)) {
                        isSameProduct = true;
                        Object qtyVal = overall_Inventory.getValueAt(row, 6);
                        int originalValue = Integer.parseInt(qtyVal.toString());

                        if (originalValue >= quantity && quantity != 0) {
                            int newValue = originalValue - quantity;
                            if (newValue <= 0) {
                                status = "Out of Stock";
                                overall_Inventory.setValueAt(status, row, 5);
                            } else if (newValue <= 20) {
                                status = "Low Stock";
                                overall_Inventory.setValueAt(status, row, 5);
                            } else {
                                status = "In Stock";
                                overall_Inventory.setValueAt(status, row, 5);
                            }

                            overall_Inventory.setValueAt(newValue, row, 6);
                            Toyland_InventorySystem.status.set(row, status);
                            Toyland_InventorySystem.stocks.set(row, String.valueOf(newValue));
                            Toyland_InventorySystem.outTransaction_barCode.add(barCode);
                            Toyland_InventorySystem.outTransaction_customerName.add(customerName);
                            Toyland_InventorySystem.outTransaction_date.add(date);
                            Toyland_InventorySystem.outTransaction_qty.add(qty);
                            Toyland_InventorySystem.outTransaction_destination.add(destination);
                            Toyland_InventorySystem.outTransaction_purpose.add(purpose);
                            Toyland_InventorySystem.outTransaction_accountDetails.add(Toyland_InventorySystem.getUserID());

                            // TO UPDATE TOP SELLING PRODUCTS ON THE DASHBOARD
                            if (Toyland_InventorySystem.fastItem.contains(itemName)) {
                                int index = Toyland_InventorySystem.fastItem.indexOf(itemName);
                                int originalSales = Integer.parseInt(Toyland_InventorySystem.fastSales.get(index));
                                int newSales = originalSales + quantity;
                                Toyland_InventorySystem.fastSales.set(index, String.valueOf(newSales));
                            } else {
                                Toyland_InventorySystem.fastItem.add(itemName);
                                Toyland_InventorySystem.fastSales.add(qty);
                            }

                            // TO UPDATE BEST SELLER ITEM AND BEST SELLER ITEMS SOLD
                            if (Toyland_InventorySystem.getBestSeller().equals(itemName)) {
                                Toyland_InventorySystem.setHighestSales(Toyland_InventorySystem.getHighestSales() + quantity);
                            } else {
                                if (quantity >= Toyland_InventorySystem.getHighestSales()) {
                                    Toyland_InventorySystem.setBestSeller(itemName);
                                    Toyland_InventorySystem.setHighestSales(quantity);
                                }
                            }

                            JOptionPane.showMessageDialog(null, "STOCK SAVED SUCCESSFULLY!");
                            updateData_For_Dashboard();
                            break;
                        } else if (originalValue < quantity) {
                            JOptionPane.showMessageDialog(null, "Insufficient available stocks", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "You have entered an invalid quantity value.", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                    }
                }

                if (isSameProduct == false) {
                    JOptionPane.showMessageDialog(null, "ITEM DOES NOT EXIST", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                }
                clearFields_In_InventoryOUT();
                clearProductInfo_In_InventoryOUT();
            }
        }
    }//GEN-LAST:event_inventoryOut_SAVE

    private void inventoryOut_CANCEL(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryOut_CANCEL
        if (jLabel92.isEnabled()) {
            clearFields_In_InventoryOUT();
            clearProductInfo_In_InventoryOUT();
        }
    }//GEN-LAST:event_inventoryOut_CANCEL

    private void viewInventory_SearchBar(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_viewInventory_SearchBar
        if (jTextField11.getText().trim().equals("Search Item") || jTextField11.getText().trim().equals("")) {
            jTextField11.setText("Search Item");
            jTextField11.setCaretPosition(0);
            jTextField11.setForeground(new Color(0x8D8D8D));
        } else {
            DefaultTableModel obj = (DefaultTableModel) jTable3.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            jTable3.setRowSorter(obj1);
            obj1.setRowFilter(RowFilter.regexFilter(jTextField11.getText()));
        }
    }//GEN-LAST:event_viewInventory_SearchBar

    private void goTo_inventoryIn_Transactions(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goTo_inventoryIn_Transactions
        inventoryIN_TransactionsFrame in = new inventoryIN_TransactionsFrame();
        in.setVisible(true);
    }//GEN-LAST:event_goTo_inventoryIn_Transactions

    private void goTo_inventoryOut_Transactions(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goTo_inventoryOut_Transactions
        inventoryOUT_TransactionsFrame out = new inventoryOUT_TransactionsFrame();
        out.setVisible(true);
    }//GEN-LAST:event_goTo_inventoryOut_Transactions

    private void createProduct_ADD(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createProduct_ADD
        if (!jLabel94.isEnabled() && !jLabel96.isEnabled()) {
            jTextField17.setEditable(true);
            jTextField17.setEnabled(true);
            jTextField21.setEditable(true);
            jTextField21.setEnabled(true);
            jComboBox3.setEnabled(true);
            jTextField22.setEditable(true);
            jTextField22.setEnabled(true);
            jTextField23.setEditable(true);
            jTextField23.setEnabled(true);
            jTextArea3.setEditable(true);
            jTextArea3.setEnabled(true);
            jLabel95.setEnabled(false);
            jLabel94.setEnabled(true);
            jLabel96.setEnabled(true);
            clearFields_In_CreateProduct();
        }
    }//GEN-LAST:event_createProduct_ADD

    private void createProduct_SAVE(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createProduct_SAVE
        if (jLabel94.isEnabled()) {
            if (jTextField17.getText().trim().isEmpty() || jTextField21.getText().trim().isEmpty() || jComboBox3.getSelectedItem() == null || jTextField22.getText().trim().isEmpty() || jTextField23.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all the required fields.", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
            } else {
                String barCode = jTextField17.getText();
                String itemName = jTextField21.getText();
                String itemType = jComboBox3.getSelectedItem().toString();
                String unitP = jTextField22.getText();
                String retailP = jTextField23.getText();
                String descrip = jTextArea3.getText();
                String status = "Out of Stock";
                String qty = "0";

                int unitPrice = Integer.parseInt(jTextField22.getText());
                int retailPrice = Integer.parseInt(jTextField23.getText());
                if (retailPrice < unitPrice) {
                    JOptionPane.showMessageDialog(null, "Retail Price should be greater than the Unit Price", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                    clearFields_In_CreateProduct();
                } else if (retailPrice == 0 || unitPrice == 0) {
                    JOptionPane.showMessageDialog(null, "You have entered an invalid value for Retail Price / Unit Price", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                    clearFields_In_CreateProduct();
                } else {
                    Object[] row = {barCode, itemName, itemType, unitP, retailP, status, qty};
                    DefaultTableModel view_Inventory = (DefaultTableModel) jTable3.getModel();
                    boolean isSameProduct = false;
                    int rowCount = view_Inventory.getRowCount();

                    for (int rowNum = 0; rowNum < rowCount; rowNum++) {
                        Object cellValue = view_Inventory.getValueAt(rowNum, 0);
                        if (cellValue != null && cellValue.toString().equals(barCode)) {
                            isSameProduct = true;
                            JOptionPane.showMessageDialog(null, "BARCODE IS ALREADY REGISTRED!", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                    }

                    if (isSameProduct == false) {
                        view_Inventory.addRow(row);
                        Toyland_InventorySystem.barCode.add(barCode);
                        Toyland_InventorySystem.itemName.add(itemName);
                        Toyland_InventorySystem.itemType.add(itemType);
                        Toyland_InventorySystem.unitPrice.add(unitP);
                        Toyland_InventorySystem.retailPrice.add(retailP);
                        Toyland_InventorySystem.description.add(descrip);
                        Toyland_InventorySystem.status.add(status);
                        Toyland_InventorySystem.stocks.add(qty);
                        JOptionPane.showMessageDialog(null, "NEW ITEM ADDED!");
                        updateData_For_Dashboard();
                    }
                    clearFields_In_CreateProduct();
                }
            }
        }
    }//GEN-LAST:event_createProduct_SAVE

    private void createProduct_CANCEL(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createProduct_CANCEL
        if (jLabel96.isEnabled()) {
            clearFields_In_CreateProduct();
        }
    }//GEN-LAST:event_createProduct_CANCEL

    private void createProduct_Barcode_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_createProduct_Barcode_Validation
        // Validation: Numbers only and Maximum of 13 digits 
        String pn = jTextField17.getText();
        int l = pn.length();
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            if (l < 13) {
                jTextField17.setEditable(true);
            } else {
                jTextField17.setEditable(false);
            }
        } else {
            if (Character.isISOControl(c)) {
                jTextField17.setEditable(true);
            } else {
                jTextField17.setEditable(false);
            }
        }
    }//GEN-LAST:event_createProduct_Barcode_Validation

    private void createProduct_UnitPrice_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_createProduct_UnitPrice_Validation
        // Validation: Numbers and a period
        String pn = jTextField22.getText();
        int l = pn.length();
        char c = evt.getKeyChar();

        if (Character.isDigit(c) || c == '.') {
            if (c == '.' && pn.contains(".")) {
                // Allowing only one period
                jTextField22.setEditable(false);
            } else if (l < 10) {
                jTextField22.setEditable(true);
            } else {
                jTextField22.setEditable(false);
            }
        } else {
            if (Character.isISOControl(c)) {
                jTextField22.setEditable(true);
            } else {
                jTextField22.setEditable(false);
            }
        }
    }//GEN-LAST:event_createProduct_UnitPrice_Validation

    private void createProduct_RetailPrice_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_createProduct_RetailPrice_Validation
        // Validation: Numbers and a period
        String pn = jTextField23.getText();
        int l = pn.length();
        char c = evt.getKeyChar();

        if (Character.isDigit(c) || c == '.') {
            if (c == '.' && pn.contains(".")) {
                // Allowing only one period
                jTextField23.setEditable(false);
            } else if (l < 10) {
                jTextField23.setEditable(true);
            } else {
                jTextField23.setEditable(false);
            }
        } else {
            if (Character.isISOControl(c)) {
                jTextField23.setEditable(true);
            } else {
                jTextField23.setEditable(false);
            }
        }
    }//GEN-LAST:event_createProduct_RetailPrice_Validation

    private void inventoryIn_ProductInfo(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryIn_ProductInfo
        char keyChar = evt.getKeyChar();
        jLabel121.setVisible(true);
        String barCode = jTextField2.getText();
        jTextArea1.setEditable(false);
        if (barCode.isEmpty()) {
            clearProductInfo_In_InventoryIN();
        }
        boolean isSameProduct = false;
        DefaultTableModel view_Inventory = (DefaultTableModel) jTable3.getModel();
        int rowCount = view_Inventory.getRowCount();

        if (Character.isDigit(keyChar) || keyChar == '\b') {
            for (int row = 0; row < rowCount; row++) {
                Object cellValue = view_Inventory.getValueAt(row, 0);
                if (cellValue != null && cellValue.toString().equals(barCode)) {
                    String barC = String.valueOf(view_Inventory.getValueAt(row, 0));
                    String itemN = String.valueOf(view_Inventory.getValueAt(row, 1));
                    String itemT = String.valueOf(view_Inventory.getValueAt(row, 2));
                    String unitP = String.valueOf(view_Inventory.getValueAt(row, 3));
                    String retailP = String.valueOf(view_Inventory.getValueAt(row, 4));
                    String stocks = String.valueOf(view_Inventory.getValueAt(row, 6));
                    String descrip = Toyland_InventorySystem.description.get(row);
                    jTextField15.setText(barC);
                    jTextField30.setText(itemN);
                    jTextField31.setText(itemT);
                    jTextField32.setText(unitP);
                    jTextField29.setText(retailP);
                    jLabel49.setText(stocks);
                    jTextArea1.setText(descrip);
                    jLabel121.setIcon(new ImageIcon("Product_Found.png"));
                    isSameProduct = true;
                    break;
                }
            }

            if (isSameProduct == false) {
                clearProductInfo_In_InventoryIN();
                jLabel121.setIcon(new ImageIcon("Product_NotFound.png"));
            }
        }
    }//GEN-LAST:event_inventoryIn_ProductInfo

    private void viewInventory_ProductInfo(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewInventory_ProductInfo
        int row = jTable3.getSelectedRow();
        if (row != -1) {
            DefaultTableModel view_Inventory = (DefaultTableModel) jTable3.getModel();
            String barC = String.valueOf(view_Inventory.getValueAt(row, 0));
            String itemN = String.valueOf(view_Inventory.getValueAt(row, 1));
            String itemT = String.valueOf(view_Inventory.getValueAt(row, 2));
            String unitP = String.valueOf(view_Inventory.getValueAt(row, 3));
            String retailP = String.valueOf(view_Inventory.getValueAt(row, 4));
            String descrip = Toyland_InventorySystem.description.get(row);
            jTextField25.setText(barC);
            jTextField24.setText(itemN);
            jTextField26.setText(itemT);
            jTextField27.setText(unitP);
            jTextField28.setText(retailP);
            jTextArea4.setText(descrip);
        }
    }//GEN-LAST:event_viewInventory_ProductInfo

    private void inventoryOut_ProductInfo(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryOut_ProductInfo
        char keyChar = evt.getKeyChar();
        String barCode = jTextField14.getText();
        jTextArea5.setEditable(false);
        if (barCode.isEmpty()) {
            clearProductInfo_In_InventoryOUT();
        }
        boolean isSameProduct = false;
        DefaultTableModel view_Inventory = (DefaultTableModel) jTable3.getModel();
        int rowCount = view_Inventory.getRowCount();

        if (Character.isDigit(keyChar) || keyChar == '\b') {
            for (int row = 0; row < rowCount; row++) {
                Object cellValue = view_Inventory.getValueAt(row, 0);
                if (cellValue != null && cellValue.toString().equals(barCode)) {
                    String barC = String.valueOf(view_Inventory.getValueAt(row, 0));
                    String itemN = String.valueOf(view_Inventory.getValueAt(row, 1));
                    String itemT = String.valueOf(view_Inventory.getValueAt(row, 2));
                    String unitP = String.valueOf(view_Inventory.getValueAt(row, 3));
                    String retailP = String.valueOf(view_Inventory.getValueAt(row, 4));
                    String stocks = String.valueOf(view_Inventory.getValueAt(row, 6));
                    String descrip = Toyland_InventorySystem.description.get(row);
                    jTextField37.setText(barC);
                    jTextField36.setText(itemN);
                    jTextField35.setText(itemT);
                    jTextField34.setText(unitP);
                    jTextField33.setText(retailP);
                    jTextArea5.setText(descrip);
                    jLabel112.setText(stocks);
                    jLabel122.setIcon(new ImageIcon("Product_Found.png"));
                    isSameProduct = true;
                    break;
                }
            }

            if (isSameProduct == false) {
                clearProductInfo_In_InventoryOUT();
                jLabel122.setIcon(new ImageIcon("Product_NotFound.png"));
            }
        }
    }//GEN-LAST:event_inventoryOut_ProductInfo

    private void inventoryOut_Barcode_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryOut_Barcode_Validation
        // Validation: Numbers only and Maximum of 13 digits 
        String pn = jTextField14.getText();
        int l = pn.length();
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            if (l < 13) {
                jTextField14.setEditable(true);
            } else {
                jTextField14.setEditable(false);
            }
        } else {
            if (Character.isISOControl(c)) {
                jTextField14.setEditable(true);
            } else {
                jTextField14.setEditable(false);
            }
        }
    }//GEN-LAST:event_inventoryOut_Barcode_Validation

    private void inventoryOut_CustomerName_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryOut_CustomerName_Validation
        // Validation: Only Letters and Max Characters: 80
        String word = jTextField16.getText();
        int l1 = word.length();
        char c1 = evt.getKeyChar();
        if (Character.isLetter(c1)) {
            if (l1 < 80) {
                jTextField16.setEditable(true);
            } else {
                jTextField16.setEditable(false);
            }
        } else {
            if (Character.isWhitespace(c1) || Character.isISOControl(c1) || evt.getExtendedKeyCode() == KeyEvent.VK_SHIFT) {
                jTextField16.setEditable(true);
            } else {
                jTextField16.setEditable(false);
            }
        }
    }//GEN-LAST:event_inventoryOut_CustomerName_Validation

    private void inventoryOut_Quantity_Validation(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inventoryOut_Quantity_Validation
        // Validation: Numbers only 
        String pn = jTextField19.getText();
        int l = pn.length();
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            if (l < 6) {
                jTextField19.setEditable(true);
            } else {
                jTextField19.setEditable(false);
            }
        } else {
            if (Character.isISOControl(c)) {
                jTextField19.setEditable(true);
            } else {
                jTextField19.setEditable(false);
            }
        }
    }//GEN-LAST:event_inventoryOut_Quantity_Validation

    private void inventoryOut_Decrement(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryOut_Decrement
        String numString = jTextField19.getText();
        if (numString.isEmpty() || numString.equals("1") || numString.equals("0")) {
            jTextField19.setText("1");
        } else {
            Integer number = Integer.valueOf(numString);
            number -= 1;
            jTextField19.setText(number.toString());
        }
    }//GEN-LAST:event_inventoryOut_Decrement

    private void inventoryOut_Increment(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryOut_Increment
        String numString = jTextField19.getText();
        if (numString.isEmpty() || numString.equals("0")) {
            jTextField19.setText("1");
        } else {
            Integer number = Integer.valueOf(numString);
            number += 1;
            jTextField19.setText(number.toString());
        }
    }//GEN-LAST:event_inventoryOut_Increment

    private void jPanel40MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel40MouseEntered
        jPanel40.setBackground(new Color(139, 59, 255));
    }//GEN-LAST:event_jPanel40MouseEntered

    private void jPanel40MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel40MouseExited
        jPanel40.setBackground(new Color(167, 114, 242));
    }//GEN-LAST:event_jPanel40MouseExited

    private void jPanel30MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel30MouseEntered
        jPanel30.setBackground(new Color(139, 59, 255));
    }//GEN-LAST:event_jPanel30MouseEntered

    private void jPanel30MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel30MouseExited
        jPanel30.setBackground(new Color(167, 114, 242));
    }//GEN-LAST:event_jPanel30MouseExited

    private void viewInventory_Clear_SearchBar(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewInventory_Clear_SearchBar
        if (!jTextField11.getText().trim().equals("Search Item")) {
            jTextField11.setText("Search Item");
            jTextField11.setForeground(new Color(0x8D8D8D));
        }
        DefaultTableModel obj = (DefaultTableModel) jTable3.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        jTable3.setRowSorter(obj1);
        obj1.setRowFilter(null);
    }//GEN-LAST:event_viewInventory_Clear_SearchBar

    private void viewInventory_SearchBar_KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_viewInventory_SearchBar_KeyPressed
        char keyChar = evt.getKeyChar();
        if (jTextField11.getText().equals("Search Item") && Character.isLetterOrDigit(keyChar) && !Character.isWhitespace(keyChar)) {
            jTextField11.setText(null);
            jTextField11.setForeground(new Color(0x0000));
        }
    }//GEN-LAST:event_viewInventory_SearchBar_KeyPressed

    private void viewInventory_SearchBar_FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viewInventory_SearchBar_FocusLost
        if (jTextField11.getText().isEmpty()) {
            jTextField11.setText("Search Item");
            jTextField11.setForeground(new Color(0x8D8D8D));
        }
    }//GEN-LAST:event_viewInventory_SearchBar_FocusLost

    private void viewInventory_SearchBar_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewInventory_SearchBar_MouseClicked
        if (jTextField11.getText().equals("Search Item")) {
            jTextField11.setCaretPosition(0);
        }
    }//GEN-LAST:event_viewInventory_SearchBar_MouseClicked

    private void viewLogs_SearchBar_Clear(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewLogs_SearchBar_Clear
        if (!jTextField38.getText().trim().equals("Search Log")) {
            jTextField38.setText("Search Log");
            jTextField38.setForeground(new Color(0x8D8D8D));
        }
        DefaultTableModel obj = (DefaultTableModel) jTable5.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        jTable5.setRowSorter(obj1);
        obj1.setRowFilter(null);
    }//GEN-LAST:event_viewLogs_SearchBar_Clear

    private void viewLogs_SearchBar_KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_viewLogs_SearchBar_KeyPressed
        char keyChar = evt.getKeyChar();
        if (jTextField38.getText().equals("Search Log") && Character.isLetterOrDigit(keyChar) && !Character.isWhitespace(keyChar)) {
            jTextField38.setText(null);
            jTextField38.setForeground(new Color(0x0000));
        }
    }//GEN-LAST:event_viewLogs_SearchBar_KeyPressed

    private void viewLogs_SearchBar_FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_viewLogs_SearchBar_FocusLost
         if (jTextField38.getText().isEmpty()) {
            jTextField38.setText("Search Log");
            jTextField38.setForeground(new Color(0x8D8D8D));
        }
    }//GEN-LAST:event_viewLogs_SearchBar_FocusLost

    private void viewLogs_SearchBar_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewLogs_SearchBar_MouseClicked
        if (jTextField38.getText().equals("Search Log")) {
            jTextField38.setCaretPosition(0);
        }
    }//GEN-LAST:event_viewLogs_SearchBar_MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new menuFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane10;
    private javax.swing.JLayeredPane jLayeredPane11;
    private javax.swing.JLayeredPane jLayeredPane12;
    private javax.swing.JLayeredPane jLayeredPane13;
    private javax.swing.JLayeredPane jLayeredPane14;
    private javax.swing.JLayeredPane jLayeredPane15;
    private javax.swing.JLayeredPane jLayeredPane16;
    private javax.swing.JLayeredPane jLayeredPane17;
    private javax.swing.JLayeredPane jLayeredPane18;
    private javax.swing.JLayeredPane jLayeredPane19;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JLayeredPane jLayeredPane8;
    private javax.swing.JLayeredPane jLayeredPane9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
