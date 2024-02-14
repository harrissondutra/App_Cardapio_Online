<u>**Cardápio Online**</u>



App para Android desenvolvido em Kotlin.



Este app foi desenvolvido para o projeto de Write Code do curso de desenvolvimento Android do Irede.



**1 - Tela Inicial (Cadastro)**

<Inserir imagem>



*Nesta tela o usuário irá inserir seu nome e o número da sua mesa para agilizar o processo de entrega do garçon.*



<Inserir imagem>

*Aqui ocorrem validações as seguintes:*

- *Não é permitido avançar a tela sem que seja inserido um nome do cliente*
- *O número da mesa é selecionável, de acordo com a quantidade de mesas inseridas no sistema.*



**2 - Menu Principal**

<inserir telas>

Aqui o usuário tem disponível o cardápio dividido por categorias, onde pode adicionar ou remover, a quantidade de cada item que deseja pedir, Todo cardápio está organizado de forma que seja retrátil e a tela fique o mais limpa possível. Utilização de Neasted RecyclerView para tal organização.

Aqui verificamos que ao adicionar ou remover um item, uma Snackbar informa o valou adicionado ou removido.

É exibido o valor total de todas as seleções.

Também é possível limpar todas as seleções em lote.

Após a escolha, o usuário é encaminhado para a tela de Checkout.



Validações:

<Inserir imagem>

- Não é possível avançar à tela de Checkout sem item selecionado, é exibido mensagem informativa através de Toasts
- Ao clicar em "Limpar tudo" é checado se existe algo já escolhido, caso negativo, é avisado que não é possível limpar as escolhas



**3 - Tela de Checkout**

<inserir imagens>

Esta é a última fase do aplicativo, nela é exibida uma descrição detalhada do pedido, a mesa que o cliente está, e um espaço para que o usuário possa incluir observações.

É exibido novamente o valor total do pedido para que o cliente tenha sempre à vista o valor do seu pedido

Após isso, o botão de "Realizar pedido" envia alertas de confirmações, em caso de confirmação o cliente tem um número de pedido gerado e o processo é encerrado, retornando para o Menu Principal.

Como esta tela é para simples conferência, não foi necessária a criação de validação. 





Por: Harrisson Dutra - Fevereiro / 2024

