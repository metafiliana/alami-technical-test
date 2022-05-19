package dev.metafiliana.alamitechnicaltest.member.services;

import dev.metafiliana.alamitechnicaltest.exception.GeneralBadRequestException;
import dev.metafiliana.alamitechnicaltest.exception.GeneralNotFoundException;
import dev.metafiliana.alamitechnicaltest.member.entities.Member;
import dev.metafiliana.alamitechnicaltest.member.repositories.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getMember(String name) {
        try {
            List<Member> members;
            if (StringUtils.hasText(name)) {
                members = memberRepository.findByNameContaining(name);
            } else {
                members = memberRepository.findAll();
            }
            return members;
        } catch (Exception e) {
            throw e;
        }
    }

    public Member getMemberById(Long id) {
        try {
            Optional<Member> member = memberRepository.findById(id);
            if (!member.isPresent()) {
                throw new GeneralNotFoundException();
            }
            return member.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public HashMap<Long, Member> getMemberByIds(Set<Long> memberIds) {
        try {
            HashMap<Long, Member> mapMembers = new HashMap<Long, Member>();
            List<Member> members = memberRepository.findMemberByIds(memberIds);

            members.forEach((n) -> mapMembers.put(n.getId(), n));
            return mapMembers;
        } catch (Exception e) {
            throw e;
        }
    }

    public Member createMember(Member member) {
        try {
            Member memberData = memberRepository.findByNik(member.getNik());
            if (memberData != null) {
                logger.error("createMember error, duplicate user with same nik");
                throw new GeneralBadRequestException();
            }

            Member newMember = new Member();
            newMember.setNik(member.getNik());
            newMember.setName(member.getName());
            newMember.setDob(member.getDob());
            newMember.setAddress(member.getAddress());

            return memberRepository.save(newMember);
        } catch (Exception e) {
            throw e;
        }
    }
}
