package tech.syss.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import tech.syss.api.model.Member;
import tech.syss.api.model.Membership;
import tech.syss.api.repository.MemberRepository;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private MembershipService membershipService;
    private final Random random = new Random();

    public MemberService(MemberRepository memberRepository, MembershipService membershipService) {
        this.memberRepository = memberRepository;
        this.membershipService = membershipService;
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
        return memberRepository.save(member);
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
    // TODO: Implement set payment.
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
        return memberRepository.findByCode(code);
    }
}
