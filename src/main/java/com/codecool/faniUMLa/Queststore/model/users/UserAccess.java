package com.codecool.faniUMLa.Queststore.model.users;

import java.util.ArrayList;
import java.util.List;

public enum UserAccess {
    ADMIN {
        @Override
        public List<UserPrivilege> initPrivileges() {
            List<UserPrivilege> privileges = new ArrayList<>();

            privileges.add(UserPrivilege.GREET);
            return privileges;
        }
    },

    MENTOR {
        @Override
        public List<UserPrivilege> initPrivileges() {
            List<UserPrivilege> privileges = new ArrayList<>();

            //privileges.add(UserPriviledge.EXIT);

            return privileges;
        }
    },

    CODECOOLER {
        public List<UserPrivilege> initPrivileges() {
            List<UserPrivilege> privileges = new ArrayList<>();
            //privileges.add(UserPriviledge.ADD_MENTOR);


            return privileges;
        }
    };

    private List<UserPrivilege> privileges = initPrivileges();
    public abstract List<UserPrivilege> initPrivileges();
    public List<UserPrivilege> getPrivileges() {
        return privileges;
    }
}

