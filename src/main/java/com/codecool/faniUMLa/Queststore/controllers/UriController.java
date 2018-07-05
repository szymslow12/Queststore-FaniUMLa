package com.codecool.faniUMLa.Queststore.controllers;

import java.util.HashMap;
import java.util.Map;

public class UriController extends AppController {
    public String getSubSite(String query) {
        String[] entry = query.split("[?=]");
        return entry[1];
    }

    public int getParameter(String query) {
        String[] array = query.split("[?=]");
        return Integer.parseInt(array[3]);
        }
    }


