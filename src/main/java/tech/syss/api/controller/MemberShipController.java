package tech.syss.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.syss.api.model.Membership;
import tech.syss.api.service.MembershipService;

@RestController
@RequestMapping("/api/memberships")
public class MemberShipController {

    private MembershipService membershipService;

    public MemberShipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping
    public ResponseEntity<Object> all() {
        return ResponseEntity.ok().body(membershipService.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membership> get(@PathVariable Long id) {
        Membership membership = membershipService.get(id);
        if (membership == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(membership);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Membership> create(@RequestBody Membership membership) {
        Membership createdMembership = membershipService.create(membership);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMembership);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Membership> update(@PathVariable Long id, @RequestBody Membership membership) {
        if (membershipService.get(id) == null) {
            return ResponseEntity.notFound().build();
        }
        membership.setId(id);
        membershipService.save(membership);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Membership> delete(@PathVariable Long id) {
        membershipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
