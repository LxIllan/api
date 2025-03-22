package tech.syss.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import tech.syss.api.model.Member;
import tech.syss.api.model.Membership;
import tech.syss.api.model.Payment;
import tech.syss.api.model.User;
import tech.syss.api.repository.MemberRepository;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private MembershipService membershipService;
    private PaymentService paymentService;
    private final AuthService authService;
    private final Random random = new Random();

    public MemberService(MemberRepository memberRepository, MembershipService membershipService, PaymentService paymentService, AuthService authService) {
        this.memberRepository = memberRepository;
        this.membershipService = membershipService;
        this.paymentService = paymentService;
        this.authService = authService;
    }

    public Member create(String name, String lastName, String email, String phone, Long membershipId) {
        Membership membership = membershipService.get(membershipId);
        if (membership == null) {
            throw new RuntimeException("Membership not found.");
        }
        LocalDateTime endMembership = getEndMembership(LocalDateTime.now(), membership);
        Member member = new Member();
        member.setCode(generateUniqueCode());
        member.setName(name);
        member.setLastName(lastName);
        member.setEmail(email);
        member.setPhone(phone);
        member.setMembership(membership);
        member.setEndMembership(endMembership);
        memberRepository.save(member);

        User user = authService.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        Payment payment = paymentService.create(member.getId(), membership.getId(), user.getId(), membership.getPrice());
        if (payment == null) {
            throw new RuntimeException("Payment not created.");
        }

        // TODO: Implement the update method.

        return member;
    }

    public List<Member> all() {
        return memberRepository.findAll();
    }

    public Member get(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id) {
        memberRepository.deleteById(id);
        return true;
    }

    // TODO: Implement the update method.
    // TODO: Implement set assistance.
    // TODO: Implement save photo.

    private LocalDateTime getEndMembership(LocalDateTime now, Membership membership) {
        return now.plusMonths(membership.getMonths()).plusWeeks(membership.getWeeks());
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = String.format("%04d", random.nextInt(10000));
        } while (findByCode(code) != null);
        return code;
    }

    public Member findByCode(String code) {
        return memberRepository.findByCode(code).orElse(null);
    }
}
