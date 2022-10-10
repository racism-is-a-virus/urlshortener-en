## O que é esse aplicativo? 

Urlshortener é um pequeno aplicativo Android que permite encurtar urls e exibir um histórico dos links recentemente encurtados para seus sites favoritos.

Este aplicativo é composto por apenas uma tela, que tem:

- Uma entrada de texto em que o usuário pode digitar a URL do site para encurtar;
- Um botão que acionará a ação de envio deste link para o serviço;
- Uma lista com os links/aliases recentemente encurtados.

## Tecnologias/Arquitetura:
- [x] Plataforma - Android: https://developer.android.com/
- [x] Linguagem - Kotlin: https://kotlinlang.org/
- [x] Single activity - Apresentação por Ian Lake -> 'Single activity - Why, when, and how (Android Dev Summit '18)': https://www.youtube.com/watch?v=2k8x8V77CrU&ab_channel=AndroidDevelopers
- [x] Multi-Module - Apresentação por Yigit Boyar, Florina Muntenescu -> 'Build a modular Android app architecture (Google I/O'19)':
 https://www.youtube.com/watch?v=PZBg5DIzNww&ab_channel=AndroidDevelopers
- [x] Navigation component: https://developer.android.com/guide/navigation/navigation-getting-started
- [x] Koin -DI framework: https://insert-koin.io/
- [x] MVVM
- [x] Clean architecture: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- [x] Clean code
- [x] Coroutines: https://kotlinlang.org/docs/coroutines-guide.html
- [x] Retrofit

## Testes:
- [x] Testes unitários - MockK: https://github.com/mockk/mockk
- [x] Testes de componentes com - Robolectric: http://robolectric.org/
- [x] Testes instrumentados com - Espresso: https://developer.android.com/training/testing/espresso

## Organização do código

O aplicativo está dividido em 03 módulos:

- [app](https://github.com/fredelinhares/url-shortener/tree/master/app): responsável por executar os testes de UI (em um eventual deploy, esse módulo - e todo o código contido nele - não será deployado para produção).

- [core](https://github.com/fredelinhares/url-shortener/tree/master/core): 
  - Infraestrutura de comunicação com a API (ApiClientBuilder, RequestManager), exceptions de comunicação com o servidor, etc...
  - Gerenciamento de estado dos fragmentos: BaseViewModel, ViewState.

- [urlshortener](https://github.com/fredelinhares/url-shortener/tree/master/urlshortener): módulo responsável pela implementação da feature. Contém a tela (UrlShortenerListFragment) com a qual o usuário interage.

## Observações:

- É de suma importância salientar que não levou-se em consideração o desenvolvimento de um layout com aspecto visual avançado.
- A divisão dos módulos não levou-se em consideração o fator escalabilidade. Num projeto real, poderiamos criar por exemplo, um app multi repositório,
onde poderiamos ter, por exemplo:
  - Um repositório de dados, seguindo o modelo de "single source of truth (SSOT)": https://en.wikipedia.org/wiki/Single_source_of_truth
  - Um repositório separado para cada feature do projeto - exemplo, o módulo urlshortener. Tais módulos de features seriam, no final, incorporados num aplicativo pai via maven/gradle, aplicando uma estratégia de gerenciamento de dependências - BOM or Bill Of Materials (exemplo em, https://firebase.blog/posts/2020/11/dependency-management-ios-android).
  - Um repositório para gerenciamento da infraestrutura de sdks como firebase.
  - Um repositório para gerenciamento de skds para analytics...
