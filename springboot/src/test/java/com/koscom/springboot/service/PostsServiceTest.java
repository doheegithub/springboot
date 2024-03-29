package com.koscom.springboot.service;

import com.koscom.springboot.domain.posts.Posts;
import com.koscom.springboot.domain.posts.PostsRepository;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import com.koscom.springboot.web.dto.posts.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class PostsServiceTest {

    @Autowired
    PostsRepository postRepository;

    @Autowired
    PostsService postService;

    @AfterEach
    void tearDown() {
        // postRepository.deleteAllInBatch(); //delete from table
        postRepository.deleteAll(); // jpa 상태를 보고 자식 테이블까지 삭제할지 결정
    }

    @Test
    void postsService를통해_저장이된다(){
        String title = "test";
        String content = "test2";

        PostsSaveRequestDto dto =PostsSaveRequestDto.builder()
                .title("test")
                .content("test2")
                .build();

        postService.save(dto);

        List<Posts> result = postRepository.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void postsService를통해_수정이된다(){
        String title = "test";
        String content = "test2";

        //미리 저장된 값을 하나 생성
        Posts save = postRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());

        System.out.println("save.title:::"+save.getTitle());
        System.out.println("save.content:::"+save.getContent());

        PostsUpdateRequestDto dto =PostsUpdateRequestDto.builder()
                .title("test")
                .content("test2")
                .build();

        postService.update(save.getId(),dto);

        List<Posts> result = postRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void posts룰_수정하면_수정시간이_갱신된다(){

        //미리 저장된 값을 하나 생성
        Posts save = postRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());

        LocalDateTime beforeTime = save.getModifiedDate();
        System.out.println("beforeTime:::"+beforeTime);


        PostsUpdateRequestDto dto =PostsUpdateRequestDto.builder()
                .title("test")
                .content("test2")
                .build();

        postService.update(save.getId(),dto);

        List<Posts> result = postRepository.findAll();
        LocalDateTime newTime = result.get(0).getModifiedDate();

        System.out.println("newTime=="+newTime);
        assertThat(newTime).isAfter(beforeTime);

    }

    @Test
    void postsService를통해_삭제된다(){

        //미리 저장된 값을 하나 생성
        Posts save = postRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());
        
        //해당 save id로 삭제
        postService.delete(save.getId());

        //삭제 후 조회시 아무것도 없어야 함
        List<Posts> result = postRepository.findAll();
        assertThat(result).hasSize(0);
    }

    @Test
    void id가_일치해야만_삭제가된다(){

        //미리 저장된 값을 하나 생성
        Posts save = postRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());

        //해당 save id로 삭제
        postService.delete(99999l);

        //삭제 후 조회시 아무것도 없어야 함
        List<Posts> result = postRepository.findAll();
        assertThat(result).hasSize(0);
    }

    @Test
    void id가_일치해야만_삭제가된다_2(){

        //미리 저장된 값을 하나 생성
        Posts save = postRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());

        Posts deleteTarget = postRepository.save(Posts.builder()
                .title("1")
                .content("2")
                .build());


        //해당 save id로 삭제, save를 deleteTarget으로 바꾸면 정상적으로 테스트 돌아감
        postService.delete(save.getId());

        //삭제 후 조회시 
        Optional<Posts> byId = postRepository.findById(deleteTarget.getId());

        //Optional << null을 대체하기 위해 나옴. null일수도 있고, 아닐수도 있고.
        //Posts << null이 아닌 무조건 있는 값이 넘어오는것!

        assertThat(byId.isPresent()).isFalse();
        //isPresent()는 값이 있는지를 체크학는 것, 값이 있으면 True로 떨어짐
        assertThat(byId.get().getId()).isEqualTo(deleteTarget.getId());

    }


}
