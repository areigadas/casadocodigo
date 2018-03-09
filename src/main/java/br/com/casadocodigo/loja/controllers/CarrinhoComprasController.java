package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.ItemCarrinho;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/cart")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {
	@Autowired
	private ProdutoDAO produtoDao;
	@Autowired
	private CarrinhoCompras carrinhoCompras; // como manter o estado do carrinho entre as requisicoes?

	
	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipo) {		
		ItemCarrinho carrinhoItem = criaItem(produtoId, tipo);
		//precisa adicionar no carrinho de compras
		carrinhoCompras.add(carrinhoItem);
		ModelAndView modelAndView = new ModelAndView("redirect:/cart/itens");
	    return modelAndView;
	}
	
	
	private ItemCarrinho criaItem(Integer produtoId, TipoPreco tipo){
		Produto produto = produtoDao.find(produtoId);
	    ItemCarrinho carrinhoItem = new ItemCarrinho(produto, tipo);
	    return carrinhoItem;
	}
	
	@RequestMapping("/itens")
	public ModelAndView itens() {
		ModelAndView modelAndView = new ModelAndView("itens");
		return modelAndView;
	}

	@RequestMapping("/remove")
	public ModelAndView remove(Integer produtoId, TipoPreco tipoPreco) {
		this.carrinhoCompras.remove(produtoId, tipoPreco);
		ModelAndView modelAndView = new ModelAndView("redirect:/cart/itens");
		return modelAndView;
	}
}
