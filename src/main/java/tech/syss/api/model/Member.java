package tech.syss.api.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.syss.api.model.audit.Auditable;

@Getter @Setter
@Entity @NoArgsConstructor @AllArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String photo;

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = false)
    @JsonIgnoreProperties({"createdAt", "updatedAt", "months", "weeks", "price"})
    private Membership membership;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime endMembership;

    // TODO: Add Payments.
    // TODO: Add Assitances.
}
