package br.cesul;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//classe principal e só carrega o fxml e exibe na tela
//nao deve conter nenhuma logica de dominio
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //usaremos o fxml loader pra carregar o layout da tela a partir do aquivo fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/cesul/trip_view.fxml"));
        Parent root = loader.load();
        stage.setTitle("Trip Planner - MVC");
        stage.setScene ( new Scene(root));
        stage.show();
    }

    public static void main (String [] args) {
        launch(args);
    }
}
