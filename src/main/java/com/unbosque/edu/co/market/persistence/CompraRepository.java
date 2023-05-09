package com.unbosque.edu.co.market.persistence;

import com.unbosque.edu.co.market.domain.Purchase;
import com.unbosque.edu.co.market.domain.repository.PurchaseRepository;
import com.unbosque.edu.co.market.persistence.crud.CompraCrudRepository;
import com.unbosque.edu.co.market.persistence.entity.Compra;
import com.unbosque.edu.co.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;
    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId).map(compras->{
            return mapper.toPurchases(compras);
        });
    }

    @Override
    public Purchase save(Purchase purchase) {

        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return null;
    }
}
