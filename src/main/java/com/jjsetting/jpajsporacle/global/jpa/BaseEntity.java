package com.jjsetting.jpajsporacle.global.jpa;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //@CreatedDate,@LastModifiedDate를 사용하기 위해 필요
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter
@ToString
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;
}
