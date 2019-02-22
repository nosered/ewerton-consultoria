package br.eti.esabreu.ewertonconsultoria.util;

import org.springframework.security.core.context.SecurityContextHolder;

import br.eti.esabreu.ewertonconsultoria.model.Usuario;

public class SegurancaUtil {

	public static Usuario getUsuarioAutenticado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
