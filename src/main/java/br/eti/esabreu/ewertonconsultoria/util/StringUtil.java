package br.eti.esabreu.ewertonconsultoria.util;

import org.springframework.util.StringUtils;

import com.github.slugify.Slugify;

public class StringUtil extends StringUtils {
	
	public static String slugfy(String texto) {
		return new Slugify().slugify(texto);
	}
}
