package sy.screat_img.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sy.screat_img.entity.User;
import sy.screat_img.service.UserService;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String showloginPage() {
        return "loginform"; // Thymeleaf는 templates 폴더에서 signUpform.html 파일을 렌더링
    }
    @GetMapping("/loginform")
    public String showloginPage2() {
        return "loginform";
    }
    @GetMapping("/signUp")
    public String showSignUpPage() {
        return "signUpform";
    }

    @PostMapping("/user")
    public String registerCorporate(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.register(user);
            redirectAttributes.addFlashAttribute("message", "회원가입 성공!");
            return "redirect:/loginform";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "회원가입 실패");
            return "redirect:/signUpform";
        }
    }
}
