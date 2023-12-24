package com.example.ControleDeGastosPB;

import com.example.ControleDeGastosPB.loader.DataLoader;
import com.example.ControleDeGastosPB.modelo.*;
import com.example.ControleDeGastosPB.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
@EnableFeignClients
public class ControleDeGastosPbApplication {

	@Autowired
	private DataLoader dataLoader;

	public static void main(String[] args) {
		SpringApplication.run(ControleDeGastosPbApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
			CategoriaService categoriaService,
			ClassificacaoService classificacaoService,
			ResultadoService resultadoService,
			EnderecoService enderecoService
	) {
		return (args) -> {
			dataLoader.carregarDadosIniciais();
			Scanner scanner = new Scanner(System.in);
			int opcao;
			do {
				System.out.println("------- MENU -------");
				System.out.println("1. Listar Resultados");
				System.out.println("2. Listar Resultados Por Domínio");
				System.out.println("3. Adicionar Resultado");
				System.out.println("4. Adicionar Categoria");
				System.out.println("5. Editar Resultado");
				System.out.println("6. Editar Categoria");
				System.out.println("7. Excluir Resultado");
				System.out.println("8. Excluir Categoria");
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
							// Listar Resultados Por Domínio
							listarResultadosPorDominioMenu(scanner, categoriaService, classificacaoService, enderecoService, resultadoService);
							break;
						case 3:
							// Adicionar Resultado
							adicionarResultado(scanner, categoriaService, classificacaoService, resultadoService, enderecoService);
							break;
						case 4:
							// Adicionar Categoria
							adicionarCategoria(scanner, categoriaService);
							break;
						case 5:
							// Editar Resultado
							editarResultado(scanner, resultadoService);
							break;
						case 6:
							// Editar Categoria
							editarCategoria(scanner, categoriaService);
							break;
						case 7:
							// Excluir Resultado
							excluirResultado(scanner, resultadoService);
							break;
						case 8:
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

	private void adicionarResultado(
			Scanner scanner,
			CategoriaService categoriaService,
			ClassificacaoService classificacaoService,
			ResultadoService resultadoService,
			EnderecoService enderecoService
	) {
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

				System.out.println("Escolha a classificação:");
				List<Classificacao> classificacoes = classificacaoService.listarClassificacoes();
				classificacoes.forEach(c -> System.out.println(c.getId() + ". " + c.getNome()));

				System.out.print("Escolha o número da classificação: ");
				if (scanner.hasNextLong()) {
					Long classificacaoId = scanner.nextLong();
					scanner.nextLine();
					Optional<Classificacao> classificacaoOptional = classificacaoService.buscarClassificacaoPorId(classificacaoId);

					if (classificacaoOptional.isPresent()) {
						Classificacao classificacao = classificacaoOptional.get();

						System.out.print("CEP: ");
						String cep = scanner.nextLine();

						if (cep.length() == 8 && cep.matches("\\d+")) {
							Endereco endereco = enderecoService.buscarCep(cep);

							if (endereco != null) {
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
									resultado.setEndereco(endereco);
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
								System.out.println("Endereço não encontrado para o CEP informado. Tente novamente.");
							}
						} else {
							System.out.println("O CEP deve ter exatamente 8 dígitos. Tente novamente.");
						}
					} else {
						System.out.println("Classificação não encontrada. Tente novamente.");
					}
				} else {
					System.out.println("Entrada inválida para o número da classificação. Tente novamente.");
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

	private void listarResultadosPorDominioMenu(Scanner scanner, CategoriaService categoriaService, ClassificacaoService classificacaoService, EnderecoService enderecoService, ResultadoService resultadoService) {
		int opcao;

		do {
			System.out.println("------- LISTAR RESULTADOS POR DOMINIO -------");
			System.out.println("Escolha o domínio:");
			System.out.println("1. Categoria");
			System.out.println("2. Classificacao");
			System.out.println("3. Endereco");
			System.out.println("0. Voltar ao Menu Principal");
			System.out.print("Escolha uma opção: ");

			if (scanner.hasNextInt()) {
				opcao = scanner.nextInt();
				switch (opcao) {
					case 1:
						listarResultadosPorCategoria(scanner, categoriaService, resultadoService);
						break;
					case 2:
						listarResultadosPorClassificacao(scanner, classificacaoService, resultadoService);
						break;
					case 3:
						listarResultadosPorEndereco(scanner, enderecoService, resultadoService);
						break;
					case 0:
						System.out.println("Voltando ao Menu Principal.");
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
	}

	private void listarResultadosPorCategoria(Scanner scanner, CategoriaService categoriaService, ResultadoService resultadoService) {
		System.out.println("Escolha a categoria:");

		List<Categoria> categorias = categoriaService.listarCategorias();
		for (Categoria categoria : categorias) {
			System.out.println(categoria.getId() + ". " + categoria.getNome());
		}

		System.out.print("Escolha o número da categoria: ");
		if (scanner.hasNextLong()) {
			Long categoriaId = scanner.nextLong();
			scanner.nextLine();

			Optional<Categoria> categoriaOptional = categoriaService.buscarCategoriaPorId(categoriaId);
			if (categoriaOptional.isPresent()) {
				Categoria categoriaSelecionada = categoriaOptional.get();
				List<Resultado> resultadosCategoria = resultadoService.getResultadosPorCategoria(categoriaSelecionada);

				if (!resultadosCategoria.isEmpty()) {
					System.out.println("Resultados para a categoria " + categoriaSelecionada.getNome() + ":");
					for (Resultado resultado : resultadosCategoria) {
						System.out.println(resultado);
					}
				} else {
					System.out.println("Não há resultados para a categoria selecionada.");
				}
			} else {
				System.out.println("Categoria não encontrada. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o número da categoria. Tente novamente.");
			scanner.next();
		}
	}

	private void listarResultadosPorClassificacao(Scanner scanner, ClassificacaoService classificacaoService, ResultadoService resultadoService) {
		System.out.println("Escolha a classificação:");

		List<Classificacao> classificacoes = classificacaoService.listarClassificacoes();
		for (Classificacao classificacao : classificacoes) {
			System.out.println(classificacao.getId() + ". " + classificacao.getNome());
		}

		System.out.print("Escolha o número da classificação: ");
		if (scanner.hasNextLong()) {
			Long classificacaoId = scanner.nextLong();
			scanner.nextLine();

			Optional<Classificacao> classificacaoOptional = classificacaoService.buscarClassificacaoPorId(classificacaoId);
			if (classificacaoOptional.isPresent()) {
				Classificacao classificacaoSelecionada = classificacaoOptional.get();
				List<Resultado> resultadosClassificacao = resultadoService.getResultadosPorClassificacao(classificacaoSelecionada);

				if (!resultadosClassificacao.isEmpty()) {
					System.out.println("Resultados para a classificação " + classificacaoSelecionada.getNome() + ":");
					for (Resultado resultado : resultadosClassificacao) {
						System.out.println(resultado);
					}
				} else {
					System.out.println("Não há resultados para a classificação selecionada.");
				}
			} else {
				System.out.println("Classificação não encontrada. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o número da classificação. Tente novamente.");
			scanner.next();
		}
	}

	private void listarResultadosPorEndereco(Scanner scanner, EnderecoService enderecoService, ResultadoService resultadoService) {
		System.out.println("Escolha o endereço:");

		List<Endereco> enderecos = enderecoService.listarEnderecos();

		for (Endereco endereco : enderecos) {
			System.out.println(endereco.getId() + ".1 " + endereco.getCep() + " - " + endereco.getUf());
		}

		System.out.print("Escolha o número do endereço: ");
		if (scanner.hasNextLong()) {
			Long enderecoId = scanner.nextLong();
			scanner.nextLine();

			Optional<Endereco> enderecoOptional = enderecoService.buscarEnderecoPorId(enderecoId);
			if (enderecoOptional.isPresent()) {
				Endereco enderecoSelecionado = enderecoOptional.get();
				List<Resultado> resultadosEndereco = resultadoService.getResultadosPorEndereco(enderecoSelecionado);

				if (!resultadosEndereco.isEmpty()) {
					System.out.println("Resultados para o endereço " + enderecoSelecionado.getCep() + " - " + enderecoSelecionado.getUf() + ":");
					for (Resultado resultado : resultadosEndereco) {
						System.out.println(resultado);
					}
				} else {
					System.out.println("Não há resultados para o endereço selecionado.");
				}
			} else {
				System.out.println("Endereço não encontrado. Tente novamente.");
			}
		} else {
			System.out.println("Entrada inválida para o número do endereço. Tente novamente.");
			scanner.next();
		}
	}

}