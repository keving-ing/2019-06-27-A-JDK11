package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private EventsDao dao;
	private List<Adiacenza> archi; 
	private List<String> best;  //lista che contiene il percorso che passa per piú vertici possibili
	
	
	public Model() {
		dao = new EventsDao();
	}
	
	public List<String> getCategorie()
	{
		return dao.getCategorie();
	}
	
	public void creaGrafo(String categoria, int a)
	{
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(categoria, a));
		this.archi = dao.getArchi(categoria, a);
		for(Adiacenza ad:this.archi)
		{
			Graphs.addEdgeWithVertices(grafo, ad.getV1(), ad.getV2(), ad.getPeso());
		}
		
		System.out.println("Vertici: " + this.grafo.vertexSet().size());
		System.out.println("Archi: " + this.grafo.edgeSet().size());
	}
	
	public int nV()
	{
		return this.grafo.vertexSet().size();
	}
	
	public int nA()
	{
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getArchi()
	{
		return archi;
	}
	
	public List<Adiacenza> getArchiMaggiorePesoMedio()
	{
		//scorro gli archi e calcolo il peso medio
		double pesoMax = 0.0;
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet())
		{
			double peso = this.grafo.getEdgeWeight(e);
			 if(peso>pesoMax)
			 {
				 pesoMax = peso;
			 }
			
		}
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet())
		{
			if(this.grafo.getEdgeWeight(e) == pesoMax)
			{
				result.add(new Adiacenza(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e)));
				//dall'arco e prendo vertice sorgente, vertice destinazione e peso
			}
		}
		
		return result;
		
	}
	
}
