package com.vsp.project1.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

  @RequestMapping(method = GET)
  public String home(Model model) {
	  model.addAttribute("name", "vsp");
    return "home";
  }

}
