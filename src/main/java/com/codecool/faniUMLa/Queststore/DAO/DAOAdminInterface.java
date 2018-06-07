package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.Mentor;

import java.util.ArrayList;

public interface DAOAdminInterface {
    void createClass(String className);
    ArrayList<Mentor> getAllMentors();
}
