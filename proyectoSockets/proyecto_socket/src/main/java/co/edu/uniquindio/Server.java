package co.edu.uniquindio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args) {
        int puerto = 12345;

        try(ServerSocket socket = new ServerSocket(puerto)){
            System.out.println("Servidor iniciado en el puerto: " + puerto);
            Socket clientSocket = socket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            String inputLine;

            String usuario = "admin";
            String contrasena = "1234";

            while ((inputLine = input.readLine()) != null){
                String[] credenciales = inputLine.split(":");

                if (credenciales.length == 2) {
                    String usuarioInput = credenciales[0];
                    String contrasenaInput = credenciales[1];

                    if (usuarioInput.equals(usuario) && contrasenaInput.equals(contrasena)) {
                        output.println("Inicio de sesión exitoso");
                    } else {
                        output.println("Credenciales incorrectas");
                    }
                } else {
                    output.println("Formato incorrecto. Usa 'usuario:contraseña'");
                }
            }
        } catch (IOException e){
                e.printStackTrace();
        }
    }
}
