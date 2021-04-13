package com.github.filipealves.agendaapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.filipealves.agendaapi.model.entity.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {

}
