package com.sks.sksschool.controller;

import com.sks.sksschool.model.Holiday;
import com.sks.sksschool.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HolidayController {

    @Autowired
    private HolidayRepository holidayRepository;
/*
* This is a controller class created and here we are adding some dummy holidays and in return
* we are sending typeOfHoliday(NATIONAL/FESTIVAL) and the list of holiday of particular type.
* for example - NATIONAL,{Holiday1Object,2Object,..)
* We are adding this in Model. Although we are not returning this Model class, thymeleaf will automatically
* find this Model class and run the for each loop and fetch the value. See holidays.html file.
* */
    @GetMapping("/holidays/{display}")
    public String listOfHolidays(@PathVariable String display, Model model){
        if(display !=null){
            if(display.equals("all")){
                model.addAttribute("national",true);
                model.addAttribute("festival",true);
            }else if(display.equalsIgnoreCase("national")){
                model.addAttribute("national",true);
            }else if(display.equalsIgnoreCase("festival")){
                model.addAttribute("festival",true);
            }
        }


        List<Holiday> holidayList=holidayRepository.findAll();

       /* List<Holiday> holidayList = Arrays.asList(
                new Holiday(" 1st Jan","Happy New Year",Holiday.Type.NATIONAL),
                new Holiday(" 15th Aug","Happy Independence Day",Holiday.Type.NATIONAL),
                new Holiday(" 2nd Oct","Mahatma Gandhi Jayanti",Holiday.Type.NATIONAL),
                new Holiday(" 26th Jan","Happy Republic Day",Holiday.Type.NATIONAL),
                new Holiday(" 16th Oct","Happy Durga Puja",Holiday.Type.FESTIVAL),
                new Holiday(" 4th Nov","Happy Diwali",Holiday.Type.FESTIVAL),
                new Holiday(" 25th Dec","Happy Christmas",Holiday.Type.FESTIVAL)
        );*/

       Holiday.Type[] types = Holiday.Type.values();
       for(Holiday.Type type : types){
          List<Holiday> holidayTypeList=
                  holidayList.stream().filter(e -> e.getType().equals(type)).toList();
          model.addAttribute(type.toString(),holidayTypeList);
       }

       return "holidays.html";
    }
}
