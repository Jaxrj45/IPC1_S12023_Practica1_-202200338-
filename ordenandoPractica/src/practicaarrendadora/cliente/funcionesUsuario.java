package practicaarrendadora.cliente;

import java.time.LocalDateTime;
import static practicaarrendadora.PracticaArrendadora.clientes;
import static practicaarrendadora.PracticaArrendadora.iCliente;
import static practicaarrendadora.PracticaArrendadora.iFila;
import static practicaarrendadora.PracticaArrendadora.menuPrincipal;
import static practicaarrendadora.PracticaArrendadora.sn;
import static practicaarrendadora.PracticaArrendadora.vehiculos;
import static practicaarrendadora.admin.funcionesAdmin.Costo;
import static practicaarrendadora.admin.funcionesAdmin.MarcasReporte;
import static practicaarrendadora.admin.funcionesAdmin.ModelosReporte;
import static practicaarrendadora.admin.funcionesAdmin.Vnumero;
import static practicaarrendadora.admin.funcionesAdmin.arrendado;
import static practicaarrendadora.admin.funcionesAdmin.buscarPlaca;
import static practicaarrendadora.admin.funcionesAdmin.contadorColumna;
import static practicaarrendadora.admin.funcionesAdmin.diasDescuento;
import static practicaarrendadora.admin.funcionesAdmin.filaDescuento;
import static practicaarrendadora.admin.funcionesAdmin.guardarPorcentajes;
import static practicaarrendadora.admin.funcionesAdmin.menuAdmin;
import static practicaarrendadora.admin.funcionesAdmin.retornarFila;
import static practicaarrendadora.admin.funcionesAdmin.retornarMarcaVehiculos;
import static practicaarrendadora.admin.funcionesAdmin.retornarModeloVehiculos;

import static practicaarrendadora.admin.funcionesAdmin.retornarPrecioCarro;

public class funcionesUsuario {

    public static String nitClienteLog = "";
    public static boolean logged = false;
    public static int sumaModelo = 0;
    public static int sumaMarca = 0;
    public static int contadorAutosDisponibles=0;

    public static void menuCliente() {

        System.out.println("--Menu Cliente--");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar Sesión");
        System.out.println("3. Realizar reservas");
        System.out.println("4. Cerrar Sesion");

        String opMenuCliente = sn.nextLine();
        boolean a = true;
        while (a) {
            switch (opMenuCliente) {

                case "1":
                    agregarCliente();
                    a = false;
                    break;
                case "2":
                    loginCliente();
                    a = false;
                    break;
                case "3":
                    rentarC();
                    a = false;
                    break;
                case "4":
                    logged = false;
                    menuPrincipal();
                    a = false;
                    break;

                default:
                    System.out.println("Opcion invalida vuelva a intentarlo");
                    opMenuCliente = sn.nextLine();
                    break;
            }

        }

    }

