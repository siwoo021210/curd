package com.example.firstproject.controller;
import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    @GetMapping("/signup")
    public String signup(){
        return "members/new";
    }
    @PostMapping("/join")
    public String join(MemberForm form) {
        log.info(form.toString());
        //System.out.println(form.toString());

        Member member =form.toEntity();
        log.info(member.toString());
        //bSystem.out.println(member.toString());

        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        return "redirect:/members/"+saved.getId();
    }
    @GetMapping("/members/{id}")
    public String show (@PathVariable long id, Model model){
        Member memberEntity =memberRepository.findById(id).orElse(null);
        model.addAttribute("member",memberEntity);
        return "members/show";
    }
    @GetMapping("/members")
    public String index(Model model){
        ArrayList<Member> members = memberRepository.findAll();
        model.addAttribute("members",members);

        return "members/index";
    }
    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        Member memberEntity =memberRepository.findById(id).orElse(null);
        model.addAttribute("members",memberEntity);
        return "members/edit";
    }
    @PostMapping("/members/update")
    public String update(MemberForm form){
        log.info(form.toString());

        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());

        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if(target != null){
            memberRepository.save(memberEntity);
        }

        return "redirect:/members/"+memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다");

        Member target =memberRepository.findById(id).orElse(null);
        log.info(target.toString());

        if(target != null){
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됬습니다!");
        }
        return "redirect:/members";
    }

}
