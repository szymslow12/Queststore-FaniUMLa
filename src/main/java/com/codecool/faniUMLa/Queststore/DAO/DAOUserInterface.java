package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.User;

public interface DAOUserInterface {
    User getUser(String login, String password);
}
