package com.example.main.infrastructure.spi.adapter;

import com.example.main.domain.Id;
import com.example.main.domain.comment.Comment;
import com.example.main.infrastructure.spi.adapters.CommentPostAdapter;
import com.example.main.infrastructure.spi.entity.CommentEntity;
import com.example.main.infrastructure.spi.entity.EntityId;
import com.example.main.infrastructure.spi.entity.PostEntity;
import com.example.main.infrastructure.spi.repository.CommentRepository;
import com.example.main.infrastructure.spi.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.main.fixtures.CommentMockFactory.buildCommentMock;
import static com.example.main.fixtures.PostEntityMockFactory.buildPostEntityMock;
import static com.example.main.fixtures.PostMockFactory.POST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentPostAdapterTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentPostAdapter commentPostAdapter;

    @Test
    void should_comment_post_save_new_comment_when_post_exist() {
        // GIVEN
        PostEntity postEntity = buildPostEntityMock();
        Comment commentExpected = buildCommentMock();
        when(postRepository.findById(any())).thenReturn(Optional.of(postEntity));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(CommentEntity.buildFrom(postEntity, commentExpected));

        // WHEN
        Id newCommentId = commentPostAdapter.commentPost(POST_ID, commentExpected);

        // THEN
        assertThat(newCommentId.getValue()).isEqualTo(commentExpected.getId().getValue());
        verify(postRepository, atLeastOnce()).findById(any(EntityId.class));
        verify(commentRepository, atLeastOnce()).save(any(CommentEntity.class));
    }

    @Test
    void should_comment_post_not_save_new_comment_when_post_does_not_exist() {
        // GIVEN
        Comment comment = buildCommentMock();
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        // WHEN
        Id newCommentId = commentPostAdapter.commentPost(POST_ID, comment);

        // THEN
        assertThat(newCommentId).isNull();
        verify(postRepository, atLeastOnce()).findById(any(EntityId.class));
        verify(postRepository, never()).save(any(PostEntity.class));
    }
}
