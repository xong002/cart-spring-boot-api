package sg.edu.ntu.cart_api.entity;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Bean
// POJO - Plain Old Java Object
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id; // to allow id to be null value (not possible for 'int'), for when record is
                // being created, id is still null.

    @Column(nullable = false) // to prevent value from being null. Default is nullable.
    String name;

    String description;

    float price = 0f;

    @Column(name = "created_at", updatable = false) // based on the existing data column name. to also stop from further
                                                    // updates
    Timestamp createdAt = new Timestamp(new Date().getTime());

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
