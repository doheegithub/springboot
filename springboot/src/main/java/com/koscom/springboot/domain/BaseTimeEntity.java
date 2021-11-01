package com.koscom.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// Auditing : createAt/updateAt/createUser/UpdateUser 자동으로 관리해주는 기능
@Getter
@MappedSuperclass // (1) JPA에 컬럼을 만들어야 하는 의미
@EntityListeners(AuditingEntityListener.class) // (2)
public abstract class BaseTimeEntity {

    @CreatedDate // (3)등록시간
    private LocalDateTime createdDate;

    @LastModifiedDate // (4)수정시간
    private LocalDateTime modifiedDate;

}