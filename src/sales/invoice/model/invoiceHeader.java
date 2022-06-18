package sales.invoice.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Eslam.Lotfy
 */
public class invoiceHeader {

    private int num;
    private String cutomer;
    private Date invodate;
    private ArrayList<invoiceLines> lines ;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
       public invoiceHeader() {
        
    }
       
    public invoiceHeader(int num, String cutomer, Date invodate) {
        this.num = num;
        this.cutomer = cutomer;
        this.invodate = invodate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCutomer() {
        return cutomer;
    }

    public void setCutomer(String cutomer) {
        this.cutomer = cutomer;
    }

    public Date getDate() {
        return invodate;
    }

    public void setDate(Date invodate) {
        this.invodate = invodate;
    }

    public ArrayList<invoiceLines> getLines() {  // error
        if(lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<invoiceLines> lines) {
        this.lines = lines;
    }

    public double getInvoiceTotal() {
        double total = 0.0;    
        for (int i = 0; i < getLines().size(); i++) {
            total += lines.get(i).getInvoiceLinesTotal();
        }
        return total;
    }

    @Override
    public String toString() {
      
      return  num + "," + df.format(invodate) + ", "+ cutomer ;
      

    }



   

}
