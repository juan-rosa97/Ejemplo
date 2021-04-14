import java.sql.ResultSet;
import java.util.Scanner;

public class Usuarios {
	private String user;
	private String pass;
	private String num_tarjeta;
	private String direccion;
	private String rango;
	
	public Usuarios() {
		this.user = "";
		this.pass = "";
		this.num_tarjeta = "";
		this.direccion = "";
		this.rango = "Usuario";
	}
	
	public Usuarios(String user, String pass, String num_tarjeta, String direccion, String rango) {
		this.user = user;
		this.pass = pass;
		this.num_tarjeta = num_tarjeta;
		this.direccion = direccion;
		this.rango = rango;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPass() {
		return pass;
	}
	

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getNum_tarjeta() {
		return num_tarjeta;
	}
	
	public void setNum_tarjeta(String num_tarjeta) {
		this.num_tarjeta = num_tarjeta;
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getRango() {
		return rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}
	
	void registrarse() {
		Scanner teclado=new Scanner(System.in);
		boolean fin=false;

		while (fin==false) {
			System.out.print("Introduzca nombre de usuario: ");
			setUser(teclado.nextLine());
			System.out.print("Introduzca la contraseña: ");
			setPass(teclado.nextLine());
			//buscar en la base de datos si existe alguien con ese nombre, si existe pues avisar que el nombre esta en uso
			String query="Select * from usuarios where user='"+getUser()+"';";
			try {
				ResultSet rs=mysql.IniciarSesion(query);
				if (rs!=null) {
					System.out.println("Usuario ya registrado. Elija otro, por favor.");
				} else {
					// si no existe ese usuario se procede a pedir los otros datos y se introducen en la base de datos
					fin=true;
					System.out.print("Introduzca numero de tarjeta: ");
					setNum_tarjeta(teclado.nextLine());
					System.out.print("Introduzca la direccion de envio: ");
					setDireccion(teclado.nextLine());
					query="Insert into usuarios values ('"+getUser()+"','"+getPass()+"','"+getNum_tarjeta()+"','"+getDireccion()+"','usuario');";
					mysql.ejecutar(query);
					usuario_normal();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void iniciar_sesion() {
		Scanner teclado=new Scanner(System.in);
		boolean fin=false;
		while (fin==false) {
			System.out.print("Usuario: ");
			setUser(teclado.next());
			System.out.print("Contraseña: ");
			setPass(teclado.next());
			String query = "Select * from usuarios where user='"+getUser()+"' and pass='"+getPass()+"'";
			// aqui pedimos el resultset. si es nulo significa que el usuario o la contraseña esta equivocado
			try {
				ResultSet rs=mysql.IniciarSesion(query);
				if (rs==null) {
					System.out.println("Usuario o contraseña equivocados.");
				} else {
					// aqui segun el rango que tiene(usuario o admin) sacaremos un menu diferente
					fin=true;
					setNum_tarjeta(rs.getString(3));
					setDireccion(rs.getString(4));
					setRango(rs.getString(5));
					if (getRango().equals("usuario")) { 
						usuario_normal();
					} else if (getRango().equals("admin")) {
						admin();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void usuario_normal() {
		Scanner teclado=new Scanner(System.in);
		char letra='1';
		String query="";
		do {
			System.out.println();
			System.out.println("Bienvenido, "+getUser());
			System.out.println();
			System.out.println("a.	Cambiar mis datos personales.\r\n"
					+ "b.	Visitar la tienda.\r\n"
					+ "c.	Añadir producto a la cesta.\r\n"
					+ "d.	Entrar en la cesta.\r\n"
					+ "e.	Salir de mi cuenta.\r\n"
					+ "");
			letra=teclado.next().charAt(0);
			switch (letra) {
			case 'a':
				cambiar_datos();				
				break;
			case 'b':
				query="select * from productos";
				mysql.mostrarProd(query);
				break;
			case 'c':
				/*query="select * from pedido where id_usuario="+id+";";
				String cont="select count(*) from pedido where id_usuario="+id+";";
				mysql.mostrarPed(query,cont);*/
				break;
			case 'd':
				break;
			case 'e':
				System.out.println("Adios, "+user+".");
				break;
			default:
			
			}
		} while(letra!='e');
	}
	
	void admin() {
		Scanner teclado=new Scanner(System.in);

		char letra='j';
		do {
			System.out.println();
			System.out.println("Bienvenido, "+getUser()+". Eres administrador del sitio.");
			System.out.println();
			System.out.println("a.	Introducir nuevo producto.\r\n"
				+ "b.	Modificar datos del producto.\r\n"
				+ "c.	Eliminar un producto.\r\n"
				+ "d.	Ver datos de un usuario.\r\n"
				+ "e.	Cambiar permisos de un usuario.\r\n"
				+ "f.	Salir de mi cuenta.\r\n"
				+ "");
			letra=teclado.next().charAt(0);
			switch (letra) {
			case 'a':
				producto p=new producto();
				p.nuevo();
				break;
			case 'b':
				producto p2=new producto();
				p2.modificar();
				break;
			case 'c':
				producto p3=new producto();
				p3.eliminar();
				break;
			case 'd':
				ver_datos();
				break;
			case 'e':
				cambiar_permisos();
				break;
			case 'f':
				System.out.println("Adios, "+user+".");
				break;
			default:

			}
		} while (letra!='f');
	}

	void cambiar_datos() {
		Scanner teclado=new Scanner(System.in);
		
		int opcion=0;
		do {
			System.out.println("Usuario: "+getUser()+"\r\nContraseña: "+getPass()+"\r\nNumero de Tarjeta: "+getNum_tarjeta()+ "\r\nDirección: "+getDireccion()+"\r\n");
			System.out.println("1.	Cambiar nombre de usuario.\r\n"
					+ "2.	Cambiar contraseña.\r\n"
					+ "3.	Cambiar numero de tarjeta.\r\n"
					+ "4.	Cambiar dirección.\r\n"
					+ "0.	Atras.\r\n"
					+ "");
			opcion=teclado.nextInt();
			switch(opcion) {
			case 1:
				String nuevo_usuario="";
				while (!nuevo_usuario.equals(getUser())) {
					System.out.print("Introduce el nuevo usuario: ");
					nuevo_usuario=teclado.next();
					String query = "Select * from usuarios where user='"+nuevo_usuario+"'";
					// aqui pedimos el resultset. si es nulo significa que el usuario o la contraseña esta equivocado
					try {
						ResultSet rs=mysql.IniciarSesion(query);
						if (rs==null) {
							// si aqui no devuelve nada es que el usuario no esta cogido, asi que se puede actualizar
							query="update usuarios set user='"+nuevo_usuario+"' where user='"+getUser()+"';";
							mysql.ejecutar(query);
							setUser(nuevo_usuario);
							System.out.println("¡Nombre de usuario cambiado con exito! \r\n");
						} else {
							System.out.println("Usuario ya registrado. Por favor, elija otro nombre");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 2:
				System.out.print("Introduce la nueva contraseña: ");
				setPass(teclado.nextLine());
				String query="update usuarios set pass='"+getPass()+"' where user='"+getUser()+"';";
				mysql.ejecutar(query);
				System.out.println("¡Contraseña cambiada con exito! \r\n");
				break;
			case 3:
				System.out.print("Introduce el nuevo numero de tarjeta: ");
				setNum_tarjeta(teclado.nextLine());
				query="update usuarios set num_tarjeta='"+getNum_tarjeta()+"' where user='"+getUser()+"';";
				mysql.ejecutar(query);
				System.out.println("¡Numero de tarjeta cambiado con exito! \r\n");
				break;
			case 4:
				System.out.print("Introduce la nueva direccion: ");
				teclado.nextLine();
				setDireccion(teclado.nextLine());
				query="update usuarios set direccion='"+getDireccion()+"' where user='"+getUser()+"';";
				mysql.ejecutar(query);
				System.out.println("¡Dirección cambiada con exito! \r\n");
				break;
			case 0:
				
				System.out.println("Guardando cambios... \r\n");
				break;
			default:
				System.out.println("Opción no válida.");
			}
		} while (opcion!=0);	
	}
}

