package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainController {

    private static final String ANIMATE_BASE_TEXT = "Animate";
    private static final String INTERROBANG = "‽";
    private static final int MAX_INTERROBANGS = 12;

    @FXML
    private TextField wordField;

    @FXML
    private Button beginButton;

    @FXML
    private Button animateButton;

    @FXML
    private VBox letterBox;

    private Timeline animationTimeline;
    private int interrobangCount = 0;

    @FXML
    public void initialize() {
        animateButton.setText(ANIMATE_BASE_TEXT);

        beginButton.setOnAction(e -> handleBegin());
        animateButton.setOnAction(e -> handleAnimate());
    }

    private void handleBegin() {
        String text = wordField.getText();

        if (text == null || text.trim().isEmpty()) {
            showAlert();
            wordField.requestFocus();
            return;
        }

        createLetterButtons(text.trim());
    }

    private void handleAnimate() {
        if (animationTimeline != null) {
            animationTimeline.stop();
        }

        interrobangCount = 0;
        animateButton.setText(ANIMATE_BASE_TEXT);

        animationTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.25), e -> addInterrobang())
        );

        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.play();
    }

    private void addInterrobang() {
        if (interrobangCount >= MAX_INTERROBANGS) {
            animationTimeline.stop();
            return;
        }

        interrobangCount++;
        animateButton.setText(ANIMATE_BASE_TEXT + INTERROBANG.repeat(interrobangCount));

        if (interrobangCount >= MAX_INTERROBANGS) {
            animationTimeline.stop();
        }
    }

    private void createLetterButtons(String text) {
        letterBox.getChildren().clear();

        for (int i = 0; i < text.length(); i++) {
            Button button = new Button(String.valueOf(text.charAt(i)));

            button.setMinWidth(30);
            button.setPrefWidth(30);
            button.setMaxWidth(30);

            button.setMaxHeight(Double.MAX_VALUE);
            VBox.setVgrow(button, Priority.ALWAYS);

            letterBox.getChildren().add(button);
        }
    }

    private void showAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a word.");
        alert.showAndWait();
    }
}