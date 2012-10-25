package py.com.pg.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import py.com.pg.webstock.entities.Cliente;
import py.com.pg.webstock.entities.Pago;
import py.com.webstock.ejb.services.Services;

public class Main {

	private static BufferedReader br;
	static Caller caller;

	static SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"HH:mm:ss dd/MM/yyyy");

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		Integer opcion = 0;
		do {
			System.out.println("Elija un proveedor de servicios");
			System.out.println("1 - Enterprise Java Beans");
			System.out.println("2 - Web Service");
			try {
				opcion = Integer.parseInt(br.readLine());
			} catch (Exception e) {
				System.out.println("Ingrese una opcion correcta");
			}
		} while (opcion < 1 || opcion > 2);
		switch (opcion) {
		case 1:
			caller = getByEJB();
			break;
		case 2:
			caller = getByWS();
		}
		do {
			System.out.println("Ingrese una opcion");
			System.out.println("Actualmente usando: "
					+ caller.getClass().getSimpleName());
			System.out.println("1 - Ingrese un pago");
			System.out.println("2 - Ingrese un archivo");
			System.out.println("3 - Cambiar a Enterprise Java Beans");
			System.out.println("4 - Cambiar a Web Service");
			try {
				opcion = Integer.parseInt(br.readLine());
				if (opcion < 1 || opcion > 4) {
					System.out.println("Ingrese 1 o 2");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Ingrese una opcion correcta");
				continue;
			}
			switch (opcion) {
			case 1:
				pagoIndividual();
				break;
			case 2:
				pagoArchivo();
				break;
			case 3:
				caller = getByEJB();
				break;
			case 4:
				caller = getByWS();

			}
		} while (true);

	}

	private static void pagoIndividual() throws Exception {
		Pago p = new Pago();
		System.out.println("Ingrese un pago");
		System.out.println("Ingrese un codigo de pago");
		p.setCodPago(Integer.parseInt(br.readLine()));
		System.out.println("Ingrese un codigo de cliente");
		Cliente c = new Cliente();
		c.setId(Integer.parseInt(br.readLine()));
		p.setCliente(c);
		System.out.println("Ingrese un monto de pago");
		p.setMonto(Double.parseDouble(br.readLine()));
		System.out
				.println("Ingrese una fecha en el formato HH:mm:ss dd/MM/yyyy");
		p.setfecha(formatoFecha.parse(br.readLine()));
		for (String s : caller.ejecutarPagos(p))
			System.out.println(s);
	}

	private static void pagoArchivo() throws Exception {
		File f;
		System.out.println("Ingrese un nombre de archivo");
		f = new File(br.readLine());
		while (!f.exists()) {
			System.out
					.println("Ingrese un nombre correcto, el archivo no existe");
			f = new File(br.readLine());
		}
		Pago[] pagos = cargarArchivo(new InputStreamReader(new FileInputStream(
				f)));
		for (String s : caller.ejecutarPagos(pagos))
			System.out.println(s);
	}

	private static Caller getByWS() {
		return new WSCaller((new Services()).getPagoPort());
	}

	public static Caller getByEJB() throws NamingException {
		return new EJBCaller(EJBHelper.lookupRemotePagoService());
	}

	public static Pago[] cargarArchivo(InputStreamReader isr) throws Exception {

		BufferedReader bf = new BufferedReader(isr);
		String linea = bf.readLine();
		String partes[];
		List<Pago> pagos = new ArrayList<Pago>();
		while (linea != null && linea != "") {
			Pago nuevo = new Pago();
			partes = linea.split(";");
			nuevo.setCodPago(Integer.parseInt(partes[0]));
			Cliente c = new Cliente();
			c.setId(Integer.parseInt(partes[1]));
			nuevo.setCliente(c);
			nuevo.setMonto(Double.parseDouble(partes[2]));
			nuevo.setfecha(formatoFecha.parse(partes[3]));
			pagos.add(nuevo);
			linea = bf.readLine();
		}
		return pagos.toArray(new Pago[pagos.size()]);
	}
}
