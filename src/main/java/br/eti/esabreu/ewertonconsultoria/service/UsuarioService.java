package br.eti.esabreu.ewertonconsultoria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.eti.esabreu.ewertonconsultoria.model.Grupo;
import br.eti.esabreu.ewertonconsultoria.model.Usuario;
import br.eti.esabreu.ewertonconsultoria.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email);
		usuario.getGrupos().size();
		for(Grupo grupo: usuario.getGrupos()) {
			grupo.getPermissoes().size();
		}
		return usuario;
	}
}