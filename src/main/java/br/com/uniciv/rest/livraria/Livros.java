package br.com.uniciv.rest.livraria;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "livros")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Livros {

	@XmlElement(name = "livro")
	private List<Livro> listLivros = new ArrayList<>();

	public List<Livro> getLivros() {
		return listLivros;
	}

	public void setLivros(List<Livro> livros) {
		this.listLivros = livros;
	}

}
