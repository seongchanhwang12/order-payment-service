package dev.chan.orderpaymentservice.domain.member;

import java.util.Optional;

public interface MemberRepository {

    void save(Member member);

    Optional<Member> findById(long id);

}
