package com.koscom.springboot.service;

import com.koscom.springboot.domain.posts.Posts;
import com.koscom.springboot.domain.posts.PostsRepository;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import com.koscom.springboot.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final로 선언된 필드들은 생성자 항목으로 자동 포함시켜서 생성자 생성
@Service // spring bin 등록 & Service 클래스 선언

public class PostsService {

    private final PostsRepository postsRepository;

    //등록
    @Transactional
    public long save(PostsSaveRequestDto requestsDto){
        Posts posts = postsRepository.save(requestsDto.toEntity());
        return posts.getId();
    }

    //수정
    @Transactional
    public Long update (Long id, PostsUpdateRequestDto dto){
        //db에서 가져온 값을 jpa 내부에서 캐시함 (1차캐시)
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자는 없습니다. id="+id));

        // dirty checking
        // 처음에 가져온 entity와 transction이 끝나는 시점의 값이 다르면 자동 update
        entity.update(dto.getTitle(),dto.getContent());

        return entity.getId();
    }

    //조회
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자는 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }
}
