package tech.syss.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tech.syss.api.model.Membership;
import tech.syss.api.repository.MembershipRepository;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Membership create(Membership membership) {
        return membershipRepository.save(membership);
    }

    public List<Membership> all() {
        return membershipRepository.findAll();
    }

    public Membership get(Long id) {
        return membershipRepository.findById(id).orElse(null);
    }

    public Membership save(Membership membership) {
        return membershipRepository.save(membership);
    }

    public boolean delete(Long id) {
        membershipRepository.deleteById(id);
        return true;
    }
}
