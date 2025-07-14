package com.ritika.blog.controllers;

import com.ritika.blog.config.AppConstant;
import com.ritika.blog.entities.Post;
import com.ritika.blog.payloads.ApiResponse;
import com.ritika.blog.payloads.PostDto;
import com.ritika.blog.payloads.PostResponse;
import com.ritika.blog.services.FileService;
import com.ritika.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create post

    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
        PostDto post=this.postService.createPost(postDto, categoryId, userId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //post by user
    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") Integer userId) {
        List<PostDto> posts=this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //post by category
    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<PostDto> posts=this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //get all posts
    @GetMapping("posts")
    public ResponseEntity<PostResponse> getAllThePosts(@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required=false)Integer pageNumber,
                                                        @RequestParam(value="pageSize",defaultValue=AppConstant.PAGE_SIZE,required = false)Integer pageSize,
                                                       @RequestParam(value="sortBy",defaultValue=AppConstant.SORT_BY,required=false) String sortBy,
                                                       @RequestParam(value="sortDir",defaultValue=AppConstant.SORT_DIR,required=false) String sortDir) {
        PostResponse posts=this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir );
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    //get post by its id
    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
        PostDto post=this.postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("post/{postId}")
    public ApiResponse deletePost(@PathVariable("postId") Integer postId) {
        this.postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully!",true);
    }

    //update post
    @PutMapping("post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
        PostDto post=this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(post,HttpStatus.OK);
    }

    //search post by title
    @GetMapping("post/search/{title}")
    public ResponseEntity<List<PostDto>> getPostByTitle(@PathVariable("title") String title) {
        List<PostDto> posts=this.postService.searchPosts(title);
        return new ResponseEntity<>(posts,HttpStatus.OK);

    }

    //upload file
    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile file,
                                                         @PathVariable("postId") Integer postId) throws IOException {
        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadFile(path,file);
        postDto.setImageName(fileName);
        PostDto updatedPost=this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);

    }

    //serve file
    @GetMapping("post/images/{imageName:.+}")
    public void downloadFile(
            @PathVariable("imageName") String fileName,
            HttpServletResponse response
    ) throws IOException {
        try {
            InputStream resource = this.fileService.getResource(path, fileName);
            if (resource == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("File not found: " + fileName);
                return;
            }

            // Detect MIME type
            String fullPath = path + File.separator + fileName;
            String mimeType = Files.probeContentType(Paths.get(fullPath));

            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // fallback
            }

            response.setContentType(mimeType);
            StreamUtils.copy(resource, response.getOutputStream());
            resource.close();
        } catch (FileNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("File not found: " + fileName);
        }
    }








}
