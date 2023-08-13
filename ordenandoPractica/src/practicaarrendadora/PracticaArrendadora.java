package practicaarrendadora;

import java.util.Scanner;
import static practicaarrendadora.admin.funcionesAdmin.loginAdmin;
import static practicaarrendadora.admin.funcionesAdmin.menuAdmin;
import static practicaarrendadora.cliente.funcionesUsuario.menuCliente;

public class PracticaArrendadora {

    public static String[][] vehiculos = new String[10][7];
    public static String[][] clientes = new String[10][3];
    public static int iFila = 0;
    public static int iCliente = 0;
    public static String usuario = "admin_202200338";
    public static String contrasenia = "202200338";

    public static Scanner sn = new Scanner(System.in);

    public static void main(String[] args) {

        menuPrincipal();

    }

    public static void menuPrincipal() {
        Scanner sn = new Scanner(System.in);

        System.out.println("Menu Principal");
        System.out.println("1. Administrador");
        System.out.println("2. Cliente");

        String opcion = sn.nextLine();
        String valor1 = "1";
        String valor2 = "2";
        boolean a = true;
        while (a) {
            switch (opcion) {
                case "1":
                    loginAdmin();
                    a = false;
                    break;

                case "2":
                    menuCliente();
                    a = false;
                    break;

                default:
                    System.out.println("Ingrese un valor valido");
                    opcion = sn.nextLine();
                    break;

            }

        }
    }

}
