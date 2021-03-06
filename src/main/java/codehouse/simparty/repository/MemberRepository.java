package codehouse.simparty.repository;

import codehouse.simparty.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m " +
            "from Member m " +
            "where m.fromSocial = :social and m.email = :email")
    Optional<Member> findByEmail(String email, boolean social);
}
