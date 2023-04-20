package com.example.demo2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
public class GitController {
    @FXML
    private void Reset(ActionEvent event) {
        String path = getInput("请输入要执行git reset的路径：");
        if (path == null || path.isEmpty()) {
            return;
        }
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "reset");
            pb.directory(new File(path));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Git reset exit code: " + exitCode);
            showSuccessDialog("Git reset success.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            showErrorDialog("Error executing git reset.");
        }
    }
    private String getInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("输入");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }
    @FXML
    private void handleRevert(ActionEvent event) {
        String commit = getInput("请输入要revert的commit：");
        if (commit == null || commit.isEmpty()) {
            return;
        }
        String path = getInput("请输入要执行git revert的路径：");
        if (path == null || path.isEmpty()) {
            return;
        }
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "revert", commit);
            pb.directory(new File(path));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Git revert exit code: " + exitCode);
            showSuccessDialog("Git revert success.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            showErrorDialog("Error executing git revert.");
        }
    }
    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
