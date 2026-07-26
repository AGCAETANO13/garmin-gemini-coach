package org.example.repository;

import org.example.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
    // O JpaRepository já nos fornece métodos como:
    // - save() -> para salvar ou atualizar um treino
    // - findById() -> para buscar pelo ID
    // - findAll() -> para listar todos os treinos do banco
    // - deleteById() -> para deletar um treino
}