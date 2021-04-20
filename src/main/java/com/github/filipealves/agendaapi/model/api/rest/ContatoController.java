package com.github.filipealves.agendaapi.model.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.filipealves.agendaapi.model.entity.Contato;
import com.github.filipealves.agendaapi.model.repository.ContatoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contatos")
@CrossOrigin("*")
public class ContatoController {
	
	private final ContatoRepository contatoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Contato save(@RequestBody Contato contato) {
		return contatoRepository.save(contato);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		contatoRepository.deleteById(id);
	}
	
	@GetMapping
	public List<Contato> list() {
		return contatoRepository.findAll();
	}
	
	@PatchMapping("{id}/favorito")
	public void favorite(@PathVariable Integer id) {
		Optional<Contato> contatoPesquisado = contatoRepository.findById(id);
		contatoPesquisado.ifPresent(contato -> {
			boolean favorito = contato.getFavorito() == Boolean.TRUE;
			contato.setFavorito(!favorito);
			contatoRepository.save(contato);
		});
	}
	
	@PutMapping("{id}/foto")
	public byte[] addPhoto(@PathVariable Integer id, 
						   @RequestParam("foto") Part arquivo) {
		Optional<Contato> contato = contatoRepository.findById(id);
		return contato.map(contact -> {
			try {
				InputStream is = arquivo.getInputStream();
				byte[] bytes = new byte[(int) arquivo.getSize()];
				IOUtils.readFully(is, bytes);
				contact.setFoto(bytes);
				contatoRepository.save(contact);		
				is.close();
				return bytes;
				} catch (IOException e) {
					return null;
			}
		}).orElse(null);
	}

}
