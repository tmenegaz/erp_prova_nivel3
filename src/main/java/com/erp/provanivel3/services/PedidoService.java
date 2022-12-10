package com.erp.provanivel3.services;

import com.erp.provanivel3.domain.Pedido;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    Optional<Pedido> findById(String id);

    void deleteById(String id);

    List<Pedido> findAll();

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
