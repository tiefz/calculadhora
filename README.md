[![GitHub release](https://img.shields.io/github/release/tiefz/calculadhora.svg)](https://GitHub.com/tiefz/calculadhora/releases/)

[![GitHub issues](https://img.shields.io/github/issues/tiefz/calculadhora.svg)](https://GitHub.com/tiefz/calculadhora/issues/) [![GitHub issues-closed](https://img.shields.io/github/issues-closed/tiefz/calculadhora.svg)](https://GitHub.com/tiefz/calculadhora/issues?q=is%3Aissue+is%3Aclosed)

[![Github releases (by asset)](https://img.shields.io/github/downloads/tiefz/calculadhora/latest/calculadhora)](https://GitHub.com/tiefz/calculadhora/releases/) [![Github all releases](https://img.shields.io/github/downloads/tiefz/calculadhora/total.svg)](https://GitHub.com/tiefz/calculadhora/releases/)

![](https://github.com/tiefz/calculadhora/workflows/Android%20CI/badge.svg)

# Calculadhora
### Projeto App Android CalculadHora
Use  a CalculadHora para saber exatamente a hora em que você precisa sair do trabalho naqueles dias em que você entrou alguns minutos mais tarde, fez algumas pausas a mais no trabalho pra um cafezinho extra e tirou menos tempo de almoço que o de costume.
Calcule o total de horas trabalhadas no dia e de acordo com a carga horária de trabalho ajustada, saiba o banco de horas se ficou positivo ou negativo.
Ajuiste a CalculadHora para a sua carga horária de trabalho, como por exemplo 5:40, 7:00, 8:00 ou 8:48 (Não considerando sua hora de almoço).

Em breve novas funcionalidades como: Calculo de hora trabalhada no mês, banco de horas, horas positivas e negativas do mês e calculo de valor de hora extra.

![](https://github.com/tiefz/calculadhora/blob/master/samples/calculadhora-logo128.png)

### Lançamentos:

#### Versão 1.2.3
- Remodelagem da home para facilitar o fluxo e diminuir a quantidade de informação desnecessária
- Unificação do fluxo de calculo de saída e hora extra em um único fragment no final para reduzir as opções na home

#### Versão 1.2.2
- Implementação de tela de Ajuda / FAQ com Recyclerview e Cardview

#### Versão 1.2.1
- Implementação de tela de boas vindas para primeiro acesso com Activity e Fragment
- Ajuste no tamanho da fonte dos botões de calculo e alteração do botão de alarme de imagem para texto
- Ajuste no botão Next da tela de pausas extras para ficar sempre visível 

#### Versão 1.2.0
- Implementação de fragment para calcular a hora total trabalhada
- Implementação de lógica através de intent e bundle para mapear a opção do usuário e ativar o ultimo fragment que vai calcular a saída ou a hora total trabalhada
- Alteração na lógica que trata as pausas extras, calculando e enviando apenas uma unica string ao invés de um array com várias strings  

#### Versão 1.1.5
- Atualização da SDK para 28(Android 9). Implementação de um resumo na tela de calcular a saída e ajuste para sempre mostrar o horário que está salvo em sharedpreferences em todas os fragments de seleção de horário. 

#### Versão 1.1.4
- Implementação do botão para criar alames após o calculo do horário de saída. 

#### Versão 1.1.3
- Implementação de um validador de tempo para corrigir problemas relacionados à incluir um horario menor que o anterior ou não selecionar o horário anterior. 

#### Versão 1.1.2
- Correção da cor errada no Textview do valor da carga horária na view de configurações

#### Versão 1.1.1
- Correção do bug com classe LocalTime não compatível
- Melhorias nas cores da Home e config
- Melhoria no fluxo de troca de fragments
- Configuração inicial de pausa extra com dialog

#### Versão 1.1
- Adicionado Menu lateral
- Adicionado tela de configuração com persistência
- Adicionado Layout Tabview com uma Activity e 5 fragments
- Adicionado Tela inicial com ultimo horário calculado

#### Versão 1.0
- Calculo de horário de saída. 
- Uma Activity simples

