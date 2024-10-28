package co.edu.uniquindio.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InicioSesionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_ingresar;

    @FXML
    private PasswordField txt_contrasenia;

    @FXML
    private TextField txt_nombre;

    @FXML
    void btn_ingresar(ActionEvent event) {
        String usuario = txt_nombre.getText();
        String contrasena = txt_contrasenia.getText();
        String entrada = usuario + ":" + contrasena;

        // Crear un hilo para manejar la conexión al servidor
        new Thread(() -> {
            try (Socket socket = new Socket("localhost", 12345);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(usuario + ":" + contrasena); // Enviar credenciales

                String respuesta = in.readLine(); // Leer respuesta del servidor
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void initialize() {
        assert btn_ingresar != null : "fx:id=\"btn_ingresar\" was not injected: check your FXML file 'inicioSesion.fxml'.";
        assert txt_contrasenia != null : "fx:id=\"txt_contrasenia\" was not injected: check your FXML file 'inicioSesion.fxml'.";
        assert txt_nombre != null : "fx:id=\"txt_nombre\" was not injected: check your FXML file 'inicioSesion.fxml'.";

    }

}
