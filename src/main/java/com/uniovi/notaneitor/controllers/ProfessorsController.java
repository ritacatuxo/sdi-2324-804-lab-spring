package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.ProfessorsService;
import com.uniovi.notaneitor.validators.ProfessorAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    @Autowired
    private ProfessorsService professorsService;
    private final ProfessorAddValidator professorAddValidator;

    public ProfessorsController(ProfessorsService professorsService, ProfessorAddValidator professorAddValidator) {
        this.professorsService = professorsService;
        this.professorAddValidator = professorAddValidator;
    }

    // petición de añadir
    @RequestMapping(value = "/professor/add")
    public String getProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/add";
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@Validated Professor professor, BindingResult result) {

        professorAddValidator.validate(professor, result);
        //si se produce un error, redirigimos a la vista add otra vez
        if(result.hasErrors()) {
            return "professor/add";
        }

        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }



    // petición de editar
    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/edit";
    }

    @RequestMapping(value="/professor/edit", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @RequestParam Long id){
        // Al editar el registro de un profesor NO enviará el identificador del registro
        //en la URL, sino en el cuerpo del mensaje -> con esto se refiere a lo de request param???
        professor.setId(id);
        professorsService.addProfessor(professor);
        return "redirect:/professor/details/"+id;
    }






    // petición de ver detalle de un profesor
    @RequestMapping("/professor/details/{id}")
    public String getProfessorDetail(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/details";
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
