package sy.screat_img.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.util.List;
import java.util.Optional;

import sy.screat_img.entity.Crpimgs;
import sy.screat_img.repository.CrpimgsRepository;
import sy.screat_img.entity.User;


@Controller
public class ImgController {

    @Autowired
    private CrpimgsRepository crpimgsRepository;

    // 이미지 업로드 처리
    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<String> uploadImage(
            @RequestBody Crpimgs crpimgs,
            HttpSession session) {


        User user = (User) session.getAttribute("user");
        System.out.println(crpimgs.getImgname());
        if ( user == null) {
            System.out.println("세션에 사용자 정보가 없습니다");
            return ResponseEntity.status(400).body("세션에 사용자 정보가 없습니다.");
        }

        // User 객체 설정
        crpimgs.setUser(user);
        crpimgsRepository.save(crpimgs);


        return ResponseEntity.ok("이미지 업로드 성공");

    }

    //클라이언트로부터 이미지ID를 받아와 해당하는 이미지 스트링 반환해주기
    @GetMapping("/img")
    public ResponseEntity<String> sendImage(@RequestParam("imageId") Long imgId, HttpSession session) throws FileNotFoundException {
        User user = (User) session.getAttribute("user");
        Long sessionUserId = user.getId();

        // imgId로 Crpimgs 조회
        Optional<Crpimgs> crpimg = crpimgsRepository.findById(imgId);

        // 이미지의 userid와 세션의 userid 비교
        if (!crpimg.get().getUser().getId().equals(sessionUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 권한이 없는 경우
        }
        return ResponseEntity.status(HttpStatus.OK).body(crpimg.get().getImg());
    }

    //main 페이지에 유저가 올린 이미지 목록 보내기 및 뷰 렌더링하기
    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        // 세션에서 userId를 가져옴
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
        if (userId == null) {
            return "redirect:/login";  // 세션에 userId가 없으면 로그인 페이지로 리다이렉트
        }

        // userId에 해당하는 Crpimgs의 모든 imgname과 id를 가져옴
        List<Crpimgs> images = crpimgsRepository.findByUserId(userId);

        // 모델에 images 데이터를 담아서 main.html로 전달
        model.addAttribute("images", images);

        return "main";  // main.html을 반환
    }

}
