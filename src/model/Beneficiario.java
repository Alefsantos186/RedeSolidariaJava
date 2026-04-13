package model;

public class Beneficiario extends Usuario {
   private String tipoBeneficiario;
   private int nivelPrioridade;

    public Beneficiario(int id, String nome, String telefone, String email, String endereco, String tipoBeneficiario, int nivelPrioridade) {
        super(id, nome, telefone, email, endereco);
        this.tipoBeneficiario = tipoBeneficiario;
        this.nivelPrioridade = nivelPrioridade;
    }

    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public int getNivelPrioridade() {
        return nivelPrioridade;
    }

    public void setNivelPrioridade(int nivelPrioridade) {
        this.nivelPrioridade = nivelPrioridade;
    }
}