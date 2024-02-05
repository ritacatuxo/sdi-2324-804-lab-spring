package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.ProfessorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    private final ProfessorsService professorsService;

    public ProfessorsController(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }

    // petición de añadir
    @RequestMapping(value = "/professor/add")
    public String getProfessor() {
        return "professor/add";
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }




    // petición de editar
    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(@PathVariable Long id) {
        return professorsService.getProfessor(id).toString();
    }

    @RequestMapping(value="/professor/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable Long id){
        professorsService.edit(id, professor);
        return "Professor edited";
    }

    // petición de ver detalle de un profesor
    @RequestMapping("/professor/details/{id}")
    public String getProfessorDetail(@PathVariable Long id) {
        if(professorsService.getProfessor(id) == null)
            return "No professor found";
        else
            return professorsService.getProfessor(id).toString();
    }

    // petición de listar
    @RequestMapping("/professor/list")
    public String getList(Model model)
    {
        model.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/list";
    }


    // petición de borrar
    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id){
        professorsService.deleteProfessor(id);
        return "redirect:/professor/list";
    }

}
