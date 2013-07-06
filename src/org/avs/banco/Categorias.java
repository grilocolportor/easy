package org.avs.banco;

public class Categorias {
	
	private Integer id;
	private String categoria;
	private Integer favorito;
	
	public Categorias(){
		
	}
	
	public Categorias(Integer id, String categoria, Integer favorito) {
		this.id = id;
		this.categoria = categoria;
		this.favorito = favorito;
	}

	public Integer getFavorito() {
		return favorito;
	}

	public void setFavorito(Integer favorito) {
		this.favorito = favorito;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
	
	

}
