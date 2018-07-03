package com.codecool.faniUMLa.Queststore.controllers;

import java.util.HashMap;
import java.util.Map;

public class UriController extends AppController {
    public String getSubSite(String query) {
        String[] entry = query.split("=");
        return entry[1];
    }
}
