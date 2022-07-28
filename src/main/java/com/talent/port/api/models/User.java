package com.talent.port.api.models;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Serializable {

	private Long id;

	@NotEmpty(message = "Please enter the Missing Required Fields")
	@NotNull(message = " field cannot be empty")
	private String nombre;
	@NotNull(message = " field cannot be empty")
	@NotEmpty(message = "Please enter the Missing Required Fields")
	private String apellido;
	@Pattern(regexp = "^(\\+\\d{1,3}( )?)?(\\(\\d{3}\\)|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Please enter a valid phone number")
	@NotNull(message = " field cannot be empty")
	private String telefono;
	@NotEmpty(message = "Please enter the Missing Required Fields")
	@NotNull(message = "field cannot be empty")
	@Email(message = "Must be a Properly Formatted Email Address")
	private String gmail;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date created_at;
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{9,500}", message = "incorrect password")
	@NotEmpty(message = "Please enter the Missing Required Fields")
	@NotBlank(message = " password is mandatory")
	private String contraseña;

	private String token;

	@DateTimeFormat(iso = ISO.DATE)
	private Date updated_at;

	@NotNull(message = "field cannot be empty")
	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy/dd/MM")
	private Date fecha_nacimiento;

	@NotEmpty(message = "Please enter the Missing Required Fields")
	@Transient
	private String retypePassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	@JsonIgnore
	@JsonProperty(value = "created_at")
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date date) {
		this.created_at = date;
	}

	@JsonIgnore
	@JsonProperty(value = "contraseña")
	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@JsonIgnore
	@JsonProperty(value = "token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonIgnore
	@JsonProperty(value = "updated_at")
	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getretypePassword() {
		return retypePassword;
	}

	public void setretypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public User(String nombre, String apellido, String telefono, String gmail, Date created_at, String contraseña,
			String token, Date updated_at, Date fecha_nacimiento) {

		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.gmail = gmail;
		this.created_at = created_at;
		this.contraseña = contraseña;
		this.token = token;
		this.updated_at = updated_at;
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
}
