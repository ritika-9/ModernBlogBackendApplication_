package com.ritika.blog.controllers;

import com.ritika.blog.payloads.ApiResponse;
import com.ritika.blog.payloads.CommentDto;
import com.ritika.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //create the comment
    @PostMapping("post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable("postId") Integer postId) {
        CommentDto com=this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(com, HttpStatus.CREATED);
    }

    //delete the comment by commentId
    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
    }

//    //update the comment
//    @PutMapping("comment/{commentId}")
//    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,@PathVariable("commentId") Integer commentId){
//       CommentDto com=this.commentService.updateComment(commentId,commentDto);
//        return new ResponseEntity<CommentDto>(com,HttpStatus.OK);
//    }
//
//    //get comments by userId
//    @GetMapping("user/{userId}/comments")
//    public ResponseEntity<List<CommentDto>> getCommentsOfUser(@PathVariable("userId") Integer userId) {
//        List<CommentDto> com=this.commentService.getCommentsByUserId(userId);
//        return new ResponseEntity<>(com,HttpStatus.OK);
//    }
//
//    //get comments by postId
//    @GetMapping("post/{postId}/comments")
//    public ResponseEntity<List<CommentDto>> getCommentsOfPost(@PathVariable("postId") Integer postId) {
//        List<CommentDto> com=this.commentService.getCommentsByPostId(postId);
//        return new ResponseEntity<>(com,HttpStatus.OK);
//    }
}
