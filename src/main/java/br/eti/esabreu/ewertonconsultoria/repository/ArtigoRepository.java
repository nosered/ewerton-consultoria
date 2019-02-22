package br.eti.esabreu.ewertonconsultoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.eti.esabreu.ewertonconsultoria.model.Artigo;

@Repository
public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
	
	public Artigo findBySlug(String slug);
	
}