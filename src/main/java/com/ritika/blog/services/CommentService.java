package com.ritika.blog.services;

import com.ritika.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);
    // CommentDto updateComment(Integer commentId, CommentDto commentDto);
   // List<CommentDto> getCommentsByPostId(Integer postId);
   // List<CommentDto> getCommentsByUserId(Integer postId);

}
