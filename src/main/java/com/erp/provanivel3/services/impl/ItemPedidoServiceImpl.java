package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.DTO.PedidoDTO;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.exception.CondicaoException;
import com.erp.provanivel3.domain.exception.DescontoException;
import com.erp.provanivel3.repositories.ItemPedidoRepository;
import com.erp.provanivel3.repositories.PedidoRepository;
import com.erp.provanivel3.services.EntityService;
import com.erp.provanivel3.services.ItemPedidoService;
import com.erp.provanivel3.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService, EntityService {

    @Autowired
    private ItemPedidoRepository repository;

    @Autowired
    private PedidoServiceImpl service;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CatalogoServiceImpl produtoServicoService;


    @Override
    public List<ItemPedido> findAll() {
        return repository.findAll();
    }


    @Override
    public void deleteById(String id) {
        Catalogo produtoServico = produtoServicoService.findById(id);
        try {
            delete(produtoServico);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não foi possível escluir o item");
        }
    }

    @Override
    public void delete(Catalogo produtoServico) {
        repository.deleteAll(produtoServico.getItens());
    }

    @Override
    public void update(Set<ItemPedido> obj, String id) {
        Pedido pedido = service.findById(id);
        Set<ItemPedido> newObj = null;

        List<UUID> objIds = obj.stream()
                .filter(ip -> ip.getCatalogo().getId() != null)
                .map(ps -> ps.getCatalogo().getId())
                .collect(Collectors.toList());

        List<UUID> pedidoIds = pedido.getItens()
                .stream().filter(ip -> ip.getCatalogo().getId() != null)
                .map(ps -> ps.getCatalogo().getId())
                .collect(Collectors.toList());

        Object[] objIdsStr = objIds.toArray();
        Object[] pedidoIdsStr = pedidoIds.toArray();

        int count = objIds.size();
        int reverse = 0;

        if (objIds.size() == pedidoIds.size()) {
            while (count > 0) {
                if (objIdsStr[count - 1].equals(pedidoIdsStr[reverse++])) {
                    for (ItemPedido ip : obj) {
                        newObj = pedido.getItens();
                        updateData(newObj, ip);
                    }
                }
                else {
                    throw new DataIntegrityException("Não é possível atualizar um um item de pedido que não possui referência");
                }
                count--;
            }
        }
        else {
            throw new DataIntegrityException("Não é possível atualizar um um item de pedido que não possui referência");
        }

    }

    private void updateData(Set<ItemPedido> newObj, ItemPedido obj) {
        for (ItemPedido ip : newObj) {
            ip.setDesconto(obj.getDesconto());
            ip.setQuantidade(obj.getQuantidade());
            ip.setPreco(obj.getPreco());
            ip.setCatalogo(obj.getCatalogo());
            repository.save(ip);
        }
    }

    @Override
    public void updateAdd(PedidoDTO objDTO) {
        Pedido pedido = service.findById(objDTO.getId().toString());
        Set<ItemPedido> newObj = pedido.getItens();
        for (ItemPedido ip : objDTO.getItens()) {
            newObj.add(ip);
            repository.save(ip);
        }
        save(pedido);
    }

    @Override
    public Pedido save(Pedido obj) {
        obj = pedidoRepository.save(obj);
        for (ItemPedido ip : obj.getItens()) {
            if (ip.getCatalogo() == null){
                obj = null;
                throw new CondicaoException("Não é possível incluir o produto desativado no pedido");
            }
            ip.setCatalogo(
                    produtoServicoService.findById(
                            ip.getCatalogo().getId().toString()
                    ));
//            setDesconto valida o obj e pode gerar uma exception para anular o obj
            ip.setDesconto(ip.getDesconto(), obj, ip.getCatalogo());
            ip.setPreco(ip.getCatalogo().getPreco());
            if (obj.getItens().size() == 0) {
                obj = null;
                throw new DescontoException("Não é possível oferecer desconto. O pedido está fechado ou não é produto");
            }
            ip.setPedido(obj);
        }
        try {
            repository.saveAll(obj.getItens());
        } catch (DescontoException e) {
            throw new DescontoException("Não é possível oferecer desconto. O pedido está fechado ou não é produto");
        }
        return obj;
    }

}