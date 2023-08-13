package practicaarrendadora.admin;

import java.util.Scanner;
import practicaarrendadora.PracticaArrendadora;
import static practicaarrendadora.PracticaArrendadora.iFila;
import static practicaarrendadora.PracticaArrendadora.menuPrincipal;
import static practicaarrendadora.PracticaArrendadora.sn;
import static practicaarrendadora.PracticaArrendadora.vehiculos;
import practicaarrendadora.cliente.funcionesUsuario;
import static practicaarrendadora.cliente.funcionesUsuario.sleep;
import static practicaarrendadora.cliente.funcionesUsuario.sumaMarca;
import static practicaarrendadora.cliente.funcionesUsuario.sumaModelo;

public class funcionesAdmin {

    public static String[][] diasDescuento = new String[5][2];
    public static int filaDescuento = 0;
    public static int contadorColumna = 0;
    public static String arrendado = "";
    public static String MarcaReporte = "";
    public static String DiasReporte = "";
    public static String[][] MarcasReporte = new String[10][5];
    public static String[][] ModelosReporte = new String[10][5];

    public static void menuAdmin() {
        Scanner sn = new Scanner(System.in);
        System.out.println("--Menu Administrador--");
        System.out.println("1. Agregar vehiculo");
        System.out.println("2. Agregar Descuento");
        System.out.println("3. Modificar Vehiculo");
        System.out.println("4. Mostrar usuarios");
        System.out.println("5. Eliminar vehiculo");
        System.out.println("6. Realizar Reportes");
        System.out.println("7. Cerrar Sesion");

        String opcion = sn.nextLine();
        boolean ad = true;
        while (ad) {

            switch (opcion) {
                case "1":
                    ad = false;
                    agregarVehiculos();

                    break;

                case "2":
                    ad = false;
                    descuento();

                    break;

                case "3":
                    ad = false;
                    editarCarro();

                    break;

                case "4":
                    ad = false;
                    System.out.println("Nit     Nombre    Apellido");
                    funcionesUsuario.mostrarUsuario();

                    break;

                case "5":
                    ad = false;
                    eliminarCarro();

                    break;

                case "6":
                    ad = false;
                    reportes();
                    break;

                case "7":
                    ad = false;
                    menuPrincipal();

                    break;

                default:
                    System.out.println("Ingrese un numero valido");
                    opcion = sn.nextLine();
                    break;
            }
        }

    }

    public static void agregarVehiculos() {

        String agregarOtravez = "";
        String respuesta = "";

        int aColumna = 0;

        do {

            aColumna = 0;

            System.out.println("Ingrese la marca del vehiculo");
            String nombreAuto = sn.nextLine();
            PracticaArrendadora.vehiculos[iFila][aColumna] = nombreAuto;

            System.out.println("Ingrese la linea del vehiculo");
            String lineaAuto = sn.nextLine();
            aColumna = aColumna + 1;
            PracticaArrendadora.vehiculos[iFila][aColumna] = lineaAuto;

            System.out.println("Ingrese el modelo del vehiculo");
            String modelo = sn.nextLine();
            aColumna = aColumna + 1;
            PracticaArrendadora.vehiculos[iFila][aColumna] = modelo;

            System.out.println("Ingrese la placa del vehiculo");
            String placa = sn.nextLine();
            //************Verificando si la placa ya existe /****************
            boolean retorno = buscarPlaca(placa);

            while (retorno == true) {

                if (retorno) {
                    System.out.println("esta placa ya existe.");
                    System.out.println("Porfavor ingrese otra placa");
                    placa = sn.nextLine();
                    retorno = buscarPlaca(placa);

                } else {
                    System.out.println("no existe");
                }
            }
            aColumna = aColumna + 1;
            PracticaArrendadora.vehiculos[iFila][aColumna] = placa;

            System.out.println("Costo por dia del alquiler");
            String costo = sn.nextLine();
            boolean val = Vnumero(costo);
            while (val) {
                System.out.println("Ingrese un numero");
                costo = sn.nextLine();
                val = Vnumero(costo);
            }

            boolean contesta = Costo(costo);
            while (contesta == false) {
                System.out.println("El valor Ingresado no es correcto");
                costo = sn.nextLine();
                contesta = Costo(costo);
            }
            aColumna = aColumna + 1;
            PracticaArrendadora.vehiculos[iFila][aColumna] = costo;

            arrendado = "false";
            aColumna = aColumna + 1;
            PracticaArrendadora.vehiculos[iFila][aColumna] = arrendado;

            String condicion = "0";

            System.out.println("Se agrego Correctamente");
            //agregando la marca a la matriz marcaReporte y el valor de dias
            boolean verificarMarca = verificarMarcaExistente(nombreAuto);
            if (verificarMarca == false) {
                MarcasReporte[iFila][0] = nombreAuto;
                MarcasReporte[iFila][1] = "0";
            }

            //agregando el modelo a la matriz ModelosReporte y el valor de dias
            boolean verificarsiAgregar = verificarModeloExistente(modelo);
            if (verificarsiAgregar == false) {
                ModelosReporte[iFila][0] = modelo;
                ModelosReporte[iFila][1] = "0";
            }

            iFila = iFila + 1;
            System.out.println("");
            //agregarOtravez="no";

            System.out.println("Desea Agregar otro vehiculo?");
            System.out.println("1. Agregar");
            System.out.println("2. Salir");
            agregarOtravez = sn.nextLine();
            boolean opcionNumero = Vnumero(agregarOtravez);
            while (opcionNumero) {
                System.out.println("Ingrese el num '1' o '2' ");
                agregarOtravez = sn.nextLine();
                opcionNumero = Vnumero(agregarOtravez);
            }
            if (Integer.parseInt(agregarOtravez) == 2) {
                menuAdmin();
            }

        } while (Integer.parseInt(agregarOtravez) == 1);

    }

