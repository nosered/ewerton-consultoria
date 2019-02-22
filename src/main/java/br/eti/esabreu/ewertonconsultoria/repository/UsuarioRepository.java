package br.eti.esabreu.ewertonconsultoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.esabreu.ewertonconsultoria.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByEmail(String email);
}