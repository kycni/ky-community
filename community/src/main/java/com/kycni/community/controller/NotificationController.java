package com.kycni.community.controller;

import com.kycni.community.dto.NotificationDTO;
import com.kycni.community.enums.NotificationTypeEnum;
import com.kycni.community.model.User;
import com.kycni.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kycni
 * @date 2022/1/28 21:41
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        
        NotificationDTO notificationDTO = notificationService.read(id, user);
        
        if (NotificationTypeEnum.RELY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.RELY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}