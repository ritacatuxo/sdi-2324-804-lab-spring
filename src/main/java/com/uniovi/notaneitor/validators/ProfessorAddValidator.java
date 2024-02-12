package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.ProfessorsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

// validación de todos los campos de signup.html
@Component
public class ProfessorAddValidator implements Validator {
    private final ProfessorsService professorsService;
    public ProfessorAddValidator(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Recibe el target (objeto con los datos del formulario), se comprueba cada atributo, si existe algún error
     * incluimos información sobre él en el objeto errors. Para cada error incluimos el nombre del campo relaccionado
     * con el error y la ID del mensaje (fichero messages de internacionalización) que queremos mostrar como error.
     * */
    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;

        // implementa comprobaciones comunes (z.B. vacio)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Error.empty");

        // validaciones del dni (longitud, letra y único)
        if (professor.getDni().trim().length() != 9){
            errors.rejectValue("dni", "Error.professorAdd.dni.length");
        }

        if(!Character.isLetter(professor.getDni().trim().charAt(professor.getDni().trim().length() - 1))){
            errors.rejectValue("dni", "Error.professorAdd.dni.letter");
        }
        List<Professor> profs = professorsService.getProfessors();
        System.out.println(profs);
        for(Professor prof : profs){
            if(professor.getDni().equals(prof.getDni())){
                errors.rejectValue("dni", "Error.professorAdd.dni.unique");
            }
    }


    }
}
