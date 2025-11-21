package view;

import controller.*;
import model.*;
import repository.*;
import service.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuView {
    private Scanner scanner;
    private MotoristaController motoristaController;
    private PassageiroController passageiroController;
    private CarroController carroController;
    private CorridaController corridaController;
    private PagamentoController pagamentoController;

    public MenuView() {
        this.scanner = new Scanner(System.in);
        
        // Usando repositórios JDBC
        IRepository<Motorista> motoristaRepo = new MotoristaRepositoryDB();
        IRepository<Passageiro> passageiroRepo = new PassageiroRepositoryDB();
        IRepository<Carro> carroRepo = new CarroRepositoryDB();
        IRepository<Corrida> corridaRepo = new CorridaRepositoryDB();
        IRepository<Pagamento> pagamentoRepo = new PagamentoRepositoryDB();
        
        // Criando serviços
        MotoristaService motoristaService = new MotoristaService(motoristaRepo);
        PassageiroService passageiroService = new PassageiroService(passageiroRepo);
        CarroService carroService = new CarroService(carroRepo, motoristaRepo);
        CorridaService corridaService = new CorridaService(corridaRepo, motoristaRepo, passageiroRepo);
        PagamentoService pagamentoService = new PagamentoService(pagamentoRepo, corridaRepo);
        
        // Criando controllers
        this.motoristaController = new MotoristaController(motoristaService);
        this.passageiroController = new PassageiroController(passageiroService);
        this.carroController = new CarroController(carroService);
        this.corridaController = new CorridaController(corridaService);
        this.pagamentoController = new PagamentoController(pagamentoService);
    }

    public void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n========== SISTEMA UBER ==========");
            System.out.println("1 - Motoristas");
            System.out.println("2 - Passageiros");
            System.out.println("3 - Carros");
            System.out.println("4 - Corridas");
            System.out.println("5 - Pagamentos");
            System.out.println("0 - Sair");
            System.out.println("===================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        menuMotoristas();
                        break;
                    case 2:
                        menuPassageiros();
                        break;
                    case 3:
                        menuCarros();
                        break;
                    case 4:
                        menuCorridas();
                        break;
                    case 5:
                        menuPagamentos();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido!");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void menuMotoristas() {
        int opcao;
        do {
            System.out.println("\n========== MOTORISTAS ==========");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("5 - Pesquisar");
            System.out.println("0 - Voltar");
            System.out.println("=================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        cadastrarMotorista();
                        break;
                    case 2:
                        listarMotoristas();
                        break;
                    case 3:
                        atualizarMotorista();
                        break;
                    case 4:
                        removerMotorista();
                        break;
                    case 5:
                        pesquisarMotorista();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido!");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarMotorista() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CNH: ");
        String cnh = scanner.nextLine();
        motoristaController.cadastrar(nome, cpf, telefone, email, cnh);
    }

    private void listarMotoristas() {
        List<Motorista> motoristas = motoristaController.listar();
        if (motoristas != null && !motoristas.isEmpty()) {
            System.out.println("\n--- Lista de Motoristas ---");
            motoristas.forEach(System.out::println);
        } else {
            System.out.println("Nenhum motorista cadastrado.");
        }
    }

    private void atualizarMotorista() {
        System.out.print("ID do motorista: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("CNH: ");
            String cnh = scanner.nextLine();
            motoristaController.atualizar(id, nome, cpf, telefone, email, cnh);
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void removerMotorista() {
        System.out.print("ID do motorista: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            motoristaController.remover(id);
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void pesquisarMotorista() {
        System.out.print("ID do motorista: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Motorista motorista = motoristaController.pesquisar(id);
            if (motorista != null) {
                System.out.println(motorista);
            } else {
                System.out.println("Motorista não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void menuPassageiros() {
        int opcao;
        do {
            System.out.println("\n========== PASSAGEIROS ==========");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("5 - Pesquisar");
            System.out.println("0 - Voltar");
            System.out.println("==================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        cadastrarPassageiro();
                        break;
                    case 2:
                        listarPassageiros();
                        break;
                    case 3:
                        atualizarPassageiro();
                        break;
                    case 4:
                        removerPassageiro();
                        break;
                    case 5:
                        pesquisarPassageiro();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido!");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarPassageiro() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        passageiroController.cadastrar(nome, cpf, telefone, email);
    }

    private void listarPassageiros() {
        List<Passageiro> passageiros = passageiroController.listar();
        if (passageiros != null && !passageiros.isEmpty()) {
            System.out.println("\n--- Lista de Passageiros ---");
            passageiros.forEach(System.out::println);
        } else {
            System.out.println("Nenhum passageiro cadastrado.");
        }
    }

    private void atualizarPassageiro() {
        System.out.print("ID do passageiro: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            passageiroController.atualizar(id, nome, cpf, telefone, email);
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void removerPassageiro() {
        System.out.print("ID do passageiro: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            passageiroController.remover(id);
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void pesquisarPassageiro() {
        System.out.print("ID do passageiro: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Passageiro passageiro = passageiroController.pesquisar(id);
            if (passageiro != null) {
                System.out.println(passageiro);
            } else {
                System.out.println("Passageiro não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void menuCarros() {
        int opcao;
        do {
            System.out.println("\n========== CARROS ==========");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("5 - Pesquisar");
            System.out.println("0 - Voltar");
            System.out.println("============================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        cadastrarCarro();
                        break;
                    case 2:
                        listarCarros();
                        break;
                    case 3:
                        atualizarCarro();
                        break;
                    case 4:
                        removerCarro();
                        break;
                    case 5:
                        pesquisarCarro();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido!");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarCarro() {
        try {
            System.out.print("ID do Motorista: ");
            int motoristaId = Integer.parseInt(scanner.nextLine());
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();
            System.out.print("Marca: ");
            String marca = scanner.nextLine();
            System.out.print("Ano: ");
            int ano = Integer.parseInt(scanner.nextLine());
            System.out.print("Cor: ");
            String cor = scanner.nextLine();
            carroController.cadastrar(motoristaId, placa, modelo, marca, ano, cor);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite números válidos para ID do motorista e ano!");
        }
    }

    private void listarCarros() {
        List<Carro> carros = carroController.listar();
        if (carros != null && !carros.isEmpty()) {
            System.out.println("\n--- Lista de Carros ---");
            carros.forEach(System.out::println);
        } else {
            System.out.println("Nenhum carro cadastrado.");
        }
    }

    private void atualizarCarro() {
        try {
            System.out.print("ID do carro: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Motorista: ");
            int motoristaId = Integer.parseInt(scanner.nextLine());
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();
            System.out.print("Marca: ");
            String marca = scanner.nextLine();
            System.out.print("Ano: ");
            int ano = Integer.parseInt(scanner.nextLine());
            System.out.print("Cor: ");
            String cor = scanner.nextLine();
            carroController.atualizar(id, motoristaId, placa, modelo, marca, ano, cor);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite números válidos!");
        }
    }

    private void removerCarro() {
        System.out.print("ID do carro: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            carroController.remover(id);
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void pesquisarCarro() {
        System.out.print("ID do carro: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Carro carro = carroController.pesquisar(id);
            if (carro != null) {
                System.out.println(carro);
            } else {
                System.out.println("Carro não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void menuCorridas() {
        int opcao;
        do {
            System.out.println("\n========== CORRIDAS ==========");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("5 - Pesquisar");
            System.out.println("0 - Voltar");
            System.out.println("=============================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        cadastrarCorrida();
                        break;
                    case 2:
                        listarCorridas();
                        break;
                    case 3:
                        atualizarCorrida();
                        break;
                    case 4:
                        removerCorrida();
                        break;
                    case 5:
                        pesquisarCorrida();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido!");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarCorrida() {
        try {
            System.out.print("ID do Motorista: ");
            int motoristaId = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Passageiro: ");
            int passageiroId = Integer.parseInt(scanner.nextLine());
            System.out.print("Origem: ");
            String origem = scanner.nextLine();
            System.out.print("Destino: ");
            String destino = scanner.nextLine();
            System.out.print("Distância (km): ");
            double distancia = Double.parseDouble(scanner.nextLine());
            System.out.print("Valor: ");
            double valor = Double.parseDouble(scanner.nextLine());
            System.out.print("Data e Hora (yyyy-MM-dd HH:mm): ");
            String dataHoraStr = scanner.nextLine();
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Status: ");
            String status = scanner.nextLine();
            corridaController.cadastrar(motoristaId, passageiroId, origem, destino, distancia, valor, dataHora, status);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite números válidos!");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Use: yyyy-MM-dd HH:mm");
        }
    }

    private void listarCorridas() {
        List<Corrida> corridas = corridaController.listar();
        if (corridas != null && !corridas.isEmpty()) {
            System.out.println("\n--- Lista de Corridas ---");
            corridas.forEach(System.out::println);
        } else {
            System.out.println("Nenhuma corrida cadastrada.");
        }
    }

    private void atualizarCorrida() {
        try {
            System.out.print("ID da corrida: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Motorista: ");
            int motoristaId = Integer.parseInt(scanner.nextLine());
            System.out.print("ID do Passageiro: ");
            int passageiroId = Integer.parseInt(scanner.nextLine());
            System.out.print("Origem: ");
            String origem = scanner.nextLine();
            System.out.print("Destino: ");
            String destino = scanner.nextLine();
            System.out.print("Distância (km): ");
            double distancia = Double.parseDouble(scanner.nextLine());
            System.out.print("Valor: ");
            double valor = Double.parseDouble(scanner.nextLine());
            System.out.print("Data e Hora (yyyy-MM-dd HH:mm): ");
            String dataHoraStr = scanner.nextLine();
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Status: ");
            String status = scanner.nextLine();
            corridaController.atualizar(id, motoristaId, passageiroId, origem, destino, distancia, valor, dataHora, status);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite números válidos!");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Use: yyyy-MM-dd HH:mm");
        }
    }

    private void removerCorrida() {
        System.out.print("ID da corrida: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            corridaController.remover(id);
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void pesquisarCorrida() {
        System.out.print("ID da corrida: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Corrida corrida = corridaController.pesquisar(id);
            if (corrida != null) {
                System.out.println(corrida);
            } else {
                System.out.println("Corrida não encontrada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void menuPagamentos() {
        int opcao;
        do {
            System.out.println("\n========== PAGAMENTOS ==========");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("5 - Pesquisar");
            System.out.println("0 - Voltar");
            System.out.println("================================");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        cadastrarPagamento();
                        break;
                    case 2:
                        listarPagamentos();
                        break;
                    case 3:
                        atualizarPagamento();
                        break;
                    case 4:
                        removerPagamento();
                        break;
                    case 5:
                        pesquisarPagamento();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido!");
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarPagamento() {
        try {
            System.out.print("ID da Corrida: ");
            int corridaId = Integer.parseInt(scanner.nextLine());
            System.out.print("Valor: ");
            double valor = Double.parseDouble(scanner.nextLine());
            System.out.print("Método de Pagamento: ");
            String metodoPagamento = scanner.nextLine();
            System.out.print("Data e Hora do Pagamento (yyyy-MM-dd HH:mm): ");
            String dataPagamentoStr = scanner.nextLine();
            LocalDateTime dataPagamento = LocalDateTime.parse(dataPagamentoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Status: ");
            String status = scanner.nextLine();
            pagamentoController.cadastrar(corridaId, valor, metodoPagamento, dataPagamento, status);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite números válidos!");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Use: yyyy-MM-dd HH:mm");
        }
    }

    private void listarPagamentos() {
        List<Pagamento> pagamentos = pagamentoController.listar();
        if (pagamentos != null && !pagamentos.isEmpty()) {
            System.out.println("\n--- Lista de Pagamentos ---");
            pagamentos.forEach(System.out::println);
        } else {
            System.out.println("Nenhum pagamento cadastrado.");
        }
    }

    private void atualizarPagamento() {
        try {
            System.out.print("ID do pagamento: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("ID da Corrida: ");
            int corridaId = Integer.parseInt(scanner.nextLine());
            System.out.print("Valor: ");
            double valor = Double.parseDouble(scanner.nextLine());
            System.out.print("Método de Pagamento: ");
            String metodoPagamento = scanner.nextLine();
            System.out.print("Data e Hora do Pagamento (yyyy-MM-dd HH:mm): ");
            String dataPagamentoStr = scanner.nextLine();
            LocalDateTime dataPagamento = LocalDateTime.parse(dataPagamentoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Status: ");
            String status = scanner.nextLine();
            pagamentoController.atualizar(id, corridaId, valor, metodoPagamento, dataPagamento, status);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite números válidos!");
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Use: yyyy-MM-dd HH:mm");
        }
    }

    private void removerPagamento() {
        System.out.print("ID do pagamento: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            pagamentoController.remover(id);
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }

    private void pesquisarPagamento() {
        System.out.print("ID do pagamento: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Pagamento pagamento = pagamentoController.pesquisar(id);
            if (pagamento != null) {
                System.out.println(pagamento);
            } else {
                System.out.println("Pagamento não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido!");
        }
    }
}


