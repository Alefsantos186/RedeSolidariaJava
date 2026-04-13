🤝 Rede Solidária de Doação - Java POO
==================================
Aplicação orientada a objetos desenvolvida em Java para apoiar o cadastro e gerenciamento de itens doados para pessoas e instituições em situação de necessidade. Este projeto visa conectar doadores e beneficiários, ajudando no reaproveitamento de recursos, e atende aos ODS 1, 2, 10 e 12 da ONU.

Projeto desenvolvido para fins acadêmicos - Foco: Checkpoint 1.

✨ O que o sistema faz (Checkpoint 1)
==================================
Cadastrar Doadores

Cadastrar Beneficiários (ONGs, famílias, escolas, etc.)

Cadastrar Itens para Doação

Listar dados salvos (Doadores, Beneficiários e Itens Disponíveis)

Salvar e carregar dados automaticamente ao abrir e fechar o programa.

🚀 Como usar
==================================
Pré-requisito: Ter o Java (JDK) instalado na máquina.

Clone o projeto:
```bash
git clone https://github.com/Alefsantos186/RedeSolidariaJava.git
cd RedeSolidariaJava/src
```

Compile os arquivos Java:
```bash
javac main/Main.java model/*.java repository/*.java
```

Execute o sistema:
```bash
java main.Main
```
📝 Exemplo prático:
Ao executar, você navegará por um menu interativo via CLI (linha de comando):
==================================
         MENU PRINCIPAL
        
         --- CADASTRAR ---
        
        1. Cadastrar Doador
        2. Cadastrar Beneficiário
        3. Cadastrar Item para Doação
        
        --- CONSULTAR ---
        4. Listar Itens Disponíveis
        5. Listar Doadores
        6. Listar Beneficiários
        0. Sair

Persistência Local: Ao escolher a opção "0. Sair", o sistema varre as listas em memória e salva tudo em arquivos .dat locais de forma automática, garantindo que nenhum cadastro seja perdido.

📂 Estrutura do projeto
==================================
```text
src/
├── model/                 # Classes de domínio
│   ├── Usuario.java       # Classe base
│   ├── Doador.java        # Herda de Usuario
│   ├── Beneficiario.java  # Herda de Usuario
│   └── ItemDoacao.java    # Entidade de itens
├── repository/            # Armazenamento em arquivo
│   └── CadastroRepository.java # Gerencia serialização
└── main/                  # Execução do sistema
    └── Main.java          # Menu principal CLI
```
    
🛠️ Tecnologias
==================================
Java (Linguagem base)

Scanner (Entrada de dados via terminal)

ArrayList (Armazenamento em memória das entidades)

ObjectOutputStream / ObjectInputStream (Serialização de objetos para arquivos binários .dat)
