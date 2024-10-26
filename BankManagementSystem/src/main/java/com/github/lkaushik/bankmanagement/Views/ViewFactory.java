package com.github.lkaushik.bankmanagement.Views;

import com.github.lkaushik.bankmanagement.Controllers.Admin.AdminController;
import com.github.lkaushik.bankmanagement.Controllers.Client.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    // Client Views
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;

    // Admin Views
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane createClientView;

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    /*
    * Client Views Sections
    */

    public StringProperty getSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        return createView(dashboardView, "Client/Dashboard.fxml");
    }

    public AnchorPane getTransactionsView() {
        return createView(transactionsView, "Client/Transactions.fxml");
    }

    public AnchorPane getAccountsView() {
        return createView(accountsView, "Client/Accounts.fxml");
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    /*
    * Admin Views Section
    * */

    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {
        return createView(createClientView, "Admin/CreateClient.fxml");
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        createStage(loader);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        } catch(Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Trust Us Bank");
        stage.show();
    }

    private AnchorPane createView(AnchorPane view, String resourcePath) {
        if(view == null){
            try{
                view = new FXMLLoader(getClass().getResource("/FXML/" + resourcePath)).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }
}
