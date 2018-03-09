
package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
@RequestMapping("/produtos") 
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	@Autowired
    private FileSaver fileSaver;

	@InitBinder //needed to bind the validation with the object @Valid
	public void InitBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
//		binder.addValidators(produtoValidation);
	}
	
	@RequestMapping(value="/form")
	public ModelAndView form(Produto produto) {
		//preciso mandar informacoes para a view (listagem de tipos de preco)
		ModelAndView modelAndView = new ModelAndView("form");
		modelAndView.addObject("listaPreco", TipoPreco.values());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView gravar( MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {		
		if (result.hasErrors()) {
            return form(produto);
            //return new ModelAndView("produtos/form");
        }	
		System.out.println("Produto: "+produto);
		System.out.println("Arquivo: "+sumario.getOriginalFilename());
		//tem que fazer o binder na mao 
		String path = fileSaver.write("arquivos-sumario", sumario);
	    produto.setSumarioPath(path);
		produtoDao.gravar(produto);
		redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:produtos"); // redirecting to user GET instead.
		//return listar(); se só fizer isso, continua no POST....
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar(){
	    List<Produto> produtos = produtoDao.listar();
	    ModelAndView modelAndView = new ModelAndView("lista");
	    modelAndView.addObject("produtos", produtos);
	    modelAndView.addObject("mensagem", "Produtos listados abaixo.");
	    return modelAndView;
	}
	
	@RequestMapping(value="/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") int id) {
		Produto produto = produtoDao.find(id);
		/*List<Preco> precos = produto.getPrecos();
		System.out.println("Titulo: " + produto.getTitulo());
		for (int i=0; i<precos.size();i++) {
			System.out.println("Precos Tipo: "+precos.get(i).getTipo() );
			System.out.println("Precos Valor: "+precos.get(i).getValor() );
		}*/
		ModelAndView modelAndView = new ModelAndView("detalhe");		
		modelAndView.addObject("produto", produto );
		return modelAndView;
	}
	
}
