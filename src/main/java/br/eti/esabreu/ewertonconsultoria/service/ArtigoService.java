package br.eti.esabreu.ewertonconsultoria.service;

import static br.eti.esabreu.ewertonconsultoria.util.SegurancaUtil.getUsuarioAutenticado;
import static br.eti.esabreu.ewertonconsultoria.util.StringUtil.slugfy;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.eti.esabreu.ewertonconsultoria.exception.BlogException;
import br.eti.esabreu.ewertonconsultoria.model.Artigo;
import br.eti.esabreu.ewertonconsultoria.model.Categoria;
import br.eti.esabreu.ewertonconsultoria.repository.ArtigoRepository;

@Service
public class ArtigoService {

	@Autowired
	private ArtigoRepository artigoRepository;
	
	public List<Artigo> buscar() {
		return artigoRepository.findAll();
	}
	
	public Artigo salvar(Artigo artigo) {
		artigo.setSlug(slugfy(artigo.getTitulo()));
		artigo.setUsuario(getUsuarioAutenticado());
		artigo.setDataPublicacao(LocalDateTime.now());
		
		// para teste
		Categoria categoria = new Categoria();
		categoria.setId(1);
		artigo.setCategoria(categoria);
		// remover
		
		return artigoRepository.save(artigo);
	}
	
	public Artigo buscarPorSlug(String slug) throws BlogException {
		Artigo artigo = artigoRepository.findBySlug(slug);
		if(artigo == null) throw new BlogException();
		
		return artigo;
	}

	public Page<Artigo> buscarArtigosPaginados(Pageable paginacao) {
		return artigoRepository.findAll(paginacao);
	}
}