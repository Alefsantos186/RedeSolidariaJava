package model;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
   protected int id;
   protected String nome;
   protected String telefone;
   protected String email;
   protected String endereco;

   public Usuario(int id, String nome, String telefone, String email, String endereco){
    this.id = id;
    this.nome = nome;
    this.telefone = telefone;
    this.email = email;
    this.endereco = endereco;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getTelefone() {
      return telefone;
   }

   public void setTelefone(String telefone) {
      this.telefone = telefone;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getEndereco() {
      return endereco;
   }

   public void setEndereco(String endereco) {
      this.endereco = endereco;
   }


}
