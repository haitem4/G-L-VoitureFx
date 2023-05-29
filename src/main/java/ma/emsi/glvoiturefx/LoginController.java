package ma.emsi.glvoiturefx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.emsi.glvoiturefx.entities.User;
import ma.emsi.glvoiturefx.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    UserService userService = new UserService();
    @FXML
    private TextField TMdp;

    @FXML
    private TextField TUsername;

    @FXML
    private Button btnConn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConn.setOnAction(actionEvent -> login());
    }

    public void login(){
        String username = TUsername.getText();
        String password = TMdp.getText();
        User user = userService.findByUsername(username);
        try {
            password = userService.encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (user == null || !user.getPassword().equals(password)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Login Error", ButtonType.CANCEL);
            alert.show();
        } else {
            btnConn.getScene().getWindow().hide();
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("location_view.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setTitle("Liste des Locations");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
