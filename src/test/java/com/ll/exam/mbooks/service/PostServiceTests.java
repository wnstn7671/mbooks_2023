package com.ll.exam.mbooks.service;

import com.ll.mbooks.app.member.entity.Member;
import com.ll.mbooks.app.member.repository.MemberRepository;
import com.ll.mbooks.app.post.entity.Post;
import com.ll.mbooks.app.post.service.PostService;
import com.ll.mbooks.app.postTag.entity.PostTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostServiceTests {
    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("글 작성")
    void t1() {
        Member author = memberRepository.findByUsername("user1").get();

        Post post = postService.write(author, "제목", "내용", "내용", "#IT #IT 특집기사 #백엔드");

        assertThat(post).isNotNull();
        assertThat(post.getSubject()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        assertThat(post.getContentHtml()).isEqualTo("내용");

        List<PostTag> postTags = postService.getPostTags(post);

        postTags.forEach(postTag ->
                assertThat(postTag.getPostKeyword().getContent()).containsAnyOf("IT", "IT 특집기사", "백엔드"));
        assertThat(post).isNotNull();
    }

    @Test
    @DisplayName("글 수정")
    void t2() {
        Post post = postService.findById(1).get();
        postService.modify(post, "제목 new", "내용 new", "내용 new", "#IT #일반기사 #프론트엔드");

        assertThat(post).isNotNull();
        assertThat(post.getSubject()).isEqualTo("제목 new");
        assertThat(post.getContent()).isEqualTo("내용 new");
        assertThat(post.getContentHtml()).isEqualTo("내용 new");

        List<PostTag> postTags = postService.getPostTags(post);

        postTags.forEach(postTag ->
                assertThat(postTag.getPostKeyword().getContent()).containsAnyOf("IT", "일반기사", "프론트엔드"));
        assertThat(post).isNotNull();
    }
}
