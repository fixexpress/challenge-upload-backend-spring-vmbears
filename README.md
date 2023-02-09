# challenge-upload-backend-spring-vmbears
challenge files upload backend

see /doc folder


Swagger API:
http://localhost:8080/swagger-ui.html

Swagger Documentation:
http://localhost:8080/api-docs

Doc file:
http://localhost:8080/api-docs.yaml


Tools:
generate classes to JABX
	1) generate schema
	https://www.freeformatter.com/xsd-generator.html#before-output
	2) generate class
		xjc agentes.xsd
	*using JABX installer with jdk8
validade xml
	https://onlinexmltools.com/validate-xml


Teste Service:
curl -X POST http://localhost:8080/upload -F "file=@exemplo_01.xml"

curl -X POST http://localhost:8080/upload -F "file=@/tmp/sample.txt"
The -F option is used to send a file as the body of the request. The syntax "file=@sample.txt" indicates the file parameter name "file" and the path to the file "sample.txt".

curl -X POST http://localhost:8080/upload -F "file=@/dev/teste/vmbears/docs/exemplo_01.xml"
curl -X POST http://localhost:8080/upload -F "file=@c:\\dev\\teste\\vmbears\\docs\\exemplo_01.xml"

Database H2:
http://localhost:8080/h2-ui



__________________________

Desafio:

	Criar um sistema Web composto de um Front-end SPA (Single Page Application) Angular e um Back-end Java Spring Boot para envio de arquivos XML e posterior processamento deles.
Requisitos
Criar uma interface Web para upload de um ou mais arquivos com extensão .xml.
Para o desenvolvimento da interface, deve ser utilizado o tema Indigo do Angular Material.
Durante o envio do(s) arquivo(s) mostrar um loader para informar ao usuário que estão sendo processados, e ao final esse loader deve desaparecer (utilizar componentes do Angular Material).
Os arquivos contêm uma lista de agentes com código e data em formato ISO, e uma lista com quatro regiões (SE, S, NE, N) com sete valores numéricos de geração, compra e preço médio.
Todos os arquivos seguem o mesmo formato, como nos exemplos em anexo:
exemplo_01.xml
exemplo_02.xml
exemplo_03.xml
Não é necessário validar os dados dos arquivos, considere que eles estarão sempre corretos e no formato especificado acima.
Os arquivos devem ser lidos e enviados um a um, sequencialmente.
Os arquivos podem conter quantidades grandes de dados, por exemplo, 1.000 agentes (agentes/agente[0..999])
Os dados de preço médio (/agentes/agente[]/submercado[]/precoMedio) são confidenciais, portanto devem ser removidos ou substituídos por valores em branco antes de serem enviados.
Ao receber cada arquivo, o back-end deve apenas imprimir na saída padrão (System.out) os códigos de agentes (/agentes/agente[]/codigo) recebidos.
Utlizar 
Salvar os itens no Banco de dados.
Recuperar um dado consolidado por região.


Instruções
Criar o sistema utilizando as seguintes tecnologias base:

Front-end:
Angular 12+

	Angular Material 12+
	Typescript 4+
	RxJS 6+
	NodeJS 14+

Back-end:

	Spring Boot 2 
	Maven 3
	JPA
	Hibernate

Banco de Dados:

Disponibilizá-lo em um repositório Git público (exemplo: GitHub, Bitbucket).


