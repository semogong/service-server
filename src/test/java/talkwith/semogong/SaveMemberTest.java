package talkwith.semogong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.Member;
import talkwith.semogong.repository.MembrRepository;

@SpringBootTest
public class SaveMemberTest {
    @Autowired
    MembrRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {
        Member member = new Member();
        member.setName("park");

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);



    }
}