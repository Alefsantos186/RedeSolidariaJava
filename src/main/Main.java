package main;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.stream.Collectors;
import util.Validador;

import model.Doador;
import model.Beneficiario;
import model.ItemDoacao;
import model.Solicitacao;
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
                System.out.println("Entrada Inválida! Por favor, digite apenas números inteiros.");
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
        System.out.println("   REDE SOLIDÁRIA DE DOAÇÃO");
        System.out.println("==================================");
        System.out.println("Como deseja acessar o sistema?");
        System.out.println("1. Usuário (Apenas Cadastro e Consulta)");
        System.out.println("2. Administrador (Acesso Total)");
        System.out.print("Escolha: ");
        
        perfil = lerInteiroSeguro(scanner);

        if (perfil == 2){
            System.out.print("Digite a senha de administrador: ");
            String senha = scanner.nextLine().trim();
            if (!senha.equals(SENHA_ADMIN)){
                System.out.println("Senha incorreta! Você entrou como Usuário.");
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
            System.out.println("2. Cadastrar Beneficiário");
            System.out.println("3. Cadastrar Item para Doação");
            System.out.println("\n--- CONSULTAR ---");
            System.out.println("4. Listar Itens Disponíveis (Com Filtro)");
            System.out.println("5. Listar Doadores");
            System.out.println("6. Listar Beneficiários (Ordenado por Prioridade)");
            System.out.println("\n--- SOLICITAÇÕES ---");
            System.out.println("7. Solicitar Item para um Beneficiário");
            
           if (perfil == 2) {
                System.out.println("\n--- ÁREA ADMINISTRATIVA ---");
                System.out.println("8. Atualizar Telefone do Doador");
                System.out.println("9. Excluir Doador (Individual)");
                System.out.println("10. Excluir Beneficiário (Individual)");
                System.out.println("11. Excluir Item de Doação (Individual)");
                System.out.println("12. APAGAR TODOS os Doadores");
                System.out.println("13. APAGAR TODOS os Beneficiários");
                System.out.println("14. APAGAR TODOS os Itens de Doação");
                System.out.println("15. Concluir Entrega (Mudar status para Entregue/Concluída)");
                System.out.println("16. Relatórios do Sistema");
            }
            
            System.out.println("\n0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiroSeguro(scanner);

            switch(opcao) {

                case 1:
                    System.out.println("\n--- NOVO DOADOR ---");
                    int idDoador = bancoDeDados.gerarIdDoador();
                    System.out.println("ID gerado automaticamente: " + idDoador);
                    
                    System.out.print("\nNome: ");
                    String nomeD = scanner.nextLine().trim();
                    while (!Validador.isNomeValido(nomeD)) {
                        System.out.print("Inválido! Não use números ou símbolos. Digite o nome: ");
                        nomeD = scanner.nextLine().trim();
                    }
                    String nomeDoador = Validador.formatarNome(nomeD);

                    System.out.print("Telefone ex: (79)999999999 : ");
                    String telefoneDoador = scanner.nextLine().trim();
                    while (!Validador.isTelefoneValido(telefoneDoador)) {
                        System.out.print("Inválido! Digite com DDD e 11 números: ");
                        telefoneDoador = scanner.nextLine().trim();
                    }

                    System.out.print("Email (gmail, hotmail, yahoo): ");
                    String emailDoador = scanner.nextLine().trim();
                    while (!Validador.isEmailValido(emailDoador) || bancoDeDados.emailDoadorExiste(emailDoador)) {
                        if (!Validador.isEmailValido(emailDoador)) {
                            System.out.print("Inválido! Digite um e-mail aceito: ");
                        } else {
                            System.out.print("Este e-mail já está cadastrado! Digite outro: ");
                        }
                        emailDoador = scanner.nextLine().trim();
                    }

                    System.out.print("Endereço: ");
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
                    System.out.println("\n--- NOVO BENEFICIÁRIO ---");
                    int idBen = bancoDeDados.gerarIdBeneficiario();
                    System.out.println("ID gerado automaticamente: " + idBen);
                    
                    System.out.print("\nNome: ");
                    String nomeB = scanner.nextLine().trim();
                    while (!Validador.isNomeValido(nomeB)) {
                        System.out.print("Inválido! Não use números ou símbolos. Digite o nome: ");
                        nomeB = scanner.nextLine().trim();
                    }
                    String nomeBen = Validador.formatarNome(nomeB);

                    System.out.print("Telefone ex: (79)999999999 : ");
                    String telefoneBen = scanner.nextLine().trim();
                    while (!Validador.isTelefoneValido(telefoneBen)) {
                        System.out.print("Inválido! Digite com DDD e 11 números: ");
                        telefoneBen = scanner.nextLine().trim();
                    }

                    System.out.print("Email (gmail, hotmail, yahoo): ");
                    String emailBen = scanner.nextLine().trim();
                    while (!Validador.isEmailValido(emailBen) || bancoDeDados.emailBeneficiarioExiste(emailBen)) {
                        if (!Validador.isEmailValido(emailBen)) {
                            System.out.print("Inválido! Digite um e-mail aceito: ");
                        } else {
                            System.out.print("Este e-mail já está cadastrado! Digite outro: ");
                        }
                        emailBen = scanner.nextLine().trim();
                    }

                    System.out.print("Endereço: ");
                    String enderecoBen = Validador.formatarNome(scanner.nextLine().trim());

                    System.out.print("Tipo (ex: Família, ONG, Escola): ");
                    String tipoBen = Validador.formatarNome(scanner.nextLine().trim());

                    System.out.println("Nível de Prioridade definido automaticamente como: 2 (Média). A triagem será feita pela administração.");
                    int prioridadeBen = 2; 

                    Beneficiario novoBeneficiario = new Beneficiario(idBen, nomeBen, telefoneBen, emailBen, enderecoBen, tipoBen, prioridadeBen);
                    bancoDeDados.salvarBeneficiario(novoBeneficiario);

                    System.out.println("\n==================================");
                    System.out.println("     BENEFICIÁRIO CADASTRADO");
                    System.out.println("==================================");
                    System.out.println("ID: " + idBen);
                    System.out.println("Nome: " + nomeBen);
                    System.out.println("Tipo: " + tipoBen);
                    System.out.println("==================================");
                    break;

                case 3:
                    System.out.println("\n--- NOVO ITEM DE DOAÇÃO ---");
                    int idItem = bancoDeDados.gerarIdItem();
                    System.out.println("ID gerado automaticamente: " + idItem);

                    String verDoadores;
                    do {
                        System.out.print("Deseja ver a lista de Doadores? (S/N): ");
                        verDoadores = scanner.nextLine().trim().toUpperCase();
                        if (!verDoadores.equals("S") && !verDoadores.equals("N")) {
                            System.out.println("[ERRO] Entrada inválida! Digite obrigatoriamente 'S' para Sim ou 'N' para Não.");
                        }
                    } while (!verDoadores.equals("S") && !verDoadores.equals("N"));

                    if(verDoadores.equals("S")) {
                        bancoDeDados.listarDoadores().forEach(d -> System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome()));
                    }
                    
                    System.out.print("Digite o ID do Doador que está fornecendo este item (ou digite 0 para voltar ao menu): ");
                    int idDoadorOrigem = lerInteiroSeguro(scanner);
                    
                    if (idDoadorOrigem == 0) {
                        System.out.println("Operação cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    while (bancoDeDados.buscarDoadorPorId(idDoadorOrigem) == null) {
                        System.out.print("[ERRO] Doador não encontrado! Digite um ID válido (ou digite 0 para cancelar): ");
                        idDoadorOrigem = lerInteiroSeguro(scanner);
                        if (idDoadorOrigem == 0) break;
                    }
                    
                    if (idDoadorOrigem == 0) {
                        System.out.println("Operação cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    System.out.print("Nome do Item (ex: Casaco, Cesta Básica): ");
                    String nomeItem = Validador.formatarNome(scanner.nextLine().trim());

                    System.out.print("Categoria (ex: Alimento, Vestuário): ");
                    String categoriaItem = Validador.formatarNome(scanner.nextLine().trim());

                    String detalhesRoupa = ""; 
                    if (categoriaItem.equalsIgnoreCase("Vestuario") || categoriaItem.equalsIgnoreCase("Vestuário")) {
                        System.out.println("\nOpções de Vestuário Detectadas:");
                        System.out.print("Gênero (Masculina, Feminina, Unissex): ");
                        String genero = Validador.formatarNome(scanner.nextLine().trim());
                        System.out.print("Tamanho (P, M, G, GG, Infantil, etc): ");
                        String tamanho = scanner.nextLine().trim().toUpperCase();
                        
                        detalhesRoupa = " [Gênero: " + genero + " | Tamanho: " + tamanho + "]";
                    }

                    System.out.print("Descrição Detalhada: ");
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
                        System.out.println("\nEstado de Conservação:");
                        System.out.println("1 - Novo");
                        System.out.println("2 - Seminovo");
                        System.out.println("3 - Usado");
                        System.out.print("Escolha a opção (1-3): ");
                        opEstado = lerInteiroSeguro(scanner);
                        
                        if (opEstado == 1) estadoItem = "Novo";
                        else if (opEstado == 2) estadoItem = "Seminovo";
                        else if (opEstado == 3) estadoItem = "Usado";
                        else System.out.println("Opção inválida! Tente novamente.");
                    }

                    ItemDoacao novoItem = new ItemDoacao(idItem, nomeItem, categoriaItem, descriItem, qtdItem, estadoItem, "Disponível", idDoadorOrigem);
                    bancoDeDados.salvarItem(novoItem);
                    
                    Doador doadorSalvo = bancoDeDados.buscarDoadorPorId(idDoadorOrigem);
                    String nomeDoadorSalvo = (doadorSalvo != null) ? doadorSalvo.getNome() : "Desconhecido";

                    System.out.println("\n==================================");
                    System.out.println("         ITEM CADASTRADO");
                    System.out.println("==================================");
                    System.out.println("ID: " + idItem);
                    System.out.println("Item: " + nomeItem);
                    System.out.println("Doador: " + nomeDoadorSalvo);
                    System.out.println("Status: Disponível");
                    System.out.println("==================================");
                    break;

                case 4:
                    System.out.println("\n--- ITENS DISPONÍVEIS ---");

                    List<ItemDoacao> itensAtivos = bancoDeDados.listarItens().stream()
                        .filter(i -> i.getStatus().equalsIgnoreCase("Disponível") || i.getStatus().equalsIgnoreCase("Reservado"))
                        .collect(Collectors.toList());

                    if(itensAtivos.isEmpty()) {
                        System.out.println("Nenhum item disponível no momento.");
                    } else {
                        System.out.println("Deseja filtrar a lista?");
                        System.out.println("1 - Mostrar Todos");
                        System.out.println("2 - Filtrar por Categoria (ex: Vestuario, Alimento)");
                        System.out.print("Escolha: ");
                        int opFiltro = lerInteiroSeguro(scanner);

                        if (opFiltro == 2) {
                            System.out.print("Digite a categoria para filtrar: ");
                            String catFiltro = Validador.formatarNome(scanner.nextLine().trim());
                            System.out.println("\n--- RESULTADO DO FILTRO ---");
                            
                            List<ItemDoacao> filtrados = itensAtivos.stream()
                                 .filter(i -> i.getCategoria().equalsIgnoreCase(catFiltro))
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
                    System.out.println("\n--- LISTA DE BENEFICIÁRIOS (Ordenada por Urgência) ---");
                    List<Beneficiario> beneficiarios = bancoDeDados.listarBeneficiarios();
                    if (beneficiarios.isEmpty()) {
                        System.out.println("Nenhum beneficiário cadastrado.");
                    } else {
                        beneficiarios.stream()
                            .sorted(java.util.Comparator.comparingInt(Beneficiario::getNivelPrioridade))
                            .forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Tipo: " + b.getTipoBeneficiario() + " | Prioridade: " + b.getNivelPrioridade()));
                    }
                    break;

                case 7:
                    System.out.println("\n--- SOLICITAR ITEM ---");
                    System.out.println("-> Consulte o ID nas listas abaixo caso não lembre:");
                    
                    String verListas;
                    do {
                        System.out.print("Deseja ver a lista de Beneficiários e Itens agora? (S/N): ");
                        verListas = scanner.nextLine().trim().toUpperCase();
                        if (!verListas.equals("S") && !verListas.equals("N")) {
                            System.out.println("[ERRO] Entrada inválida! Digite obrigatoriamente 'S' para Sim ou 'N' para Não.");
                        }
                    } while (!verListas.equals("S") && !verListas.equals("N"));

                    if(verListas.equals("S")) {
                        bancoDeDados.listarBeneficiarios().forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome()));
                        System.out.println("-----------------");
                        bancoDeDados.listarItens().stream()
                            .filter(i -> i.getStatus().equalsIgnoreCase("Disponível"))
                            .forEach(i -> System.out.println("ID: " + i.getId() + " | Item: " + i.getNomeItem() + " | Estoque: " + i.getQuantidade() + " | Status: " + i.getStatus()));
                    }

                    System.out.print("\nDigite o ID do Beneficiário (ou digite 0 para voltar ao menu): ");
                    int idBenSol = lerInteiroSeguro(scanner);
                    if (idBenSol == 0) {
                        System.out.println("Operação cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    System.out.print("Digite o ID do Item Desejado (ou digite 0 para voltar ao menu): ");
                    int idItemSol = lerInteiroSeguro(scanner);
                    if (idItemSol == 0) {
                        System.out.println("Operação cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    System.out.print("Quantidade Necessária: ");
                    int qtdSol = lerInteiroSeguro(scanner);
                    while (qtdSol <= 0) {
                        System.out.print("[ERRO] A quantidade deve ser maior que zero. Digite novamente: ");
                        qtdSol = lerInteiroSeguro(scanner);
                    }
                    
                    System.out.print("Justificativa (ex: Roupas para campanha de inverno): ");
                    String justificativa = Validador.formatarNome(scanner.nextLine().trim());
                    
                    bancoDeDados.registrarSolicitacao(idBenSol, idItemSol, qtdSol, justificativa);
                    break;

                case 8:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- ATUALIZAR TELEFONE DOADOR ---");
                    System.out.print("ID do doador: ");
                    int idAtualizar = lerInteiroSeguro(scanner);
                    System.out.print("NOVO telefone: ");
                    String novoTel = scanner.nextLine().trim();
                    bancoDeDados.atualizarTelefoneDoador(idAtualizar, novoTel);
                    break;  

                case 9: 
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- EXCLUIR DOADOR ---");
                    System.out.print("ID do doador: ");
                    int idDelDoador = lerInteiroSeguro(scanner);
                    boolean doadorRemovido = bancoDeDados.deletarDoador(idDelDoador);
                    if(doadorRemovido) System.out.println("[SUCESSO] Doador removido.");
                    else System.out.println("[ERRO] Doador não encontrado.");
                    break;  

                case 10: 
                   if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- EXCLUIR BENEFICIÁRIO ---");
                    System.out.print("ID do beneficiário: ");
                    int idDelBen = lerInteiroSeguro(scanner);
                    boolean benRemovido = bancoDeDados.deletarBeneficiario(idDelBen);
                    if(benRemovido) System.out.println("[SUCESSO] Beneficiário removido.");
                    else System.out.println("[ERRO] Beneficiário não encontrado.");
                    break;  

                case 11: 
                   if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- EXCLUIR ITEM ---");
                    System.out.print("ID do item: ");
                    int idDelItem = lerInteiroSeguro(scanner);
                    boolean itemRemovido = bancoDeDados.deletarItem(idDelItem);
                    if(itemRemovido) System.out.println("[SUCESSO] Item removido.");
                    else System.out.println("[ERRO] Item não encontrado.");
                    break;
                
                case 12: 
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\nATENÇÃO! Você está prestes a apagar TODOS os Doadores da base de dados!");
                    System.out.print("Digite 'CONFIRMAR' em maiúsculo para prosseguir: ");
                    if (scanner.nextLine().trim().equals("CONFIRMAR")) {
                        bancoDeDados.apagarTodosDoadores();
                        System.out.println("\nTodos os Doadores foram apagados.");
                    } else {
                        System.out.println("\nOperação cancelada. Os Doadores estão salvos.");
                    }
                    break;

                case 13: 
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\nATENÇÃO! Você está prestes a apagar TODOS os Beneficiários da base de dados!");
                    System.out.print("Digite 'CONFIRMAR' em maiúsculo para prosseguir: ");
                    if (scanner.nextLine().trim().equals("CONFIRMAR")) {
                        bancoDeDados.apagarTodosBeneficiarios();
                        System.out.println("\nTodos os Beneficiários foram apagados.");
                    } else {
                        System.out.println("\nOperação cancelada. Os Beneficiários estão salvos.");
                    }
                    break;

                case 14: 
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\nATENÇÃO! Você está prestes a apagar TODOS os Itens de Doação da base de dados!");
                    System.out.print("Digite 'CONFIRMAR' em maiúsculo para prosseguir: ");
                    if (scanner.nextLine().trim().equals("CONFIRMAR")) {
                        bancoDeDados.apagarTodosItens();
                        System.out.println("\nTodos os Itens foram apagados.");
                    } else {
                        System.out.println("\nOperação cancelada. Os Itens estão salvos.");
                    }
                    break;
                    
                case 15:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n--- CONCLUIR ENTREGA ---");
                    
                    List<Solicitacao> pendentes = bancoDeDados.listarSolicitacoes().stream()
                        .filter(s -> !s.getStatus().equalsIgnoreCase("Concluída"))
                        .collect(Collectors.toList());

                    if(pendentes.isEmpty()) {
                        System.out.println("Não há solicitações pendentes para entrega.");
                        break;
                    }

                    System.out.println("Solicitações Pendentes no Sistema:");
                    pendentes.forEach(s -> System.out.println("ID Solicitacao: " + s.getId() + " | Beneficiário: " + s.getBeneficiario().getNome() + " | Status: " + s.getStatus()));
                    
                    System.out.print("\nDigite o ID da Solicitação para confirmar a entrega física (ou 0 para cancelar): ");
                    int idSolEntrega = lerInteiroSeguro(scanner);

                    if (idSolEntrega == 0) {
                        System.out.println("Operação cancelada! Voltando ao menu principal...");
                        break;
                    }
                    
                    bancoDeDados.concluirEntrega(idSolEntrega);
                    break;    

                case 16:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Administradores."); break; }
                    System.out.println("\n==================================");
                    System.out.println("       RELATÓRIOS DO SISTEMA");
                    System.out.println("==================================");
                    System.out.println("Total de Doadores: " + bancoDeDados.listarDoadores().size());
                    System.out.println("Total de Beneficiários: " + bancoDeDados.listarBeneficiarios().size());
                    System.out.println("Total de Itens Cadastrados: " + bancoDeDados.listarItens().size());
                    long concluidas = bancoDeDados.listarSolicitacoes().stream().filter(s -> s.getStatus().equalsIgnoreCase("Concluída")).count();
                    System.out.println("Total de Entregas Concluídas: " + concluidas);
                    System.out.println("==================================");
                    break;

                case 0:
                    System.out.println("\nSalvando dados e saindo do sistema... Até logo!");
                    break;
                    
                default:
                    System.out.println("\nOpção inválida! Tente novamente");                       
            } 
        } 
        scanner.close();
    }
}