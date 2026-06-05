package main;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.stream.Collectors;
import util.Validador;

import model.Doador;
import model.Beneficiario;
import model.ItemDoacao;
import model.Solicitacao;
import model.DoacaoEfetivada;
import repository.CadastroRepository;

public class Main {
    private static final String SENHA_ADMIN ="admin123";
    
    public static int lerInteiroSeguro(Scanner scanner) {
        while (true) {
            try {
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Entrada Invalida! Por favor, digite apenas numeros inteiros.");
                System.out.print("Tente novamente: ");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CadastroRepository bancoDeDados = new CadastroRepository();
        int perfil = 1;
        int opcao = -1;

        System.out.println("==================================");
        System.out.println("   REDE SOLIDARIA DE DOACAO");
        System.out.println("==================================");
        System.out.println("Como deseja acessar o sistema?");
        System.out.println("1. Usuario (Apenas Cadastro e Consulta)");
        System.out.println("2. Administrador (Acesso Total)");
        System.out.print("Escolha: ");
        
        perfil = lerInteiroSeguro(scanner);

        if (perfil == 2){
            System.out.print("Digite a senha de administrador: ");
            String senha = scanner.nextLine().trim();
            if (!senha.equals(SENHA_ADMIN)){
                System.out.println("Senha incorreta! Voce entrou como Usuario.");
                perfil = 1;
            }else {
                System.out.println("Login de Administrador realizado com sucesso!");
            }
        }
        
        while (opcao != 0) {
            System.out.println("\n==================================");
            System.out.println("         MENU PRINCIPAL");
            System.out.println("==================================");
            System.out.println("--- CADASTRAR ---");
            System.out.println("1. Cadastrar Doador");
            System.out.println("2. Cadastrar Beneficiario");
            System.out.println("3. Cadastrar Item para Doacao");
            System.out.println("\n--- CONSULTAR ---");
            System.out.println("4. Listar Itens Disponiveis");
            System.out.println("5. Listar Doadores");
            System.out.println("6. Listar Beneficiarios");
            System.out.println("\n--- SOLICITACOES ---");
            System.out.println("7. Solicitar Item para um Beneficiario");
            
           if (perfil == 2) {
                System.out.println("\n--- AREA ADMINISTRATIVA ---");
                System.out.println("8. Atualizar Telefone do Doador");
                System.out.println("9. Excluir Doador (Individual)");
                System.out.println("10. Excluir Beneficiario (Individual)");
                System.out.println("11. Excluir Item de Doacao (Individual)");
                System.out.println("12. Concluir Entrega");
                System.out.println("13. Cancelar Solicitacao");
                System.out.println("14. Listar Solicitacoes");
                System.out.println("15. Alterar Prioridade do Beneficiario");
                System.out.println("16. Relatorio Geral do Sistema");
                System.out.println("17. Relatorio de Doacoes Efetivadas");
            }
            
            System.out.println("\n0. Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiroSeguro(scanner);

            switch(opcao) {

                case 1:
                    System.out.println("\n--- NOVO DOADOR ---");
                    int idDoador = bancoDeDados.gerarIdDoador();
                    System.out.println("ID gerado automaticamente: " + idDoador);
                    
                    System.out.print("\nNome: ");
                    String nomeD = scanner.nextLine().trim();
                    while (!Validador.isNomeValido(nomeD)) {
                        System.out.print("Invalido! Nao use numeros ou simbolos. Digite o nome: ");
                        nomeD = scanner.nextLine().trim();
                    }
                    String nomeDoador = Validador.formatarNome(nomeD);

                    System.out.print("Telefone ex: (79)999999999 : ");
                    String telefoneDoador = scanner.nextLine().trim();
                    while (!Validador.isTelefoneValido(telefoneDoador)) {
                        System.out.print("Invalido! Digite com DDD e 11 numeros: ");
                        telefoneDoador = scanner.nextLine().trim();
                    }

                    System.out.print("Email (gmail, hotmail, yahoo): ");
                    String emailDoador = scanner.nextLine().trim();
                    while (!Validador.isEmailValido(emailDoador) || bancoDeDados.emailDoadorExiste(emailDoador)) {
                        if (!Validador.isEmailValido(emailDoador)) {
                            System.out.print("Invalido! Digite um e-mail aceito: ");
                        } else {
                            System.out.print("Este e-mail ja esta cadastrado! Digite outro: ");
                        }
                        emailDoador = scanner.nextLine().trim();
                    }

                    System.out.print("Endereco: ");
                    String enderecoDoador = Validador.formatarNome(scanner.nextLine().trim());

                    Doador novoDoador = new Doador(idDoador, nomeDoador, telefoneDoador, emailDoador, enderecoDoador);
                    bancoDeDados.salvarDoador(novoDoador);

                    System.out.println("\n==================================");
                    System.out.println("        DOADOR CADASTRADO");
                    System.out.println("==================================");
                    System.out.println("ID: " + idDoador);
                    System.out.println("Nome: " + nomeDoador);
                    System.out.println("==================================");
                    break;

                case 2:
                    System.out.println("\n--- NOVO BENEFICIARIO ---");
                    int idBen = bancoDeDados.gerarIdBeneficiario();
                    System.out.println("ID gerado automaticamente: " + idBen);
                    
                    System.out.print("\nNome: ");
                    String nomeB = scanner.nextLine().trim();
                    while (!Validador.isNomeValido(nomeB)) {
                        System.out.print("Invalido! Nao use numeros ou simbolos. Digite o nome: ");
                        nomeB = scanner.nextLine().trim();
                    }
                    String nomeBen = Validador.formatarNome(nomeB);

                    System.out.print("Telefone ex: (79)999999999 : ");
                    String telefoneBen = scanner.nextLine().trim();
                    while (!Validador.isTelefoneValido(telefoneBen)) {
                        System.out.print("Invalido! Digite com DDD e 11 numeros: ");
                        telefoneBen = scanner.nextLine().trim();
                    }

                    System.out.print("Email (gmail, hotmail, yahoo): ");
                    String emailBen = scanner.nextLine().trim();
                    while (!Validador.isEmailValido(emailBen) || bancoDeDados.emailBeneficiarioExiste(emailBen)) {
                        if (!Validador.isEmailValido(emailBen)) {
                            System.out.print("Invalido! Digite um e-mail aceito: ");
                        } else {
                            System.out.print("Este e-mail ja esta cadastrado! Digite outro: ");
                        }
                        emailBen = scanner.nextLine().trim();
                    }

                    System.out.print("Endereco: ");
                    String enderecoBen = Validador.formatarNome(scanner.nextLine().trim());

                    System.out.print("Tipo (ex: Familia, ONG, Escola): ");
                    String tipoBen = Validador.formatarNome(scanner.nextLine().trim());

                    System.out.println("Nivel de Prioridade definido automaticamente como: 2 (Media). A triagem sera feita pela administracao.");
                    int prioridadeBen = 2; 

                    Beneficiario novoBeneficiario = new Beneficiario(idBen, nomeBen, telefoneBen, emailBen, enderecoBen, tipoBen, prioridadeBen);
                    bancoDeDados.salvarBeneficiario(novoBeneficiario);

                    System.out.println("\n==================================");
                    System.out.println("     BENEFICIARIO CADASTRADO");
                    System.out.println("==================================");
                    System.out.println("ID: " + idBen);
                    System.out.println("Nome: " + nomeBen);
                    System.out.println("Tipo: " + tipoBen);
                    System.out.println("==================================");
                    break;

                case 3:
                    System.out.println("\n--- NOVO ITEM DE DOACAO ---");
                    int idItem = bancoDeDados.gerarIdItem();
                    System.out.println("ID gerado automaticamente: " + idItem);

                    String verListasAux;
                    do {
                        System.out.print("Deseja ver a lista de Doadores? (S/N): ");
                        verListasAux = scanner.nextLine().trim().toUpperCase();
                        if (!verListasAux.equals("S") && !verListasAux.equals("N")) {
                            System.out.println("[ERRO] Entrada invalida! Digite obrigatoriamente 'S' para Sim ou 'N' para Nao.");
                        }
                    } while (!verListasAux.equals("S") && !verListasAux.equals("N"));

                    if(verListasAux.equals("S")) {
                        bancoDeDados.listarDoadores().forEach(d -> System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome()));
                    }
                    
                    System.out.print("Digite o ID do Doador que esta fornecendo este item (ou digite 0 para voltar ao menu): ");
                    int idDoadorOrigem = lerInteiroSeguro(scanner);
                    
                    if (idDoadorOrigem == 0) {
                        System.out.println("Operacao cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    while (bancoDeDados.buscarDoadorPorId(idDoadorOrigem) == null) {
                        System.out.print("[ERRO] Doador nao encontrado! Digite um ID valido (ou digite 0 para cancelar): ");
                        idDoadorOrigem = lerInteiroSeguro(scanner);
                        if (idDoadorOrigem == 0) break;
                    }
                    
                    if (idDoadorOrigem == 0) {
                        System.out.println("Operacao cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    System.out.print("Nome do Item (ex: Casaco, Cesta Basica): ");
                    String nomeItem = Validador.formatarNome(scanner.nextLine().trim());

                    System.out.print("Categoria (ex: Alimento, Vestuario): ");
                    String categoriaItem = scanner.nextLine().trim();
                    while (!Validador.isNomeValido(categoriaItem)) {
                        System.out.print("[ERRO] Categoria invalida! Use apenas letras. Digite novamente: ");
                        categoriaItem = scanner.nextLine().trim();
                    }
                    categoriaItem = Validador.formatarNome(categoriaItem);

                    String detalhesRoupa = ""; 
                    if (categoriaItem.equalsIgnoreCase("Vestuario")) {
                        System.out.println("\nOpcoes de Vestuario Detectadas:");
                        System.out.print("Genero (Masculina, Feminina, Unissex): ");
                        String genero = Validador.formatarNome(scanner.nextLine().trim());
                        System.out.print("Tamanho (P, M, G, GG, Infantil, etc): ");
                        String tamanho = scanner.nextLine().trim().toUpperCase();
                        
                        detalhesRoupa = " [Genero: " + genero + " | Tamanho: " + tamanho + "]";
                    }

                    System.out.print("Descricao Detalhada: ");
                    String descriItem = Validador.formatarNome(scanner.nextLine().trim()) + detalhesRoupa;

                    System.out.print("Quantidade: ");
                    int qtdItem = lerInteiroSeguro(scanner);
                    while (qtdItem <= 0) {
                        System.out.print("[ERRO] A quantidade deve ser maior que zero. Digite novamente: ");
                        qtdItem = lerInteiroSeguro(scanner);
                    }
                    
                    int opEstado = 0;
                    String estadoItem = "";
                    while (opEstado < 1 || opEstado > 3) {
                        System.out.println("\nEstado de Conservacao:");
                        System.out.println("1 - Novo");
                        System.out.println("2 - Seminovo");
                        System.out.println("3 - Usado");
                        System.out.print("Escolha a opcao (1-3): ");
                        opEstado = lerInteiroSeguro(scanner);
                        
                        if (opEstado == 1) estadoItem = "Novo";
                        else if (opEstado == 2) estadoItem = "Seminovo";
                        else if (opEstado == 3) estadoItem = "Usado";
                        else System.out.println("Opcao invalida! Tente novamente.");
                    }

                    ItemDoacao novoItem = new ItemDoacao(idItem, nomeItem, categoriaItem, descriItem, qtdItem, estadoItem, "Disponivel", idDoadorOrigem);
                    bancoDeDados.salvarItem(novoItem);
                    
                    Doador doadorSalvo = bancoDeDados.buscarDoadorPorId(idDoadorOrigem);
                    String nomeDoadorSalvo = (doadorSalvo != null) ? doadorSalvo.getNome() : "Desconhecido";

                    System.out.println("\n==================================");
                    System.out.println("         ITEM CADASTRADO");
                    System.out.println("==================================");
                    System.out.println("ID: " + idItem);
                    System.out.println("Item: " + nomeItem);
                    System.out.println("Doador: " + nomeDoadorSalvo);
                    System.out.println("Status: Disponivel");
                    System.out.println("==================================");
                    break;

                case 4:
                    System.out.println("\n--- ITENS DISPONIVEIS ---");

                    List<ItemDoacao> itensAtivos = bancoDeDados.listarItens().stream()
                        .filter(i -> i.getStatus().equalsIgnoreCase("Disponivel") || i.getStatus().equalsIgnoreCase("Reservado"))
                        .collect(Collectors.toList());

                    if(itensAtivos.isEmpty()) {
                        System.out.println("Nenhum item disponivel no momento.");
                    } else {
                        System.out.println("Deseja filtrar a lista?");
                        System.out.println("1 - Mostrar Todos");
                        System.out.println("2 - Filtrar por Categoria (ex: Vestuario, Alimento)");
                        System.out.print("Escolha: ");
                        int opFiltro = lerInteiroSeguro(scanner);

                        if (opFiltro == 2) {
                            System.out.print("Digite a categoria para filtrar: ");
                            String catFiltro = scanner.nextLine().trim();
                            while (!Validador.isNomeValido(catFiltro)) {
                                System.out.print("[ERRO] Categoria invalida! Use apenas letras. Digite novamente: ");
                                catFiltro = scanner.nextLine().trim();
                            }
                            catFiltro = Validador.formatarNome(catFiltro);
                            
                            System.out.println("\n--- RESULTADO DO FILTRO ---");
                            String catFinal = catFiltro;
                            List<ItemDoacao> filtrados = itensAtivos.stream()
                                 .filter(i -> i.getCategoria().equalsIgnoreCase(catFinal))
                                 .collect(Collectors.toList());
                            
                            if(filtrados.isEmpty()) {
                                System.out.println("Nenhum item encontrado nessa categoria.");
                            } else {
                                filtrados.forEach(i -> {
                                    Doador d = bancoDeDados.buscarDoadorPorId(i.getIdDoador());
                                    String nomeDoDoador = (d != null) ? d.getNome() : "Desconhecido";
                                    System.out.println("ID: " + i.getId() + " | Item: " + i.getNomeItem() + " | Doador: " + nomeDoDoador + " | Qtd: " + i.getQuantidade() + " | Status: " + i.getStatus());
                                });
                            }
                        } else {
                            itensAtivos.forEach(i -> {
                                Doador d = bancoDeDados.buscarDoadorPorId(i.getIdDoador());
                                String nomeDoDoador = (d != null) ? d.getNome() : "Desconhecido";
                                System.out.println("ID: " + i.getId() + " | Item: " + i.getNomeItem() + " | Doador: " + nomeDoDoador + " | Qtd: " + i.getQuantidade() + " | Status: " + i.getStatus());
                            });
                        }
                    }
                    break;

               case 5:
                    System.out.println("\n--- LISTA DE DOADORES ---");
                    List<Doador> doadores = bancoDeDados.listarDoadores();
                    if (doadores.isEmpty()) {
                        System.out.println("Nenhum doador cadastrado.");
                    } else {
                        System.out.println("1 - Mostrar Todos");
                        System.out.println("2 - Filtrar por Estado (Sigla)");
                        System.out.print("Escolha: ");
                        int opFiltroDoador = lerInteiroSeguro(scanner);

                        if (opFiltroDoador == 2) {
                            System.out.print("Digite a sigla do Estado (ex: SP, SE, BA): ");
                            String ufBuscada = scanner.nextLine().trim().toUpperCase();
                            System.out.println("\n--- DOADORES EM " + ufBuscada + " ---");
                            
                            List<Doador> filtradosUF = doadores.stream()
                                    .filter(d -> Validador.obterEstadoPorTelefone(d.getTelefone()).equals(ufBuscada))
                                    .collect(Collectors.toList());
                                    
                            if(filtradosUF.isEmpty()){
                                System.out.println("Nenhum doador encontrado nesse estado.");
                            } else {
                                filtradosUF.forEach(d -> System.out.println("ID: " + d.getId() +" | Nome: " + d.getNome() + " | Tel: " + d.getTelefone() + " | Email: " + d.getEmail()));
                            }
                        } else {
                            doadores.forEach(d -> System.out.println("ID: " + d.getId() +" | Nome: " + d.getNome() + " | Estado: " + Validador.obterEstadoPorTelefone(d.getTelefone()) + " | Tel: " + d.getTelefone()));
                        }
                    }
                    break;
                    
                case 6:
                    System.out.println("\n--- LISTA DE BENEFICIARIOS (Ordenada por Urgencia) ---");
                    List<Beneficiario> beneficiarios = bancoDeDados.listarBeneficiarios();
                    if (beneficiarios.isEmpty()) {
                        System.out.println("Nenhum beneficiario cadastrado.");
                    } else {
                        beneficiarios.stream()
                            .sorted(java.util.Comparator.comparingInt(Beneficiario::getNivelPrioridade))
                            .forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Tipo: " + b.getTipoBeneficiario() + " | Prioridade: " + b.getNivelPrioridade()));
                    }
                    break;

                case 7:
                    System.out.println("\n--- SOLICITAR ITEM ---");
                    System.out.println("-> Consulte o ID nas listas abaixo caso nao lembre:");
                    
                    String verListas;
                    do {
                        System.out.print("Deseja ver a lista de Beneficiarios e Itens agora? (S/N): ");
                        verListas = scanner.nextLine().trim().toUpperCase();
                        if (!verListas.equals("S") && !verListas.equals("N")) {
                            System.out.println("[ERRO] Entrada invalida! Digite obrigatoriamente 'S' para Sim ou 'N' para Nao.");
                        }
                    } while (!verListas.equals("S") && !verListas.equals("N"));

                    if(verListas.equals("S")) {
                        bancoDeDados.listarBeneficiarios().forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome()));
                        System.out.println("-----------------");
                        bancoDeDados.listarItens().stream()
                            .filter(i -> i.getStatus().equalsIgnoreCase("Disponivel"))
                            .forEach(i -> System.out.println("ID: " + i.getId() + " | Item: " + i.getNomeItem() + " | Estoque: " + i.getQuantidade() + " | Status: " + i.getStatus()));
                    }

                    System.out.print("\nDigite o ID do Beneficiario (ou digite 0 para voltar ao menu): ");
                    int idBenSol = lerInteiroSeguro(scanner);
                    if (idBenSol == 0) {
                        System.out.println("Operacao cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    System.out.print("Digite o ID do Item Desejado (ou digite 0 para voltar ao menu): ");
                    int idItemSol = lerInteiroSeguro(scanner);
                    if (idItemSol == 0) {
                        System.out.println("Operacao cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    System.out.print("Quantidade Necessaria: ");
                    int qtdSol = lerInteiroSeguro(scanner);
                    while (qtdSol <= 0) {
                        System.out.print("[ERRO] A quantidade deve ser maior que zero. Digite novamente: ");
                        qtdSol = lerInteiroSeguro(scanner);
                    }
                    
                    System.out.print("Justificativa (minimo 10 caracteres): ");
                    String justificativa = scanner.nextLine().trim();
                    while (justificativa.length() < 10) {
                        System.out.print("[ERRO] Justificativa muito curta. Explique melhor a necessidade: ");
                        justificativa = scanner.nextLine().trim();
                    }
                    justificativa = Validador.formatarNome(justificativa);
                    
                    bancoDeDados.registrarSolicitacao(idBenSol, idItemSol, qtdSol, justificativa);
                    break;

                case 8:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- ATUALIZAR TELEFONE DOADOR ---");
                    List<Doador> listaDoadores = bancoDeDados.listarDoadores();
                    if (listaDoadores.isEmpty()) {
                        System.out.println("Nenhum doador cadastrado no sistema.");
                        break;
                    }
                    System.out.println("Doadores Cadastrados:");
                    listaDoadores.forEach(d -> System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome() + " | Tel Atual: " + d.getTelefone()));
                    System.out.print("\nDigite o ID do doador para atualizar o telefone (ou 0 para cancelar): ");
                    int idAtualizar = lerInteiroSeguro(scanner);
                    if (idAtualizar == 0) {
                        System.out.println("Operacao cancelada!");
                        break;
                    }
                    while (bancoDeDados.buscarDoadorPorId(idAtualizar) == null) {
                        System.out.print("[ERRO] Doador nao encontrado! Digite um ID valido (ou 0 para cancelar): ");
                        idAtualizar = lerInteiroSeguro(scanner);
                        if (idAtualizar == 0) break;
                    }
                    if (idAtualizar == 0) {
                        System.out.println("Operacao cancelada!");
                        break;
                    }
                    System.out.print("Digite o NOVO telefone: ");
                    String novoTel = scanner.nextLine().trim();
                    while (!Validador.isTelefoneValido(novoTel)) {
                        System.out.print("[ERRO] Telefone invalido! Digite com DDD e 11 numeros: ");
                        novoTel = scanner.nextLine().trim();
                    }
                    bancoDeDados.atualizarTelefoneDoador(idAtualizar, novoTel);
                    break;  

                case 9: 
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- EXCLUIR DOADOR ---");
                    System.out.print("ID do doador: ");
                    int idDelDoador = lerInteiroSeguro(scanner);
                    
                    System.out.print("Tem certeza que deseja excluir o doador " + idDelDoador + "? (S/N): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                        boolean doadorRemovido = bancoDeDados.deletarDoador(idDelDoador);
                        if(doadorRemovido) System.out.println("[SUCESSO] Doador removido.");
                    } else {
                        System.out.println("Operacao cancelada.");
                    }
                    break;  

                case 10: 
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- EXCLUIR BENEFICIARIO ---");
                    System.out.print("ID do beneficiario: ");
                    int idDelBen = lerInteiroSeguro(scanner);
                    
                    System.out.print("Tem certeza que deseja excluir o beneficiario " + idDelBen + "? (S/N): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                        boolean benRemovido = bancoDeDados.deletarBeneficiario(idDelBen);
                        if(benRemovido) System.out.println("[SUCESSO] Beneficiario removido.");
                    } else {
                        System.out.println("Operacao cancelada.");
                    }
                    break;  

                case 11: 
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- EXCLUIR ITEM ---");
                    System.out.print("ID do item: ");
                    int idDelItem = lerInteiroSeguro(scanner);
                    
                    System.out.print("Tem certeza que deseja excluir o item " + idDelItem + "? (S/N): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                        boolean itemRemovido = bancoDeDados.deletarItem(idDelItem);
                        if(itemRemovido) System.out.println("[SUCESSO] Item removido.");
                    } else {
                        System.out.println("Operacao cancelada.");
                    }
                    break;
                    
                case 12:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- CONCLUIR ENTREGA ---");
                    
                    List<Solicitacao> pendentes = bancoDeDados.listarSolicitacoes().stream()
                        .filter(s -> !s.getStatus().equalsIgnoreCase("Concluida") && !s.getStatus().equalsIgnoreCase("Cancelada"))
                        .collect(Collectors.toList());

                    if(pendentes.isEmpty()) {
                        System.out.println("Nao ha solicitacoes pendentes para entrega.");
                        break;
                    }

                    System.out.println("Solicitacoes Pendentes no Sistema:");
                    pendentes.forEach(s -> System.out.println("ID Solicitacao: " + s.getId() + " | Beneficiario: " + s.getBeneficiario().getNome() + " | Status: " + s.getStatus()));
                    
                    System.out.print("\nDigite o ID da Solicitacao para confirmar a entrega fisica (ou 0 para cancelar): ");
                    int idSolEntrega = lerInteiroSeguro(scanner);

                    if (idSolEntrega == 0) {
                        System.out.println("Operacao cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    System.out.print("Adicione uma observacao (ou deixe em branco): ");
                    String observacao = scanner.nextLine().trim();
                    
                    if (observacao.isEmpty()) {
                        observacao = "Entrega concluida sem observacoes adicionais.";
                    } else {
                        while (observacao.length() < 5) {
                            System.out.print("[ERRO] Observacao muito curta. Digite novamente (minimo 5 caracteres) ou deixe em branco: ");
                            observacao = scanner.nextLine().trim();
                            if (observacao.isEmpty()) {
                                observacao = "Entrega concluida sem observacoes adicionais.";
                                break;
                            }
                        }
                    }
                    
                    bancoDeDados.concluirEntrega(idSolEntrega, observacao);
                    break;    

                case 13:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- CANCELAR SOLICITACAO ---");
                    
                    List<Solicitacao> cancelaveis = bancoDeDados.listarSolicitacoes().stream()
                        .filter(s -> !s.getStatus().equalsIgnoreCase("Concluida") && !s.getStatus().equalsIgnoreCase("Cancelada"))
                        .collect(Collectors.toList());

                    if (cancelaveis.isEmpty()) {
                        System.out.println("Nao ha solicitacoes ativas pendentes que possam ser canceladas.");
                        break;
                    }

                    System.out.println("Solicitacoes Ativas no Sistema:");
                    cancelaveis.forEach(s -> System.out.println("ID Solicitacao: " + s.getId() + " | Beneficiario: " + s.getBeneficiario().getNome() + " | Item: " + s.getItem().getNomeItem() + " | Qtd: " + s.getQuantidadeSolicitada()));
                    
                    System.out.print("\nDigite o ID da Solicitacao que deseja CANCELAR (ou 0 para voltar): ");
                    int idSolCancelar = lerInteiroSeguro(scanner);

                    if (idSolCancelar == 0) {
                        System.out.println("Operacao cancelada!");
                        break;
                    }

                    bancoDeDados.cancelarSolicitacao(idSolCancelar);
                    break;

                case 14:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- LISTAR SOLICITACOES ---");
                    List<Solicitacao> todasSolicitacoes = bancoDeDados.listarSolicitacoes();
                    if (todasSolicitacoes.isEmpty()) {
                        System.out.println("Nenhuma solicitacao registrada no sistema.");
                    } else {
                        todasSolicitacoes.forEach(s -> System.out.println("ID: " + s.getId() + " | Beneficiario: " + s.getBeneficiario().getNome() + " | Item: " + s.getItem().getNomeItem() + " | Qtd: " + s.getQuantidadeSolicitada() + " | Status: " + s.getStatus()));
                    }
                    break;

                case 15:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- ALTERAR PRIORIDADE DO BENEFICIARIO ---");
                    List<Beneficiario> listaBens = bancoDeDados.listarBeneficiarios();
                    if (listaBens.isEmpty()) {
                        System.out.println("Nenhum beneficiario cadastrado.");
                        break;
                    }
                    listaBens.forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Prioridade Atual: " + b.getNivelPrioridade()));
                    
                    System.out.print("\nDigite o ID do beneficiario para alterar (ou 0 para cancelar): ");
                    int idPri = lerInteiroSeguro(scanner);
                    if (idPri == 0) break;
                    
                    while (bancoDeDados.buscarBeneficiarioPorId(idPri) == null) {
                        System.out.print("[ERRO] ID invalido! Digite novamente (ou 0 para cancelar): ");
                        idPri = lerInteiroSeguro(scanner);
                        if (idPri == 0) break;
                    }
                    if (idPri == 0) break;
                    
                    int novaPri = 0;
                    while (novaPri < 1 || novaPri > 3) {
                        System.out.println("Defina o novo nivel de prioridade:");
                        System.out.println("1 - Alta");
                        System.out.println("2 - Media");
                        System.out.println("3 - Baixa");
                        System.out.print("Escolha (1-3): ");
                        novaPri = lerInteiroSeguro(scanner);
                    }
                    
                    bancoDeDados.alterarPrioridadeBeneficiario(idPri, novaPri);
                    break;

                case 16:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n==================================");
                    System.out.println("       RELATORIO GERAL DO SISTEMA");
                    System.out.println("==================================");
                    System.out.println("Total de Doadores: " + bancoDeDados.listarDoadores().size());
                    System.out.println("Total de Beneficiarios: " + bancoDeDados.listarBeneficiarios().size());
                    System.out.println("Total de Itens Cadastrados: " + bancoDeDados.listarItens().size());
                    System.out.println("Total de Solicitacoes Registradas: " + bancoDeDados.listarSolicitacoes().size());
                    System.out.println("Total de Doacoes Efetivadas: " + bancoDeDados.listarDoacoesEfetivadas().size());
                    
                    System.out.println("\n--- ITENS POR CATEGORIA ---");
                    bancoDeDados.listarItens().stream()
                        .collect(Collectors.groupingBy(ItemDoacao::getCategoria, Collectors.counting()))
                        .forEach((cat, total) -> System.out.println(cat + ": " + total));
                        
                    System.out.println("\n--- DOADORES POR ESTADO ---");
                    bancoDeDados.listarDoadores().stream()
                        .collect(Collectors.groupingBy(d -> Validador.obterEstadoPorTelefone(d.getTelefone()), Collectors.counting()))
                        .forEach((estado, total) -> System.out.println(estado + ": " + total));
                        
                    System.out.println("\n--- ITENS MAIS SOLICITADOS ---");
                    Map<String, Long> itensMaisSolicitados = bancoDeDados.listarSolicitacoes().stream()
                        .filter(s -> s.getItem() != null)
                        .collect(Collectors.groupingBy(s -> s.getItem().getNomeItem(), Collectors.counting()));
                    
                    if (itensMaisSolicitados.isEmpty()) {
                        System.out.println("Nenhuma solicitacao registrada.");
                    } else {
                        itensMaisSolicitados.entrySet().stream()
                            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                            .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue() + " solicitacoes"));
                    }
                    System.out.println("==================================");
                    break;

                case 17:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n==================================");
                    System.out.println("     RELATORIO DE DOACOES EFETIVADAS");
                    System.out.println("==================================");
                    List<DoacaoEfetivada> relatorioDoacoes = bancoDeDados.listarDoacoesEfetivadas();

                    if (relatorioDoacoes.isEmpty()) {
                        System.out.println("Nenhuma doacao foi efetivada ate o momento.");
                    } else {
                        relatorioDoacoes.forEach(d -> {
                            String nomeDoDoadorRegistrado = (d.getDoador() != null) ? d.getDoador().getNome() : "Doador nao encontrado";
                            String nomeDoBeneficiarioRegistrado = (d.getBeneficiario() != null) ? d.getBeneficiario().getNome() : "Beneficiario nao encontrado";
                            String nomeDoItemRegistrado = (d.getItem() != null) ? d.getItem().getNomeItem() : "Item nao encontrado";
                            
                            System.out.println(
                                "ID Doacao: " + d.getId()
                                + " | Data: " + d.getData()
                                + " | Beneficiario: " + nomeDoBeneficiarioRegistrado
                                + " | Doador: " + nomeDoDoadorRegistrado
                                + " | Item: " + nomeDoItemRegistrado
                                + " | Obs: " + d.getObservacoes()
                            );
                        });
                        
                        System.out.println("\n--- BENEFICIARIOS MAIS ATENDIDOS ---");
                        bancoDeDados.listarDoacoesEfetivadas().stream()
                            .collect(Collectors.groupingBy(bancoDeDados::obterNomeBeneficiarioDaDoacao, Collectors.counting()))
                            .entrySet().stream()
                            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                            .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue() + " entregas"));
                    }
                    System.out.println("==================================");
                    break;

                case 0:
                    System.out.println("\nSalvando dados e saindo do sistema... Ate logo!");
                    break;
                    
                default:
                    System.out.println("\nOpcao invalida! Tente novamente");                       
            } 
        } 
        scanner.close();
    }
}