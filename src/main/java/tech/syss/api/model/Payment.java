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

@Setter @Getter
@Entity @NoArgsConstructor @AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnoreProperties({"membership", "phone", "photo", "createdAt", "updatedAt"})
    private Member member;

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = false)
    @JsonIgnoreProperties({"months", "weeks", "price", "createdAt", "updatedAt"})
    private Membership membership;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"credentialsNonExpired", "accountNonExpired", "accountNonLocked", "roles", "createdAt", "updatedAt"})
    private User user;

    private Double price;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    public Payment(Member member, Membership membership, User user, Double price, LocalDateTime date) {
        this.member = member;
        this.membership = membership;
        this.user = user;
        this.price = price;
        this.date = date;
    }
}
