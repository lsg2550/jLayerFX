package gfx;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.cache.BuildCache;
import utils.notifications.ConfirmationBox;
import utils.operations.IO;

/**
 *
 * @author Luis
 */
public class GUI {

    private static final Image ICON = new Image("assets/ico/ico.png");
    private static ProgressBar pBar = new ProgressBar(0); //Diplays How Much Loading Of Files Is Done
    private static BorderPane root = new BorderPane();
    private static Text text = new Text(), //Display What Files The App Is Reading
            directoryText = new Text(); //Display Directory Being Read

    public GUI() {
        mainStage();
    }

    private void mainStage() {
        //U(ser)I(nterface)
        HBox topHBox = new HBox(5),
                bottomHBox = new HBox(5);
        Button folderSelect = new Button("Select Folder"),
                generateImage = new Button("Generate Image");
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();

        //HBoxes
        topHBox.getChildren().addAll(folderSelect, generateImage, directoryText);
        bottomHBox.getChildren().addAll(pBar, text);
        topHBox.setAlignment(Pos.CENTER);
        bottomHBox.setAlignment(Pos.CENTER);
        topHBox.setMaxHeight(16);
        bottomHBox.setMaxHeight(16);

        //Button Handlers
        generateImage.setDisable(true);
        folderSelect.setOnAction(e -> {
            BuildCache.chooseFolder(scene);
        });
        generateImage.setOnAction(e -> {
            text.setText("Image Created!");
            IO.writeImage(SwingFXUtils.fromFXImage(BuildCache.getToBeGeneratedIV().getImage(), null), "getUserInput");
            new ConfirmationBox(scene);
        });

        //BorderPane
        root.setTop(topHBox);
        root.setBottom(bottomHBox);
        root.centerProperty().addListener((o, oV, nV) -> {
            if (root.getCenter() != null) {
                generateImage.setDisable(false);
            }
        });

        stage.setTitle("Generate Image");
        stage.getIcons().add(ICON);
        stage.setScene(scene);
        stage.show();
    }

    public static Image getICON() {
        return ICON;
    }

    public static ProgressBar getpBar() {
        return pBar;
    }

    public static Text getText() {
        return text;
    }

    public static Text getDirectoryText() {
        return directoryText;
    }

    public static BorderPane getRoot() {
        return root;
    }
}