    public static void editarCarro() {

        String nuevoMarca = "";
        String nuevoModelo = "";
        String nuevaLinea = "";
        String nuevoCosto = "";
        String encontrarP = "";
        boolean boolPlaca = buscarPlaca(encontrarP);
        String editarOtravez = "";
        do {

            System.out.println("Ingrese la placa del carro que quiere modificar");
            encontrarP = sn.nextLine();
            boolPlaca = buscarPlaca(encontrarP);

            if (boolPlaca) {

                System.out.println("Ingrese la nueva marca");

                nuevoMarca = sn.nextLine();
                String fila = retornarFila(encontrarP);
                PracticaArrendadora.vehiculos[Integer.parseInt(fila)][0] = nuevoMarca;
                System.out.println("Se edito con exito");

                System.out.println("Ingrese la nueva linea");

                nuevaLinea = sn.nextLine();
                fila = retornarFila(encontrarP);
                PracticaArrendadora.vehiculos[Integer.parseInt(fila)][1] = nuevaLinea;
                System.out.println("Se edito con exito");

                System.out.println("Ingrese el nuevo Modelo");

                nuevoModelo = sn.nextLine();
                fila = retornarFila(encontrarP);
                PracticaArrendadora.vehiculos[Integer.parseInt(fila)][2] = nuevoModelo;
                System.out.println("Se edito con exito");

                System.out.println("Ingrese el nuevo costo");

                nuevoCosto = sn.nextLine();
                boolean val = Vnumero(nuevoCosto);
                while (val) {
                    System.out.println("Ingrese un numero");
                    nuevoCosto = sn.nextLine();
                    val = Vnumero(nuevoCosto);
                }
                boolean contesta = Costo(nuevoCosto);
                while (contesta == false) {
                    System.out.println("El valor Ingresado no es correcto");
                    nuevoCosto = sn.nextLine();
                    contesta = Costo(nuevoCosto);
                }
                fila = retornarFila(encontrarP);
                PracticaArrendadora.vehiculos[Integer.parseInt(fila)][4] = nuevoCosto;
                System.out.println("Se edito con exito");

                //System.out.println("Marca  Linea   Modelo  Placa   Costo");
                for (int ifila = 0; ifila < 4; ifila++) {

                    for (int columna = 0; columna < 5; columna++) {
                        if (PracticaArrendadora.vehiculos[ifila][columna] != null) {

                            System.out.print(PracticaArrendadora.vehiculos[ifila][columna] + '\t');
                        }
                    }
                    System.out.println("");

                }

            } else {
                System.out.println("Esta placa no existe");
                editarOtravez = "2";
                menuAdmin();
            }
            System.out.println("Desea Agregar otro vehiculo?");
            System.out.println("1. Editar");
            System.out.println("2. Salir");
            editarOtravez = sn.nextLine();
            boolean opcionNumeroEdit = Vnumero(editarOtravez);
            while (opcionNumeroEdit) {
                System.out.println("Ingrese un numero");
                editarOtravez = sn.nextLine();
                opcionNumeroEdit = Vnumero(editarOtravez);
            }
            if (Integer.parseInt(editarOtravez) == 2) {
                menuAdmin();
            }

        } while (Integer.parseInt(editarOtravez) == 1);
    }

    public static boolean buscarPlaca(String bplaca) {

        for (int fila = 0; fila < PracticaArrendadora.vehiculos.length; fila++) {

            for (int columna = 0; columna < 5; columna++) {

                if (bplaca.equals(PracticaArrendadora.vehiculos[fila][3])) {

                    return true;
                }
            }
        }

        return false;
    }

