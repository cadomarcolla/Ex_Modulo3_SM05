package br.com.futurodev.primeiraapi.controllers;


import br.com.futurodev.primeiraapi.Dto.PedidoRepresentationModel;
import br.com.futurodev.primeiraapi.Dto.ProdutoRepresentationModel;
import br.com.futurodev.primeiraapi.input.PedidoInput;
import br.com.futurodev.primeiraapi.input.ProdutoInput;
import br.com.futurodev.primeiraapi.model.Pedido;
import br.com.futurodev.primeiraapi.model.Produto;
import br.com.futurodev.primeiraapi.services.CadastroProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Produtos")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @ApiOperation("Salva um produto")
    @PostMapping
    public ResponseEntity<ProdutoRepresentationModel> cadastrar(@ApiParam(value = "Json dados de produtoInput") @RequestBody ProdutoInput produtoInput) {
        Produto produto = toDomainObject(produtoInput);
        cadastroProdutoService.salvar(produto);
        return new ResponseEntity<ProdutoRepresentationModel>(toModel(produto), HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza um produto")
    @PutMapping
    public ResponseEntity<ProdutoRepresentationModel> atualizar(@ApiParam(value = "Json dados de produtoInput com idProduto") @RequestBody ProdutoInput produtoInput) {
        Produto produto = cadastroProdutoService.salvar(toDomainObject(produtoInput));
        return new ResponseEntity<ProdutoRepresentationModel>(toModel(produto), HttpStatus.OK);
    }

    @ApiOperation("Deleta um produto")
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<String> delete(@ApiParam(value = "ID do produto", example = "1") @RequestParam Long idProduto) {
        cadastroProdutoService.deleteById(idProduto);
        return new ResponseEntity<String>("Produto ID " + idProduto + " deletado com sucesso!", HttpStatus.OK);
    }

    @ApiOperation("Busca um produto por ID")
    @GetMapping(value = "/produto/{idProduto}")
    public ResponseEntity<ProdutoRepresentationModel> getProdutoById(@ApiParam(value = "ID do produto", example = "1") @PathVariable(value = "idProduto") Long idProduto) {
        ProdutoRepresentationModel produtoRepresentationModel = toModel(cadastroProdutoService.getProdutoById(idProduto));
        return new ResponseEntity<ProdutoRepresentationModel>(produtoRepresentationModel, HttpStatus.OK);
    }

    @ApiOperation("Busca produtos por nome")
    @GetMapping(value = "/produto")
    public ResponseEntity<List<ProdutoRepresentationModel>> getProdutosByName(@ApiParam(value = "Nome do produto") @RequestParam(name = "descricao") String descricao) {
        List<Produto> produtos = cadastroProdutoService.getProdutosByDescricao(descricao.toUpperCase());
        List<ProdutoRepresentationModel> produtosRepresentationModel = toCollectionModel(produtos);
        return new ResponseEntity<List<ProdutoRepresentationModel>>(produtosRepresentationModel,HttpStatus.OK);
    }

    @ApiOperation("Listar produtos")
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProdutoRepresentationModel>> getProdutos(){
        List<Produto> produtos = cadastroProdutoService.getProdutos();
        List<ProdutoRepresentationModel> produtosRepresentationModel = toCollectionModel(produtos);
        return new ResponseEntity<List<ProdutoRepresentationModel>>(produtosRepresentationModel, HttpStatus.OK);
    }



    private Produto toDomainObject(ProdutoInput produtoInput) {
        Produto produto = new Produto();
        produto.setId(produtoInput.getIdProduto());
        produto.setDescricao(produtoInput.getDescricao());
        produto.setPrecoCompra(produtoInput.getPrecoCompra());
        produto.setPrecoVenda(produtoInput.getPrecoVenda());
        return produto;
    }

    private ProdutoRepresentationModel toModel(Produto produto) {
        ProdutoRepresentationModel produtoRepresentationModel = new ProdutoRepresentationModel();
        produtoRepresentationModel.setId(produto.getId());
        produtoRepresentationModel.setDescricao(produto.getDescricao());
        produtoRepresentationModel.setPrecoCompra(produto.getPrecoCompra());
        produtoRepresentationModel.setPrecoVenda(produto.getPrecoVenda());
        return produtoRepresentationModel;
    }

    private List<ProdutoRepresentationModel> toCollectionModel(List<Produto> produtos){
        return produtos.stream().map(Produto ->toModel(Produto)).collect(Collectors.toList());
}

}
