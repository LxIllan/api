package tech.syss.api.service;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.syss.api.model.Member;
import tech.syss.api.model.Membership;
import tech.syss.api.model.Payment;
import tech.syss.api.model.User;
import tech.syss.api.repository.MemberRepository;
import tech.syss.api.repository.MembershipRepository;
import tech.syss.api.repository.PaymentRepository;
import tech.syss.api.repository.UserRepository;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;
    private MemberRepository memberRepository;
    private MembershipRepository membershipRepository;
    private UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, MemberRepository memberRepository, MembershipRepository membershipRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    public Payment create(Long memberId, Long membershipId, Long userId, Double price) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found."));
        Membership membership = membershipRepository.findById(membershipId)
            .orElseThrow(() -> new RuntimeException("Membership not found."));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found."));

        Payment payment = new Payment(member, membership, user, price, LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Page<Payment> all(Specification<Payment> spec, Pageable pageable) {
        return paymentRepository.findAll(spec, pageable);
    }

    public Payment get(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
}
