package com.example.first.Controller;

import com.example.first.bean.User;
import com.example.first.prop.OracleBean;
import com.example.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private OracleBean oracleBean;
    @Autowired
    UserService userService;

    @Log("默认方法")
    @RequestMapping("/index")
    String index() {
        return oracleBean.getUrl()+"——"+oracleBean.getPort();
    }
    @RequestMapping("/selectUserById")
    String selectUserById(Model model) {
        User user = userService.selectUserById(1);
        model.addAttribute("user", user);
        System.out.println(user);
        // 配合thymeleaf，必须使用@Controller，不能使用@RestController
        return "userInfo";
    }
}