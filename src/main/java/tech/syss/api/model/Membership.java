package tech.syss.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import tech.syss.api.model.audit.Auditable;

@Entity @Setter @Getter
public class Membership extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer months;
    private Integer weeks;
    private Double price;

    public Membership(String name, Integer months, Integer weeks, Double price) {
        this.name = name;
        this.months = months;
        this.weeks = weeks;
        this.price = price;
    }

    public Membership() {}
}
