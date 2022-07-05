package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Jpa와 연동한 테스트!
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;  //본 메서드에 있는거  테스트로 당겨오기!


    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* Case 1 : 4번 게시글의 모든 댓글 조회*/
        {
            // 입력 데이터 준비
            Long articleId = 4L; //부모의 ID값 PK
            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId); //commentRepository를 이용해 articleId를 통해서 comments를 Comment의 묶음으로 리턴할 것이다!
            // 예상하기
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅"); //article은  FK이다 따라서 부모의 4L을 뜻함!
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");
            List<Comment> expected = Arrays.asList(a, b, c);      //List형태로 실제수행을 하기때문에 예상도 맞춰주기 위해 리스트로 바꾼다!
            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }
    }
    @Test
    void findByArticleId_1번_게시글의_모든_댓글() {
        /* Case 2: 1번 게시글의 모든 댓글 조회(Comment table에서 article_id(FK)가 1인게 없음: 따라서 비어있는 값이 나올것임! */
        {
            // 입력 데이터 준비
            Long articleId = 1L;    //부모의 아이디값(articleid)! 게시글의 PK
            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId); //commentRepository를 이용해 articleId를 통해서 comments를 Comment의 묶음으로 리턴할 것이다!
            // 예상하기
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();      //실제로 부모키 1번이 아무것도 없으므로 실제로 비어있는 값이 출력할것이다
            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 게시글의 댓글이 없음!"); //예상한결과와 실제 수행한 결과 값을 비교한다!
        } //반환값이 null값을 반환한다!
    }
        /* 여러가지 테스트 케이스 과제!(여러가지 경우를 코드로 짜볼것--> 테스트케이스 */
    @Test
    void findByArticleId_9번_게시글의_모든_댓글() {
        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9L;
            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId); //aritcleId Fk를 이용해 부모 PK에 맞는 번호의 댓글 묶음을 가져온다!
            // 예상 하기
            Article article = new Article(9L, null, null);
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(),"9번 게시글은 없습니다!");
        }
    }

    @Test
    void findByArticleId_9999번_게시글의_모든_댓글() {
        /* Case 4: 9999번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9999L;
            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId); //aritcleId Fk를 이용해 부모 PK에 맞는 번호의 댓글 묶음을 가져온다!
            // 예상 하기
            Article article = new Article(9999L, null, null);
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(),"9999번 게시글은 없습니다!");
        }
    }
    @Test
    void findByArticleId_마이너스1번_게시글의_모든_댓글() {
        /* Case 4: -1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = -1L;
            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId); //aritcleId Fk를 이용해 부모 PK에 맞는 번호의 댓글 묶음을 가져온다!
            // 예상 하기
            Article article = new Article(-1L, null, null);
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(),"-1번 게시글은 없습니다!");
        }
    }

    /////////닉네임으로 모든 댓글 조회하기!/////////
    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            //입력 데이터 준비
            String nickname = "Park";
            // 실제수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 예상하기
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ") /*게시글의 ID(PK)적용 부모 키, article 항목 적어주기!*/, nickname, "굳 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "치킨"); //id값을 바꾸면 예상하는값과 실제수행하는 결과값이 다르다고 오류가 나타나는걸 볼 수 있다.!
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c); //조회할때사용하는것!리스트로 보여주기 및 가져오기
            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }
    }
        @Test
        void findByNickname_kim() {
            /* Case 2: "Kim"의 모든 댓글 조회 */
            {
                //입력 데이터 준비
                String nickname = "Kim";
                // 실제수행
                List<Comment> comments = commentRepository.findByNickname(nickname);
                // 예상하기
                Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ") /*게시글의 ID(PK)적용 부모 키, article 항목 적어주기!*/, nickname, "아이 엠 샘");
                Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브"); //id값을 바꾸면 예상하는값과 실제수행하는 결과값이 다르다고 오류가 나타나는걸 볼 수 있다.!
                Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");
                List<Comment> expected = Arrays.asList(a, b, c); //조회할때사용하는것!리스트로 보여주기 및 가져오기
                // 검증
                assertEquals(expected.toString(), comments.toString(), "Kim의 모든 댓글을 출력!");
            }
        }

        @Test
        void findByNickname_null() {  // 없어 닉네임 그냥 데이터에 할당이 되어있지 않음 nickname 이라는것이 , 얜 초기화 자체가 없어
            /* Case 3: null의 모든 댓글 조회 */
            {
                // 입력 데이터 준비
                //String nickname;  오...안써도된다 null이라서 선언만한건데 garbage 데이터가 되버림!
                // 실제수행
                List<Comment> comments = commentRepository.findByNickname(null);
                // 예상하기
                List<Comment> expected = Arrays.asList();
                // 검증
                assertEquals(expected, comments,"null이 없음");
            }
        }

        @Test
        void findByNickname_빈공간(){  //nickname 변수는 있지만 들어가있는게 없음 초기화하면 빈공간은 있음
        /* Case 4: ""의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "";
            // 실제수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 예상하기
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected, comments,"null이 없음");  //toString 안해도되는이유 : 문자열로 반환을 할게 없어서 null로 받아들인다고 생각!

        }
    }

        @Test
        void findByNickname_i(){
        /* Case 5: "i"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "i";
            // 실제수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 예상하기
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected, comments,"i가 없음 ");

        }

    }
}