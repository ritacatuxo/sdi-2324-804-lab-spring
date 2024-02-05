package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // responde a peticiones Rest
public class MarksController {

    // declarar que el servicio MarksService va a ser usado desde este controlador (Inyectar el servicio)
    @Autowired
    private MarksService marksService;



    // un método por cada URL a la que responde el controlador
    @RequestMapping("/mark/list")
    public String getList(Model model) {
        model.addAttribute("markList", marksService.getMarks());
        return "mark/list";
    }

    @RequestMapping(value = "/mark/add")
    public String getMark() {
        return "mark/add";
    }
    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark) {
        marksService.addMark(mark);
        return "redirect:/mark/list";

    }


    // petición GET y que pueda recibir un parámetro id.
    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id){
        marksService.deleteMark(id);
        return "redirect:/mark/list";

    }

    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/edit";
    }
    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        // de dónde sale el primer parametro? en que lugar se lo pasan?
        // no entiendo lo de que ese mark (el primer parametro) sea el resultado del formulario de edit.html, ya que lo unico que mete el usuario es la descripcion y el score. en que lugar se crea el objeto en si?
        mark.setId(id);
        marksService.addMark(mark);
        return "redirect:/mark/details/"+id;
    }

}