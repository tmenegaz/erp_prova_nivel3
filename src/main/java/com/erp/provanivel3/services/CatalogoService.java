package com.erp.provanivel3.services;

import com.erp.provanivel3.domain.Catalogo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CatalogoService {

    Optional<Catalogo> findById(String id);

    Catalogo save(Catalogo obj);

     void deleteById(String id);

    List<Catalogo> findAll();

    void update(Catalogo obj);

    Page<Catalogo> search(
            String nome,
            String tipo,
            String condicaoProduto,
            Integer page,
            Integer linesPerPage,
            String orderBy,
            String direction
    );

}