    public static void rentarC() {
        String placaRentar = "";
        String diasRenta = "";
        do {
            
        
        if (iFila >= 0) {
            if (logged == true) {

                System.out.println("-------------Autos Disponibles------------------------------");
                System.out.println("Marca  Linea   Modelo   Placa   Costo  Arrendado");
                for (int fila = 0; fila < vehiculos.length; fila++) {

                    for (int columna = 0; columna < vehiculos[fila].length; columna++) {
                        if (vehiculos[fila][columna] != null) {
                            if (vehiculos[fila][5].equalsIgnoreCase("false")) {

                                System.out.print(vehiculos[fila][columna] + " " + '\t');
                                contadorAutosDisponibles=vehiculos[fila].length;
                                
                            }
                        }
                    }
                    System.out.println("");
                }

                System.out.println("---------Descuentos segun dias de arrendamiento-----------");
                System.out.println("");
                System.out.println("Dias    Porcentaje de Descuento %");

                for (int filaDescuento = 0; filaDescuento < diasDescuento.length; filaDescuento++) {

                    for (int columnaDescuento = 0; columnaDescuento < diasDescuento[filaDescuento].length; columnaDescuento++) {
                        if (diasDescuento[filaDescuento][columnaDescuento] != null) {
                            System.out.print(diasDescuento[filaDescuento][columnaDescuento] + '\t');
                        }
                    }
                    System.out.println("");
                }

                System.out.println("Ingrese la placa del vehiculo que quiere rentar");
                placaRentar = sn.nextLine();
                buscarPlaca(placaRentar);
                boolean verificacionPlaca = buscarPlaca(placaRentar);

                while (verificacionPlaca == false) {

                    if (verificacionPlaca == false) {
                        System.out.println("esta placa no existe.");
                        System.out.println("Porfavor ingrese otra placa");
                        placaRentar = sn.nextLine();
                        verificacionPlaca = buscarPlaca(placaRentar);

                    }
                }
                System.out.println("Placa Valida");
                String filaCarroArrendado = retornarFila(placaRentar);
                arrendado = "true";
                vehiculos[Integer.parseInt(filaCarroArrendado)][5] = arrendado;
                vehiculos[Integer.parseInt(filaCarroArrendado)][6] = nitClienteLog;
                System.out.println("Ingrese la cantidad de Dias que desea Rentar el vehiculo");
                diasRenta = sn.nextLine();

                //vehiculos[Integer.parseInt(filaCarroArrendado)][6] = diasRenta;
                boolean noEsNumero = Vnumero(diasRenta);
                while (noEsNumero) {
                    System.out.println("Ingrese un numero");
                    diasRenta = sn.nextLine();
                    noEsNumero = Vnumero(diasRenta);
                }

                boolean mayoraCero = Costo(diasRenta);
                while (mayoraCero == false) {
                    System.out.println("El valor Ingresado no es correcto");
                    diasRenta = sn.nextLine();
                    mayoraCero = Costo(diasRenta);
                }
                //Sumando los dias si el modelo ya esta el la matriz que cree 
                String modeloMatrizVehiculos = retornarModeloVehiculos(placaRentar);
                for (int filaMatrizReporte = 0; filaMatrizReporte < ModelosReporte.length; filaMatrizReporte++) {
                    for (int columnaMatrizReporte = 0; columnaMatrizReporte < ModelosReporte[filaMatrizReporte].length; columnaMatrizReporte++) {
                        if (modeloMatrizVehiculos.equals(ModelosReporte[filaMatrizReporte][columnaMatrizReporte])) {
                            int operacionSuma = Integer.parseInt(ModelosReporte[filaMatrizReporte][1]);

                            operacionSuma = operacionSuma + Integer.parseInt(diasRenta);
                            ModelosReporte[filaMatrizReporte][1] = String.valueOf(operacionSuma);
                            sumaModelo = sumaModelo + Integer.parseInt(diasRenta);
                        }
                    }

                }
                //Sumando los dias si la marca ya aprece en mi matriz 
                String MarcaMatrizVheiculoVehiculos = retornarMarcaVehiculos(placaRentar);
                for (int filaMatrizMarca = 0; filaMatrizMarca < MarcasReporte.length; filaMatrizMarca++) {
                    for (int columnaMatrizMarca = 0; columnaMatrizMarca < MarcasReporte[filaMatrizMarca].length; columnaMatrizMarca++) {
                        if (MarcaMatrizVheiculoVehiculos.equals(MarcasReporte[filaMatrizMarca][columnaMatrizMarca])) {
                            int operacionSuma = Integer.parseInt(MarcasReporte[filaMatrizMarca][1]);

                            operacionSuma = operacionSuma + Integer.parseInt(diasRenta);
                            MarcasReporte[filaMatrizMarca][1] = String.valueOf(operacionSuma);
                            sumaMarca = sumaMarca + Integer.parseInt(diasRenta);
                        }
                    }

                }

                String precioObtenido = retornarPrecioCarro(placaRentar);
                int costoxDias = Integer.parseInt(precioObtenido) * Integer.parseInt(diasRenta);
                String variablePorcentajes = guardarPorcentajes((diasRenta));
                //System.out.println(variablePorcentajes);
                facturaRenta();
                //verificarNit()

                for (int fila = 0; fila < vehiculos.length; fila++) {

                    for (int columna = 0; columna < vehiculos[fila].length; columna++) {
                        if (vehiculos[fila][columna] != null) {
                            if (vehiculos[fila][5].equalsIgnoreCase("true")) {
                                if (nitClienteLog.equals(vehiculos[fila][6])) {

                                    System.out.print(vehiculos[fila][columna] + '\t');
                                }
                            }
                        }
                    }
                    System.out.println("");
                }
                System.out.println("Dias de renta: " + diasRenta);
                System.out.println("Costo por dias de renta: " + costoxDias);
                System.out.println("Porcentaje de Descuento aplicado: " + variablePorcentajes);
                System.out.println("Sub-Total: " + costoxDias);
                int operacion = costoxDias * (Integer.parseInt(variablePorcentajes)) / 100;
                System.out.println("El total con el descuento aplicado es de : ");
                System.out.println(costoxDias - operacion);
                
                //System.out.println(contadorAutosDisponibles);
                //contadorAutosDisponibles=contadorAutosDisponibles-1;
                
                System.out.println("Presiona una tecla para volver al menu");
                String volver = sn.nextLine();
                sleep();
                menuCliente();

            } else {
                System.out.println("Necesita Iniciar Sesion");
                sleep();
                menuCliente();
            }
        } else {
            System.out.println("No hay ningun vehculo registrado");
            sleep();
            menuAdmin();
        }
        } while (contadorAutosDisponibles>0);
    
    }

