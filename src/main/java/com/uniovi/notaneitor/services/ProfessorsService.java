package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorsService {

    private List<Professor> list = new ArrayList<>();

    @PostConstruct
    public void init() {
        // lista de profesores por defecto
        list.add(new Professor(1L, "11111111H", "Nombre 1", "Apellido 1", "Categoria 1"));
        list.add(new Professor(2L, "22222222H", "Nombre 2", "Apellido 2", "Categoria 2"));
        list.add(new Professor(3L, "33333333H", "Nombre 3", "Apellido 3", "Categoria 3"));
        list.add(new Professor(4L, "44444444H", "Nombre 4", "Apellido 4", "Categoria 4"));
    }

    public List<Professor> getProfessors() {
        return list;
    }
    public Professor getProfessor(Long id) {
        return list.stream()
                .filter(professor -> professor.getId().equals(id)).findFirst().get();
    }
    public void addProfessor(Professor professor) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        if (professor.getId() == null) {
            professor.setId(list.get(list.size() - 1).getId() + 1);
        }
        list.add(professor);
    }

    public void deleteProfessor(Long id) {
        list.removeIf(professor -> professor.getId().equals(id));
    }

    public void edit(Long id, Professor professor)
    {
        Professor p = getProfessor(id);
        p.setName(professor.getName());
        p.setSurname(professor.getSurname());
        p.setDni(professor.getDni());
        p.setCategory(professor.getCategory());
    }

}
