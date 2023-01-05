package nl.hsleiden.iprwc.s1132776.model.database;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="products")
public class Product {
    @Id
    private int id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column
    private int stock;

    @Column
    private int price;

    @Column(name = "short_description")
    private String shortDescription;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    private String name;


    public Product(int id) {
        this.id = id;
    }

    public Product() {
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getName() {
        return name;
    }
}