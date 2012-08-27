package org.primefaces.examples.view;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.primefaces.sample.User;
import com.primefaces.sample.UserService;

@ManagedBean
@ApplicationScoped
public class TableBean implements Serializable {

	private static final long serialVersionUID = -7989542768982898289L;

	private final static String[] colors;

	private final static String[] manufacturers;

	private String firstName;

	private List<User> usuarios;

	static {
		colors = new String[10];
		colors[0] = "Black";
		colors[1] = "White";
		colors[2] = "Green";
		colors[3] = "Red";
		colors[4] = "Blue";
		colors[5] = "Orange";
		colors[6] = "Silver";
		colors[7] = "Yellow";
		colors[8] = "Brown";
		colors[9] = "Maroon";

		manufacturers = new String[10];
		manufacturers[0] = "Mercedes";
		manufacturers[1] = "BMW";
		manufacturers[2] = "Volvo";
		manufacturers[3] = "Audi";
		manufacturers[4] = "Renault";
		manufacturers[5] = "Opel";
		manufacturers[6] = "Volkswagen";
		manufacturers[7] = "Chrysler";
		manufacturers[8] = "Ferrari";
		manufacturers[9] = "Ford";
	}

	public TableBean() {
		UserService us = new UserService();
		usuarios = us.getAllUsers();

		System.out.println("ME SHAMARON2");
	}

	public List<User> getCarsSmall() {
		System.out.println("ME SHAMARON");
		return usuarios;
	}

	public int getRandomYear() {
		return (int) (Math.random() * 50 + 1960);
	}

	public String getRandomColor() {
		return colors[(int) (Math.random() * 10)];
	}

	public String getRandomManufacturer() {
		return manufacturers[(int) (Math.random() * 10)];
	}

	public String getRandomModel() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	public String[] getManufacturers() {
		return manufacturers;
	}

	public String[] getColors() {
		return colors;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Car Edited",
				((User) event.getObject()).getUsername());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Car Cancelled",
				((User) event.getObject()).getUsername());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
