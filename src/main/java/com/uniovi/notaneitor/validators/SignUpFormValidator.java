package com.uniovi.notaneitor.validators;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

// validación de todos los campos de signup.html
@Component
public class SignUpFormValidator implements Validator {
    private final UsersService usersService;
    public SignUpFormValidator(UsersService usersService) {
        this.usersService = usersService;
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
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty"); // implementa comprobaciones comunes (z.B. vacio)
        if (user.getDni().length() < 5 || user.getDni().length() > 24) {
            errors.rejectValue("dni", "Error.signup.dni.length");}

        if (usersService.getUserByDni(user.getDni()) != null) {
            errors.rejectValue("dni", "Error.signup.dni.duplicate");}
        if (user.getName().length() < 5 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");}
        if (user.getLastName().length() < 5 || user.getLastName().length() > 24) {
            errors.rejectValue("lastName", "Error.signup.lastName.length");}
        if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
            errors.rejectValue("password", "Error.signup.password.length");}
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm",
                    "Error.signup.passwordConfirm.coincidence");}
    }
}
