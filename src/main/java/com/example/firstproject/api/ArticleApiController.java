package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //log 를 사용할 수있게해주는 어노테이션이다!
@RestController //RestApi 용 컨트롤러! 데이터(보통 JSON형태)를반환한다
public class ArticleApiController {

    @Autowired  //객체를 연결해주는 어노테이션 연결해준다!자유롭게 객체를 사용할 수있 게해준다, DI = 외부에서 가져온다는 뜻!(data.sql)
    private ArticleRepository articleRepository; //객체

    //GET메서드
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }


    //단일 article 가져오기
    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //POST 새로 생성하는 요청!
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){ //RestAPI에서 JSON으로 던질때는 @RequestBody가 있어야 받아진다!
        Article article = dto.toEntity();
        return articleRepository.save(article);

    }
    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){ //상태코드를 같이 리턴하기위해 ResponseEntity<Article>로 바꿔준다.

        //1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id,article.toString()); //article은 객체 이므로 toString으로 캐스팅해준다!
        //2. 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리(대상이 없거나,id가 다른경우)
        if(target ==null || id != article.getId()){
            //400 (잘못된 요청 응답!)
            log.info("잘못된요청! id:{}, article:{}",id,article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); //BAD_REQUEST는 400번 에러를 반환하는 상태이다!
        }
        //4. 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }


    //DELETE

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //잘못된 요청 처리
        if (target ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 대상 삭제
        articleRepository.delete(target);
        // 데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
