package com.selabg11.freshbazaar;

public class Items {
    private String itemId;
    private String itemName;
    private String prodDate;
    private String expDate;
    private String amount;
    private String unit;
    private String category;
    private String cost;
    private String discount;
    private String imglink;


    public Items(){}

    public String getDiscount() {
        return discount;
    }

    public Items(String itemId, String itemName, String prodDate, String expDate, String amount, String unit, String category, String cost, String imglink, String discount){
        this.itemId = itemId;
        this.itemName = itemName;
        this.discount = discount;
        this.prodDate = prodDate;
        this.expDate = expDate;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
        this.cost = cost;

        this.imglink = imglink;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getProdDate() {
        return prodDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        return category;
    }

    public String getCost() {
        return cost;
    }

    public String getImglink() {
        return imglink;
    }
}