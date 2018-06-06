package com.codecool.faniUMLa.Queststore.model.users;

import java.util.ArrayList;
import java.util.List;

public enum UserAccess {
    ADMIN {
        @Override
        public List<UserPriviledge> initPrivileges() {
            List<UserPriviledge> privileges = new ArrayList<>();


            return privileges;
        }
    },

    MENTOR {
        @Override
        public List<UserPriviledge> initPrivileges() {
            List<UserPriviledge> privileges = new ArrayList<>();

            //privileges.add(UserPriviledge.EXIT);

            return privileges;
        }
    },

    CODECOOLER {
        public List<UserPriviledge> initPrivileges() {
            List<UserPriviledge> privileges = new ArrayList<>();
            //privileges.add(UserPriviledge.ADD_MENTOR);


            return privileges;
        }
    };

    private List<UserPriviledge> privileges = initPrivileges();
    public abstract List<UserPriviledge> initPrivileges();
    public List<UserPriviledge> getPrivileges() {
        return privileges;
    }
}

