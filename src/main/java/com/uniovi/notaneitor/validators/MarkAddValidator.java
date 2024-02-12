package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

// validación de todos los campos de signup.html
@Component
public class MarkAddValidator implements Validator {
    private final MarksService marksService;
    public MarkAddValidator(MarksService marksService) {
        this.marksService = marksService;
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
        Mark mark = (Mark) target;

        // validaciones del rango y de la desctipción
        if (mark.getScore() < 0 || mark.getScore() > 10) {
            errors.rejectValue("score", "Error.markAdd.score.range");
        }
        if(mark.getDescription().replace(" ","").length() < 20){
            errors.rejectValue("description", "Error.markAdd.description.length");
        }

    }
}
