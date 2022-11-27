package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // 댓글 목록 조회(함수 comments 사용)
    @GetMapping("/api/articles/{articleId}/comments") // Get방식으로 요청을 받아온다!
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){  //메서드 수행 , 커맨트의 리스트를 반환해오는것   , Dto형태로 컨트롤러가 요청을 받고 반환할때도 Dto 방식으로 반환한다!
        //웨이터가 하는 일(컨트롤러)을 두 가지로 분류한다.
        //서비스에게 위임 , 서비스를 호출
        List<CommentDto> dtos = commentService.comments(articleId); // articleId 값을 입력값으로 해서 Comment값을 Dto로 바꾼 것의 묶음을 반환할 것이다.

        //결과 응답 , 무조건 잘 반환이 된다는 가정하에 리턴값 (rest컨트롤러에서 반환하는것이다 dto로)
       return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성 (함수 create 사용)
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto){
        // 서비스에게 위임 (위임을 해서 값을 가져온다)
        CommentDto createDto = commentService.create(articleId,dto); //articleId = comment 소속되어있는 게시글 id값, dto는 JSON데이터가 담겨져있는것 , CommentDto를 반환 했으면 좋겠다~~그 변수의 이름은 createDto
        //결과 응답( 가져온 값에 대해 결과 응답), 잘 반환이 된다는 가정하에 리턴값!
       return ResponseEntity.status(HttpStatus.OK).body(createDto); //ResponseEntity는  서비스에서 컨트롤러으로 보내는 값을말한다!
    }

    // 댓글 수정 (함수 update사용)
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,@RequestBody CommentDto dto) {
        // 서비스에게 위임
        CommentDto updatedDto = commentService.update(id, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto); //만약 ok가 아니라면 update 함수 내부에서 에외 발생으로 처리할것이다 위에 create 함수처럼!
    }
    // 댓글 삭제 (함수 delete 사용)
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id /* 제이슨을 받아오지 않으므로 이건 없어도됌@RequestBody CommentDto dto*/) {
        // 서비스에게 위임
        CommentDto deletedDto = commentService.delete(id);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }


}
