package com.nowcoder.controller;

import com.nowcoder.model.*;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.SensitiveService;

import com.nowcoder.service.UserService;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuetionController {

    private static final Logger logger = LoggerFactory.getLogger(QuetionController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @RequestMapping(path = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content
    ) {

        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            if (hostHolder.getUser() != null) {
                question.setUserId(hostHolder.getUser().getId());
            } else {
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            if (questionService.addQuestion(question) > 0) {
                return WendaUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");

    }

    @RequestMapping(path = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(@PathVariable("qid") int qid,
                                 Model model) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        List<ViewObject> vos = new ArrayList<>();
        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);

        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        return "detail";

    }
}
