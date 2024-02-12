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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentsService;
    @GetMapping("/articles/new")
    public String newArticleFrom(){
        return  "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticles(ArticleForm form){
        log.info(form.toString());

        Article article =form.toEntity();
        log.info(article.toString());

        Article saved =articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/"+saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id =" + id);

        // 1. id를 조회해 데이터 가져오기
        Article articleEntity =articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentsService.comments(id);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        model.addAttribute("commentsDtos",commentsDtos);

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){

        // 1. 모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델이 데이터 등록하기
        model.addAttribute("articleList",articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id,Model model){

        //수정할 데이터 가졍오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);

        return "/articles/edit";
    }
    @PostMapping("/article/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        // 1. DTO 데이터 받아오기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. 엔티티 DB에 저장하기
        // 2-1. DB 에서 기존 데이터 가져오기
        Article target =articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2 기존 데이터 값을 갱신하기
        if(target != null){
            articleRepository.save(articleEntity);
        }

        // 3. 수정결과 페이지로 리다이렉트 하기
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다");
        // 1. 삭제할 대상 가져오기
        Article target =articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됬습니다!");
        }

        // 3. 결과페이지로 리다이렉트하기
        return "redirect:/articles";
    }


}
