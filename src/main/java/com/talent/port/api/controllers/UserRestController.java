package com.talent.port.api.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.talent.port.api.CognitoClean;
import com.talent.port.api.dao.IUsuarioDao;
import com.talent.port.api.models.User;
import com.talent.port.api.services.IUserService;

@RestController
public class UserRestController {

	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserService UsuarioService;
	@Autowired
	private IUsuarioDao UsuarioDao;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> dataUser = new HashMap<>();
		
		
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
		try {
			List<User> dato = UsuarioDao.findByemail(user.getGmail());

			if (dato != null && !dato.isEmpty() && dato.toString() != "") {
				dataUser.put("Field", "The user is already registered");
				response.put("statusCode", 400);
				response.put("Data", null);
				response.put("errors", dataUser);
				response.put("msg", "Error in filling the form");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

			}
		} catch (DataAccessException e) {
			log.error(e.getMessage());

		}

		if (user.getContraseña().equals(user.getretypePassword())) {

			try {
				String encodedPassword = passwordEncoder.encode(user.getContraseña());
				log.error("encodedPassword :"+ encodedPassword);
				user.setContraseña(encodedPassword);
				user.setretypePassword(encodedPassword);
				CognitoClean opj = new CognitoClean();
				opj.CognitoAws(user.getGmail().toString(), encodedPassword.toString());
				UUID uuid = UUID.randomUUID();
				String uuidAsString = uuid.toString();
				user.setToken(uuidAsString);
				user.setCreated_at(new Date());
				dataUser.put("Lastname", user.getApellido());
				dataUser.put("Phone", user.getTelefono());
				dataUser.put("email", user.getGmail());
				dataUser.put("Lastname", user.getApellido());
				dataUser.put("date", user.getFecha_nacimiento());
				dataUser.put("name", user.getNombre().toString());
				
				UsuarioService.save(user);

			} catch (DataAccessException e) {
				response.put("statusCode", 500);
				response.put("Data", null);
				response.put("msg", "Error performing database insert ");
				log.error(e.getMessage());
				response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			dataUser.put("Field-10", "incorrect Confirma_password");
			response.put("statusCode", 400);
			response.put("Data", null);
			response.put("errors", dataUser);
			response.put("msg", "Error in filling the form");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		response.put("statusCode", 201);
		response.put("Data", dataUser);
		response.put("msg", "Successful User Creation ");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

}
