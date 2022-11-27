package com.example.firstproject.service;


import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    //comment DB를 필요로도 하지만 article의 DB도 필요로 하기때문에 이렇게 두 가지를 모두 당겨온다!


    //댓글 조회 하기!!!
    public List<CommentDto> comments(Long articleId) { //comments 메서드 생성
        //comments 메서드의 하는일!
        //조회 : 댓글목록(commentRepository를 이용해 가져온다) , 서비스가 데이터를 가져올때 리퍼지토리한테 시킨다
// 방법1       List<Comment> comments = commentRepository.findByArticleId(articleId);  //articleId에 맞는 모든 comment를 리스트 형태(묶음) 이름은 comments 타입은 Comment으로 반환한다
//
//      //변환 : 엔티티 -> DTO형태로 반환 해야하므로( List<CommentDto> 이부분)
//        List<CommentDto> dtos = new ArrayList<CommentDto>();  //commentDto를 담는 ArrayList를 하나 만들어준다. List타입의 이름은 dtos
//        for (int i = 0; i<comments.size(); i++){ //비어있는 dtos(ArrayList에) 하나하나 댓글을 변환해서 넣어주면 된다 배열속에!
//            Comment c = comments.get(i);  //comments에서 하나씩가져와서 c에 저장한다
//            CommentDto dto = CommentDto.createCommentDto(c); //Dto로 변환해서 넣는것, (생성메서드) 클래스메서드를 호출하는 방식으로 만든다.
//            dtos.add(dto);
//
//        }
        //반환 :
        //결과적으로 위에   List<CommentDto> dtos = new ArrayList<CommentDto>(); 여기 리스트에 변환이 되어 저장이 되어진다! 그것을 반환한다!!
//        return dtos;  방법1
        return commentRepository.findByArticleId(articleId)   //방법 2 . 부모의 ID에맞는 댓글을 찾아 그 값들을 반환한다.
                .stream()  // 댓글을 하나씩 가져옴
                .map(comment -> CommentDto.createCommentDto(comment))   //꺼내온 댓글 각각 을 생성메서드crate~를 이용하여 입력으로 들어온 comment 를 넣고 CommentDto로 변환한다
                .collect(Collectors.toList()); //각각을 리스트로 묶어준다!!!!!!!! stream 에 대한 문법도 꼭 알아두기!
    }

    // 댓글 생성하기!!!댓글 생성이므로 (DB에 변환가있으므로) 트랜잭션 처리해야함
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
//        log.info("입력값 => {}: ",articleId);
//        log.info("입력값 => {}: ",dto);
        // 게시글 조회 및 예외 발생, article이 있으면 순차적으로 수행되지만 없다면 예외가 발생해서 그 다음 아래 순서들이 수행 되지 않는 것이다!
        Article article=articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다!"));// orElseThrow --> 없다면 예외를 발생 시킨다
        // 댓글 엔티티 생성 (Comment 엔티티에 createComment라는  생성 메서드가 없으므로 메서드를 만들어주어야한다!)
           Comment comment = Comment.createComment(dto,article); //Comment클래스에서 createComment수행(dto와 article)을 던졌을때 comment를 만들었으면 좋겠어!
        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment); //comment 엔티티를 저장한다 commentRepository를 통해서
        //DTO로 변경하여 반환
            return CommentDto.createCommentDto(created);

            //반환되는 값 로그 찍기(이렇게 적어도되는데 이것이 다른 부분에서 반복되는 코드이기때문에 AOP 사용을 위해 지운다(주석처리)
//        CommentDto createdDto = CommentDto.createCommentDto(created);
//        log.info("반환 값 => {}", createdDto);
//        return createdDto;
    }

    @Transactional //롤백 처리!!! 데이터를 건드리므로!
    public CommentDto update(Long id, CommentDto dto) {
        //댓글 조회 및 예외 발생
       Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다!"));

        //댓글 수정
        target.patch(dto); //입력값을 dto로 넣어서 수정한다! dto는 요청이 되는 JSON 데이터!
        // DB로 갱신
        Comment updated = commentRepository.save(target);
        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional //DB를 건드므로 필요하다 트랜잭션이!(롤백)
    public CommentDto delete(Long id) {
        // 댓글 조회(및 예외 발생)
        Comment target= commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다!"));
        // 댓글 DB에서 삭제
        commentRepository.delete(target);
        // 삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}
