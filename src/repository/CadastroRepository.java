package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Beneficiario;
import model.DoacaoEfetivada;
import model.Doador;
import model.ItemDoacao;
import model.Solicitacao;

public class CadastroRepository {

    public static final String STATUS_DISPONIVEL = "Disponivel";
    public static final String STATUS_RESERVADO = "Reservado";
    public static final String STATUS_ENTREGUE = "Entregue";
    public static final String STATUS_CANCELADA = "Cancelada";
    public static final String STATUS_CONCLUIDA = "Concluida";
    public static final String STATUS_APROVADA = "Aprovada";

    private List<Beneficiario> beneficiarios = new ArrayList<>();
    private List<Doador> doadores = new ArrayList<>();
    private List<ItemDoacao> itens = new ArrayList<>();
    private List<Solicitacao> solicitacoes = new ArrayList<>();
    private List<DoacaoEfetivada> doacoesEfetivadas = new ArrayList<>();
    
    private final String ARQUIVO_DOACOES_EFETIVADAS = "doacoes_efetivadas.dat";
    private final Random random = new Random();

    public CadastroRepository() {
        carregarDados();
    }
    
    public void salvarBeneficiario(Beneficiario b) { 
        beneficiarios.add(b); 
        salvarNoArquivo(); 
    }
    
    public List<Beneficiario> listarBeneficiarios() { 
        return beneficiarios; 
    }
    
    public boolean deletarBeneficiario(int id) { 
        boolean vinculado = solicitacoes.stream()
            .anyMatch(s -> s.getBeneficiario().getId() == id);
        
        if (vinculado) {
            System.out.println("[ERRO] Nao e possivel excluir: este beneficiario possui solicitacoes vinculadas.");
            return false;
        }
        
        boolean removido = beneficiarios.removeIf(b -> b.getId() == id); 
        if (removido) salvarNoArquivo();
        return removido;
    }

    public void salvarDoador(Doador d) { 
        doadores.add(d); 
        salvarNoArquivo(); 
    }
    
    public List<Doador> listarDoadores() { 
        return doadores; 
    }
    
    public boolean deletarDoador(int id) { 
        boolean vinculado = itens.stream()
            .anyMatch(i -> i.getIdDoador() == id);
        
        if (vinculado) {
            System.out.println("[ERRO] Nao e possivel excluir: este doador possui itens de doacao vinculados.");
            return false;
        }
        
        boolean removido = doadores.removeIf(d -> d.getId() == id); 
        if (removido) salvarNoArquivo();
        return removido;
    }
    
