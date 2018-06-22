package com.prueba.areamovil.backend.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.areamovil.backend.entity.Usuario;
import com.prueba.areamovil.backend.repositories.UsuarioRepository;
import com.prueba.areamovil.backend.wsvo.RespuestaWSVO;

@Controller
@RequestMapping(path = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping(path = "/crearUsuario")
	public @ResponseBody String addNuevoUsuario(@RequestParam String nombre) throws Exception{
		String retorno;
		try {

			RespuestaWSVO wsVO =getUsuarioApi(nombre);
			 
			if (wsVO!=null) {
				Usuario usu = new Usuario();
				usu.setId(wsVO.getId());
				usu.setNombre(nombre);
				usu.setAvatar(wsVO.getAvatar_url());
				usuarioRepository.save(usu);
				retorno = "Usuario guardado";
			}else {
			retorno="El usuario no existe en el api";
			
			}
		}catch(

	Exception e)
	{
		retorno = e.toString();
	}return retorno;
	}

	@GetMapping(path = "/getUsuario")
	public @ResponseBody Iterable<Usuario> getAllUsers() {

		return usuarioRepository.findAll();
	}
	
	
	private RespuestaWSVO  getUsuarioApi(String nombre) {
		RespuestaWSVO retorno = null;
		try {
			
			String urlAux="https://api.github.com/users/" +nombre.replace(" ", "_");
			URL  url = new URL(urlAux);
			 
            
            URLConnection con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String respuesta;
            StringBuffer buffer = new StringBuffer();
            while ((respuesta = in.readLine()) != null) {
            	buffer.append(respuesta);
            }
            ObjectMapper mapper = new ObjectMapper();
            retorno = mapper.readValue(buffer.toString(), RespuestaWSVO.class);
            
           
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (ProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  retorno;
		
	}
	
	

}
