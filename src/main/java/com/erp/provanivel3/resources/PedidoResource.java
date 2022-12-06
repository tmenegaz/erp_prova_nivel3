package com.erp.provanivel3.resources;

import com.erp.provanivel3.domain.DTO.PedidoDTO;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.enums.StatusPedido;
import com.erp.provanivel3.services.impl.ItemPedidoServiceImpl;
import com.erp.provanivel3.services.impl.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoServiceImpl pedidoService;

    @Autowired
    private ItemPedidoServiceImpl itemPedidoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> findById(
            @PathVariable(value = "id") String id
    ) {
        return ResponseEntity.ok().body(pedidoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {
        List<Pedido> list = pedidoService.findAll();
        List<PedidoDTO> listDTO = list.stream()
                .map(p -> new PedidoDTO(p))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value="status", defaultValue="instante") String status,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="instante") String orderBy,
            @RequestParam(value="direction", defaultValue="DESC") String direction) {
        status = status.toLowerCase().equals(StatusPedido.ABERTO.getDescription()) ?
                String.valueOf(StatusPedido.valueOf(status.toUpperCase()).getCod()) :
                status.toLowerCase().equals(StatusPedido.FECHADO.getDescription()) ?
                        String.valueOf(StatusPedido.valueOf(status.toUpperCase()).getCod()) : "";
        Page<Pedido> list = pedidoService.search(status, page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") String id
    ) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/item/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable(value = "id") String id
    ) {
        itemPedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") String id,
            @RequestBody() PedidoDTO objDTO
    ) {
        Pedido obj = pedidoService.fromDTO(objDTO);
        obj.setId(UUID.fromString(id));
        pedidoService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/item/{id}")
    public ResponseEntity<Void> updateItens(
            @PathVariable(value = "id") String id,
            @RequestBody() Pedido obj
    ) {
        PedidoDTO objDTO = new PedidoDTO(obj.getItens());
        objDTO.setId(UUID.fromString(id));
        itemPedidoService.update(objDTO.getItens(), id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/addItem")
    public ResponseEntity<Void> addItem(
            @RequestBody() Pedido obj
    ) {
        PedidoDTO objDTO = new PedidoDTO(obj.getItens());
        objDTO.setId(obj.getId());
        Pedido ped = new Pedido(obj.getInstante(), obj.getStatus());
        for (ItemPedido ip: objDTO.getItens()) {
            ip.setPedido(ped);
            ip.getPedido().setId(obj.getId());
        }
        itemPedidoService.updateAdd(objDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestBody() Pedido obj
    ) {
        obj = pedidoService.save(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
