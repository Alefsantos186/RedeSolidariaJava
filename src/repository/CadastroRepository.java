package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Beneficiario;
import model.Doador;
import model.ItemDoacao;

public class CadastroRepository {

    private List<Beneficiario> beneficiarios = new ArrayList<>();
    private List<Doador> doadores = new ArrayList<>();
    private List<ItemDoacao> itens = new ArrayList<>();

    public CadastroRepository() {
        carregarDados();
    }
    
    public void salvarBeneficiario(Beneficiario b) { beneficiarios.add(b); salvarNoArquivo(); }
    public List<Beneficiario> listarBeneficiarios() { return beneficiarios; }
    public void deletarBeneficiario(int id) { beneficiarios.removeIf(b -> b.getId() == id); salvarNoArquivo(); }

    public void salvarDoador(Doador d) { doadores.add(d); salvarNoArquivo(); }
    public List<Doador> listarDoadores() { return doadores; }
    public void deletarDoador(int id) { doadores.removeIf(d -> d.getId() == id); salvarNoArquivo(); }
    
    public void atualizarTelefoneDoador(int id, String novoTelefone) {
        for (Doador doador : doadores) {
            if (doador.getId() == id) {
                doador.setTelefone(novoTelefone);
                salvarNoArquivo();
                System.out.println("Telefone atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Doador não encontrado.");
    }

    public void salvarItem(ItemDoacao i) { itens.add(i); salvarNoArquivo(); }
    public List<ItemDoacao> listarItens() { return itens; }
    public void deletarItem(int id) { itens.removeIf(i -> i.getId() == id); salvarNoArquivo(); }

    private void salvarNoArquivo() {
        try (ObjectOutputStream oosB = new ObjectOutputStream(new FileOutputStream("beneficiarios.dat"));
             ObjectOutputStream oosD = new ObjectOutputStream(new FileOutputStream("doadores.dat"));
             ObjectOutputStream oosI = new ObjectOutputStream(new FileOutputStream("itens.dat"))) {
             
            oosB.writeObject(beneficiarios);    
            oosD.writeObject(doadores);
            oosI.writeObject(itens);    
        } catch (IOException e) { 
            System.out.println("Erro ao Salvar: "+ e.getMessage()); 
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try (ObjectInputStream oisB = new ObjectInputStream(new FileInputStream("beneficiarios.dat"))) {
            beneficiarios = (List<Beneficiario>) oisB.readObject();
        } catch (Exception e) { }

        try (ObjectInputStream oisD = new ObjectInputStream(new FileInputStream("doadores.dat"))) {
            doadores = (List<Doador>) oisD.readObject();
        } catch (Exception e) { }

        try (ObjectInputStream oisI = new ObjectInputStream(new FileInputStream("itens.dat"))) {
            itens = (List<ItemDoacao>) oisI.readObject();
        } catch (Exception e) { }
    }
}