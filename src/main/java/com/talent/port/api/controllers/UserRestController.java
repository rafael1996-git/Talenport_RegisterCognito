package com.talent.port.api.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.talent.port.api.CognitoClean;
import com.talent.port.api.dao.IUserDao;
import com.talent.port.api.models.User;
import com.talent.port.api.services.IUserService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/talentPort")
public class UserRestController {

	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private IUserService UsuarioService;
	@Autowired
	private IUserDao UsuarioDao;

	@SuppressWarnings("null")
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> dataUser = new HashMap<>();
		Map<String, Object> dataUserpass = new HashMap<>();

		Optional<User> dato = UsuarioDao.findByEmail(user.getGmail());
		user.setretypePassword(user.getContraseña());
		String pass=user.getContraseña();
	
		log.info("paso por aqui::::::: pass: "+ user.getretypePassword()  );

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
			for (Integer i = 0; i < errors.size(); i++) {
				dataUser.put("Field-".concat(i.toString()), errors.get(i));

			}

			response.put("statusCode", 400);
			response.put("Data", null);
			response.put("errors", dataUser);
			response.put("msg", "Error in filling the form");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		 
		 if (dato.isPresent()  ) {
			 
			 dataUser.put("Field", "The user is already registered");
				log.info("paso por aqui::::::: correo presente"  );

		        response.put("statusCode", 400);
		        response.put("Data", null);
		        response.put("errors", dataUser);
		        response.put("msg", "Error in filling the form");
		        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		        
		      }

		
		

		try {
			
			CognitoClean opj=new CognitoClean();
			opj.CognitoAws(user.getGmail().toString(), user.getContraseña().toString());
			UUID uuid = UUID.randomUUID();
			String uuidAsString = uuid.toString();
			user.setToken(uuidAsString);
			System.out.println("Your UUID is: " + uuidAsString + ": " + user.getToken());

			dataUser.put("Lastname", user.getApellido());
			dataUser.put("Phone", user.getTelefono());
			dataUser.put("email", user.getGmail());
			dataUser.put("Lastname", user.getApellido());
			dataUser.put("date", user.getFecha());
			dataUser.put("name", user.getNombre().toString());
		
			UsuarioService.save(user);
			log.info("nombre:::::::" + user.getNombre().toString());
		} catch (DataAccessException e) {
			response.put("statusCode", 500);
			response.put("Data", null);
			response.put("msg", "Error performing database insert ");
			log.error(e.getMessage());
			response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("statusCode", 201);
		response.put("Data", dataUser);
		response.put("msg", "Successful User Creation ");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

}
