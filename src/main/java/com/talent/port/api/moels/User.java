package com.talent.port.api.moels;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.talent.port.api.utils.ValidPhoneNumber;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IDUSER")
	private Long id;

	@Column(name = "NAME")
	@NotEmpty(message = "Please enter the Missing Required Fields")

	private String nombre;
	@NotEmpty(message = "Please enter the Missing Required Fields")
	@Column(name = "LASTNAME")
	private String apellido;
	@Pattern(regexp = "^(\\+\\d{1,3}( )?)?(\\(\\d{3}\\)|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message="Please enter a valid phone number")
//	@ValidPhoneNumber(message="Please enter a valid phone number")
	@Column(name = "PHONE")
	private String telefono;
	@NotEmpty(message = "Please enter the Missing Required Fields")
	@Column(name = "EMAIL")
	@Email(message = "Must be a Properly Formatted Email Address")
	private String gmail;
	@NotNull(message = "the date field cannot be empty")
    @DateTimeFormat(iso = ISO.DATE ,pattern = "yyyy/dd/MM" )
    @NotNull
	@Column(name = "CREATE_AT")
	private Date fecha;
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{9,50}", message = "incorrect password")
	@NotEmpty(message = "Please enter the Missing Required Fields")
	@Column(name = "PASSWORD")
	private String contraseña;

	@Column(name = "TOKEN")
	private String token;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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


	
	public User( String nombre, String apellido, String telefono, String gmail, Date fecha, String contraseña,
			String token) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.gmail = gmail;
		this.fecha = fecha;
		this.contraseña = contraseña;
		this.token = token;
	}



	

	private static final long serialVersionUID = 1L;
}
