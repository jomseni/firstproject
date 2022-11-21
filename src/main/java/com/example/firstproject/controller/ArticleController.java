package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j //로깅을 위한 골뱅이(어노테이션)이다!!!
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결! 31번째 줄 articleRepository 객체를 따로 생성안하고 이것으로 자동 생성연결!
    private ArticleRepository articleRepository; //articleRepository객체가 없어서 아래에서 오류가 생겼으므로 ArticleRepository타입의 객체를 선언해준다

    @Autowired
    private CommentService commentService; //데이터 목록 조회 부분에서 commentService를 사용하기위해 정의

    @GetMapping("/articles/new")
    public String newArticleForm(){   //페이지를 보여주기 위해서 메서드를 만든다
        return "articles/new";      //리턴값에 우리가 보여줄 뷰페이지를 넣어준다!
    }

    @PostMapping("/articles/create")  //form을 받는 메서드,던지는 방법을 post로 하기로 했기때문에 post 맵핑으로!!! 제목,내용에 적은 것들이 여기의 주소로 던져진다!
    public String createArticle(ArticleForm form){  //작성한 데이터가 파라미터로 던져진다! DTO 객체로 받아진다, this로 클래스 객체로 변환
        log.info(form.toString());
        //로깅이란? 자동차 블랙박스 느낌 == 모든 과정,일들을 기록한다! println 쓰면 안된다 서버에선 기록이안된다!
        //로깅 기능으로 대체!  System.out.println(form.toString());    //DTO로 데이터를 담겨진 후  받아와서 출력하는것!

        //1. Dto를 변환! Entity로! 컨트롤러를 통해!
        Article article = form.toEntity();     //toEntity메서드를 호출해서 article 이라는 타입의 Entity를 반환해오는 방식!
        log.info(article.toString());       //println라인을 대체한다! 로깅을 통해서! 이것 또한 리팩토링으로 볼 수 있다!


        //2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article); // articleRepository가 save메서드를 실행하게한다. article데이터를 세이브하게 하여 세이브 한 것을 반환한다 . Article Entity타입으로 saved 이름을 가지고 반환하게 한다.
        log.info(saved.toString()); //마찬가지로 println 자리에 로깅을 사용한다!
        return "redirect:/articles/" + saved.getId();          //리다이렉트 적용 부분!
    }
    // 아랫 부분은 데이터 목록 조회하기!
    @GetMapping("/articles/{id}")   //id는 변수 이다! 넣어지는 숫자에 따라 다른것 적용! id 값을 컨트롤러에서 받아온다
    public String show(@PathVariable Long id, Model model){   //위의 url주소로 부터 id 변수를 가져온다! => pathVariable
        log.info("id = " + id );        //로그를 통해서 id 1000번이  잘 받아진걸 볼 수 있다!

        // 1 : id로 데이터를 가져옴! repository를 통해 가져온다!
        Article articleEntity = articleRepository.findById(id).orElse(null); // 저장 되어있던거 를 다시 가져오므로 SQL에서 자바로 다시바꿔야하므로 사용!repository 이용해서!
        List<CommentDto> commentDtos = commentService.comments(id);
        //아이디 값을 통해 찾았는데 해당아이디 값 없으면 null을 반환한다의 뜻!

        // 2 : 가져온 데이터를 모델에 등록!
        model.addAttribute("article",articleEntity); //article이라는 이름으로 articleEntity 등록!
        model.addAttribute("commentDtos",commentDtos);
        // 3 : 보여줄 페이지를 설정!
        return "articles/show";
    }

    @GetMapping("/articles")        //컨트롤러가 articles 라는 요청을 받는다!
    public String index(Model model){          //그럼 해당 메서드가 실행한다

        //1. 모든 Article을 가져온다!
        List<Article> articleEntityList =articleRepository.findAll();   //해당 리포즈토리에있는 데이터를 모두 가져오는 것이다! , ArrayList 형식으로 반환하겠다.
        //ArrayList의 상위 타입인 List로 해도 괜찮다!

        //2. 가져온 Article 묶음을 뷰로 전달한다!
        model.addAttribute("articleList",articleEntityList);
        //모델에 데이터 등록!
        //위에서 가져온 아티클 엔티티 리스트를 뷰로 전달 === 모델 사용! 위에 파라미터에 모델 추가

        //3. 뷰 페이지를 설정한다.
        return "articles/index";                  //뷰페이지를 설정하는 코드! articles/index.mustache
    }


    //데이터 수정부분!!!!
    //컨트롤러가 edit 페이지의 url을 요청을 받는 부분!
    @GetMapping("/articles/{id}/edit") //중괄호를 하나만 써야 변수를 받아 올수있는것이다! pathvariable의 변수 id 와  변수 id 이름이 같아야 적용이 된다!!매우중요!! 여기부분 오타 조심!!!
    public String edit(@PathVariable Long id, Model model){  //아래 findById(id) 값이 빨간색이고 그 id를 url 에서 가져오므로 @PathVariable을 사용
        //수정할 데이터를 가져와야한다(repository를 통해서)! 왜냐하면 원래데이터를 수정해야하는것이므로 기본 데이터를 보여줘야한다!
        Article articleEntity = articleRepository.findById(id).orElse(null); //id값을 url요청으로 던짐! 만약에 없다면 null을 반환한다.

        //모델에 데이터를 등록한다!(뷰 페이지에 사용할 수 있게!) 파라미터에 model 선언
        model.addAttribute("article", articleEntity); //이름을 article으로 한다. 앞에서 가져온 articleEntity 을!
        //뷰페이지설정 . 컨트롤러가 해당 요청을받으면!!!!이렇게 return값으로 반환한다!
        return "articles/edit"; //수정 페이지를 응답으로 반환해야한다!
    }



    @PostMapping("/articles/update") //여기로 던지기로함!
    public String update(ArticleForm form){ //DTO로 받아오기
        //id 값을 추가 시켰기 때문에!Articleform에! 따라서 DTO에도 id를 추가
        //터미널에 해당 id 를 수정했을때 잘 수정 되었는지 확인한다
        log.info(form.toString());

        //1 . DTO로 받아왔으니 엔티티로 변환한다
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2 . 엔티티를 DB로 저장한다.! 생성하는 것이 아니라 기본적으로 있던 것을 수정하는 것이므로 생성할때와 과정이 다르다 두가지로 나누어짐!

        //2-1 : DB에서 기존 데이터를 가져온다!
        //articleRepository를 통해서 id에 맞는 값을 가져와서 target에 값을 반환한다. 그러나 없는 id 값이면 null 반환
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2 : 기존 데이터에 값이 있다면 값을 갱신한다.
        if( target!= null){
            articleRepository.save(articleEntity); //엔티티가 DB로 갱신한다
        }

        //3 . 수정 결과 페이지로 리다이렉트 한다! id를 찾아 수정하면 페이지를 그 바꾼 id 번호의 웹 피이지로 이동한다!
        return "redirect:/articles/" + articleEntity.getId();
    }


    //데이터 삭제하기 부분!!!!!!!
    @GetMapping("/articles/{id}/delete")    //delete 요청받아오기!
    public String delete(@PathVariable Long id ,RedirectAttributes rttr){  //findById(id)부분의 id의 파라미터 찾아주기! url의 값에서 가져오기위해 이노테이션사용!
        log.info("삭제 요청이 들어왔습니다!!");
        //1 . 삭제 대상을 요청한다!(가져오기!)
        //JPA에서 제공하는 리퍼즈터리를 통해서 데이터를 가져온다!
        //대상이 있는지 우선확인한다!
      Article target = articleRepository.findById(id).orElse(null);  //article entity를 가져온다! 반환!
        log.info(target.toString());
       // 2 . 그 대상을 삭제한다!
        if( target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다");

        }
        //3 . 데이터 삭제 결과 목록 리다이렉트한다!
        //delete 누르면 리다이렉트로 목록 사이트값을 반환하여 띄운다!
        return "redirect:/articles";
    }
}
