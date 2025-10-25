package br.com.example.vendas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ola")
public class OlaMundo {

	@GetMapping
	public String ola () {
		return "ola";
	}
	
}
