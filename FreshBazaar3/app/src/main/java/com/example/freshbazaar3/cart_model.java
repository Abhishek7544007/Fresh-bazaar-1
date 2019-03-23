package com.example.freshbazaar3;

public class cart_model {
    private String pid, pname, price, quantity;
    public cart_model(){}

    public cart_model(String pid, String pname, String price, String quantity)
    {
        this.pid=pid;
        this.pname=pname;
        this.price=price;
        this.quantity=quantity;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }
}
