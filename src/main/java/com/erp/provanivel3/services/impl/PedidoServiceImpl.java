package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.DTO.PedidoDTO;
import com.erp.provanivel3.domain.ItemPedido;
import com.erp.provanivel3.domain.Pedido;
import com.erp.provanivel3.domain.QPedido;
import com.erp.provanivel3.domain.exception.CondicaoException;
import com.erp.provanivel3.domain.exception.DescontoException;
import com.erp.provanivel3.repositories.ItemPedidoRepository;
import com.erp.provanivel3.repositories.PedidoRepository;
import com.erp.provanivel3.services.EntityService;
import com.erp.provanivel3.services.PedidoService;
import com.erp.provanivel3.services.exceptions.DataIntegrityException;
import com.erp.provanivel3.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements PedidoService, EntityService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CatalogoServiceImpl produtoServicoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Page<Pedido> search(String status, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		if (!status.isEmpty()) {
//            return pedidoRepository.findAll(QPedido.pedido.status.like("%"+status+"%"),pageRequest);
        }
		return pedidoRepository.findAll(pageRequest);
	}

    @Override
    public void deleteById(String id) {
        findById(id);
        try {
            pedidoRepository.deleteById((UUID.fromString(id)));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um pedido que possui produto ou servico");
        }
    }

    @Override
    public Pedido findById(String id) {
        Optional<Pedido> pedido = pedidoRepository.findOne(
                QPedido.pedido.id.eq(UUID.fromString(id))
        );
        return pedido.orElseThrow(
                () -> new ObjectNotFoundException(
                        "Produto ou serviço não encontrado: Id: " + id + ", Tipo: " + Pedido.class.getName()
                )
        );
    }

    @Override
    public Pedido save(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
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
            itemPedidoRepository.saveAll(obj.getItens());
        } catch (DescontoException e) {
            throw new DescontoException("Não é possível oferecer desconto. O pedido está fechado ou não é produto");
        }
        return obj;
    }

    @Override
    public void update(Pedido obj) {
        Pedido newObj = findById(obj.getId().toString());
        updateData(newObj, obj);
        pedidoRepository.save(newObj);
    }

    private void updateData(Pedido newObj, Pedido obj) {
        newObj.setStatus(obj.getStatus());
    }

    public Pedido fromDTO(PedidoDTO objDTO) {
        return new Pedido(objDTO.getStatus());
    }

}
