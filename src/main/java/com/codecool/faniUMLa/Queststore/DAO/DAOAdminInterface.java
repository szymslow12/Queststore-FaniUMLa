package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.store.Level;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;

import java.util.ArrayList;
import java.util.Map;

public interface DAOAdminInterface {
    void createClass(String className);
    ArrayList<Mentor> getAllMentors();
    Mentor getMentor(int index);
    void createMentor(ArrayList<String> userData);
    void editMentor(String column_name, String changedWord, Integer idUser);
    void createLevel(String levelName, Integer thresholdLevel);
    Map<String, Integer> getAllClasses();
    ArrayList<Level> getAllLevels();
    void deleteMentor(int index);
    void deleteClass(int index);
    void deleteLevel(int index);
}
