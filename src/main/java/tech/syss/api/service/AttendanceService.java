package tech.syss.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.syss.api.model.Attendance;
import tech.syss.api.model.Member;
import tech.syss.api.model.Membership;
import tech.syss.api.repository.AttendanceRepository;
import tech.syss.api.repository.MemberRepository;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, MemberRepository memberRepository) {
        this.attendanceRepository = attendanceRepository;
        this.memberRepository = memberRepository;
    }

    // TODO: Make tests for this method
    public Attendance save(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (member.getEndMembership().isBefore(LocalDate.now())) {
            throw new RuntimeException("Member has no active membership");
        }
        Membership membership = member.getMembership();
        if (membership.getStartHour() != null && membership.getEndHour() != null) {
            int hour = LocalDateTime.now().getHour();
            if (hour <= membership.getStartHour() || hour > membership.getEndHour()) {
                throw new RuntimeException("Member can only access the gym between " + membership.getStartHour()
                        + " and " + membership.getEndHour());
            }
        }
        Attendance attendance = new Attendance(member);
        return attendanceRepository.save(attendance);
    }

    // TODO: Cache the results of this method
    public List<String> all(Specification<Attendance> spec) {
        List<Attendance> attendances = attendanceRepository.findAll(spec);
        List<String> att = attendances.stream().map(attendance -> attendance.getDate().toString())
                .collect(Collectors.toList());
        return att;
    }
}
