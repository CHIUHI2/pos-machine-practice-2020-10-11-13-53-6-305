package pos.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        Map<String, Integer> itemQuantityMapping = this.getItemQuantityMapping(barcodes);

        return this.generateReceipt(itemQuantityMapping);
    }

    private Map<String, Integer> getItemQuantityMapping(List<String> barcodes) {
        Map<String, Integer> itemQuantityMapping = new TreeMap<>();

        for(String barcode : barcodes) {
            itemQuantityMapping.putIfAbsent(barcode, 0);
            Integer count = itemQuantityMapping.get(barcode);
            itemQuantityMapping.put(barcode, count+1);
        }

        return  itemQuantityMapping;
    }

    private String generateReceipt(Map<String, Integer> itemQuantityMapping) {
        StringBuilder receipt = new StringBuilder("***<store earning no money>Receipt***");

        List<ReceiptItemDetail> receiptItemDetailList = this.getReceiptItemDetailList(itemQuantityMapping);
        for(ReceiptItemDetail receiptItemDetail : receiptItemDetailList) {
            receipt.append("\nName: ").append(receiptItemDetail.getName()).append(", Quantity: ").append(receiptItemDetail.getQuantity()).append(", Unit price: ").append(receiptItemDetail.getUnitPrice()).append(" (yuan), Subtotal: ").append(receiptItemDetail.getSubTotal()).append(" (yuan)");
        }

        receipt.append("\n----------------------");

        receipt.append("\nTotal: ").append(this.calculateTotal(receiptItemDetailList)).append(" (yuan)");

        receipt.append("\n**********************");

        return receipt.toString();
    }

    private List<ReceiptItemDetail> getReceiptItemDetailList(Map<String, Integer> itemQuantityMapping) {
        List<ReceiptItemDetail> receiptItemDetailList = new ArrayList<>();
        Map<String, ItemInfo> itemInfoMapping =  this.getItemInfoMapping();

        for(Map.Entry<String, Integer> itemQuantityEntry : itemQuantityMapping.entrySet()) {
            String barCode = itemQuantityEntry.getKey();
            Integer quantity = itemQuantityEntry.getValue();
            ItemInfo itemInfo = itemInfoMapping.get(barCode);
            if(itemInfo == null) {
                continue;
            }

            ReceiptItemDetail receiptItemDetail = new ReceiptItemDetail(barCode, itemInfo.getName(), itemInfo.getPrice(), quantity);
            receiptItemDetailList.add(receiptItemDetail);
        }

        return receiptItemDetailList;
    }

    private Integer calculateTotal(List<ReceiptItemDetail> receiptItemDetailList) {
        int total = 0;

        for(ReceiptItemDetail receiptItemDetail : receiptItemDetailList) {
            total += receiptItemDetail.getSubTotal();
        }

        return total;
    }

    private Map<String, ItemInfo> getItemInfoMapping() {
        Map<String, ItemInfo> itemInfoMapping = new HashMap<>();

        List<ItemInfo> itemInfoList = ItemDataLoader.loadAllItemInfos();
        for(ItemInfo itemInfo: itemInfoList) {
            itemInfoMapping.put(itemInfo.getBarcode(), itemInfo);
        }

        return itemInfoMapping;
    }
}
