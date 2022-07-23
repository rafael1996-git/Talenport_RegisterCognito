package com.talent.port.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.PUT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.MessageActionType;
import com.talent.port.api.CognitoClean;
import com.talent.port.api.moels.User;
import com.talent.port.api.services.IUserService;



@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/talentPort")
public class UserRestController {

	
	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private IUserService UsuarioService;
	
	@GetMapping("/lista")
	public ResponseEntity<?> All() {
		List<User> listaUser = null;
		Map<String, Object> response = new HashMap<>();

		try {

			listaUser = UsuarioService.findAll();

		} catch (DataAccessException e) {
			response.put("mensaje", "Error del servidor contacte ");
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		if (listaUser == null || listaUser.isEmpty()) {
			response.put("mensaje", "No hay Registros en la base de datos!");
			response.put("NOT_FOUND", HttpStatus.NOT_FOUND);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
		log.info("_________"+listaUser);
		response.put("statusCode", HttpStatus.OK);
		response.put("msg", "El usuario listado exitoso");
		response.put("Data", listaUser);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	@SuppressWarnings("null")
	@PostMapping("/create")
	public ResponseEntity<?>  create( @Valid @RequestBody User user, BindingResult result) throws Exception{
		User usernew =null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors =result.getFieldErrors().stream().map(err ->"Field '"+err.getField()+"' " +err.getDefaultMessage() ).collect(Collectors.toList());
			response.put("statusCode",400);
			response.put("Data", null);
			response.put("errors", errors);
			response.put("msg", "Error in filling the form");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST) ;
		}
		try {
//			CognitoClean opj=new CognitoClean();
//			opj.CognitoAws(user.getGmail().toString(), user.getContraseña().toString());
			UUID uuid = UUID.randomUUID();
	        String uuidAsString = uuid.toString();
	        user.setToken(uuidAsString);
	        System.out.println("Your UUID is: " + uuidAsString + ": " + user.getToken());
	        UsuarioService.save(user);
	        usernew.setNombre(user.getNombre());
	        usernew.setApellido(user.getApellido());
	        usernew.setTelefono(user.getTelefono());
	        usernew.setGmail(user.getGmail());
	        usernew.setFecha(user.getFecha());

		} catch (DataAccessException e) {
			response.put("statusCode", 500);
			response.put("Data", null);
			response.put("msg","Error performing database insert ");
			log.error(e.getMessage());
			response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
	
		response.put("statusCode", 201);
		response.put("Data", usernew);
		response.put("msg","Successful User Creation ");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
		
	}
	
	
	
	
	
	
}
