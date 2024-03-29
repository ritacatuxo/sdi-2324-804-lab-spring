package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.repositories.ProfessorsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorsService {

    private final ProfessorsRepository professorsRepository;

    public ProfessorsService(ProfessorsRepository professorsRepository) {
        this.professorsRepository = professorsRepository;
    }

    public List<Professor> getProfessors() {
        List<Professor> professors = new ArrayList<Professor>();
        professorsRepository.findAll().forEach(professors::add);
        return professors;
    }
    public Professor getProfessor(Long id) {
        return professorsRepository.findById(id).get();
    }
    public void addProfessor(Professor professor) {
        professorsRepository.save(professor);
    }

    public void deleteProfessor(Long id) {
        professorsRepository.deleteById(id);
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
