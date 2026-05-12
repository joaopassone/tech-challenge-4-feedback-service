package br.com.fiap.tc4.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "feedback")
public class Feedback {
    @Id
    int id;
    String descricao;
    int nota;
    Date data;

    public Feedback(String descricao, int nota, Date data) {
        this.descricao = descricao;
        this.nota = nota;
        this.data = data;
    } 
}
