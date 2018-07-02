package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class AppController extends Controller implements HttpHandler {
    DAOAdminInterface daoAdmin;
    DAOMentorInterface daoMentor;
    DAOCodecoolerInterface daoCodecooler;
    //UserInputs inputs;

    public AppController() {
        daoAdmin = new DAOAdmin(connection);
        daoMentor = new DAOMentor(connection);
        daoCodecooler = new DAOCodecooler(connection);
        //inputs = new UserInputs();
        //signIn();
    }

    @Override
    public void handle (HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            System.out.println("in get");
            switch(httpExchange.getRequestURI().toString()) {
                case "/Admin":
                    response= this.getFile("html/admin/Mentors.html");
                    break;
                case "/Classes":
                    response= this.getFile("html/admin/Classes.html");
                    break;
                case "/Levels":
                    response= this.getFile("html/admin/Levels.html");
                    break;
                case "/Mentor":
                    response= this.getFile("html/mentor/Students.html");
                    break;
                case "/Artifacts":
                    response= this.getFile("html/mentor/Artifacts.html");
                    break;
                case "/mentor/Quests":
                    response= this.getFile("html/mentor/Quests.html");
                    break;
                case "/Student":
                    response= this.getFile("html/student/Store.html");
                    break;
                case "/Inventory":
                    response= this.getFile("html/student/Inventory.html");
                    break;
                case "/student/Quests":
                    response= this.getFile("html/student/Quests.html");
                    break;
            }
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getFile(String filepath) {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filepath).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

//    public void handleMenu(UserPrivilege privilege) {
//        switch (privilege) {
//            case CREATE_MENTOR:
//                daoAdmin.createMentor(getUserData());
//                break;
//            case CREATE_CLASS:
//                String class_name = askUser("Provide class_name");
//                daoAdmin.createClass(class_name);
//                break;
//            case EDIT_MENTOR:
//                view.displayList(daoAdmin.getAllMentors(), "");
//                int chosenMentor = (Integer.valueOf(askUser("Which mentor would you like to choose(number) ")) - 1);
//                handleMentorUpdate(daoAdmin.getMentor(chosenMentor).getIdUser());
//                break;
//            case SEE_MENTOR:
//                view.displayList(daoAdmin.getAllMentors(), "");
//                int mentorIndex = (Integer.valueOf(askUser("Provide mentor's number")) - 1);
//                view.printLine(daoAdmin.getMentor(mentorIndex).getMentorDetails());
//                break;
//            case CREATE_LEVELS:
//                String level_name = askUser("Provide name");
//                daoAdmin.createLevel(level_name);
//                break;
//            case CREATE_CODECOOLER:
//                daoMentor.addNewCodecooler();
//                break;
//            case ADD_QUEST:
//                daoMentor.addNewQuest();
//                break;
//            case UPDATE_QUEST:
//                daoMentor.updateQuest();
//                break;
//            case ADD_ARTIFACT:
//                daoMentor.addNewArtifact();
//                break;
//            case UPDATE_ARTIFACT:
//                daoMentor.updateArtifact();
//                break;
//            case MARK_QUEST_DONE:
//                daoMentor.markQuestDone();
//                break;
//            case MARK_BOUGHT_ARTIFACT:
//                daoMentor.markBoughtArtifact();
//                break;
//            case SEE_CODECOOLERS_WALLETS:
//                daoMentor.seeCodecoolersWallet();
//                break;
//            case SEE_WALLET:
//                view.printLine(String.valueOf(daoCodecooler.getCoolcoins(getUser().getIdUser())));
//                break;
//            case SEE_LEVEL:
//                view.printLine(daoCodecooler.getLevel(getUser().getIdUser()));
//                break;
//            case BUY_ARTIFACT:
//                daoCodecooler.showArtifacts();
//                int idArtifact = inputs.getInt("Which artifact would you like to buy? ");
//                daoCodecooler.buyArtifact(getUser().getIdUser(), idArtifact);
//            case EXIT:
//                view.printLine("Bye bye");
//        }
//    }

//    public void run() {
//        UserPrivilege privilege = null;
//        do {
//            try{
//                privilege = choosePrivilege();
//                handleMenu(privilege);
//            } catch (NumberFormatException e) {
//                view.printLine("Please provide number!");
//            }
//        }while(isRun(privilege));
//    }

//    private ArrayList<String> getUserData() {
//        ArrayList <String> userData = new ArrayList<>();
//        String[] column = {"first name", "last name", "phone number", "email", "login", "password"};
//        for(int i = 0; i<column.length; i++) {
//            userData.add(askUser("Provide " + column[i] + ": "));
//        }
//        return userData;
//    }

//    private void handleMentorUpdate(Integer id_user) {
//        String word;
//        String answer;
//        String[] column = {"first_name", "last_name", "phone_number", "email"};
//        for(int i = 0; i < column.length; i++) {
//            answer = askUser("Would you like to update " + column[i] + " (y/n)?");
//            if(answer.equalsIgnoreCase("y")) {
//                word = askUser("Provide " + column[i] + ": ");
//                daoAdmin.editMentor(column[i], word, id_user);
//            }
//        }
//    }
//
//    private boolean isRun(UserPrivilege privilege) {
//        return privilege != UserPrivilege.EXIT;
//    }
}
