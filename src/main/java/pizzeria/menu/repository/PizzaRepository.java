package pizzeria.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pizzeria.menu.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    public List<Pizza> findByNomeContainingIgnoreCase(String nome);

}
