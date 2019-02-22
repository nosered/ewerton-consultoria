package br.eti.esabreu.ewertonconsultoria.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.eti.esabreu.ewertonconsultoria.exception.BlogException;
import br.eti.esabreu.ewertonconsultoria.model.Artigo;
import br.eti.esabreu.ewertonconsultoria.model.Usuario;
import br.eti.esabreu.ewertonconsultoria.service.ArtigoService;

@Controller
@RequestMapping("/artigo")
public class ArtigoController {

	@Autowired
	private ArtigoService artigoService;
	
	@GetMapping("/form")
	public ModelAndView form(Artigo artigo) {
		ModelAndView mView = new ModelAndView("consultoria/form-artigo");
		mView.addObject("artigo", artigo);
		return mView;
	}
	
	@PostMapping("/criar")
	public ModelAndView criar(@ModelAttribute Artigo artigo, @RequestPart("upload") MultipartFile upload, HttpServletRequest request, @AuthenticationPrincipal Usuario usuario) {
		ModelAndView mView = new ModelAndView("redirect:/artigo/form");
		try {
			if(!upload.isEmpty()) {
				String destFileName = UUID.randomUUID() + ".png";
				File destFile = new File(System.getProperty("user.home") + "\\" + destFileName);
				upload.transferTo(destFile);
				String thumbnailUrl = request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(request.getServerPort()+"").concat("/images/").concat(destFileName);
				artigo.setThumbnail(thumbnailUrl);
			}
			artigoService.salvar(artigo);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			mView.setViewName("404");
			return mView;
		} catch (IOException e) {
			e.printStackTrace();
			mView.setViewName("404");
			return mView;
		}
		return mView;
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Artigo artigo) {
		return form(artigo);
	}
	
	@GetMapping
	public ModelAndView buscar() {
		ModelAndView mView = new ModelAndView("blog/lista-artigo");
		List<Artigo> artigos = artigoService.buscar();
		mView.addObject("artigos", artigos);
		return mView;
	}
	
	@GetMapping("/listagem")
	public ModelAndView listar(@RequestParam("pagina") Optional<Integer> paginaParam, @RequestParam("tamanho") Optional<Integer> tamanhoParam) {
		ModelAndView mView = new ModelAndView("consultoria/lista-artigo");
		Integer pagina = paginaParam.orElse(1);
        Integer tamanho = tamanhoParam.orElse(3);
 
        Page<Artigo> artigosPagina = artigoService.buscarArtigosPaginados(PageRequest.of(pagina - 1, tamanho));
 
        mView.addObject("artigosPagina", artigosPagina);
 
        Integer totalPaginas = artigosPagina.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numeroPaginas = IntStream.rangeClosed(1, totalPaginas).boxed().collect(Collectors.toList());
            mView.addObject("numeroPaginas", numeroPaginas);
        }
        return mView;
	}
	
	@GetMapping("/{slug}")
	public ModelAndView buscar(@PathVariable("slug") String slug) {
		ModelAndView mView = new ModelAndView("blog/ler-artigo");
		try {
			Artigo artigo = artigoService.buscarPorSlug(slug);
			mView.addObject("artigo", artigo);
		} catch(BlogException exception) {
			mView.setViewName("blog/nao-encontrado");
		}
		return mView;
	}
}