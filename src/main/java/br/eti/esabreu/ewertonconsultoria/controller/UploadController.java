package br.eti.esabreu.ewertonconsultoria.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.eti.esabreu.ewertonconsultoria.dto.ImageLocation;
import br.eti.esabreu.ewertonconsultoria.model.Usuario;

@Controller
public class UploadController {

	@PostMapping("/upload")
	public ModelAndView criar(@RequestPart("upload") MultipartFile upload, HttpServletRequest request, @AuthenticationPrincipal Usuario usuario) {
		ModelAndView mView = new ModelAndView("redirect:/artigo/form");
		try {
			String destFileName = UUID.randomUUID() + ".png";
			File destFile = new File(System.getProperty("user.home") + "\\" + destFileName);
			upload.transferTo(destFile);
			// String thumbnailUrl = request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(request.getServerPort()+"").concat("/images/").concat(destFileName);
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
	
	@PostMapping(value = "/image/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> imageUpload(@RequestPart("file") MultipartFile upload, HttpServletRequest req) {
		String destFileName = UUID.randomUUID() + ".png";
		File destFile = new File(System.getProperty("user.home") + "\\" + destFileName);
		try {
			upload.transferTo(destFile);
			String imgUrl = req.getScheme().concat("://").concat(req.getServerName()).concat(":").concat(req.getServerPort()+"").concat("/images/").concat(destFileName);
			ImageLocation response = new ImageLocation();
			response.setLocation(imgUrl);
			return ResponseEntity.ok().body(response);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
}