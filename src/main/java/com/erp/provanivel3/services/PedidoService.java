package com.erp.provanivel3.services;

import com.erp.provanivel3.domain.Pedido;
import org.springframework.data.domain.Page;

public interface PedidoService {

    Pedido findById(String id);

    Pedido save(Pedido obj);

    void update(Pedido obj);

    Page<Pedido> search(
            String status,
            Integer page,
            Integer linesPerPage,
            String orderBy,
            String direction
    );


}
