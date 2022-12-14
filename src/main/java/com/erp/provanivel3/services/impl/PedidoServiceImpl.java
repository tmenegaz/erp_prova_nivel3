package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.*;
import com.erp.provanivel3.domain.DTO.PedidoDTO;
import com.erp.provanivel3.domain.exception.CondicaoException;
import com.erp.provanivel3.domain.exception.DescontoException;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.repositories.ItemPedidoRepository;
import com.erp.provanivel3.repositories.PedidoRepository;
import com.erp.provanivel3.services.PedidoService;
import com.erp.provanivel3.services.exceptions.DataIntegrityException;
import com.erp.provanivel3.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CatalogoRepository catalogRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            CatalogoRepository catalogRepository,
            ItemPedidoRepository itemPedidoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.catalogRepository = catalogRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Page<Pedido> search(String status, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage = linesPerPage < 5 ? linesPerPage = 5 : linesPerPage > 20 ? linesPerPage = 20 : linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy);
        if (!status.isEmpty()) {
            return pedidoRepository.findAll(QPedido.pedido.status.eq(Integer.valueOf(status)),pageRequest);
        }
        return pedidoRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        findById(id);
        try {
            pedidoRepository.deleteById((UUID.fromString(id)));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("N??o ?? poss??vel excluir um pedido que possui produto ou servico");
        }
    }

    @Override
    public Optional<Pedido> findById(String id) {
        try {
            Optional<Pedido> pedido = pedidoRepository.findOne(
                    QPedido.pedido.id.eq(UUID.fromString(id))
            );
            return pedido;
        } catch (ObjectNotFoundException e) {

            throw  new ObjectNotFoundException(
                    "Produto ou servi??o n??o encontrado: Id: " + id + ", Tipo: " + Pedido.class.getName()
            );
        }
    }

    @Override
    @Transactional
    public Pedido save(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj = pedidoRepository.save(obj);
        for (ItemPedido ip : obj.getItens()) {
            if (ip.getCatalogo() == null){
                obj = null;
                throw new CondicaoException("N??o ?? poss??vel incluir o produto desativado no pedido");
            }

            Optional<Catalogo> catalogo = null;
            try {
                catalogo = catalogRepository.findOne(
                        QCatalogo.catalogo.id.eq(ip.getCatalogo().getId()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "O item do cat??logo n??o pode ser encontrado: Id: " + ip.getCatalogo().getId() + ", Tipo: " + Catalogo.class.getName()
                );
            }

            ip.setCatalogo(catalogo.get());
            if (ip.getQuantidade() < 1) {
                obj = null;
                throw new DataIntegrityException("A quantidade m??nima ?? 1");
            }
            ip.setQuantidade(ip.getQuantidade());

            if (ip.getDesconto() < 0 || ip.getDesconto() > 1) {
                obj = null;
                throw new DataIntegrityException("O desconto m??nima ?? 0 e o m??ximo ?? 1");
            }
//            setDesconto valida o obj e pode gerar uma exception para anular o obj
            ip.setDesconto(ip.getDesconto(), obj, ip.getCatalogo());
            ip.setPreco(ip.getPreco());
            if (obj.getItens().size() == 0) {
                obj = null;
                throw new DescontoException("N??o ?? poss??vel oferecer desconto. O pedido est?? fechado ou n??o ?? produto");
            }
            ip.setPedido(obj);
        }
        try {
            itemPedidoRepository.saveAll(obj.getItens());
        } catch (DescontoException e) {
            throw new DescontoException("N??o ?? poss??vel oferecer desconto. O pedido est?? fechado ou n??o ?? produto");
        }
        return obj;
    }

    @Override
    public void update(Pedido obj) {
        Optional<Pedido> newObj = findById(obj.getId().toString());
        updateData(newObj.get(), obj);
        pedidoRepository.save(newObj.get());
    }

    private void updateData(Pedido newObj, Pedido obj) {
        newObj.setStatus(obj.getStatus());
    }

    public Pedido fromDTO(PedidoDTO objDTO) {
        return new Pedido(objDTO.getStatus());
    }

}
