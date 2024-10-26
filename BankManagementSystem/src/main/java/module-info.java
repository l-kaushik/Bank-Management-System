module com.github.lkaushik.bankmanagement.bankmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.github.lkaushik.bankmanagement to javafx.fxml;
    exports com.github.lkaushik.bankmanagement;
    exports com.github.lkaushik.bankmanagement.Controllers;
    exports com.github.lkaushik.bankmanagement.Controllers.Admin;
    exports com.github.lkaushik.bankmanagement.Controllers.Client;
    exports com.github.lkaushik.bankmanagement.Models;
    exports com.github.lkaushik.bankmanagement.Views;
}