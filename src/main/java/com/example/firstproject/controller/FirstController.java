package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        String attributeValue;
        model.addAttribute("username",attributeValue="hongpark");   //변수를 넣기위해서 model의 addattribute 메서드 사용
        return "greetings";  //templates/greetings.mustache -> 브라우저로 전송!
    }

    @GetMapping("/bye")  //컨트롤러의 bye의 getmapping 이 받고 return값을 뷰페이지로 반환하고 거기에 사용되는 데이터 값을 model로 설정한다.
    public String seeYouNext(Model model){
        String attributeValue;
        model.addAttribute("nickname", attributeValue="홍길동");
        return "goodbye";   //굿바이 리턴 거기서 nickname 등록!! 굿바이리턴 ----> goodbye.mustache를 뷰 하게한다!
    }
}
