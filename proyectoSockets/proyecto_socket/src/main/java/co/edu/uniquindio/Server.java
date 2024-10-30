package co.edu.uniquindio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String usuario = "admin";
    private static final String contrasena = "1234";

    public static void main(String[] args) {
        int puerto = 12345;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto: " + puerto);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new Thread(() -> gestionarCliente(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gestionarCliente(Socket clientSocket) {
        try (
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
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
        } catch (IOException e) {
            System.err.println("Error gestionando cliente: " + e.getMessage());
        }
    }

    public static String getUsuario() {
        return usuario;
    }

    public static String getContrasena() {
        return contrasena;
    }
}
