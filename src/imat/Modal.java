package imat;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * By: Sebastian Nilsson
 * Date: 16-02-29
 * Project: imat26
 */
public class Modal {

    private Pane pane;
    private Pane parent;
    private Rectangle backdrop;
    private boolean modalOpen = false;
    private boolean initialized = false;

    public Modal(Pane pane, Pane parent) {
        this.pane = pane;
        this.parent = parent;
        this.backdrop = new Rectangle();
    }

    private void initialize() {
        if (!initialized) {
            parent.getChildren().add(backdrop);
            parent.getChildren().add(pane);
            initialized = true;
            backdrop.setX(0);
            backdrop.setY(0);
            backdrop.setWidth(parent.getWidth());
            backdrop.setStyle("-fx-background-color: black");
            backdrop.setHeight(parent.getHeight());
        }
    }


    private EventHandler<MouseEvent> closeModal = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Bounds bounds = pane.getBoundsInLocal();
            Bounds sceneBounds = pane.localToScene(bounds);
            int x = (int) sceneBounds.getMinX();
            int y = (int) sceneBounds.getMinY();
            int width = (int) sceneBounds.getWidth();
            int height = (int) sceneBounds.getHeight();

            int nodeXSpan = x + width;
            int nodeYSpan = y + height;

            if ((event.getX() < x || event.getX() > nodeXSpan) || (event.getY() < y || event.getY() > nodeYSpan)) {
                toggleModal();
            }
        }
    };


    public void addCloseEvent() {
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED, closeModal);
    }

    public void removeCloseEvent() {
        parent.removeEventHandler(MouseEvent.MOUSE_CLICKED, closeModal);
    }

    private void placeModal() {
        pane.setLayoutX((parent.getWidth() / 2) - 170);
        pane.setLayoutY((parent.getHeight() / 2) - (pane.getHeight() / 2));
    }

    public void toggleModal() {
        if (!modalOpen) {
            initialize();
            this.placeModal();
            fadeInBackdrop();
            fadeIn();
            addCloseEvent();
            modalOpen = true;
        } else {
            closeModal();
        }
    }
    public void closeModal() {
        fadeOut();
        fadeOutBackdrop();
        removeCloseEvent();
        modalOpen = false;
    }

    public void fadeIn() {
        pane.toFront();

        FadeTransition appearAnimation;
        appearAnimation = new FadeTransition(Duration.millis(300), this.pane);
        FadeTransition fade = appearAnimation;
        fade.setFromValue(0);
        fade.setToValue(1);
        appearAnimation.play();
    }

    public void fadeOut() {
        FadeTransition appearAnimation;
        appearAnimation = new FadeTransition(Duration.millis(300), pane);
        FadeTransition fade = appearAnimation;
        fade.setFromValue(1);
        fade.setToValue(0);
        appearAnimation.play();

        appearAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane.toBack();
            }
        });
    }

    public void fadeInBackdrop() {
        backdrop.toFront();
        FadeTransition appearAnimation;
        appearAnimation = new FadeTransition(Duration.millis(300), backdrop);
        FadeTransition fade = appearAnimation;
        fade.setFromValue(0);
        fade.setToValue(0.5);
        appearAnimation.play();
    }

    public void fadeOutBackdrop() {
        FadeTransition appearAnimation;
        appearAnimation = new FadeTransition(Duration.millis(300), backdrop);
        FadeTransition fade = appearAnimation;
        fade.setFromValue(0.5);
        fade.setToValue(0);
        appearAnimation.play();

        appearAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backdrop.toBack();
            }
        });
    }
}