    public void atualizarTelefoneDoador(int id, String novoTelefone) {
        for (Doador doador : doadores) {
            if (doador.getId() == id) {
                doador.setTelefone(novoTelefone);
                salvarNoArquivo();
                System.out.println("Telefone atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Doador nao encontrado.");
    }

    public void salvarItem(ItemDoacao i) { 
        itens.add(i); 
        salvarNoArquivo(); 
    }
    
    public List<ItemDoacao> listarItens() { 
        return itens; 
    }
    
    public boolean deletarItem(int id) { 
        boolean vinculado = solicitacoes.stream()
            .anyMatch(s -> s.getItem().getId() == id);
        
        if (vinculado) {
            System.out.println("[ERRO] Nao e possivel excluir: este item possui solicitacoes vinculadas.");
            return false;
        }
        
        boolean removido = itens.removeIf(i -> i.getId() == id); 
        if (removido) salvarNoArquivo();
        return removido;
    }
    
    public List<DoacaoEfetivada> listarDoacoesEfetivadas() {
        return doacoesEfetivadas;
    }

    private void salvarNoArquivo() {
        try (ObjectOutputStream oosB = new ObjectOutputStream(new FileOutputStream("beneficiarios.dat"));
             ObjectOutputStream oosD = new ObjectOutputStream(new FileOutputStream("doadores.dat"));
             ObjectOutputStream oosI = new ObjectOutputStream(new FileOutputStream("itens.dat"));
             ObjectOutputStream oosS = new ObjectOutputStream(new FileOutputStream("solicitacoes.dat"));
             ObjectOutputStream oosDE = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DOACOES_EFETIVADAS))) { 
             
            oosB.writeObject(beneficiarios);    
            oosD.writeObject(doadores);
            oosI.writeObject(itens);    
            oosS.writeObject(solicitacoes);
            oosDE.writeObject(doacoesEfetivadas);
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
        
        try (ObjectInputStream oisDE = new ObjectInputStream(new FileInputStream(ARQUIVO_DOACOES_EFETIVADAS))) {
            doacoesEfetivadas = (List<DoacaoEfetivada>) oisDE.readObject();
        } catch (Exception e) { }
    }
    
    public int gerarIdDoador() {
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
        int novoId;
        boolean existe;
        do {
            novoId = 10000 + random.nextInt(90000);
            int idGerado = novoId;
            existe = itens.stream().anyMatch(i -> i.getId() == idGerado);
        } while (existe);
        return novoId;
    }
    
    public int gerarIdDoacaoEfetivada() {
        int novoId;
        boolean existe;
        do {
            novoId = 10000 + random.nextInt(90000);
            int idGerado = novoId;
            existe = doacoesEfetivadas.stream().anyMatch(d -> d.getId() == idGerado);
        } while (existe);
        return novoId;
    }

    public boolean emailDoadorExiste(String email) {
        return doadores.stream().anyMatch(d -> d.getEmail().equalsIgnoreCase(email));
    }

    public boolean emailBeneficiarioExiste(String email) {
        return beneficiarios.stream().anyMatch(b -> b.getEmail().equalsIgnoreCase(email));
    }

    public Beneficiario buscarBeneficiarioPorId(int id) {
        return beneficiarios.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public ItemDoacao buscarItemPorId(int id) {
        return itens.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
    }

    public int gerarIdSolicitacao() {
        int novoid;
        boolean existe;
        do {
            novoid = 10000 + random.nextInt(90000);
            int idGerado = novoid;
            existe = solicitacoes.stream().anyMatch(s -> s.getId() == idGerado);
        } while (existe);
        return novoid;
    }

    public Doador buscarDoadorPorId(int id) {
        return doadores.stream().filter(d -> d.getId() == id).findFirst().orElse(null);
    }

    public void registrarSolicitacao(int idBeneficiario, int idItem, int quantidade, String justificativa) {
        Beneficiario b = buscarBeneficiarioPorId(idBeneficiario);
        ItemDoacao item = buscarItemPorId(idItem);

        if (b == null) {
            System.out.println("[ERRO] Beneficiario nao encontrado!");
            return;
        }

        if (item == null) {
            System.out.println("[ERRO] Item nao encontrado!");
            return;
        }

        if (!item.getStatus().equalsIgnoreCase(STATUS_DISPONIVEL)) {
            System.out.println("[ERRO] Este item nao esta disponivel (Status: " + item.getStatus() + ").");
            return;
        }

        if (quantidade > item.getQuantidade()) {
            System.out.println("[ERRO] Quantidade solicitada (" + quantidade+") e maior que o estoque(" + item.getQuantidade() + ").");
            return;
        }

        item.setQuantidade(item.getQuantidade() - quantidade);

        if (item.getQuantidade() == 0) {
            item.setStatus(STATUS_RESERVADO);
        }

        int idSol = gerarIdSolicitacao();
        Solicitacao novaSolicitacao = new Solicitacao(idSol, b, item, quantidade, justificativa, STATUS_APROVADA);
        solicitacoes.add(novaSolicitacao);

        salvarNoArquivo();

        System.out.println("\n==================================");
        System.out.println("       SOLICITACAO APROVADA");
        System.out.println("==================================");
        System.out.println("ID da Solicitacao: " + idSol);
        System.out.println("Item Solicitado: " + item.getNomeItem());
        System.out.println("Novo Estoque: " + item.getQuantidade() + " (" + item.getStatus() + ")");
        System.out.println("==================================");
    }

    public List<Solicitacao> listarSolicitacoes() {
        return solicitacoes;
    }

    public void concluirEntrega(int idSolicitacao, String observacao) {
        Solicitacao sol = solicitacoes.stream().filter(s -> s.getId() == idSolicitacao).findFirst().orElse(null);
        
        if (sol == null) {
            System.out.println("[ERRO] Solicitacao nao encontrada!");
            return;
        }
        
        if (sol.getStatus().equalsIgnoreCase(STATUS_CONCLUIDA)) {
            System.out.println("[AVISO] Esta entrega ja foi concluida anteriormente.");
            return;
        }

        if (sol.getStatus().equalsIgnoreCase(STATUS_CANCELADA)) {
            System.out.println("[ERRO] Nao e possivel concluir a entrega de uma solicitacao cancelada.");
            return;
        }

        ItemDoacao item = buscarItemPorId(sol.getItem().getId());
        
        sol.setStatus(STATUS_CONCLUIDA);
        if (item != null) {
            item.setStatus(STATUS_ENTREGUE);
        }

        Doador doadorOrigem = buscarDoadorPorId(sol.getItem().getIdDoador());

        int novoIdEfetivada = gerarIdDoacaoEfetivada();
        DoacaoEfetivada novaDoacao = new DoacaoEfetivada(
            novoIdEfetivada, 
            sol.getItem(), 
            doadorOrigem, 
            sol.getBeneficiario(), 
            observacao
        );
        
        doacoesEfetivadas.add(novaDoacao);
        salvarNoArquivo();

        System.out.println("\n==================================");
        System.out.println("        ENTREGA CONCLUIDA");
        System.out.println("==================================");
        System.out.println("ID da Solicitacao: " + sol.getId());
        System.out.println("Status da Solicitacao: " + sol.getStatus());
        System.out.println("Status do Item Fisico: Entregue");
        System.out.println("Registro de Doacao Efetivada gerado (ID: " + novoIdEfetivada + ").");
        System.out.println("==================================");
    }

    public void cancelarSolicitacao(int idSolicitacao) {
        Solicitacao sol = solicitacoes.stream()
            .filter(s -> s.getId() == idSolicitacao)
            .findFirst()
            .orElse(null);

        if (sol == null) {
            System.out.println("[ERRO] Solicitacao nao encontrada!");
            return;
        }

        if (sol.getStatus().equalsIgnoreCase(STATUS_CONCLUIDA)) {
            System.out.println("[ERRO] Nao e possivel cancelar uma entrega que ja foi concluida.");
            return;
        }

        if (sol.getStatus().equalsIgnoreCase(STATUS_CANCELADA)) {
            System.out.println("[AVISO] Esta solicitacao ja esta cancelada.");
            return;
        }

        sol.setStatus(STATUS_CANCELADA);

        ItemDoacao item = buscarItemPorId(sol.getItem().getId());
        if (item != null) {
            item.setQuantidade(item.getQuantidade() + sol.getQuantidadeSolicitada());
            item.setStatus(STATUS_DISPONIVEL);
        }

        salvarNoArquivo();

        System.out.println("\n==================================");
        System.out.println("[SUCESSO] Solicitacao Cancelada!");
        System.out.println("Os itens foram devolvidos ao estoque.");
        System.out.println("==================================");
    }

    public void alterarPrioridadeBeneficiario(int id, int novaPrioridade) {
        Beneficiario b = buscarBeneficiarioPorId(id);
        if (b != null) {
            b.setNivelPrioridade(novaPrioridade);
            salvarNoArquivo();
            System.out.println("[SUCESSO] Prioridade atualizada com sucesso!");
        } else {
            System.out.println("[ERRO] Beneficiario nao encontrado.");
        }
    }

    public String obterNomeBeneficiarioDaDoacao(DoacaoEfetivada d) {
        return d.getBeneficiario() != null ? d.getBeneficiario().getNome() : "Beneficiario nao encontrado";
    }
}