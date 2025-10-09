package dev.chan.orderpaymentservice.repository.member;

import dev.chan.orderpaymentservice.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

}
