package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Classroom;
import com.codecool.faniUMLa.Queststore.model.store.Level;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;

import java.util.List;

public interface DAOAdminInterface {
    void createClass(String className);
    List<Mentor> getAllMentors();
    Mentor getMentor(int index);
    void createMentor(List<String> userData);
    void editMentor(String column_name, String changedWord, Integer idUser);
    void createLevel(String levelName, Integer thresholdLevel);
    List<Classroom> getAllClasses();
    List<Level> getAllLevels();
    void deleteMentor(int index);
    void deleteClass(int index);
    void deleteLevel(int index);
    void editClass(int index, String name);
    void editLevel(int index, String name, int exps);
}