    public static void mostrarUsuario() {
        String num = "";

        if (iCliente != 0) {

            for (int fila = 0; fila < clientes.length; fila++) {

                for (int columna = 0; columna < clientes[fila].length; columna++) {
                    if (clientes[fila][columna] != null) {

                        System.out.print(clientes[fila][columna] + '\t');

                    }
                }
                System.out.println("");
            }
            System.out.println("Presione cualquier tecla para volver");
            num = sn.nextLine();
            sleep();
            menuAdmin();

        } else {
            System.out.println("No hay ningun usuario registrado");
            sleep();
            menuAdmin();
        }

    }

    public static void agregarCliente() {

        String nit = "";
        String nombreCliente = "";
        String apellidoCliente = "";
        int clienteCol = 0;

        System.out.println("Ingrese su numero de Nit");
        nit = sn.nextLine();
        boolean verificandoV = Vnumero(nit);
        while (verificandoV) {
            System.out.println("Ingrese un numero");
            nit = sn.nextLine();
            verificandoV = Vnumero(nit);
        }
        boolean valorBool = verificarNit(nit);
        while (valorBool) {
            System.out.println("Ingreso un numero de Nit existente");
            System.out.println("Ingrese su numero de Nit");
            nit = sn.nextLine();
            valorBool = verificarNit(nit);
        }
        clienteCol = 0;
        clientes[iCliente][clienteCol] = nit;

        System.out.println("Ingrese su nombre");
        nombreCliente = sn.nextLine();
        clienteCol = clienteCol + 1;
        clientes[iCliente][clienteCol] = nombreCliente;

        System.out.println("Ingrese su apellido");
        apellidoCliente = sn.nextLine();
        clienteCol = clienteCol + 1;
        clientes[iCliente][clienteCol] = apellidoCliente;

        System.out.println("Se agrego correctamente");
        iCliente = iCliente + 1;

        sleep();
        menuCliente();
    }

    public static void loginCliente() {
        String nitCliente = "";

        System.out.println("Ingrese su numero de Nit");
        nitCliente = sn.nextLine();

        boolean boolV = verificarNit(nitCliente);
        if (boolV) {
            System.out.println("Inicio de sesion exitoso");
            nitClienteLog = nitCliente;
            logged = true;
            sleep();
            menuCliente();

        } else {
            System.out.println("El nit ingresado no existe");
            logged = false;
            sleep();
            menuCliente();
        }

    }

    public static boolean verificarNit(String Nit) {

        for (int fila = 0; fila < clientes.length; fila++) {

            for (int columna = 0; columna < clientes.length; columna++) {

                if (Nit.equals(clientes[fila][0])) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void sleep() {
        try {
            Thread.sleep(10 * 100);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void facturaRenta() {

        System.out.println("-------------------------ByteCar-------------------------");
        String nombredelCliente = retornarNombreCliente(nitClienteLog);
        System.out.println("Nombre: " + nombredelCliente);
        System.out.println("Nit: " + nitClienteLog);
        System.out.println("Fecha y Hora: " + LocalDateTime.now());

    }

    public static String retornarNombreCliente(String PNit) {
        String nombreCliente = "no hay coincidencia";

        for (int fila = 0; fila < clientes.length; fila++) {

            for (int columna = 0; columna < clientes.length; columna++) {

                if (PNit.equals(clientes[fila][0])) {
                    nombreCliente = clientes[fila][1];

                }
            }
        }
        return nombreCliente;
    }

}
