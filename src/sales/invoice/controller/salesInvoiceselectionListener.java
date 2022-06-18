/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales.invoice.controller;

import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sales.invoice.model.invoceLinesTableModel;
import sales.invoice.model.invoiceHeader;
import sales.invoice.model.invoiceLines;
import sales.invoice.view.InvoiceFrame;

/**
 *
 * @author Eslam.Lotfy
 */
public class salesInvoiceselectionListener implements ListSelectionListener {

    private InvoiceFrame frame;
    
    public salesInvoiceselectionListener(InvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvindex = frame.getTableHeader().getSelectedRow();
        //System.out.println("Selected value :" + selectedInvindex);        
        if (selectedInvindex != -1) {
                 
        invoiceHeader selectedinvoiceHeader = frame.getInvoiceArray().get(selectedInvindex);
         ArrayList<invoiceLines> ALlLines = selectedinvoiceHeader.getLines();
        invoceLinesTableModel linesTablemodel = new invoceLinesTableModel(ALlLines);
        
        
        frame.setLinesArray(ALlLines);
        frame.getTableIlines().setModel(linesTablemodel);
        frame.getNamelabel().setText(selectedinvoiceHeader.getCutomer());
        frame.getNumberlabel().setText(""+selectedinvoiceHeader.getNum());
        frame.getTotallabel().setText(""+selectedinvoiceHeader.getInvoiceTotal());
        frame.getDatelabel().setText(InvoiceFrame.simpleDate.format(selectedinvoiceHeader.getDate()));
        

        }         
    }

}
