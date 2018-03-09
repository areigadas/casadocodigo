package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class CarrinhoCompras implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<ItemCarrinho, Integer> itens = new LinkedHashMap<>();
	
	public void add(ItemCarrinho item){
	    itens.put(item, getQuantidade(item) + 1);//substitui se já existe
	}
	
	private int getQuantidade(ItemCarrinho item) {
	    if(!itens.containsKey(item)){
	        itens.put(item, 0); 
	    }
	    return itens.get(item);
	}
	
	public int getQuantidade(){
	    return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
	}
	
	public Map<ItemCarrinho, Integer> getItens(){
		return itens;
	}
	
	public BigDecimal getTotal(ItemCarrinho item) {
		return item.getPreco().multiply(new BigDecimal(itens.get(item)));
	}
	
	public BigDecimal getTotal(){
	    BigDecimal total = BigDecimal.ZERO;
	    for (ItemCarrinho item : itens.keySet()) {
	        total = total.add(getTotal(item));
	    }
	    return total;
	}
	
	public void remove(Integer produtoId, TipoPreco tipoPreco) {	
		ItemCarrinho itemCarrinho = new ItemCarrinho(new Produto(produtoId),tipoPreco); 
		this.itens.remove(itemCarrinho);
	}
	
}
