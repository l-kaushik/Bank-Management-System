module com.github.lkaushik.bankmanagement.bankmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.lkaushik.bankmanagement.bankmanagementsystem to javafx.fxml;
    exports com.github.lkaushik.bankmanagement.bankmanagementsystem;
}