package com.github.lkaushik.bankmanagement.bankmanagementsystem.Models;

import com.github.lkaushik.bankmanagement.bankmanagementsystem.Views.ViewFactory;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;

    private Model() {
        this.viewFactory = new ViewFactory();
    }

    // synchronized make sure only 1 thread at a time can execute this method (Singleton pattern)
    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
