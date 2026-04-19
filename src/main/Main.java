package main;

import java.util.List;
import java.util.Scanner;
import util.Validador;

import model.Doador;
import model.Beneficiario;
import model.ItemDoacao;
import repository.CadastroRepository;

public class Main {
    private static final String SENHA_DEV ="admin123";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CadastroRepository bancoDeDados = new CadastroRepository();
        int perfil = 1;
        int opcao = -1;

        System.out.println("==================================");
        System.out.println("   REDE SOLIDÁRIA DE DOAÇÃO");
        System.out.println("==================================");
        System.out.println("Como deseja acessar o sistema?");
        System.out.println("1. Usuario comum (Apenas Cadastro e Consulta)");
        System.out.println("2. Desenvolvedor (Acesso Total)");
        System.out.print("Escolha: ");
        perfil = scanner.nextInt();
        scanner.nextLine();

        if (perfil == 2){
            System.out.print("Digite a senha de administrador: ");
            String senha = scanner.nextLine();
            if (!senha.equals(SENHA_DEV)){
                System.out.println("Senha incorreta! Você entrou como Usuário Comum.");
                perfil = 1;
            }else {
                System.out.println("Login de Desenvolvedor realizado com sucesso!");
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
            System.out.println("4. Listar Itens Disponíveis");
            System.out.println("5. Listar Doadores");
            System.out.println("6. Listar Beneficiários");
            
           if (perfil == 2) {
                System.out.println("\n--- ÁREA DO DESENVOLVEDOR ---");
                System.out.println("7. Atualizar Telefone do Doador");
                System.out.println("8. Excluir Doador (Individual)");
                System.out.println("9. Excluir Beneficiário (Individual)");
                System.out.println("10. Excluir Item de Doação (Individual)");
                System.out.println("11. APAGAR TODOS os Doadores");
                System.out.println("12. APAGAR TODOS os Beneficiários");
                System.out.println("13. APAGAR TODOS os Itens de Doação");
            }
            
            System.out.println("\n0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {

                case 1:
                    System.out.println("\n--- NOVO DOADOR ---");
                    int idDoador = bancoDeDados.gerarIdDoador();
                    System.out.println("ID gerado automaticamente: " + idDoador);
                    
                    System.out.print("\nNome: ");
                    String nomeD = scanner.nextLine();
                    while (!Validador.isNomeValido(nomeD)) {
                        System.out.print("Inválido! Não use números ou símbolos. Digite o nome: ");
                        nomeD = scanner.nextLine();
                    }
                    String nomeDoador = Validador.formatarNome(nomeD);

                    System.out.print("Telefone ex: (79)999999999 : ");
                    String telefoneDoador = scanner.nextLine();
                    while (!Validador.isTelefoneValido(telefoneDoador)) {
                        System.out.print("Inválido! Digite com DDD e 11 números: ");
                        telefoneDoador = scanner.nextLine();
                    }

                    System.out.print("Email (gmail, hotmail, yahoo): ");
                    String emailDoador = scanner.nextLine();
                    while (!Validador.isEmailValido(emailDoador) || bancoDeDados.emailDoadorExiste(emailDoador)) {
                        if (!Validador.isEmailValido(emailDoador)) {
                            System.out.print("Inválido! Digite um e-mail aceito: ");
                        } else {
                            System.out.print("Este e-mail já está cadastrado! Digite outro: ");
                        }
                        emailDoador = scanner.nextLine();
                    }

                    System.out.print("Endereço: ");
                    String enderecoDoador = Validador.formatarNome(scanner.nextLine());

                    Doador novoDoador = new Doador(idDoador, nomeDoador, telefoneDoador, emailDoador, enderecoDoador);
                    bancoDeDados.salvarDoador(novoDoador);
                    System.out.println("\nDoador cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.println("\n--- NOVO BENEFICIÁRIO ---");
                    int idBen = bancoDeDados.gerarIdBeneficiario();
                    System.out.println("ID gerado automaticamente: " + idBen);
                    
                    System.out.print("\nNome: ");
                    String nomeB = scanner.nextLine();
                    while (!Validador.isNomeValido(nomeB)) {
                        System.out.print("Inválido! Não use números ou símbolos. Digite o nome: ");
                        nomeB = scanner.nextLine();
                    }
                    String nomeBen = Validador.formatarNome(nomeB);

                    System.out.print("Telefone ex: (79)999999999 : ");
                    String telefoneBen = scanner.nextLine();
                    while (!Validador.isTelefoneValido(telefoneBen)) {
                        System.out.print("Inválido! Digite com DDD e 11 números: ");
                        telefoneBen = scanner.nextLine();
                    }

                    System.out.print("Email (gmail, hotmail, yahoo): ");
                    String emailBen = scanner.nextLine();
                    while (!Validador.isEmailValido(emailBen) || bancoDeDados.emailBeneficiarioExiste(emailBen)) {
                        if (!Validador.isEmailValido(emailBen)) {
                            System.out.print("Inválido! Digite um e-mail aceito: ");
                        } else {
                            System.out.print("Este e-mail já está cadastrado! Digite outro: ");
                        }
                        emailBen = scanner.nextLine();
                    }

                    System.out.print("Endereço: ");
                    String enderecoBen = Validador.formatarNome(scanner.nextLine());

                    System.out.print("Tipo (ex: Família, ONG, Escola): ");
                    String tipoBen = Validador.formatarNome(scanner.nextLine());

                    System.out.print("Nível de Prioridade (1- Alta, 2-Média, 3-Baixa): ");
                    int prioridadeBen = scanner.nextInt();
                    scanner.nextLine(); 

                    Beneficiario novoBeneficiario = new Beneficiario(idBen, nomeBen, telefoneBen, emailBen, enderecoBen, tipoBen, prioridadeBen);
                    bancoDeDados.salvarBeneficiario(novoBeneficiario);
                    System.out.println("\nBeneficiário cadastrado com sucesso!");
                    break;

                case 3:
                    System.out.println("\n--- NOVO ITEM DE DOAÇÃO ---");
                    int idItem = bancoDeDados.gerarIdItem();
                    System.out.println("ID gerado automaticamente: " + idItem);
                    
                    System.out.print("Nome do Item (ex: Casaco, Cesta Básica): ");
                    String nomeItem = Validador.formatarNome(scanner.nextLine());

                    System.out.print("Categoria (ex: Alimento, Vestuário): ");
                    String categoriaItem = Validador.formatarNome(scanner.nextLine());

                    String detalhesRoupa = ""; 
                    if (categoriaItem.equalsIgnoreCase("Vestuario") || categoriaItem.equalsIgnoreCase("Vestuário")) {
                        System.out.println("\nOpções de Vestuário Detectadas:");
                        System.out.print("Gênero (Masculina, Feminina, Unissex): ");
                        String genero = Validador.formatarNome(scanner.nextLine());
                        System.out.print("Tamanho (P, M, G, GG, Infantil, etc): ");
                        String tamanho = scanner.nextLine().toUpperCase();
                        
                        detalhesRoupa = " [Gênero: " + genero + " | Tamanho: " + tamanho + "]";
                    }

                    System.out.print("Descrição Detalhada: ");
                    String descriItem = Validador.formatarNome(scanner.nextLine()) + detalhesRoupa;

                    System.out.print("Quantidade: ");
                    int qtdItem = scanner.nextInt();
                    scanner.nextLine(); 

                    int opEstado = 0;
                    String estadoItem = "";
                    while (opEstado < 1 || opEstado > 3) {
                        System.out.println("\nEstado de Conservação:");
                        System.out.println("1 - Novo");
                        System.out.println("2 - Seminovo");
                        System.out.println("3 - Usado");
                        System.out.print("Escolha a opção (1-3): ");
                        opEstado = scanner.nextInt();
                        scanner.nextLine();
                        
                        if (opEstado == 1) estadoItem = "Novo";
                        else if (opEstado == 2) estadoItem = "Seminovo";
                        else if (opEstado == 3) estadoItem = "Usado";
                        else System.out.println("Opção inválida! Tente novamente.");
                    }

                    ItemDoacao novoItem = new ItemDoacao(idItem, nomeItem, categoriaItem, descriItem, qtdItem, estadoItem, "Disponível");
                    bancoDeDados.salvarItem(novoItem);
                    System.out.println("\nItem cadastrado com sucesso!");
                    break;

                case 4:
                    System.out.println("\n--- ITENS DISPONÍVEIS ---");
                    List<ItemDoacao> itens = bancoDeDados.listarItens();
                    if(itens.isEmpty()) {
                        System.out.println("Nenhum item cadastrado no momento.");
                    } else {
                        itens.forEach(i -> System.out.println(
                            "ID: " + i.getId() + 
                            " | Item: " + i.getNomeItem() + 
                            " | Qtd: " + i.getQuantidade() + 
                            " | Categoria: " + i.getCategoria() + 
                            " | Descrição: " + i.getDescricao() + 
                            " | Condição: " + i.getEstadoConservacao()
                        ));
                    }
                    break;

                case 5:
                    System.out.println("\n--- LISTA DE DOADORES ---");
                    List<Doador> doadores = bancoDeDados.listarDoadores();
                    if (doadores.isEmpty()) System.out.println("Nenhum doador cadastrado.");
                    else doadores.forEach(d -> System.out.println("ID: " + d.getId() +" | Nome: " + d.getNome() + " | Tel: " + d.getTelefone() + " | Email: " + d.getEmail()));
                    break;
                    
                case 6:
                    System.out.println("\n--- LISTA DE BENEFICIÁRIOS ---");
                    List<Beneficiario> beneficiarios = bancoDeDados.listarBeneficiarios();
                    if (beneficiarios.isEmpty()) System.out.println("Nenhum beneficiário cadastrado.");
                    else beneficiarios.forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Tipo: " + b.getTipoBeneficiario() + " | Prioridade: " + b.getNivelPrioridade()));
                    break;

                case 7:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\n--- ATUALIZAR TELEFONE DOADOR ---");
                    System.out.print("ID do doador: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("NOVO telefone: ");
                    String novoTel = scanner.nextLine();
                    bancoDeDados.atualizarTelefoneDoador(idAtualizar, novoTel);
                    break;  

                case 8:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\n--- EXCLUIR DOADOR ---");
                    System.out.print("ID do doador: ");
                    int idDelDoador = scanner.nextInt();
                    scanner.nextLine();
                    bancoDeDados.deletarDoador(idDelDoador);
                    System.out.println("Doador excluído (se existia).");
                    break;  

                case 9:
                   if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\n--- EXCLUIR BENEFICIÁRIO ---");
                    System.out.print("ID do beneficiário: ");
                    int idDelBen = scanner.nextInt();
                    scanner.nextLine();
                    bancoDeDados.deletarBeneficiario(idDelBen);
                    System.out.println("Beneficiário excluído (se existia).");
                    break;  

                case 10:
                   if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\n--- EXCLUIR ITEM ---");
                    System.out.print("ID do item: ");
                    int idDelItem = scanner.nextInt();
                    scanner.nextLine();
                    bancoDeDados.deletarItem(idDelItem);
                    System.out.println("Item excluído (se existia).");
                    break;
                    
                case 11:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\nATENÇÃO! Você está prestes a apagar TODOS os Doadores da base de dados!");
                    System.out.print("Digite 'CONFIRMAR' em maiúsculo para prosseguir: ");
                    if (scanner.nextLine().equals("CONFIRMAR")) {
                        bancoDeDados.apagarTodosDoadores();
                        System.out.println("\nTodos os Doadores foram apagados.");
                    } else {
                        System.out.println("\nOperação cancelada. Os Doadores estão salvos.");
                    }
                    break;

                case 12:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\nATENÇÃO! Você está prestes a apagar TODOS os Beneficiários da base de dados!");
                    System.out.print("Digite 'CONFIRMAR' em maiúsculo para prosseguir: ");
                    if (scanner.nextLine().equals("CONFIRMAR")) {
                        bancoDeDados.apagarTodosBeneficiarios();
                        System.out.println("\nTodos os Beneficiários foram apagados.");
                    } else {
                        System.out.println("\nOperação cancelada. Os Beneficiários estão salvos.");
                    }
                    break;

                case 13:
                    if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\nATENÇÃO! Você está prestes a apagar TODOS os Itens de Doação da base de dados!");
                    System.out.print("Digite 'CONFIRMAR' em maiúsculo para prosseguir: ");
                    if (scanner.nextLine().equals("CONFIRMAR")) {
                        bancoDeDados.apagarTodosItens();
                        System.out.println("\nTodos os Itens foram apagados.");
                    } else {
                        System.out.println("\nOperação cancelada. Os Itens estão salvos.");
                    }
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