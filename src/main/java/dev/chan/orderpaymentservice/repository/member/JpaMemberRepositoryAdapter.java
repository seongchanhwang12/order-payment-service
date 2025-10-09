package dev.chan.orderpaymentservice.repository.member;

import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepositoryAdapter implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public void save(Member member) {
        jpaMemberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(long id) {
        return jpaMemberRepository.findById(id);
    }
}
