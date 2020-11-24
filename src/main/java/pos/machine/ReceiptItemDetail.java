package pos.machine;

public class ReceiptItemDetail {
    private final String barcode;
    private final String name;
    private final int unitPrice;
    private final int quantity;
    private final int subTotal;


    public ReceiptItemDetail(String barcode, String name, int unitPrice, int quantity) {
        this.barcode = barcode;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subTotal = unitPrice * quantity;
    }

    public String getBarcode() { return barcode; }

    public String getName() { return name; }

    public int getUnitPrice() { return unitPrice; }

    public int getQuantity() { return quantity; }

    public int getSubTotal() { return subTotal; }
}
