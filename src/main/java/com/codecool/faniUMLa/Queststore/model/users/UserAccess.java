package com.codecool.faniUMLa.Queststore.model.users;

import java.util.ArrayList;
import java.util.List;

public enum UserAccess {
    ADMIN {
        @Override
        public List<UserPrivilege> initPrivileges() {
            List<UserPrivilege> privileges = new ArrayList<>();

            privileges.add(UserPrivilege.CREATE_MENTOR);
            privileges.add(UserPrivilege.CREATE_CLASS);
            privileges.add(UserPrivilege.EDIT_MENTOR);
            privileges.add(UserPrivilege.SEE_MENTOR);
            privileges.add(UserPrivilege.CREATE_LEVELS);

            return privileges;
        }
    },

    MENTOR {
        @Override
        public List<UserPrivilege> initPrivileges() {
            List<UserPrivilege> privileges = new ArrayList<>();

            privileges.add(UserPrivilege.CREATE_CODECOOLER);
            privileges.add(UserPrivilege.ADD_QUEST);
            privileges.add(UserPrivilege.UPDATE_QUEST);
            privileges.add(UserPrivilege.ADD_ARTIFACT);
            privileges.add(UserPrivilege.UPDATE_ARTIFACT);
            privileges.add(UserPrivilege.MARK_QUEST_DONE);
            privileges.add(UserPrivilege.MARK_BOUGHT_ARTIFACT);
            privileges.add(UserPrivilege.SEE_CODECOOLERS_WALLETS);

            return privileges;
        }
    },

    CODECOOLER {
        public List<UserPrivilege> initPrivileges() {
            List<UserPrivilege> privileges = new ArrayList<>();

            privileges.add(UserPrivilege.SEE_WALLET);
            privileges.add(UserPrivilege.BUY_ARTIFACT);
            privileges.add(UserPrivilege.BUY_ARTIFACT_WITH_TEAMMATES);
            privileges.add(UserPrivilege.SEE_LEVEL);

            return privileges;
        }
    };

    private List<UserPrivilege> privileges = initPrivileges();
    public abstract List<UserPrivilege> initPrivileges();
    public List<UserPrivilege> getPrivileges() {
        return privileges;
    }
}

