package talkwith.semogong.login.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkwith.semogong.domain.main.Member;

public interface BasicLoginRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

    Member findByName(String name);
}
