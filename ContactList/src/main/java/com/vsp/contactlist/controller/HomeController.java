package com.vsp.contactlist.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vsp.contactlist.db.ContactRepository;
import com.vsp.contactlist.db.Contact;

@Controller
//@RequestMapping("home")
public class HomeController {
	@Autowired
	ContactRepository clr;
	
  @RequestMapping(value = "home", method=GET)
  public String home(Model model) {
	  model.addAttribute("name", "vsp");
	  Contact c = new Contact("vsp","1234 Sandy Springs","678-123-3232","no Comments");
	  clr.addContact(c); 
	  
    return "home";
  }

  // For static pages - THis is NOT needed unless you need control on specific URLs
  /*@RequestMapping(value = "static", method = GET)
  public String redirect() {
    
     return "redirect:/resources/app.html";
  }*/
}