    public static String retornarFila(String bplaca) {

        for (int fila = 0; fila < PracticaArrendadora.vehiculos.length; fila++) {

            for (int columna = 0; columna < 5; columna++) {

                if (bplaca.equals(PracticaArrendadora.vehiculos[fila][3])) {
                    bplaca = String.valueOf(fila);
                    return bplaca;
                }
            }
        }
        return "hola";

    }

    public static boolean Costo(String precio) {

        if (Integer.parseInt(precio) > 0) {
            return true;

        }
        return false;
    }

    public static boolean verificarPorcentaje(String precio) {

        if (Integer.parseInt(precio) > 0 && Integer.parseInt(precio) < 100) {
            return true;

        }
        return false;
    }

    public static void loginAdmin() {
        String nombreU = "";
        String password = "";

        System.out.println("Ingrese su nombre de usuario");
        nombreU = sn.nextLine();

        System.out.println("Ingrese su Password");
        password = sn.nextLine();
        boolean entrarAdmin = false;
        while (entrarAdmin == false) {
            if (nombreU.equalsIgnoreCase("admin_202200338") && password.equalsIgnoreCase("202200338")) {
                entrarAdmin = true;
                menuAdmin();

            } else {
                System.out.println("Datos incorrectos");
                System.out.println("Ingrese su nombre de usuario");
                nombreU = sn.nextLine();

                System.out.println("Ingrese su Password");
                password = sn.nextLine();
                entrarAdmin = false;
            }

        }

    }

