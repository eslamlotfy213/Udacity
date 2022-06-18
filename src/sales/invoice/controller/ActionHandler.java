/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales.invoice.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sales.invoice.model.invoceHeaderTableModel;
import sales.invoice.model.invoceLinesTableModel;
import sales.invoice.model.invoiceHeader;
import sales.invoice.model.invoiceLines;
import sales.invoice.view.InvoiceFrame;
import sales.invoice.view.InvoiceHeaderDialog;
import sales.invoice.view.InvoiceLineDialog;
import sun.dc.pr.PRException;

/**
 *
 * @author Eslam.Lotfy
 */
public class ActionHandler implements ActionListener {

    private InvoiceFrame frame;
    private InvoiceHeaderDialog headerDialog;
    private InvoiceLineDialog lineDialog;

    public ActionHandler(InvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // System.out.println("ActionHandler code");
        switch (e.getActionCommand()) {

            //--------------header---------------------//
            case "Create New Invoice":
                System.out.println("Create New Invoice is perfoming");
                Newinv();
                break;

            case "Delete Invoice":
                System.out.println("Delete Invoice is perfoming");
                Deleteinv();
                break;

            //--------------Lines---------------------//
            case "Save":
                System.out.println("Save Button Invoice is perfoming");
                Newitem();
                break;

            case "Delete":
                System.out.println("Delete Button Invoice is perfoming");
                Deleteitem();
                break;

            case "NewInvOK":
                newInvODialogOK();
                break;

            case "NewInvCancel":
                newInvODialogCancel();
                break;

            case "newLineOK":
                newLineDialogOK();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;
            //--------------load/save files---------------------//

            case "Load File":
                System.out.println("Load File is perfoming");
                loadfile();
                break;

            case "Save File":
                System.out.println("Save File is perfoming");
                savefile();
                break;
        }
    }

    private void savefile() {
        ArrayList<invoiceHeader> invoicesArraySaves = frame.getInvoiceArray();
              JOptionPane.showMessageDialog(frame, "Please, select file to save header data!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser fc = new JFileChooser();
        try {
            int buttonresult = fc.showSaveDialog(frame);
            if (buttonresult == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter fw = new FileWriter(headerFile);
                String headerStr = "";
                String linesStr = "";
                for (invoiceHeader _inovoiceh : invoicesArraySaves) {
                    headerStr += _inovoiceh.toString();
                    headerStr += "\n";
                    for (invoiceLines line : _inovoiceh.getLines()) {
                        linesStr += line.toString();
                        linesStr += "\n";
                    }
                }
                
                headerStr = headerStr.substring(0, headerStr.length() - 1);
                linesStr = linesStr.substring(0, linesStr.length() - 1);
                buttonresult = fc.showSaveDialog(frame);
                File lineFile = fc.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
                fw.write(headerStr);
                lfw.write(linesStr);
                fw.close();
                lfw.close();
            }
                JOptionPane.showMessageDialog(frame, "Data saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error can't read file", JOptionPane.ERROR_MESSAGE);
        }
        
        frame.Displayinvoices();
    }

    private void Newinv() {

        headerDialog = new InvoiceHeaderDialog(frame);
        headerDialog.setVisible(true);

    }

    private void Deleteinv() {
        int selectedInvoiceIndex = frame.getTableHeader().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            frame.getInvoiceArray().remove(selectedInvoiceIndex);
            frame.getInvoceHeaderTableModel().fireTableDataChanged();

            frame.getTableIlines().setModel(new invoceLinesTableModel(null));

            frame.setLinesArray(null);
            frame.getNamelabel().setText("");
            frame.getNumberlabel().setText("");
            frame.getTotallabel().setText("");
            frame.getDatelabel().setText("");

        }
        frame.Displayinvoices();

    }

    private void Newitem() {

        lineDialog = new InvoiceLineDialog(frame);
        lineDialog.setVisible(true);
    }

    private void Deleteitem() {
        int selectedLineIndex = frame.getTableIlines().getSelectedRow();
        int _selectedinvoiceIndex = frame.getTableHeader().getSelectedRow();
        if (selectedLineIndex != -1) {
            frame.getLinesArray().remove(selectedLineIndex);
            invoceLinesTableModel linetablemodel = (invoceLinesTableModel) frame.getTableIlines().getModel();
            linetablemodel.fireTableDataChanged();
            frame.getTotallabel().setText("" + frame.getInvoiceArray().get(_selectedinvoiceIndex).getInvoiceTotal());
            frame.getInvoceHeaderTableModel().fireTableDataChanged();
            frame.getTableHeader().setRowSelectionInterval(_selectedinvoiceIndex, _selectedinvoiceIndex);
        }
        frame.Displayinvoices();

    }

