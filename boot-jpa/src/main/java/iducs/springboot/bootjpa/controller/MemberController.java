package iducs.springboot.bootjpa.controller;

import iducs.springboot.bootjpa.domain.Member;
import iducs.springboot.bootjpa.entity.MemberEntity;
import iducs.springboot.bootjpa.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/members") // localhost:8888/memobers로 시작
public class MemberController {

    final MemberService memberService;

    // 생성자 주입 : (Constructor Injection) vs. @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // registration form 멤버 등록 페이지
    @GetMapping("/regform")
    public String getRegform(Model model) {
        // 정보를 전달받을 빈(empty) 객체를 보냄
        model.addAttribute("member", MemberEntity.builder().build());
        return "/members/regform";  // view resolving
    }

    // 멤버 등록
    @PostMapping("")
    public String postMember(@ModelAttribute("member") Member member, Model model) {
        memberService.create(member);
        model.addAttribute("member", member);
        return "/members/info";
    }

    // 멤버 메인 페이지
    @GetMapping("")
    public String getIndex(Model model) {
        List<Member> members = memberService.readAll();
        model.addAttribute("list", members);
        return "/members/home";
    }

    // 상세 페이지
    @GetMapping("/{idx}")
    public String getMember(@PathVariable("idx") Long seq, Model model) {
        Optional<MemberEntity> member = memberService.readById(seq);
        MemberEntity m = member.get();
        model.addAttribute("member", m);
        return "/members/info";
    }

    // 수정 페이지
    @GetMapping("/{idx}/upform")
    public String getUpform(@PathVariable("idx") Long seq, Model model) {
        Optional<MemberEntity> member = memberService.readById(seq);
        MemberEntity m = member.get();
        model.addAttribute("member", m);
        return "/members/upform";
    }

    // 멤버 정보 수정
    @PutMapping("/{idx}")
    public String putMember(@ModelAttribute("member") Member member, Model model) {
        memberService.update(member);
        return "/members/info";
    }

    // 멤버 삭제
    @DeleteMapping("/{idx}")
    public String deleteMember(@ModelAttribute("idx") Long seq, Model model) {
        Member member = memberService.readById(seq);
        memberService.delete(member);
        return "redirect:/members";
    }


    // MemberService ms = new MemberServiceImpl();
    @GetMapping("/buttons") // url - http://localhost:8888/buttons
    public String getButtons() {
        return "buttons"; // http://localhost:8888/buttons.html
    }
    @GetMapping("/cards") // url - http://localhost:8888/buttons
    public String getCards() {
        return "cards"; // http://localhost:8888/buttons.html
    }
    @GetMapping("/th")
    public String getThymeleaf() {
        return "thymeleaf";
    }
    @GetMapping("/tables")
    public String getTables() {
        return "tables";
    }
    @GetMapping("/charts")
    public String getCharts() {
        return "charts";
    }
}
