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
        // 创建一个对话框，用于接收用户输入的参数
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Git Reset");
        dialog.setHeaderText("请输入Git Reset参数：");

        // 设置一个标签和输入框，并将其添加到对话框中
        Label label = new Label("参数：");
        TextField textField = new TextField();

        VBox layout = new VBox();
        layout.getChildren().addAll(label, textField);
        layout.setSpacing(10);
        dialog.getDialogPane().setContent(layout);

        // 添加确定和取消按钮
        ButtonType confirmButtonType = new ButtonType("Reset", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        // 设置结果转换器，将输入框中的文本返回为结果
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return textField.getText();
            }
            return null;
        });

        // 显示对话框，并等待用户的输入
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(params -> {
            // 用户输入参数后，执行git reset命令
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
        // 创建一个对话框，用于接收用户输入的参数
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Git Revert");
        dialog.setHeaderText("请输入Git Revert参数：");

        // 设置一个标签和输入框，并将其添加到对话框中
        Label label = new Label("参数：");
        TextField textField = new TextField();

        VBox layout = new VBox();
        layout.getChildren().addAll(label, textField);
        layout.setSpacing(10);
        dialog.getDialogPane().setContent(layout);

        // 添加确定和取消按钮
        ButtonType confirmButtonType = new ButtonType("Revert", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        // 设置结果转换器，将输入框中的文本返回为结果
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return textField.getText();
            }
            return null;
        });

        // 显示对话框，并等待用户的输入
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(params -> {
            // 用户输入参数后，执行git revert命令
            try {
                executeGitRevert(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void executeGitRevert(String params) throws IOException {
        // 使用参数执行git revert命令，并打印输出
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
