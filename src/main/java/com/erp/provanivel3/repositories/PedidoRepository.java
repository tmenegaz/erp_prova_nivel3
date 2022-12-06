package com.erp.provanivel3.repositories;

import com.erp.provanivel3.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, UUID>, JpaRepository<Pedido, UUID>, QuerydslPredicateExecutor<Pedido> {


}
