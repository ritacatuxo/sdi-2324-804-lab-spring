package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.MarkAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller // responde a peticiones Rest
public class MarksController {

    // Inyectamos el servicio por inyección basada en constructor
    private final MarksService marksService;
    private final UsersService usersService;
    private final MarkAddValidator marksValidator;
    public MarksController(MarksService marksService, UsersService usersService, MarkAddValidator marksValidator) {
        this.marksService = marksService;
        this.usersService = usersService;
        this.marksValidator = marksValidator;
    }



    // un método por cada URL a la que responde el controlador

    @RequestMapping("/mark/list")
    public String getList(Model model) {
        model.addAttribute("markList", marksService.getMarks());
        return "mark/list";
    }

    @RequestMapping(value="/mark/add")
    public String getMark(Model model){
        // creamos la mark que el usuario va a rellenar con los nuevos datos
        model.addAttribute("mark", new Mark());
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/add";
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(Model model, @Validated Mark mark, BindingResult result) {

        marksValidator.validate(mark, result);
        // si se produce un error redirigimos a la vista add otra vez
        if(result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());
            return "mark/add";
        }

        // quitar espacios de antes y después
        mark.setDescription(mark.getDescription().trim());

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

    // petición edit

    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }
    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        Mark originalMark = marksService.getMark(id);
        // modificar solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/" + id;
    }

    @RequestMapping("/mark/list/update")
    public String updateList(Model model){
        // no retorna toda la vista, solamente el fragmento marksTable
        model.addAttribute("markList", marksService.getMarks() );
        return "mark/list :: marksTable";
    }


}