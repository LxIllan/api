package tech.syss.api.model;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.syss.api.model.audit.Auditable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonIgnoreProperties({ "createdAt", "updatedAt", "months", "weeks", "price" })
    private Membership membership;

    @Column(columnDefinition = "DATE")
    private LocalDate endMembership;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Attendance> attendances;

    public Member(String code, String name, String lastName, String email, String phone, String photo,
            Membership membership,
            LocalDate endMembership) {
        this.code = code;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.membership = membership;
        this.endMembership = endMembership;
    }

    // TODO: Add Payments.
    // TODO: Add Assitances.
}
