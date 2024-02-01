package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import org.springframework.web.bind.annotation.*;

@RestController // responde a peticiones Rest
public class MarksController {
    // un método por cada URL a la que responde el controlador

    @RequestMapping("/mark/list")
    public String getList() {
        return "Getting List";
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark) {
        return "Added: " + mark.getDescription()
                + " with score: " + mark.getScore()
                + " id: " + mark.getId();
    }


    // petición GET y que pueda recibir un parámetro id.
    @RequestMapping("/mark/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return "Getting Details =>" + id;
    }


}