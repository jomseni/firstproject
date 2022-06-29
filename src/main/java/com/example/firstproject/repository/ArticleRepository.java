package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> {  //관리대상 entity와 관리대상 entity 대표값의 타입을 적는다 <> 사이에
                                //Article을 CRUD 기본 동작을 추가 코드 구현없이 확장 받아서 사용할 수 있게된다.

    @Override
    ArrayList<Article> findAll();//ArrayList를 반환하겠다.
}
//인터페이스 사용이유 : JPA 과정에서 여러가지 함수를 사용하기위함! ex save(), findAll(),,등등