package com.github.filipealves.agendaapi.model.api.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.filipealves.agendaapi.model.entity.Contato;
import com.github.filipealves.agendaapi.model.repository.ContatoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contatos")
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
	public void favorite(@PathVariable Integer id, @RequestBody Boolean favorito) {
		Optional<Contato> contatoPesquisado = contatoRepository.findById(id);
		contatoPesquisado.ifPresent(contato -> {
			contato.setFavorito(favorito);
			contatoRepository.save(contato);
		});
	}

}
