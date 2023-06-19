package com.ll.mbooks.app.postTag.entity;

import com.ll.mbooks.app.base.entity.BaseEntity;
import com.ll.mbooks.app.member.entity.Member;
import com.ll.mbooks.app.post.entity.Post;
import com.ll.mbooks.app.postKeyword.entity.PostKeyword;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;

import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostTag extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = CASCADE)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private PostKeyword postKeyword;
}
