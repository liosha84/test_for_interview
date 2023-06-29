package my.component.model;

import java.math.BigDecimal;

public class Gift {

    private String name;
    private BigDecimal price;
    public Gift(String name, BigDecimal price)
    {
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public BigDecimal getPrice(){
        return price;
    }
}
