package pizzeria.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pizzeria.menu.model.Offerta;

public interface OffertaRepository extends JpaRepository<Offerta, Integer> {


}