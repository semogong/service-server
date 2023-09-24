package talkwith.semogong.login.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkwith.semogong.domain.main.Member;

import java.util.Optional;

public interface BasicLoginRepository {

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByName(String name);
}
