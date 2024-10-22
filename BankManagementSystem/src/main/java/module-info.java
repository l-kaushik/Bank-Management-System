module com.github.lkaushik.bankmanagement.bankmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.github.lkaushik.bankmanagement.bankmanagementsystem to javafx.fxml;
    exports com.github.lkaushik.bankmanagement.bankmanagementsystem;
    exports com.github.lkaushik.bankmanagement.bankmanagementsystem.Controllers;
    exports com.github.lkaushik.bankmanagement.bankmanagementsystem.Controllers.Admin;
    exports com.github.lkaushik.bankmanagement.bankmanagementsystem.Controllers.Client;
    exports com.github.lkaushik.bankmanagement.bankmanagementsystem.Models;
    exports com.github.lkaushik.bankmanagement.bankmanagementsystem.Views;
}