package com.apap.tutorial8.controller;

import com.apap.tutorial8.model.PasswordModela;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    String pattern = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,}";
    String msg2 = "Password Anda harus mengandung angka, huruf kecil, dan berisi minimal 8 karakter";
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private ModelAndView addUserSubmit(@ModelAttribute UserRoleModel user, Model model, RedirectAttributes redir){
        if (user.getPassword().matches(pattern)){
            userService.addUser(user);
        } else {
            redir.addFlashAttribute("msg2", msg2);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @RequestMapping(value="/passwordSubmit",method=RequestMethod.POST)
    public ModelAndView updatePasswordSubmit(@ModelAttribute PasswordModela pass, Model model, RedirectAttributes redir) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserRoleModel user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String message = "";
        String message2 = "";
        if (pass.getConPassword().equals(pass.getNewPassword())) {
            if (passwordEncoder.matches(pass.getOldPassword(), user.getPassword())) {
                if (pass.getNewPassword().matches(pattern)){
                    userService.changePassword(user, pass.getNewPassword());
                    message = "Password berhasil diubah";
                } else {
                    message2 = msg2;
                }
            }
            else {
                message = "Password lama anda salah";
            }

        }
        else {
            message = "Password baru tidak sesuai";
        }


        ModelAndView modelAndView = new ModelAndView("redirect:/update-password");
        redir.addFlashAttribute("msg2", msg2);
        redir.addFlashAttribute("msg",message);
        return modelAndView;
    }
}