    private void loadfile() {
        JOptionPane.showMessageDialog(frame, "Please, select The Header File !", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser _fileChooser = new JFileChooser();
        try {
            int _result = _fileChooser.showOpenDialog(frame);
            if (_result == JFileChooser.APPROVE_OPTION) {
                File _headerFile = _fileChooser.getSelectedFile();
                Path _headerPath = Paths.get(_headerFile.getAbsolutePath());
                List<String> _headerLines = Files.readAllLines(_headerPath);
                ArrayList<invoiceHeader> invoiceHeaders = new ArrayList<>();

                System.out.println("________________InvoiceHeader______________");
                for (String _headerLine : _headerLines) {
                    String[] array = _headerLine.split(",");
                    String _firstItemID = array[0];  // id 
                    String _secondItemDate = array[1]; //date  
                    String _thirdItemCustomer = array[2];  // customer

                    int _firstCode = Integer.parseInt(_firstItemID);
                    Date _dateInovice = InvoiceFrame.simpleDate.parse(_secondItemDate);
                    invoiceHeader header = new invoiceHeader(_firstCode, _thirdItemCustomer, _dateInovice);
                    invoiceHeaders.add(header);

                    System.out.println(_headerLine);
                }

                JOptionPane.showMessageDialog(frame, "Please, select The Lines File!", "Attension", JOptionPane.WARNING_MESSAGE);

                frame.setInvoiceArray(invoiceHeaders);
                _result = _fileChooser.showOpenDialog(frame);
                if (_result == JFileChooser.APPROVE_OPTION) {
                    File _lineFile = _fileChooser.getSelectedFile();
                    Path _linePath = Paths.get(_lineFile.getAbsolutePath());
                    List<String> _LineOfLines = Files.readAllLines(_linePath);
                    // ArrayList<invoiceLines> _invoiceLines = new ArrayList<>();

                    System.out.println("_________________InvoiceLines______________");

                    for (String lineline : _LineOfLines) {
                        String[] newarry = lineline.split(",");

                        String _numItem = newarry[0];     // id invoice num   int 
                        String _itemName = newarry[1];    // item name      string 
                        String _priceItem = newarry[2];   // item price    double 
                        String _countItem = newarry[3];   // item count    int 

                        int _invoCode = Integer.parseInt(_numItem);         //convert 
                        double _invoprice = Double.parseDouble(_priceItem); //convert 
                        int _invoCount = Integer.parseInt(_countItem);      //convert 

                        invoiceHeader _invo = frame.getInoviceObj(_invoCode);
                        invoiceLines _line = new invoiceLines(_itemName, _invoprice, _invoCount, _invo);
                        _invo.getLines().add(_line);
                        System.out.println(lineline);
                    }
                }
                invoceHeaderTableModel _invoceHeaderTableModel = new invoceHeaderTableModel(invoiceHeaders);
                frame.setInvoceHeaderTableModel(_invoceHeaderTableModel);
                frame.getTableHeader().setModel(_invoceHeaderTableModel);
                frame.getTableHeader().validate();
            }

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Number Format Error\n" + ex.getMessage(), "Error NumberFormat", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "File Error\n" + ex.getMessage(), "Error File Not Found", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Read Error\n" + e.getMessage(), "Error can't open file", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Date Format Error\n" + e.getMessage(), "Error can't parse file", JOptionPane.ERROR_MESSAGE);
        }

        frame.Displayinvoices();
    }

    private void newInvODialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;

    }

    private void newInvODialogOK() {
        headerDialog.setVisible(false);
        String cutNamefield = headerDialog.getCustNameField().getText();
        String Datefield = headerDialog.getInvDateField().getText();
        Date d = new Date();

        try {
            d = InvoiceFrame.simpleDate.parse(Datefield);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Can't parse date return to today date ...", "Invalid Date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (invoiceHeader invobj : frame.getInvoiceArray()) {
            if (invobj.getNum() > invNum) {
                invNum = invobj.getNum();
            }
            invNum++;

        }

        invoiceHeader newinv = new invoiceHeader(invNum, cutNamefield, d);
        frame.getInvoiceArray().add(newinv);
        frame.getInvoceHeaderTableModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;

        frame.Displayinvoices();

    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);

        String nameField = lineDialog.getItemNameField().getText();
        String countField = lineDialog.getItemCountField().getText();
        String priceField = lineDialog.getItemPriceField().getText();

        int count = 1;
        double price = 1;
        try {

            count = Integer.parseInt(countField);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Can't convert number right now ...", "Invalid number format", JOptionPane.ERROR_MESSAGE);

        }

        try {

            price = Double.parseDouble(priceField);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Can't convert price right now ...", "Invalid price  format", JOptionPane.ERROR_MESSAGE);

        }

        int selectedinvheader = frame.getTableHeader().getSelectedRow();
        if (selectedinvheader != -1) {
            invoiceHeader invoheader = frame.getInvoiceArray().get(selectedinvheader);
            invoiceLines newlinee = new invoiceLines(nameField, price, count, invoheader);
            //invoheader.getLines().add(newlinee);
            frame.getLinesArray().add(newlinee);
            invoceLinesTableModel linetablemodel = (invoceLinesTableModel) frame.getTableIlines().getModel();
            linetablemodel.fireTableDataChanged();
            frame.getInvoceHeaderTableModel().fireTableDataChanged();

        }
        frame.getTableHeader().setRowSelectionInterval(selectedinvheader, selectedinvheader);
        lineDialog.dispose();
        lineDialog = null;

        frame.Displayinvoices();

    }

    private void newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

}
