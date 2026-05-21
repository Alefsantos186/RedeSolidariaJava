package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Beneficiario;
import model.Doador;
import model.ItemDoacao;
import model.Solicitacao;

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
             ObjectOutputStream oosI = new ObjectOutputStream(new FileOutputStream("itens.dat"));
             ObjectOutputStream oosS = new ObjectOutputStream(new FileOutputStream("solicitacoes.dat"))) { 
             
            oosB.writeObject(beneficiarios);    
            oosD.writeObject(doadores);
            oosI.writeObject(itens);    
            oosS.writeObject(solicitacoes);
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
        
        try (ObjectInputStream oisS = new ObjectInputStream(new FileInputStream("solicitacoes.dat"))) {
            solicitacoes = (List<Solicitacao>) oisS.readObject();
        } catch (Exception e) { }
    }
    
    public int gerarIdDoador() {
        Random random = new Random();
        int novoId;
        boolean existe;
        do {
            novoId = 10000 + random.nextInt(90000); 
            int idGerado = novoId; 
            existe = doadores.stream().anyMatch(d -> d.getId() == idGerado);
        } while (existe);
        return novoId;
    }

    public int gerarIdBeneficiario() {
        Random random = new Random();
        int novoId;
        boolean existe;
        do {
            novoId = 10000 + random.nextInt(90000);
            int idGerado = novoId;
            existe = beneficiarios.stream().anyMatch(b -> b.getId() == idGerado);
        } while (existe);
        return novoId;
    }

    public int gerarIdItem() {
        Random random = new Random();
        int novoId;
        boolean existe;
        do {
            novoId = 10000 + random.nextInt(90000);
            int idGerado = novoId;
            existe = itens.stream().anyMatch(i -> i.getId() == idGerado);
        } while (existe);
        return novoId;
    }
    
    public boolean emailDoadorExiste(String email) {
        return doadores.stream().anyMatch(d -> d.getEmail().equalsIgnoreCase(email));
    }

    public boolean emailBeneficiarioExiste(String email) {
        return beneficiarios.stream().anyMatch(b -> b.getEmail().equalsIgnoreCase(email));
    }

    public void apagarTodosDoadores() {
        doadores.clear();
        salvarNoArquivo(); 
    }

    public void apagarTodosBeneficiarios() {
        beneficiarios.clear();
        salvarNoArquivo(); 
    }

    public void apagarTodosItens() {
        itens.clear();
        salvarNoArquivo(); 
    }

    private List<Solicitacao> solicitacoes = new ArrayList<>();

    public Beneficiario buscarBeneficiarioPorId(int id) {
        return beneficiarios.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public ItemDoacao buscarItemPorId(int id) {
        return itens.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

   public int gerarIdSolicitacao() {
        Random random = new Random();
        int novoid;
        boolean existe;
        do{
            novoid = 10000 + random.nextInt(90000);
            int idGerado = novoid;
            existe = solicitacoes.stream().anyMatch(s -> s.getId() == idGerado);
        } while (existe);
        return novoid;
    }

    public void registrarSolicitacao(int idBeneficiario, int idItem, int quantidade, String justificativa) {
        Beneficiario b = buscarBeneficiarioPorId(idBeneficiario);
        ItemDoacao item = buscarItemPorId(idItem);

        if (b == null) {
            System.out.println("Erro: Beneficiário não encontrado!");
            return;
        }

        if (item == null) {
            System.out.println("Erro: Item não encontrado!");
            return;
        }

        if (!item.getStatus().equalsIgnoreCase("Disponível")) {
            System.out.println("Erro: Este item não está disponível(Status: " + item.getStatus() + ").");
            return;
        }

        if (quantidade > item.getQuantidade()) {
            System.out.println("Erro: Quantidade solicitada (" + quantidade+") é maior que o estoque(" + item.getQuantidade() + ").");
            return;
        }

        item.setQuantidade(item.getQuantidade() - quantidade);

        if (item.getQuantidade() == 0) {
            item.setStatus("Reservado");
        }

        int idSol = gerarIdSolicitacao();
        Solicitacao novaSolicitacao = new Solicitacao(idSol, b, item, quantidade, justificativa, "Aprovada");
        solicitacoes.add(novaSolicitacao);

        salvarNoArquivo();

        System.out.println("\n Sucesso! Solicitação aprovada (ID: " + idSol + ")");
        System.out.println("Novo estoque de '" + item.getNomeItem() + "': " + item.getQuantidade() + "(" + item.getStatus() + ")");
    }
}