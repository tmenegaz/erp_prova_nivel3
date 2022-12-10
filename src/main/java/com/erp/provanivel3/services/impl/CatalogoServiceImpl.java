package com.erp.provanivel3.services.impl;

import com.erp.provanivel3.domain.Catalogo;
import com.erp.provanivel3.domain.DTO.CatalogoDTO;
import com.erp.provanivel3.domain.QCatalogo;
import com.erp.provanivel3.repositories.CatalogoRepository;
import com.erp.provanivel3.services.CatalogoService;
import com.erp.provanivel3.services.exceptions.DataIntegrityException;
import com.erp.provanivel3.services.exceptions.IllegalArgumentException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CatalogoServiceImpl implements CatalogoService {

    private CatalogoRepository repository;

    public CatalogoServiceImpl(CatalogoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Catalogo> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        findById(id);
        try {
            repository.deleteById(UUID.fromString(id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir o produtos associado a um pedido");
        }
    }

    @Override
    public Optional<Catalogo> findById(String id) {
        try {
        Optional<Catalogo> catalogo = repository.findOne(
                    QCatalogo.catalogo.id.eq(UUID.fromString(id)));
        return catalogo;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                        "O item do catálogo não pode ser encontrado: Id: " + id + ", Tipo: " + Catalogo.class.getName()
            );
        }
    }

    @Override
    public Catalogo save(Catalogo obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    @Override
    public void update(Catalogo obj) {
        Catalogo newObj = findById(obj.getId().toString()).get();
        updateData(newObj, obj);
        repository.save(newObj);
    }

    private void updateData(Catalogo newObj, Catalogo obj) {
        newObj.setNome(obj.getNome());
        newObj.setPreco(obj.getPreco());
        newObj.setTipo(obj.getTipo());
        newObj.setCondicao(obj.getCondicao());
    }

    public Page<Catalogo> search(String nome, String tipo, String condicao, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage = linesPerPage < 5 ? linesPerPage = 5 : linesPerPage > 20 ? linesPerPage = 20 : linesPerPage,
                Sort.Direction.valueOf(direction),
                orderBy);
        if (!condicao.isEmpty() && !nome.isEmpty()) {
            return repository.findAll(QCatalogo.catalogo.nome.like("%"+nome+"%")
                    .and(QCatalogo.catalogo.condicao.eq(Integer.valueOf(condicao))),pageRequest);
        }
        if (!nome.isEmpty()) {
            return repository.findAll(QCatalogo.catalogo.nome.like("%"+nome+"%"),pageRequest);
        }
        if (!tipo.isEmpty()) {
            return repository.findAll(QCatalogo.catalogo.tipo.eq(Integer.valueOf(tipo)),pageRequest);
        }
        if (!condicao.isEmpty()) {
            return repository.findAll(QCatalogo.catalogo.condicao.eq(Integer.valueOf(condicao)),pageRequest);
        }
        return  repository.findAll(pageRequest);
    }

    public Catalogo fromDTO(CatalogoDTO objDTO) {
        return new Catalogo(
                objDTO.getId(),
                objDTO.getNome(),
                objDTO.getPreco(),
                objDTO.getTipo(),
                objDTO.getCondicao());
    }
}
