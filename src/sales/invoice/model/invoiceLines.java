package sales.invoice.model;

/**
 *
 * @author Eslam.Lotfy
 */
public class invoiceLines {

    private String name;
    private double price;
    private int count;
    private invoiceHeader header;

    
      public invoiceLines() {
       
    }
      
    public invoiceLines( String name, double price, int count, invoiceHeader header) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.header = header;

    }

    public invoiceHeader getHeader() {
        return header;
    }

    public void setHeader(invoiceHeader header) {
        this.header = header;
    }

 

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getInvoiceLinesTotal() {
        return price * count;
    }

    @Override
    public String toString() {
        return  header.getNum() + "," + name + "," + price + "," + count;

    }

 

    
    
}
