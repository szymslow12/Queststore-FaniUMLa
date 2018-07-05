package com.codecool.faniUMLa.Queststore.controllers;


import com.codecool.faniUMLa.Queststore.DAO.DAOLogin;
import com.codecool.faniUMLa.Queststore.model.users.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

public class LoginController extends UriController implements HttpHandler {
    User user = null;
    DAOLogin daoLogin = new DAOLogin(connection);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            response= this.getFile("html/login.html");

        } else if (method.equals("POST")) {

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = parseFormData(formData);

            String login = String.valueOf(inputs.get("login"));
            String password = String.valueOf(inputs.get("password"));
            this.user = daoLogin.logIn(login, password);

            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            HttpCookie cookie;
            boolean isNewSession;
            if (cookieStr != null) {  // Cookie already exists
                cookie = HttpCookie.parse(cookieStr).get(0);
                isNewSession = false;
            } else { // Create a new cookie
                cookie = new HttpCookie("sessionId", generateSessionID());
                isNewSession = true;
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            }

            if(user.getAccess().name().equals("ADMIN")) {
                response = this.getFile("html/admin/Mentors.html");

            } else if (user.getAccess().name().equals("MENTOR")) {
                response = this.getFile("html/mentor/Students.html");

            } else if (user.getAccess().name().equals("CODECOOLER")) {
                response = this.getFile("html/student/Store.html");
            } else {
                response = this.getFile("html/login.html");
            }
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String generateSessionID() {
        MessageDigest salt = null;
        try {
            salt = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String digest = bytesToHex(salt.digest());
        return digest;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
