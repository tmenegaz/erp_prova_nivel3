package com.erp.provanivel3.resources;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.DTO.CatalogoDTO;
import com.erp.provanivel3.domain.enums.CondicaoProduto;
import com.erp.provanivel3.domain.enums.TipoCatalogo;
import com.erp.provanivel3.resources.utils.URL;
import com.erp.provanivel3.services.impl.CatalogoServiceImpl;
import com.erp.provanivel3.services.impl.ItemPedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/produtosservicos")
public class CatalogoResource {

    @Qualifier("catalogoServiceImpl")
    @Autowired
    private CatalogoServiceImpl service;

    @Autowired
    private ItemPedidoServiceImpl itemPedidoService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Catalogo> findById(
            @PathVariable(value = "id") String id
    ) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Page<CatalogoDTO>> findPage(
            @RequestParam(value="nome", defaultValue="") String nome,
            @RequestParam(value="tipo", defaultValue="") String tipo,
            @RequestParam(value="condicao", defaultValue="") String condicao,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="12") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        tipo = tipo.toLowerCase().equals(TipoCatalogo.PRODUTO.getDescricao()) ?
                String.valueOf(TipoCatalogo.valueOf(tipo.toUpperCase()).getCod()) :
                tipo.toLowerCase().equals(TipoCatalogo.SERVICO.getDescricao()) ?
                        String.valueOf(TipoCatalogo.valueOf(tipo.toUpperCase()).getCod()) : "";

        condicao = condicao.toLowerCase().equals(CondicaoProduto.ATIVADO.getDescription()) ?
                String.valueOf(CondicaoProduto.valueOf(condicao.toUpperCase()).getCod()) :
                condicao.toLowerCase().equals(CondicaoProduto.DESATIVADO.getDescription()) ?
                        String.valueOf(CondicaoProduto.valueOf(condicao.toUpperCase()).getCod()) : "";
        Page<Catalogo> list = service.search(nomeDecoded, tipo, condicao, page, linesPerPage, orderBy, direction);
        Page<CatalogoDTO> listDTO = list.map(obj -> new CatalogoDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping
    public ResponseEntity<List<CatalogoDTO>> findAll() {
        List<Catalogo> list = service.findAll();
        List<CatalogoDTO> listDTO = list.stream()
                .map(p -> new CatalogoDTO(p))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") String id
    ) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") String id,
            @RequestBody() CatalogoDTO objDTO
    ) {
        Catalogo obj = service.fromDTO(objDTO);
        obj.setId(UUID.fromString(id));
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestBody @Valid CatalogoDTO objDTO
    ) {
        Catalogo obj = service.fromDTO(objDTO);
        obj = service.save(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
