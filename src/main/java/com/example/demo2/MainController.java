package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class MainController {
    @FXML
    private MenuItem gitResetMenuItem;

    @FXML
    private MenuItem gitRevertMenuItem;

    @FXML
    public void initialize() {
        gitResetMenuItem.setOnAction(event -> handleGitReset());
        gitRevertMenuItem.setOnAction(event -> handleGitRevert());
    }

    private void handleGitReset() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Git Reset");
        dialog.setHeaderText("请输入Git Reset参数：");

        Label label = new Label("参数：");
        TextField textField = new TextField();

        VBox layout = new VBox();
        layout.getChildren().addAll(label, textField);
        layout.setSpacing(10);
        dialog.getDialogPane().setContent(layout);

        ButtonType confirmButtonType = new ButtonType("Reset", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return textField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(params -> {
            try {
                executeGitReset(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void executeGitReset(String params) throws IOException {
        // 使用参数执行git reset命令，并打印输出
        String command = "git reset " + params;
        Process process = Runtime.getRuntime().exec(command);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private void handleGitRevert() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Git Revert");
        dialog.setHeaderText("请输入Git Revert参数：");

        Label label = new Label("参数：");
        TextField textField = new TextField();

        VBox layout = new VBox();
        layout.getChildren().addAll(label, textField);
        layout.setSpacing(10);
        dialog.getDialogPane().setContent(layout);

        ButtonType confirmButtonType = new ButtonType("Revert", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return textField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(params -> {
            try {
                executeGitRevert(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void executeGitRevert(String params) throws IOException {
        String command = "git revert " + params;
        Process process = Runtime.getRuntime().exec(command);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
