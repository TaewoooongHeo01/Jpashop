package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional //readOnly=false
    public Long join(Member member) {
        validDateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validDateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getUsername());
        if (!findMembers.isEmpty()) { //같은 이름의 회원이 한명이라도 있으면 중복 예외처리
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체회원 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
