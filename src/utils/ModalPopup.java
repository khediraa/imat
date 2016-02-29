package utils;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ModalPopup extends FlowPane {

    private static Node mainProgramContent;
    private static StackPane container;
    private Animation appearAnimation, disappearAnimation;

    public ModalPopup(Node content) {
        if (content != null)
            this.getChildren().add(content);

        setDefaultFade();
    }

    public ModalPopup() {
        this(null);
    }

    /** If not set, uses a default fade */
    public void setAppearAnimation(Animation animation) {
        this.appearAnimation = animation;
    }

    /** If not set, uses a default fade */
    public void setDisappearAnimation(Animation animation) {
        this.disappearAnimation = animation;
    }

    public void show() {
        if (!container.getChildren().contains(this))
            container.getChildren().add(0, this);

        prefWidth(container.getPrefWidth());
        prefHeight(container.getPrefHeight());
        toFront();
        appearAnimation.play();
    }

    /** Closes the popup. */
    public void close() {
        disappearAnimation.play();
        disappearAnimation.setOnFinished(event -> mainProgramContent.toFront());
    }

    /** Equivalent to <code>close()</code>. */
    public void hide() {
        close();
    }

    /** should only be called at program startup. */
    public static void initialize(StackPane container, Pane mainContent) {
        ModalPopup.container = container;
        ModalPopup.mainProgramContent = mainContent;
    }

    private void setDefaultFade() {
        appearAnimation = new FadeTransition(Duration.millis(300), this);
        FadeTransition fade = (FadeTransition) appearAnimation;
        fade.setFromValue(0);
        fade.setToValue(1);

        disappearAnimation = new FadeTransition(Duration.millis(300), this);
        fade = (FadeTransition) disappearAnimation;
        fade.setFromValue(1);
        fade.setToValue(0);
    }
}