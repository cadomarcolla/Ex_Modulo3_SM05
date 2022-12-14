package br.com.futurodev.primeiraapi.repository;

import br.com.futurodev.primeiraapi.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("select i from ItemPedido i where i.pedido.id = ?1 and i.id = ?2")
    ItemPedido getItemPedido(Long idPedido, Long idItemPedido);


}
