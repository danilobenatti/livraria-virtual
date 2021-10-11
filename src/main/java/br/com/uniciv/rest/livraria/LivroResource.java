package br.com.uniciv.rest.livraria;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

@Path(value = "livro")
@Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class LivroResource {

	private LivroRepositorio livroRepositorio = LivroRepositorio.getInstance();

	@GET
	public Livros getLivros() {
		Livros livros = new Livros();
		livros.setLivros(livroRepositorio.getLivros());
		return livros;
	}

	@GET
	@Path(value = "/{isbn}")
	public ItemBusca getLivroIsbn(@PathParam(value = "isbn") String isbn) {
		try {
			Livro livro = livroRepositorio.getLivroPorIsbn(isbn);
			ItemBusca item = new ItemBusca();
			item.setLivro(livro);
			Link link = Link.fromUri("/carrinho/" + livro.getId())
					.rel("carrinho").type("POST").build();
			item.addLink(link);
			return item;
		} catch (LivroNaoEncontradoException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@POST
	public Response saveLivro(Livro livro) {
		try {
			livroRepositorio.addLivro(livro);
		} catch (LivroExistenteException e) {
			throw new WebApplicationException(Status.CONFLICT);
		}
		URI uri = UriBuilder.fromPath("livro/{isbn}").build(livro.getIsbn());
		return Response.created(uri).entity(livro).build();
	}

	@PUT
	@Path(value = "/{isbn}")
	public Response updateLivro(@PathParam(value = "isbn") String isbn,
			Livro livro) {
		try {
			Livro livro2 = livroRepositorio.getLivroPorIsbn(isbn);
			livro2.setAutor(livro.getAutor());
			livro2.setGenero(livro.getGenero());
			livro2.setPreco(livro.getPreco());
			livro2.setTitulo(livro.getTitulo());
			livroRepositorio.updateLivro(livro2);
		} catch (LivroNaoEncontradoException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return Response.ok().entity(livro).build();
	}

	@DELETE
	@Path(value = "/{id}")
	public void deleteLivro(@PathParam(value = "id") Long id) {
		try {
			livroRepositorio.removeLivro(id);
		} catch (LivroNaoEncontradoException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

}
