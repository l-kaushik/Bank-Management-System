package com.github.lkaushik.bankmanagement.Views;

import com.github.lkaushik.bankmanagement.Controllers.Admin.AdminController;
import com.github.lkaushik.bankmanagement.Controllers.Client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    private AccountType loginAccountType;

    // Client Views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;
    private AnchorPane accountsView;

    // Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane createClientView;
    private AnchorPane clientsView;
    private AnchorPane depositView;
    private AnchorPane clientEditView;

    public ViewFactory() {
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
    * Client Views Sections
    */

    public ObjectProperty<ClientMenuOptions> getSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public void resetClientSelectedMenuItem() {
        clientSelectedMenuItem.set(ClientMenuOptions.DASHBOARD);
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

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public void resetAdminSelectedMenuItem() {
        adminSelectedMenuItem.set(AdminMenuOptions.CREATE_CLIENT);
    }

    public AnchorPane getCreateClientView() {
        return createView(createClientView, "Admin/CreateClient.fxml");
    }

    public AnchorPane getClientsView() {
        return createView(clientsView, "Admin/Clients.fxml");
    }

    public AnchorPane getDepositView() {
        return createView(depositView, "Admin/Deposit.fxml");
    }

    public AnchorPane getClientEditView() {
        return createView(clientEditView, "Admin/ClientEdit.fxml");
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
//        createStage(loader);
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        } catch(Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/bank.png"))));
        stage.setResizable(true);
        stage.setTitle("Trust Us Bank");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public void simulateLogOut(Stage stage) {
        closeStage(stage);
        setLoginAccountType(AccountType.CLIENT);
        showLoginWindow();
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
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/bank.png"))));
        stage.setResizable(true);
        stage.setMaximized(true);
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
