package org.novaria.view_ui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.novaria.Model.Library;
import org.novaria.Model.User;
import org.novaria.service.UserService;

import javafx.event.ActionEvent;
import java.io.IOException;

public class AuthController {

    public PasswordField loginPasswordField;
    public TextField loginEmailField;
    public TextField signUpNameField;
    public TextField signUpEmailField;
    public PasswordField signUpPasswordField;
    public PasswordField signUpConfirmPasswordField;
    public CheckBox rememberMeCheck;
    public ComboBox<String> signUpGenderBox;
    @FXML private VBox loginForm;
    @FXML private VBox signUpForm;
    @FXML private Hyperlink switchLink;
    @FXML private Label switchPrompt;
    @FXML private ImageView googleIcon;
    @FXML private ImageView facebookIcon;
    private final UserService userService = new UserService();

    private boolean showingLogin = true;

    @FXML
    public void initialize() {
        googleIcon.setImage(new Image(getClass().getResourceAsStream("/media/icons/google.png")));
        facebookIcon.setImage(new Image(getClass().getResourceAsStream("/media/icons/facebook.png")));

        // nothing fancy here yet
    }

    @FXML
    private void onLoginClicked(ActionEvent event) {
        String email = loginEmailField.getText().trim();
        String password = loginPasswordField.getText().trim();

        boolean success = userService.validateLogin(email, password);
        if (success) {
            if (rememberMeCheck.isSelected()) {
//                saveSession(email); // to be implemented
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homePage.fxml"));
                Parent authroot = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(authroot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Invalid email or password.");
        }
    }



    @FXML
    private void onSignUpClicked(ActionEvent event) {
        String name = signUpNameField.getText().trim();
        String email = signUpEmailField.getText().trim();
        String password = signUpPasswordField.getText().trim();
        String confirmPassword = signUpConfirmPasswordField.getText().trim();
        String genderstring = signUpGenderBox.getValue();
        if (email.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || genderstring == null){
            showAlert("Future Readers !","Pls fill all the fields");
            return;
        }
        if (!password.equals(confirmPassword)){
            showAlert("Future Readers !", "Passwords must match");
            return;
        }
        User.Gender gender = User.Gender.valueOf(genderstring.toUpperCase());
        User user = new User(0, name,0, gender,email , password, new Library("Novaria"));
        user.setEmail(email);
        user.setPassword(password);
        boolean success = userService.registerUser(user);
        if (success){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homePage.fxml"));
                Parent authroot = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(authroot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            showAlert("Error", "Email already in use or registration failed.");

        }
        System.out.println("Sign Up clicked 🆕");
        // TODO: validate & call backend
    }

    @FXML
    private void switchToSignUp() {
        if (showingLogin) {
            fadeSwitch(loginForm, signUpForm);
            switchLink.setText("Log in");
            switchPrompt.setText("Already have an account?");
        } else {
            fadeSwitch(signUpForm, loginForm);
            switchLink.setText("Sign up");
            switchPrompt.setText("Don't have an account?");
        }
        showingLogin = !showingLogin;
    }

    private void fadeSwitch(VBox from, VBox to) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), from);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            from.setVisible(false);
            from.setManaged(false);
            to.setVisible(true);
            to.setManaged(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), to);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        fadeOut.play();
    }

    @FXML
    private void onGoogleLoginClicked() {
        System.out.println("Google login clicked 🌐");
        // TODO: Google API integration
    }

    @FXML
    private void onFacebookLoginClicked() {
        System.out.println("Facebook login clicked 📘");
        // TODO: Facebook API integration
    }

    @FXML
    private void onForgotPassword() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText("Forgot Password");
        dialog.setContentText("Enter your email:");

        dialog.showAndWait().ifPresent(email -> {
            boolean exists = userService.findUserByEmail(email);
            if (exists) {
                showAlert("Check your mail", "A password reset link has been sent to " + email);
                // TODO: integrate real email reset later
            } else {
                showAlert("Error", "Email not found");
            }
        });
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Alert");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
