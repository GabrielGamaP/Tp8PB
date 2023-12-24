package com.example.ControleDeGastosPB;

import com.example.ControleDeGastosPB.loader.DataLoader;
import com.example.ControleDeGastosPB.modelo.Categoria;
import com.example.ControleDeGastosPB.modelo.Classificacao;
import com.example.ControleDeGastosPB.modelo.Resultado;
import com.example.ControleDeGastosPB.service.CategoriaService;
import com.example.ControleDeGastosPB.service.ClassificacaoService;
import com.example.ControleDeGastosPB.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class ControleDeGastosPbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleDeGastosPbApplication.class, args);
	}

	@Autowired
	private DataLoader dataLoader;

	@Bean
	public CommandLineRunner demo(
			CategoriaService categoriaService,
			ClassificacaoService classificacaoService,
			ResultadoService resultadoService
	) {
		return (args) -> {
			Scanner scanner = new Scanner(System.in);
			int opcao;

			dataLoader.carregarDadosIniciais();


			do {
				System.out.println("------- MENU -------");
				System.out.println("1. Listar Resultados");
				System.out.println("2. Adicionar Resultado");
				System.out.println("3. Adicionar Categoria");
				System.out.println("4. Editar Resultado");
				System.out.println("5. Editar Categoria");
				System.out.println("6. Excluir Resultado");
				System.out.println("7. Excluir Categoria");
				System.out.println("0. Sair");
				System.out.print("Escolha uma opção: ");

				if (scanner.hasNextInt()) {
					opcao = scanner.nextInt();
					switch (opcao) {
						case 1:
							// Listar Resultados
							listarResultados(resultadoService);
							break;
						case 2:
							// Adicionar Resultado
							adicionarResultado(scanner, categoriaService, classificacaoService, resultadoService);
							break;
						case 3:
							// Adicionar Categoria
							adicionarCategoria(scanner, categoriaService);
							break;
						case 4:
							// Editar Resultado
							editarResultado(scanner, resultadoService);
							break;
						case 5:
							// Editar Categoria
							editarCategoria(scanner, categoriaService);
							break;
						case 6:
							// Excluir Resultado
							excluirResultado(scanner, resultadoService);
							break;
						case 7:
							// Excluir Categoria
							excluirCategoria(scanner, categoriaService, resultadoService);
							break;
						case 0:
							// Sair
							System.out.println("Saindo do programa.");
							break;
						default:
							System.out.println("Opção inválida. Tente novamente.");
					}
				} else {
					System.out.println("Entrada inválida. Tente novamente.");
					scanner.next();
					opcao = -1;
				}

			} while (opcao != 0);
		};
	}


	private void listarResultados(ResultadoService resultadoService) {
		List<Resultado> resultados = resultadoService.listarResultados();
		resultados.forEach(System.out::println);
	}

	private void adicionarResultado(Scanner scanner, CategoriaService categoriaService, ClassificacaoService classificacaoService, ResultadoService resultadoService) {
		System.out.println("Escolha a categoria:");
		List<Categoria> categorias = categoriaService.listarCategorias();
		categorias.forEach(c -> System.out.println(c.getId() + ". " + c.getNome()));

		System.out.print("Escolha o número da categoria: ");
		if (scanner.hasNextLong()) {
			Long categoriaId = scanner.nextLong();
			scanner.nextLine();
			Optional<Categoria> categoriaOptional = categoriaService.buscarCategoriaPorId(categoriaId);

			if (categoriaOptional.isPresent()) {
				Categoria categoria = categoriaOptional.get();

				System.out.println("Escolha a classificacao:");
				List<Classificacao> classificacoes = classificacaoService.listarClassificacoes();
				classificacoes.forEach(c -> System.out.println(c.getId() + ". " + c.getNome()));

				System.out.print("Escolha o número da classificacao: ");
				if (scanner.hasNextLong()) {
					Long classificacaoId = scanner.nextLong();
					scanner.nextLine();
					Optional<Classificacao> classificacaoOptional = classificacaoService.buscarClassificacaoPorId(classificacaoId);

					if (classificacaoOptional.isPresent()) {
						Classificacao classificacao = classificacaoOptional.get();
						System.out.print("Nome: ");
						String nome = scanner.nextLine();
						System.out.print("Anotação: ");
						String anotacao = scanner.nextLine();
						System.out.print("Data (formato dd/MM/yyyy): ");
						String dataStr = scanner.nextLine();
						System.out.print("Valor: ");
						BigDecimal valor = scanner.nextBigDecimal();

						try {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							LocalDate data = LocalDate.parse(dataStr, formatter);
							Resultado resultado = new Resultado();
							resultado.setCategoria(categoria);
							resultado.setClassificacao(classificacao);
							resultado.setNome(nome);
							resultado.setAnotacao(anotacao);
							resultado.setData(data);
							resultado.setValor(valor);

							resultadoService.adicionarResultado(resultado);
							System.out.println("Resultado adicionado com sucesso.");
						} catch (DateTimeParseException e) {
							System.out.println("Formato de data inválido. Tente novamente.");
						}
					} else {
						System.out.println("Classificacao não encontrada. Tente novamente.");
					}
				} else {
					System.out.println("Entrada inválida para o número da classificacao. Tente novamente.");
					scanner.next();
				}
			} else {
				System.out.println("Categoria não encontrada. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o número da categoria. Tente novamente.");
			scanner.next();
		}
	}


	private void adicionarCategoria(Scanner scanner, CategoriaService categoriaService) {
		System.out.print("Nome da Categoria: ");
		String nome = scanner.next();

		if (!nome.isEmpty()) {
			Categoria categoria = new Categoria();
			categoria.setNome(nome);

			categoriaService.adicionarCategoria(categoria);
			System.out.println("Categoria adicionada com sucesso.");
		} else {
			System.out.println("Nome da categoria não pode ser vazio. Tente novamente.");
		}
	}

	private void editarResultado(Scanner scanner, ResultadoService resultadoService) {
		System.out.print("ID do Resultado para editar: ");
		if (scanner.hasNextLong()) {
			Long resultadoId = scanner.nextLong();
			Optional<Resultado> resultadoOptional = resultadoService.buscarResultadoPorId(resultadoId);

			if (resultadoOptional.isPresent()) {
				Resultado resultado = resultadoOptional.get();

				System.out.print("Novo Nome: ");
				String novoNome = scanner.next();
				System.out.print("Nova Anotação: ");
				String novaAnotacao = scanner.next();
				System.out.print("Nova Data (formato dd/MM/yyyy): ");
				String novaDataStr = scanner.next();

				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate novaData = LocalDate.parse(novaDataStr, formatter);

					System.out.print("Novo Valor: ");
					BigDecimal novoValor = scanner.nextBigDecimal();

					resultado.setNome(novoNome);
					resultado.setAnotacao(novaAnotacao);
					resultado.setData(novaData);
					resultado.setValor(novoValor);

					resultadoService.editarResultado(resultado);
					System.out.println("Resultado editado com sucesso.");
				} catch (DateTimeParseException e) {
					System.out.println("Formato de data inválido. Tente novamente.");
				} catch (InputMismatchException ex) {
					System.out.println("Formato de valor inválido. Tente novamente.");
					scanner.next();
				}
			} else {
				System.out.println("Resultado não encontrado. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o ID do resultado. Tente novamente.");
			scanner.next();
		}
	}



	private void editarCategoria(Scanner scanner, CategoriaService categoriaService) {
		System.out.print("ID da Categoria para editar: ");
		if (scanner.hasNextLong()) {
			Long categoriaId = scanner.nextLong();
			Optional<Categoria> categoriaOptional = categoriaService.buscarCategoriaPorId(categoriaId);

			if (categoriaOptional.isPresent()) {
				Categoria categoria = categoriaOptional.get();

				System.out.print("Novo Nome da Categoria: ");
				String novoNome = scanner.next();
				categoria.setNome(novoNome);

				categoriaService.editarCategoria(categoria);
				System.out.println("Categoria editada com sucesso.");
			} else {
				System.out.println("Categoria não encontrada. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o ID da categoria. Tente novamente.");
			scanner.next();
		}
	}

	private void excluirResultado(Scanner scanner, ResultadoService resultadoService) {
		System.out.print("ID do Resultado para excluir: ");
		if (scanner.hasNextLong()) {
			Long resultadoId = scanner.nextLong();
			Optional<Resultado> resultadoOptional = resultadoService.buscarResultadoPorId(resultadoId);

			if (resultadoOptional.isPresent()) {
				resultadoService.excluirResultado(resultadoId);
				System.out.println("Resultado excluído com sucesso.");
			} else {
				System.out.println("Resultado não encontrado. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o ID do resultado. Tente novamente.");
			scanner.next();
		}
	}

	private void excluirCategoria(Scanner scanner, CategoriaService categoriaService, ResultadoService resultadoService) {
		System.out.print("ID da Categoria para excluir: ");
		if (scanner.hasNextLong()) {
			Long categoriaId = scanner.nextLong();
			Optional<Categoria> categoriaOptional = categoriaService.buscarCategoriaPorId(categoriaId);

			if (categoriaOptional.isPresent()) {
				Categoria categoria = categoriaOptional.get();

				if (resultadoService.existeResultadoComCategoria(categoria)) {
					System.out.println("Não é possível excluir a categoria porque está sendo usada por algum resultado.");
				} else {
					categoriaService.excluirCategoria(categoriaId);
					System.out.println("Categoria excluída com sucesso.");
				}
			} else {
				System.out.println("Categoria não encontrada. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o ID da categoria. Tente novamente.");
			scanner.next();
		}
	}

}