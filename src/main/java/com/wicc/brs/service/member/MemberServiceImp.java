package com.wicc.brs.service.member;

import com.wicc.brs.dto.MemberDto;
import com.wicc.brs.entity.Member;
import com.wicc.brs.repo.MemberRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImp implements MemberService{

    private final MemberRepo memberRepo;

    public MemberServiceImp(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }


    @Override
    public MemberDto save(MemberDto memberDto) {
        Member member = Member.builder()
                .mid(memberDto.getMid())
                .name(memberDto.getName())
                .address(memberDto.getAddress())
                .contact(memberDto.getContact())
                .email(memberDto.getEmail())
                .build();
        Member save = memberRepo.save(member);
        return MemberDto.builder()
                .mid(save.getMid())
        .build();
    }

    @Override
    public List<MemberDto> findAll() {
        return memberRepo.findAll().stream().map(member-> MemberDto.builder()
                .mid(member.getMid())
                .address(member.getAddress())
                .contact(member.getContact())
                .name(member.getName())
                .email(member.getEmail())
                .build()).collect(Collectors.toList());
    }

    @Override
    public MemberDto findById(Integer integer) {
        Optional<Member> byId = memberRepo.findById(integer);
        Member member;
        if(byId.isPresent()){
            member = byId.get();
            return MemberDto.builder()
                    .mid(member.getMid())
                    .name(member.getName())
                    .contact(member.getContact())
                    .email(member.getEmail())
                    .build();
        }
        return null;
    }

    @Override
    public void deleteBYId(Integer integer) {
        memberRepo.deleteById(integer);

    }
}
