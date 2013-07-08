package org.avs.easy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Categoria extends Activity {
	
	private ListView mainListView;
	private mItems[] itemss;
	private ArrayAdapter<mItems> listAdapter;
	private ArrayList<String> listaChecked = new ArrayList<String>();
	private ArrayList<mItems> lstEstados_Encontrados = new ArrayList<mItems>();
	private ArrayList<mItems> planetList = new ArrayList<mItems>();
	private EditText et;
	private String lstrEstadosSelecionados = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria);
		
		// Find the ListView resource.
				mainListView = (ListView) findViewById(R.id.mainListView);
				mainListView.setChoiceMode(mainListView.CHOICE_MODE_MULTIPLE);
				et = (EditText) findViewById(R.id.texPesquisa);
				// When item is tapped, toggle checked properties of CheckBox and
				// Planet.
				mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View item,
									int position, long id) {
								mItems planet = listAdapter.getItem(position);
								planet.toggleChecked();
								SelectViewHolder viewHolder = (SelectViewHolder) item
										.getTag();
								viewHolder.getCheckBox().setChecked(planet.isChecked());
								
							}

				});

				// Create and populate planets.
				itemss = (mItems[]) getLastNonConfigurationInstance();
				String[] categoria = getResources().getStringArray(R.array.categoria_pt);
				for(int i=0; i< categoria.length; i++){
					planetList.add(new mItems(categoria[i]));
				}

				// Set our custom array adapter as the ListView's adapter.
				listAdapter = new SelectArralAdapter(this, planetList);
				mainListView.setAdapter(listAdapter);

				CarregarEncontrados();

				// Adiciona um TextWatcher ao TextView cujos métodos são chamados sempre
				// que este TextView sofra alterações.
				et.addTextChangedListener(new TextWatcher() {
					public void afterTextChanged(Editable s) {
						// Abstract Method of TextWatcher Interface.
					}

					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// Abstract Method of TextWatcher Interface.
					}

					// Evento acionado quando o usuário teclar algo
					// na caixa de texto "Procurar"
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {

						CarregarEncontrados();

						// Carrega o listview com os itens encontrados
						listAdapter = new SelectArralAdapter(Categoria.this, lstEstados_Encontrados);
						mainListView.setAdapter(listAdapter);
				        

					}
				});

			}
			
			public void CarregarEncontrados()
		    {
		        int textlength = et.getText().length();
		        
		        //Limpa o array com os estados encontrados
		        //para poder efetuar nova busca
		        lstEstados_Encontrados.clear();
		   
		        for (int i = 0; i < planetList.size(); i++)
		        {
		            if (textlength <= planetList.get(i).getName().length())
		            {
		                //Verifica se existe algum item no array original
		                //caso encontre é adicionado no array de encontrados
		                if(et.getText().toString().equalsIgnoreCase((String)planetList.get(i).getName().subSequence(0, textlength)))
		                {
		                    lstEstados_Encontrados.add(planetList.get(i));
		                }
		            }
		        }
		        listAdapter = new SelectArralAdapter(this, lstEstados_Encontrados);
				mainListView.setAdapter(listAdapter);
		        
		    }
			private String addDelimitador(String txt){
				if(txt.trim().length()!=0)
					txt = txt + "|";
				return txt;
			}
			
			public String montarCategoria(){
				lstrEstadosSelecionados = "";
		        
		        //Cria um array com os itens selecionados no listview
		         
		        	for (int i = 0; i < lstEstados_Encontrados.size(); i++){
		        		//pega os itens marcados
		        		if(lstEstados_Encontrados.get(i).isChecked()){
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("contabilidade")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "acount" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("aeroporto")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "airport" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("parque de diversões")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "amusement_park" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("aquario")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "aquarium" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("galeria de arte")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "art_gallery" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("caixa eletrônico")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "atm" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("padaria")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "bakery" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("bancos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "bakery" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("bares")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "bar" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("salão de beleza")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "beauty_salon" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de bicicletas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "bicycle_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("livrarias")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "book_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("boliche")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "bowling_alley" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("rodoviária")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "bus_station" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("cafés")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "cafe" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("área de camping")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "campground" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("vendas de automóveis")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "car_dealer" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("vendas de automóveis")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "car_dealer" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("lava-jato")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "car_wash" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("cassino")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "casino" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("cemitério")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "cemetery" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("igrejas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "church" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("prefeitura")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "city_hall" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de roupas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "clothing_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de conveniência")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "convenience_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("tribunal")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "courthouse" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("dentistas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "dentist" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de departamentos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "department_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("médicos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "doctor" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("eletricista")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "electrician" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de eletrônicos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "electronics_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("embaixadas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "embassy" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("estabelecimentos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "establishment" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("financiamentos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "finance" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("bombeiros")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "fire_station" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("floriculturas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "florist" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("alimentação")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "food" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("funerária")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "funeral_home" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("funerária")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "furniture_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de móveis")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "furniture_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("posto de gasolina")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "gas_station" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("empreiteiras")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "general_contractor" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("mercearias/supermercados")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "grocery_or_supermarket" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("ginásio")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "gym" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("cabelereira")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "hair_care" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de ferragens")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "hardware_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("saúde")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "health" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("artigos para o lar")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "home_goods_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("hospital")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "hospital" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("agência de seguros")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "insurance_agency" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("joalheiria")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "jewelry_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("lavanderia")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "laundry" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("advogados")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "lawyer" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("biblioteca")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "library" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de bebidas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "liquor_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("serralheiro")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "locksmith" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("alojamentos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "lodging" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("entrega de refeições")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "meal_delivery" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("self-service")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "meal_takeaway" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("mesquita")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "mosque" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("locadora")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "movie_rental" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("transportadoras")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "moving_company" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("museu")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "museum" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("night club")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "night_club" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("pintores")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "painter" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("parques")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "park" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("estacionamentos")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "parking" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("pet shopping")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "pet_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("farmácias")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "pharmacy" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("fisioterapeutas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "physiotherapist" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("encanador")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "plumber" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("policia")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "police" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("correios")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "post_office" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("corretora de imóveis")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "real_estate_agency" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("restaurantes")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "restaurant" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("escolas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "school" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("loja de calçados")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "shoe_store" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("shoppings centers")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "shopping_mall" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("spa")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "spa" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("estádios")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "stadium" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("metrô")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "subway_station" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("sinagogas")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "synagogue" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("ponto de táxi")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "taxi_stand" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("estação de trens")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "train_station" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("agencia de turismo")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "travel_agency" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("universidades")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "university" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("clínca veterinária")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "veterinary_care" ;
		        			}
		        			if(lstEstados_Encontrados.get(i).getName().contentEquals("zoológico")){
		        				lstrEstadosSelecionados = addDelimitador(lstrEstadosSelecionados) + "zoo" ;
		        			}
		        			
		        		}
		        	}
		      
		        	//Toast.makeText(this, "Estados marcados : " + lstrEstadosSelecionados, Toast.LENGTH_LONG).show();
		    
		        	
		        	
		        	return lstrEstadosSelecionados;
		        	
		        	
			}
			
			//Este evento foi definido no arquivo de layout xml
		    public void btnPesquisar_click(View view){
		    	
		    	montarCategoria();
		    	Intent i = getIntent();
		    	i.putExtra("categoria", lstrEstadosSelecionados );
		    	setResult(RESULT_OK, i);
		    	finish();
		    	
		    }
		    
		  //Este evento foi definido no arquivo de layout xml
		    public void btnAddFavorito_click(View view){
		    	selecao();
		    }
		    
		    private void selecao(){
		    	
		      
		    }

			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.categoria, menu);
				
				menu.add(0, 1, Menu.NONE, "Products");
				return super.onCreateOptionsMenu(menu);
				
				//return true;
			}
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case 1:

				//	for (int i = 0; i < listaChecked.size(); i++) {
				//		Log.d("pos : ", "" + listaChecked.get(i));
				//	}
				//	break;
				}
				return super.onOptionsItemSelected(item);
			}

			
			/** Holds planet data. */
			private static class mItems {
				private String name = "";
				private boolean checked = false;
				private int icon;

				public mItems() {
				}

				public mItems(String name) {
					this.name = name;
				}

				public mItems(String name, boolean checked) {
					this.name = name;
					this.checked = checked;
				}
				public mItems(String name, int icon) {
					this.name = name;
					this.icon = icon;
				}

				public int getIcon() {
					return icon;
				}

				public void setIcon(int icon) {
					this.icon = icon;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public boolean isChecked() {
					return checked;
				}

				public void setChecked(boolean checked) {
					this.checked = checked;
				}

				public String toString() {
					return name;
				}

				public void toggleChecked() {
					checked = !checked;
				}
			}
			
			/** Holds child views for one row. */
			private static class SelectViewHolder {
				private CheckBox checkBox;
				private TextView textView;
				private ImageView imageView;

				public SelectViewHolder() {
				}

				public SelectViewHolder(TextView textView, CheckBox checkBox) {
					this.checkBox = checkBox;
					this.textView = textView;
				}
				
				public SelectViewHolder(TextView textView, CheckBox checkBox, ImageView imageView) {
					this.checkBox = checkBox;
					this.textView = textView;
					this.imageView = imageView;
				}
				
				public ImageView getImageView() {
					return imageView;
				}

				public void setImageView(ImageView imageView) {
					this.imageView = imageView;
				}

				public CheckBox getCheckBox() {
					return checkBox;
				}

				public void setCheckBox(CheckBox checkBox) {
					this.checkBox = checkBox;
				}

				public TextView getTextView() {
					return textView;
				}

				public void setTextView(TextView textView) {
					this.textView = textView;
				}
			}

			/** Custom adapter for displaying an array of Planet objects. */
			private static class SelectArralAdapter extends ArrayAdapter<mItems> {
				private LayoutInflater inflater;

				public SelectArralAdapter(Context context, List<mItems> planetList) {
					super(context, R.layout.list_categoria, R.id.categoria_text, planetList);
					// Cache the LayoutInflate to avoid asking for a new one each time.
					inflater = LayoutInflater.from(context);
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					// Planet to display
					mItems planet = (mItems) this.getItem(position);

					// The child views in each row.
					CheckBox checkBox;
					TextView textView;
					ImageView imageView;

					// Create a new row view
					if (convertView == null) {
						convertView = inflater.inflate(R.layout.list_categoria, null);

						// Find the child views.
						textView = (TextView) convertView
								.findViewById(R.id.categoria_text);
						checkBox = (CheckBox) convertView.findViewById(R.id.categoria_checked);
						//imageView = (ImageView) convertView.findViewById(R.id.categoria_icon);
						// Optimization: Tag the row with it's child views, so we don't
						// have to
						// call findViewById() later when we reuse the row.
						convertView.setTag(new SelectViewHolder(textView, checkBox));
						// If CheckBox is toggled, update the planet it is tagged with.
						checkBox.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								CheckBox cb = (CheckBox) v;
								mItems planet = (mItems) cb.getTag();
								planet.setChecked(cb.isChecked());
							}
						});
					}
					// Reuse existing row view
					else {
						// Because we use a ViewHolder, we avoid having to call
						// findViewById().
						SelectViewHolder viewHolder = (SelectViewHolder) convertView
								.getTag();
						checkBox = viewHolder.getCheckBox();
						textView = viewHolder.getTextView();
						imageView = viewHolder.getImageView();
					}

					// Tag the CheckBox with the Planet it is displaying, so that we can
					// access the planet in onClick() when the CheckBox is toggled.
					checkBox.setTag(planet);
					// Display planet data
					checkBox.setChecked(planet.isChecked());
					textView.setText(planet.getName());
					//imageView.setImageResource(planet.getIcon());
					
					return convertView;
				}
			}

			public Object onRetainNonConfigurationInstance() {
				return itemss;
			}


		}
