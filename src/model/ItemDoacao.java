package model;

import java.time.LocalDate;
import java.io.Serializable;

public class ItemDoacao implements Serializable {
    private int id;
    private String nomeItem;
    private String categoria;
    private String descricao;
    private int quantidade;
    private String estadoConservacao;
    private LocalDate data;
    private String status;


    public ItemDoacao(int id, String nomeItem, String categoria, String descricao, int quantidade, String estadoConservacao, String status){
        this.id = id;
        this.nomeItem = nomeItem;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.estadoConservacao = estadoConservacao;
        this.data = LocalDate.now();
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}