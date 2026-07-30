package br.cesul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principal, só carrega o FXML e exibe na tela
 * Não deve conter nenhuma lógica de domínio
 */
public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Usaremos o FXMLLoader para carregar o layout da tela a partir do arquivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/cesul/trip_view.fxml"));
        Parent root = loader.load();

        stage.setTitle("TripPlanner - MVC");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main( String[] args ) { launch(args); }
}
