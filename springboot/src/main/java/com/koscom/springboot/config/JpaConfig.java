package com.koscom.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration //Spring에서 설정 클래스임을 알려주는 어노테이션
@EnableJpaAuditing //JPA Auditing 활성화 옵션

public class JpaConfig {
}
