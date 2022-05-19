package dev.metafiliana.alamitechnicaltest.member.repositories;

import dev.metafiliana.alamitechnicaltest.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.name LIKE %:name%")
    List<Member> findByNameContaining(String name);

    @Query(
            value = "SELECT * FROM member WHERE nik = :nik LIMIT 1",
            nativeQuery = true
    )
    Member findByNik(String nik);

    @Query("SELECT m FROM Member m WHERE m.id IN (:memberIds)")
    List<Member> findMemberByIds(Set<Long> memberIds);
}
