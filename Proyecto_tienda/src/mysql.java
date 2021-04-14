import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class mysql {
	//cambiar el nombre de la base de datos en las conexiones cuando cree la base de datos
	static void datos_user(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String base_datos = "jdbc:mysql://localhost:3306/proyecto?useSSL=false";
			String user = "root";
			String pass = "roble";
			Connection con = DriverManager.getConnection(base_datos, user, pass);
			Statement stn = con.createStatement();
			ResultSet rs = stn.executeQuery(query);
			while (rs.next()) {
				// Podemos poner las columnas por su orden numerico o por su nombre
				System.out.println();
				System.out.println("Usuario: " + rs.getInt(1));
				System.out.println("Contraseña: " + rs.getString(2));
				System.out.println("Numero de tarjeta: "+rs.getString(3));
				System.out.println("Dirección: " + rs.getString(4));
				System.out.println("Rango: " + rs.getString(4));
				System.out.println();
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void ejecutar(String query) {
		// Subprograma que recibe un query y ejecuta
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String base_datos = "jdbc:mysql://localhost:3306/proyecto?useSSL=false";
			String userbd = "root";
			String passbd = "roble";
			Connection con = DriverManager.getConnection(base_datos, userbd, passbd);
			Statement stn = con.createStatement();
			stn.executeUpdate(query);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static ResultSet IniciarSesion(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String base_datos = "jdbc:mysql://localhost:3306/proyecto?useSSL=false";
			String userbd = "root";
			String passbd = "roble";
			Connection con = DriverManager.getConnection(base_datos, userbd, passbd);
			Statement stn = con.createStatement();
			ResultSet rs = stn.executeQuery(query);
			if (rs.next()) {
				return rs;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	static void mostrarProd(String query) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String base_datos = "jdbc:mysql://localhost:3306/proyecto?useSSL=false";
			String userbd = "root";
			String passbd = "roble";
			Connection con = DriverManager.getConnection(base_datos, userbd, passbd);
			Statement stn = con.createStatement();
			ResultSet rs = stn.executeQuery(query);
			while (rs.next()) {
				if (rs.getInt(5)>0) {
					System.out.println("Articulo: "+rs.getString(3));
					System.out.println("ID: "+rs.getInt(1));
					System.out.println("Marca: "+rs.getString(2));
					System.out.println("Precio: "+rs.getFloat(4));
					System.out.println("Stock: "+rs.getInt(5));
				}				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void mostrarPed(String query,String cont) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String base_datos = "jdbc:mysql://localhost:3306/proyecto?useSSL=false";
			String userbd = "root";
			String passbd = "roble";
			Connection con = DriverManager.getConnection(base_datos, userbd, passbd);
			Statement stn = con.createStatement();
			ResultSet rs = stn.executeQuery(cont);
			if (rs.next()) {
				int tamano=rs.getInt(1);
			}
			rs=stn.executeQuery(query);
			pedido pedido=new pedido();
			if(rs.next()) {
				pedido.id=rs.getInt(1);
				pedido.id_cliente=rs.getInt(3);
				//pedido.fecha=new fecha(rs.getDate(4));
				pedido.importe_total=rs.getFloat(5);
				int i=0;
				pedido.articulo []=new producto[tamano];
			}
			while (rs.next()) {
				
				
								
			} 
			
			System.out.println("Articulo: "+rs.getString(3));
			System.out.println("ID: "+rs.getInt(1));
			System.out.println("Marca: "+rs.getString(2));
			System.out.println("Precio: "+rs.getFloat(4));
			System.out.println("Stock: "+rs.getInt(5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
