package br.com.uniciv.rest.livraria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LivroRepositorio {

	private Map<Long, Livro> livros = new HashMap<>();

	private static LivroRepositorio livroRepositorio;

	public static LivroRepositorio getInstance() {
		if (livroRepositorio == null) {
			livroRepositorio = new LivroRepositorio();
		}
		return livroRepositorio;
	}

	private LivroRepositorio() {

		Livro livro1 = new Livro(1L, "Livro A", "ISBN-1234", "Genero A", 23.99,
				"Autor 1");
		Livro livro2 = new Livro(2L, "Livro B", "ISBN-4321", "Genero B", 19.99,
				"Autor 2");
		livros.put(livro1.getId(), livro1);
		livros.put(livro2.getId(), livro2);

	}

	public List<Livro> getLivros() {
		return new ArrayList<>(livros.values());
	}

	public Livro getLivroPorIsbn(String isbn) {
		for (Livro livro : livros.values()) {
			if (livro.getIsbn().equalsIgnoreCase(isbn)) {
				return livro;
			}
		}
		throw new LivroNaoEncontradoException();
	}

	public void addLivro(Livro livro) {
		if (livros.containsKey(livro.getId())) {
			throw new LivroExistenteException();
		}
		livros.put(livro.getId(), livro);
	}

	public void updateLivro(Livro livro) {
		livros.put(livro.getId(), livro);
	}

	public void removeLivro(Long id) {
		if (livros.containsKey(id)) {
			livros.remove(id);
		} else {
			throw new LivroNaoEncontradoException();
		}
	}
}
