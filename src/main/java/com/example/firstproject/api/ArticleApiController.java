package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //log 를 사용할 수있게해주는 어노테이션이다!
@RestController //RestApi 용 컨트롤러! 데이터(보통 JSON형태)를반환한다
public class ArticleApiController {

    @Autowired  //객체를 연결해주는 어노테이션 연결해준다!자유롭게 객체를 사용할 수있 게해준다, DI = 외부에서 가져온다는 뜻!(data.sql),생성 객체를 가져와 연결!
    private ArticleService articleService;

    //    private ArticleRepository articleRepository; //객체

    //GET메서드 (목록조회)
    @GetMapping("/api/articles") // 이렇게 요청 받는 것은 웨이터(컨트롤러)
    public List<Article> index() {
        //기존에는 repository를 통해서 재료를 가져오게 했지만 이젠 service를 통해 가져오게 한다.
        return articleService.index(); //보조요리사와 만날 일 없어! 주방장(service)이 시킨다! repository에게!
    }
    //단일 article 가져오기(단건 조회)
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    //
    //POST 새로 생성하는 요청!
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { //RestAPI에서 JSON으로 던질때는 @RequestBody가 있어야 받아진다!
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :      //3항연산자 , 비어있다면 상태를 200반환하고 create를 담아서 repository에 보낸다.
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    //PATCH 수정
    @PatchMapping("/api/articles/{id}") //주문받아오기 -> 컨트롤러의 역할
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) { //상태코드를 같이 리턴하기위해 ResponseEntity<Article>로 바꿔준다.

        Article updated = articleService.update(id, dto);  //주방장아 업데이트해줘~ 컨트롤러의 역할(웨이터)

        //웨이터는 주문을 받고 이렇게 가져다 주기만 하면 돼!
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    //웨이터가 요리까지 하는건 너무 복잡해!!!!!
    //웨이터는 받고 반환만 하면된다!
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        //웨이터는 주문오고 반환하는거만 신경써!
        Article deleted = articleService.delete(id); //요리는 주방장에 맡겨 서비스시켜, 서비스가 delete 함수해서 반환을 deleted로 반환할것이다!
        //웨이터는 잘반환 된건지 아닌지만 판단하면된다.
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():  //삭제가 잘된경우
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //삭제가 잘못된경우 bad 보내줘
    }

    //트랜잭션(반드시 성공해야하는 일련의과정) -> 실패 -> 롤백!!!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){ //JSON파일을 받아오므로 requestbody를 써줘야한다.
        List<Article> createdList =articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}