package com.erp.provanivel3.services;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.DTO.PedidoDTO;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;

import java.util.List;
import java.util.Set;

public interface ItemPedidoService {

    void update(Set<ItemPedido> obj, String id);

    void updateAdd(PedidoDTO obj);

    List<ItemPedido> findAll();

    Pedido save(Pedido obj);

     void deleteById(String id);

    void delete(Catalogo catalogo);


}
