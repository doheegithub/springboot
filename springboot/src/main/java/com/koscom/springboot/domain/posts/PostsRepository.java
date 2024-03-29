package com.koscom.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// <Posts,Long>==> <대상이 되는 entity, pk타입>
// JpaRepository 상속받은 인터페이스는 기본 crud가 모두 자동 구현 된다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
    
    //SQL이 아니고 JPQL(JPA SQL) 뭅법
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
