package main;

import java.util.List;
import java.util.Scanner;

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
        System.out.println("2. Desenvolvelvedor (Acesso Total)");
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
                System.out.println("8. Excluir Doador");
                System.out.println("9. Excluir Beneficiário");
                System.out.println("10. Excluir Item de Doação");
            }
            
            System.out.println("\n0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1:
                    System.out.println("\n--- NOVO DOADOR ---");
                    System.out.print("ID: ");
                    int idDoador = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Nome: ");
                    String nomeDoador = scanner.nextLine();

                    System.out.print("Telefone: ");
                    String telefoneDoador = scanner.nextLine();

                    System.out.print("Email: ");
                    String emailDoador = scanner.nextLine();

                    System.out.print("Endereço: ");
                    String enderecoDoador = scanner.nextLine();

                    Doador novoDoador = new Doador(idDoador, nomeDoador, telefoneDoador, emailDoador, enderecoDoador);
                    bancoDeDados.salvarDoador(novoDoador);
                    break;

                case 2:
                    System.out.println("\n--- NOVO BENEFICIÁRIO ---");
                    System.out.print("ID: ");
                    int idBen = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nome: ");
                    String nomeBen = scanner.nextLine();

                    System.out.print("Telefone: ");
                    String telefoneBen = scanner.nextLine();

                    System.out.print("Email: ");
                    String emailBen = scanner.nextLine();

                    System.out.print("Endereço: ");
                    String enderecoBen = scanner.nextLine();

                    System.out.print("Tipo (ex: Família, ONG, Escola): ");
                    String tipoBen = scanner.nextLine();

                    System.out.print("Nível de Prioridade (1- Alta, 2-Média, 3-Baixa): ");
                    int PrioridadeBen = scanner.nextInt();
                    scanner.nextLine();

                    Beneficiario novoBeneficiario = new Beneficiario(idBen, nomeBen, telefoneBen, emailBen, enderecoBen, tipoBen, PrioridadeBen);
                    bancoDeDados.salvarBeneficiario(novoBeneficiario);
                    break;

                case 3:
                    System.out.println("\n--- NOVO ITEM DE DOAÇÃO ---");
                    System.out.print("ID: ");
                    int idItem = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nome do Item (ex: Casaco, Cesta Básica): ");
                    String nomeItem = scanner.nextLine();

                    System.out.print("Categoria (ex: Alimento, Vestuário): ");
                    String categoriaItem = scanner.nextLine();

                    System.out.print("Descrição Detalhada: ");
                    String descriItem = scanner.nextLine();

                    System.out.print("Quantidade: ");
                    int qtdItem = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Estado de Conservação (ex:Novo, Usado-Bom, etc): ");
                    String estadoItem = scanner.nextLine();

                    System.out.print("Status (ex: Disponível): ");
                    String statusItem = scanner.nextLine();

                    ItemDoacao novoItem = new ItemDoacao(idItem, nomeItem, categoriaItem, descriItem, qtdItem, estadoItem, statusItem);
                    bancoDeDados.salvarItem(novoItem);
                    break;

                case 4:
                    System.out.println("\n--- ITENS DISPONÍVEIS ---");
                    List<ItemDoacao> itens = bancoDeDados.listarItens();
                    if(itens.isEmpty()) System.out.println("nenhum item cadastrado.");
                    else itens.forEach(i -> System.out.println("ID: " + i.getId() +" | Item: "+ i.getNomeItem() + " | Qtd: " + i.getQuantidade()));
                    break;

                case 5:
                    System.out.println("\n--- LISTA DE DOADORES ---");
                    List<Doador> doadores = bancoDeDados.listarDoadores();
                    if (doadores.isEmpty()) System.out.println("Nenhum doador cadastrado.");
                    else doadores.forEach(d -> System.out.println("ID: " + d.getId() +" | Nome: " + d.getNome() + " | Tel: " + d.getTelefone()));
                    break;
                case 6:
                    System.out.println("\n--- LISTA DE BENEFICIÁRIOS ---");
                    List<Beneficiario> beneficiarios = bancoDeDados.listarBeneficiarios();
                    if (beneficiarios.isEmpty()) System.out.println("Nenhum beneficiário cadastrado.");
                    else beneficiarios.forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Prioridade: " + b.getNivelPrioridade()));
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
                    break;  

                case 9:
                   if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\n--- EXCLUIR BENEFICIÁRIO ---");
                    System.out.print("ID do beneficiário: ");
                    int idDelBen = scanner.nextInt();
                    scanner.nextLine();
                    bancoDeDados.deletarBeneficiario(idDelBen);
                    break;  

                case 10:
                   if (perfil != 2) { System.out.println("Acesso Negado! Apenas Desenvolvedores."); break; }
                    System.out.println("\n--- EXCLUIR ITEM ---");
                    System.out.print("ID do item: ");
                    int idDelItem = scanner.nextInt();
                    scanner.nextLine();
                    bancoDeDados.deletarItem(idDelItem);
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