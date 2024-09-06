package com.sks.sksschool.controller;

import com.sks.sksschool.model.Contact;
import com.sks.sksschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class ContactController {

    // If you see above, we defined @Slf4j that is given by lombok for logging. So no need to create
    //logger like below. By default it gives log variable and some methods to use.
    //Logger log = Logger.getLogger(ContactController.class.getName());

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String displayContactPage(@ModelAttribute("msgSaved") String msgSaved,
                                     Model model){
        model.addAttribute("contact",new Contact());
        return "contact.html";
    }

    /*@RequestMapping(value = {"/saveMsg"},method = RequestMethod.POST)
    public ModelAndView saveMessage(@RequestParam String name,@RequestParam String mobileNum,
                                    @RequestParam String email,@RequestParam String subject,
                                    @RequestParam String message){
        log.info("Name is "+name);
        log.info("Mobile Number is "+mobileNum);
        log.info("Email is "+email);
        log.info("Subject is "+subject);
        log.info("Message is "+message);
        return new ModelAndView("redirect:/contact");

    }*/

    /*
    The above method is also same but lengthy. Here we have created a pojo with the exact name of the
    input fields. In that way, Spring will automatically map the value with the Pojo fields
    and we have used @PostMapping instead of @RequestMapping
     */

    /* Here we are creating contact pojo and adding that in Model using @ModelAttribute and
    @valid means validation is required. So it'll check the Pojo validation defined., */

    @PostMapping("/saveMsg")
    public ModelAndView saveMessage(@Valid @ModelAttribute("contact") Contact contact,
                                    Errors errors){

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("redirect:/contact");

        if(errors.hasErrors()){
            log.error("Contact form validation failed due to {}", errors.toString());
            modelAndView.setViewName("contact");
            return modelAndView;
        }
        boolean isSaved=contactService.sendContactMsgToDB(contact);

        if(isSaved){
            modelAndView.addObject("msgSaved","We have received your message and" +
                    " We will contact you soon");
        }

        /* redirect:/contact means it'll call /contact from beginning and that /contact controller
        * will return. But if we send contact.html here, then the value the customer entered already
        * in the contact.html page, the same page will be returned with the values written over there
        * */

        return modelAndView;
    }

    @GetMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(@PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir){

        Page<Contact> contactsPageWise = contactService.fetchMessagesByStatusOpen(pageNum,sortField,sortDir);
        List<Contact> contacts = contactsPageWise.getContent();

        ModelAndView modelAndView = new ModelAndView("contactMessages");
        modelAndView.addObject("currentPage",pageNum);
        modelAndView.addObject("totalPages",contactsPageWise.getTotalPages());
        modelAndView.addObject("totalMessages",contactsPageWise.getTotalElements());
        modelAndView.addObject("sortField",sortField);
        modelAndView.addObject("sortDir",sortDir);
        modelAndView.addObject("contactMsg",contacts);
        modelAndView.addObject("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        return modelAndView;
    }

    @GetMapping("/closeMsg")
    public String closeMessage(@RequestParam(value = "id") Integer id,Authentication authentication){

        boolean isChanged=contactService.closeMessageById(id,authentication.getName());
        if(isChanged){
            log.info("Contact status changed to close for Contact id {}", id);
        }
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=asc";
    }

}
