package com.koscom.springboot.web;

import com.koscom.springboot.config.auth.dto.SessionUser;
import com.koscom.springboot.config.auth.login.LoginUser;
import com.koscom.springboot.service.PostsService;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/") //localhost::8080 쳤을때 나오는 장소
    public  String index(Model model, @LoginUser SessionUser user){
        postsService.save(new PostsSaveRequestDto("test","test","test"));
        model.addAttribute("posts", postsService.findAllDesc());

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user.getName());
        }

        return "index"; //template 밑에 있는 index.mustache를 자동으로 찾음
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
    
    //localhost:8080/posts/update/1 로 조회하면 1번글의 수정화면으로 이동하게 됨.
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
