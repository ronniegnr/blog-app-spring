package com.ronniegnr.app.controller.client;

import com.ronniegnr.app.domain.entity.Post;
import com.ronniegnr.app.domain.entity.Tag;
import com.ronniegnr.app.service.PostService;
import com.ronniegnr.app.service.TagService;
import com.ronniegnr.app.wrapper.PageWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "")
public class BlogController {

    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    private static final String VIEW_PATH = "client/blog/";
    private static final String BLOG_PAGE = VIEW_PATH + "blog";
    private static final String POST_PAGE = VIEW_PATH + "post";


    @ModelAttribute(value = "tags")
    public List<Tag> TagsWithPostCount() {
        return tagService.getAllTag();
    }

    @RequestMapping(value = "blog")
    public String blog() {
        return "redirect:/blog/1";
    }

    @RequestMapping(value = "blog/{pageNo}")
    public String blog1(@PathVariable(value = "pageNo") int pageNo, Model model) {
        PageWrapper<Post> pagedPosts = new PageWrapper<Post>(postService.getPagedPost(Post.Status.ACTIVE, pageNo), "/blog/");
        model.addAttribute("pagedPosts", pagedPosts);
        return BLOG_PAGE;
    }

    @RequestMapping(value = "blog/{tagValue}/{pageNo}")
    public String blog2(@PathVariable(value = "tagValue") String tagValue, @PathVariable(value = "pageNo") int pageNo, Model model) {
        PageWrapper<Post> pagedPosts = new PageWrapper<Post>(postService.getPagedPost(tagValue, Post.Status.ACTIVE, pageNo), "/blog/");
        model.addAttribute("pagedPosts", pagedPosts);
        return BLOG_PAGE;
    }

    @RequestMapping(value = "blog/post/{postId}")
    public String post(@PathVariable(value = "postId") int postId, Model model) {
        model.addAttribute("post", postService.getPost(postId));
        return POST_PAGE;
    }

}