    public static boolean Vnumero(String numero) {
        try {
            Integer.parseInt(numero);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static void descuento() {
        String dias = "0";
        String porcentaje = "0";

        contadorColumna = 0;
        System.out.println("Dias de alquiler");
        dias = sn.nextLine();
        Vnumero(String.valueOf(dias));
        boolean verificandoV = Vnumero(String.valueOf(dias));
        while (verificandoV) {
            System.out.println("Ingrese un numero ");
            dias = sn.nextLine();
            verificandoV = Vnumero(String.valueOf(dias));
        }
        boolean contesta = Costo(String.valueOf(dias));
        while (contesta == false) {
            System.out.println("El valor Ingresado no es correcto");
            dias = sn.nextLine();
            contesta = Costo(String.valueOf(dias));
        }
        diasDescuento[filaDescuento][contadorColumna] = dias;

        System.out.println("Porcentaje de descuento ");
        porcentaje = sn.nextLine();
        Vnumero(String.valueOf(porcentaje));
        boolean verificandoPorcentaje = Vnumero(String.valueOf(porcentaje));
        while (verificandoPorcentaje) {
            System.out.println("Ingrese un numero entero");
            porcentaje = sn.nextLine();
            verificandoPorcentaje = Vnumero(String.valueOf(porcentaje));
        }
        boolean mayoraCero = verificarPorcentaje(String.valueOf(porcentaje));
        while (mayoraCero == false) {
            System.out.println("El valor Ingresado no es correcto");
            porcentaje = sn.nextLine();
            mayoraCero = verificarPorcentaje(String.valueOf(porcentaje));
        }
        contadorColumna = contadorColumna + 1;
        diasDescuento[filaDescuento][contadorColumna] = (porcentaje);
        filaDescuento = filaDescuento + 1;
        System.out.println("Presione una letra para volver");
        String volver = sn.nextLine();
        menuAdmin();

    }

    public static void eliminarCarro() {

        String encontrarCarro = "";

        System.out.println("ingrese la placa del Carro que dese eliminar");
        encontrarCarro = sn.nextLine();
        boolean placaEliminar = false;

        placaEliminar = buscarPlaca(encontrarCarro);
        String filaArrendado = retornarFila(encontrarCarro);
        if (placaEliminar) {
            if (PracticaArrendadora.vehiculos[Integer.parseInt(filaArrendado)][5].equals("false")) {

                String filaEliminar = retornarFila(encontrarCarro);

                PracticaArrendadora.vehiculos[Integer.parseInt(filaEliminar)][0] = null;
                PracticaArrendadora.vehiculos[Integer.parseInt(filaEliminar)][1] = null;
                PracticaArrendadora.vehiculos[Integer.parseInt(filaEliminar)][2] = null;
                PracticaArrendadora.vehiculos[Integer.parseInt(filaEliminar)][3] = null;
                PracticaArrendadora.vehiculos[Integer.parseInt(filaEliminar)][4] = null;
                System.out.println("eliminado con exito");
                sleep();
                menuAdmin();

            } else {
                System.out.println("Este auto esta Rentado");
                menuAdmin();
            }
        } else {
            System.out.println("Esta placa no existe");
            menuAdmin();
        }
    }

    public static String retornarPrecioCarro(String placaPrecio) {

        for (int fila = 0; fila < PracticaArrendadora.vehiculos.length; fila++) {

            for (int columna = 0; columna < 5; columna++) {

                if (placaPrecio.equals(PracticaArrendadora.vehiculos[fila][3])) {
                    placaPrecio = String.valueOf(PracticaArrendadora.vehiculos[fila][4]);
                    return placaPrecio;

                }

            }
        }
        return "hola";

    }

    public static String guardarPorcentajes(String placaPrecio) {

        String porcentaje = "0";
        for (int filaP = 0; filaP < diasDescuento.length; filaP++) {
            for (int columnaP = 0; columnaP < diasDescuento[filaP].length; columnaP++) {

                if (diasDescuento[filaP][columnaP] != null) {
                    if ((Integer.parseInt(placaPrecio) > Integer.parseInt(diasDescuento[filaP][0]))) {
                        porcentaje = diasDescuento[filaP][1];
                    }
                }
            }
        }
        return porcentaje;

    }

    public static String retornarModeloVehiculos(String PlacaBuscarModelo) {
        for (int fila = 0; fila < PracticaArrendadora.vehiculos.length; fila++) {

            for (int columna = 0; columna < 5; columna++) {

                if (PlacaBuscarModelo.equals(PracticaArrendadora.vehiculos[fila][3])) {
                    PlacaBuscarModelo = String.valueOf(PracticaArrendadora.vehiculos[fila][2]);
                    return PlacaBuscarModelo;

                }

            }
        }

        return "false";
    }

    public static String retornarMarcaVehiculos(String PlacaBuscarMarca) {
        for (int fila = 0; fila < PracticaArrendadora.vehiculos.length; fila++) {

            for (int columna = 0; columna < 5; columna++) {

                if (PlacaBuscarMarca.equals(PracticaArrendadora.vehiculos[fila][3])) {
                    PlacaBuscarMarca = String.valueOf(PracticaArrendadora.vehiculos[fila][0]);
                    return PlacaBuscarMarca;

                }

            }
        }

        return "false";
    }

    public static void reportes() {

        if (iFila > 0) {
            if (arrendado == "true") {
                System.out.println("Modelo  Total de dias Rentado");
                for (int filaModeloMatriz = 0; filaModeloMatriz < ModelosReporte.length; filaModeloMatriz++) {
                    for (int columnaModeloMatriz = 0; columnaModeloMatriz < 3; columnaModeloMatriz++) {
                        if (ModelosReporte[filaModeloMatriz][columnaModeloMatriz] != null) {
                            System.out.print(ModelosReporte[filaModeloMatriz][columnaModeloMatriz] + '\t');

                        }
                    }
                    System.out.println(" ");
                }
                System.out.println("total:  " + sumaModelo);
                System.out.println("");

                System.out.println("Marca   Total de dias Rentado");
                for (int filaMarcaMatriz = 0; filaMarcaMatriz < MarcasReporte.length; filaMarcaMatriz++) {
                    for (int columnaMarcaMatriz = 0; columnaMarcaMatriz < 3; columnaMarcaMatriz++) {
                        if (MarcasReporte[filaMarcaMatriz][columnaMarcaMatriz] != null) {
                            System.out.print(MarcasReporte[filaMarcaMatriz][columnaMarcaMatriz] + '\t');

                        }
                    }
                    System.out.println(" ");
                }
                System.out.println("total  " + sumaMarca);
                System.out.println("");
                System.out.println("Presione una tecla para salir");
                menuAdmin();

            } else {
                System.out.println("no hay carros arrendados");
                menuAdmin();
            }

        } else {
            System.out.println("No hay vehiculos");
            menuAdmin();
        }
    }

    public static boolean verificarModeloExistente(String modelo) {

        for (int filaModeloMatriz = 0; filaModeloMatriz < ModelosReporte.length; filaModeloMatriz++) {
            for (int columnaModeloMatriz = 0; columnaModeloMatriz < 3; columnaModeloMatriz++) {
                if (ModelosReporte[filaModeloMatriz][columnaModeloMatriz] != null) {

                    if (ModelosReporte[filaModeloMatriz][columnaModeloMatriz].equals(modelo)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean verificarMarcaExistente(String marca) {

        for (int filaMarcaMatriz = 0; filaMarcaMatriz < MarcasReporte.length; filaMarcaMatriz++) {
            for (int columnaMarcaMatriz = 0; columnaMarcaMatriz < 3; columnaMarcaMatriz++) {
                if (MarcasReporte[filaMarcaMatriz][columnaMarcaMatriz] != null) {

                    if (MarcasReporte[filaMarcaMatriz][columnaMarcaMatriz].equals(marca)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
