import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		// ideas: mostrar y cambiar datos personales
		// los admin pueden modificar precios de los productos
		// hacer stock de productos e ir sumando y restando
		// crear una cesta a la que añadir productos
		// crear un archivo con la factura
		// los admin pueden ascender a los usuarios normales. crear un superadmin que puede relegar a los demas admin
		// para que un admin ascienda a un usuario debera introducir su usuario
		// muy dificil: consultar si en el banco hay dinero suficiente para realizar la operacion
		Scanner teclado=new Scanner(System.in);
		int opcion=0;
		
		System.out.println("Bienvenido a Amasonia.es, su tienda de confianza. Inicie sesión o registrese para continuar");

		do {
			//primero preguntar si quiere iniciar sesion o registrarse
			System.out.println("1: Iniciar Sesión. \r\n2: Registrarse");
			opcion=teclado.nextInt();
		} while(opcion!=1 && opcion!=2);
		
		Usuarios u=new Usuarios();

		if (opcion==1) {				
			u.iniciar_sesion();
		} else if (opcion==2) {
			u.registrarse();
		}
			
	}
}
