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
public class invoceLinesTableModel extends AbstractTableModel {

    private ArrayList<invoiceLines> _LinesArray;

    private String[] Columns = {"Item Name", "Unit Prics", "Count", "Line Total"};

    public invoceLinesTableModel(ArrayList<invoiceLines> _LinesArray) {
        this._LinesArray = _LinesArray;
    }

    @Override
    public int getRowCount() {
        return  _LinesArray == null ? 0 : _LinesArray.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        
        if (_LinesArray  == null ) {
            return  "";
        }else {
        invoiceLines linesdata = _LinesArray.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return linesdata.getName();
            case 1:
                return linesdata.getPrice();
            case 2:
                return linesdata.getCount();
            case 3:
                return linesdata.getInvoiceLinesTotal();

            default:
                return "";
          }
        }

    }

    @Override
    public String getColumnName(int column) {
        return Columns[column];
    }
}
