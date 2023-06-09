package com.example.main.fixtures;

import com.example.main.domain.Id;
import com.example.main.domain.comment.Comment;
import com.example.main.domain.post.Post;
import com.example.main.infrastructure.api.dto.CreatePostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static com.example.main.fixtures.CommentMockFactory.buildCommentMock;

public class PostMockFactory {
    private PostMockFactory() {}

    public static final Id POST_ID = new Id();
    public static final String POST_TITLE = "My great Post #1";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Post buildPostMock(Set<Comment> comments) {
        return new Post(POST_ID, POST_TITLE, comments);
    }

    public static Post buildPostMock() {
        Comment comment = buildCommentMock(POST_ID);
        return buildPostMock(Set.of(comment));
    }

    public static Post buildPostWithoutCommentMock() {
        return buildPostMock(Set.of());
    }

    public static List<Post> buildPostsMock() {
        return List.of(buildPostMock());
    }

    public static String buildFormattedCreatePostRequest() throws JsonProcessingException {
        CreatePostRequest createPostRequest = buildCreatePostRequest();
        return objectMapper.writeValueAsString(createPostRequest);
    }

    private static CreatePostRequest buildCreatePostRequest() {
        return new CreatePostRequest(POST_TITLE);
    }
}
