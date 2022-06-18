/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales.invoice.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import sales.invoice.view.InvoiceFrame;

/**
 *
 * @author Eslam.Lotfy
 */
public class invoceHeaderTableModel extends AbstractTableModel {

    private ArrayList<invoiceHeader> _invoiceArray;

     private String [] Columns = {"Invoice Number","Invoice Date","Customer Name","Invoice Total"};
     
     
    public invoceHeaderTableModel(ArrayList<invoiceHeader> _invoiceArray) {
        this._invoiceArray = _invoiceArray;
    }

    
    @Override
    public int getRowCount() {
        return _invoiceArray.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.length; // array lenght 

    }

        @Override
    public String getColumnName(int column) {
        return Columns[column];
        
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
            invoiceHeader invoicesdata = _invoiceArray.get(rowIndex);
            switch(columnIndex) {
                case 0 :  return  invoicesdata.getNum();
                case 1 :  return  InvoiceFrame.simpleDate.format(invoicesdata.getDate());
                case 2 :  return  invoicesdata.getCutomer();
                case 3 :  return  invoicesdata.getInvoiceTotal();
            }
            return "";
    }



}
