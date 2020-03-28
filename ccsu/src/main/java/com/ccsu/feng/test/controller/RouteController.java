package com.ccsu.feng.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.smartcardio.ATR;

/**
 * @author admin
 * @create 2020-03-07-16:41
 */
@Controller
public class RouteController {

    @RequestMapping("/page/person/{type}")
    public String toPerson(@PathVariable("type") String type, RedirectAttributes attr){
        attr.addAttribute("type",type);
        attr.addAttribute("paramName","");
        attr.addAttribute("paramLikeName","");
        return "redirect:/page/person";
    }

    @RequestMapping("/page/san/withPicture/{type}")
    public String withPicture(@PathVariable("type") String type,RedirectAttributes attr){
        attr.addAttribute("type",type);
        return "redirect:/page/san/withPicture";
    }

    @RequestMapping("/page/weapon/{type}")
    public String toWeapon(@PathVariable("type") String type,RedirectAttributes attr){
        attr.addAttribute("type",type);
        attr.addAttribute("paramName","");
        attr.addAttribute("paramLikeName","");
        return "redirect:/page/weapon";
    }

    @RequestMapping("/page/place/{type}")
    public String place(@PathVariable("type") String type,RedirectAttributes attr){
        attr.addAttribute("type",type);
        attr.addAttribute("paramName","");
        attr.addAttribute("paramLikeName","");
        return "redirect:/page/place";
    }


    @RequestMapping("/page/xi/deeds/{type}")
    public String deeds(@PathVariable("type") String type,RedirectAttributes attr){
        System.out.println(type);
        attr.addAttribute("type",type);
        attr.addAttribute("paramName","");
        return "redirect:/page/xi/deeds";
    }

    @RequestMapping("/page/xi/deedsDetail/{id}")
    public String deedsDetail(@PathVariable("id") Integer id,RedirectAttributes attr){
        attr.addAttribute("detailId",id);
        return "redirect:/page/xi/deedsDetail";
    }


}
