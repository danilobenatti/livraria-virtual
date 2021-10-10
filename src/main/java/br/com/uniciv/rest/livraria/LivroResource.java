package br.com.uniciv.rest.livraria;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

@Path(value = "livro")
@Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class LivroResource {

	private LivroRepositorio livroRepositorio = new LivroRepositorio();

	@GET
	public Livros getLivros() {
		Livros livros = new Livros();
		livros.setLivros(livroRepositorio.getLivros());
		return livros;
	}

	@GET
	@Path(value = "/{isbn}")
	public Livro getLivroIsbn(@PathParam(value = "isbn") String isbn) {
		try {
			return livroRepositorio.getLivroPorIsbn(isbn);
		} catch (LivroNaoEncontradoException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

}
