package dev.chan.orderpaymentservice.repository.member;

import dev.chan.orderpaymentservice.domain.member.Member;
import dev.chan.orderpaymentservice.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaMemberRepositoryAdapter.class})
class JpaMemberRepositoryAdapterTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 올바른회원데이터가주어지면_회원이_정상적으로저장되고_id가생성된다() {
        //given
        String name = "name";
        String password = "password";
        String email = "email";
        String phone = "01011111111";

        Member member = Member.signUp(email,password,password,phone,name);

        //when
        memberRepository.save(member);
        Optional<Member> foundMember = memberRepository.findById(member.getId());

        //then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).isEqualTo(member);
        assertThat(foundMember.get().getName()).isEqualTo(name);
        assertThat(foundMember.get().getPassword()).isEqualTo(password);
    }




}