package org.avs.banco;

public class Favoritos {
	
	private Integer id;
	private String favorito;
	private Integer categoria;
	private Double latitude;
	private Double longitude;
	private String endereco;
	private String tel;
	
	public Favoritos(){
		
	}
	
	public Favoritos(Integer id, String favorito, Integer categoria,
			Double latitude, Double longitude, String endereco, String tel) {
		super();
		this.id = id;
		this.favorito = favorito;
		this.categoria = categoria;
		this.latitude = latitude;
		this.longitude = longitude;
		this.endereco = endereco;
		this.tel = tel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFavorito() {
		return favorito;
	}

	public void setFavorito(String favorito) {
		this.favorito = favorito;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	

}
