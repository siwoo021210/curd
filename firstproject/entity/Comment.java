package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.net.ssl.SSLSession;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // DB가 자동으로 1씩증가
    private Long id; //  대표키

    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    @Column // 해당필드를 테이블 속성으로 매핑
    private String nickname; // 댓글을 단 사람

    @Column // 해당필드를 테이블 속성으로 매핑
    private String body; // 댓글 본문

    public static Comment createComment(CommentDto dto, Article article) {

        //예외 발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못 됐습니다");

        // 엔티티 생성및 반환
        return new Comment(dto.getId(),article,dto.getNickname(),dto.getBody());
    }

    public void patch(CommentDto dto) {

        //예외 발생
        if(this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력 돼습니다");

        //객체 갱신
        if(dto.getNickname() != null) // 수정할 닉네임이 있다면
            this.nickname =dto.getNickname(); // 내용 반영
        if(dto.getBody() != null) // 수정할 본문 데이터가 있다면
            this.body =dto.getBody(); // 내용 반영
    }


}
