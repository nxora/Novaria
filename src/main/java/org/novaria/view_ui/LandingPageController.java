package org.novaria.view_ui;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
 import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class LandingPageController {

    public BorderPane mainContent;
    @FXML private Button browseButton;
        @FXML private Button continueButton;
    @FXML private ImageView heroImage;
    @FXML private Rectangle overlayRect;
    @FXML private StackPane root;

    private final List<String> images  = List.of(
            "/media/images/library1.jpeg",
            "/media/images/library2.jpeg",
            "/media/images/library3.jpeg",
            "/media/images/library4.jpeg",
            "/media/images/library5.jpeg",
            "/media/images/library6.jpeg",
            "/media/images/library7.jpeg",
            "/media/images/library8.jpeg",
            "/media/images/library9.jpeg",
            "/media/images/library10.jpeg",
            "/media/images/library11.jpeg",
            "/media/images/library12.jpeg"
    );

    private final List<Image> loadedImages = images.stream()
            .map(path -> new Image(getClass().getResource(path).toExternalForm(),  0,  0, true, true, true))
            .toList();


    @FXML
    public void initialize() {
        animateOverlay();
        playSlideshow();
        heroImage.fitWidthProperty().bind(root.widthProperty());
        heroImage.fitHeightProperty().bind(root.heightProperty());
        heroImage.setImage(loadedImages.get(0));


        System.out.println("Landing Page loaded 🚀");

    }

    private void playSlideshow() {
        final int[] index = {0};

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), heroImage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), heroImage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeMc = new FadeTransition(Duration.seconds(1.5), mainContent);
        fadeMc.setFromValue(0);
        fadeMc.setToValue(1);
        fadeMc.play();

        PauseTransition stay = new PauseTransition(Duration.seconds(5));

        fadeOut.setOnFinished(e -> {
            index[0] = (index[0] + 1) % loadedImages.size();
            heroImage.setImage(loadedImages.get(index[0]));
        });

        SequentialTransition slideshow = new SequentialTransition(stay, fadeOut, fadeIn);
        slideshow.setCycleCount(Animation.INDEFINITE);
        slideshow.play();
    }


    private void animateOverlay() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),   new KeyValue(overlayRect.opacityProperty(), 0.45)),
                new KeyFrame(Duration.seconds(3),   new KeyValue(overlayRect.opacityProperty(), 0.6)),
                new KeyFrame(Duration.seconds(6),   new KeyValue(overlayRect.opacityProperty(), 0.45))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    // In LandingPageController.java
    @FXML
    private void onLoginClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AuthPage.fxml"));
            Parent authroot = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(authroot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openGitHub(ActionEvent event) {
            openLink("https://github.com/nxora");
        }

        private void openLink(String url){
            try {
                if (Desktop.isDesktopSupported()){
                    Desktop.getDesktop().browse(new URI(url));
                } else {
                    System.out.println("Opening browser not supported on this platform.");
                }
            }catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }

    public void openSnapchat(ActionEvent event) {
            openLink("https://www.snapchat.com/@davex.101");
    }
}


