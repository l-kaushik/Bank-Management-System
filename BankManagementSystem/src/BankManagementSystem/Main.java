package BankManagementSystem;

public class Main {
    
    public static void main(String[] args) {

        MyCon.createPathIfNotExists();

        new Login();
    }
}
