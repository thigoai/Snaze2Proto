src/
├── controller/
│   ├── GameController.java      // Controla a lógica do jogo
│   ├── BotController.java       // Controla a lógica do bot
│   └── InputHandler.java        // Lida com eventos de entrada (teclado, mouse)
│
├── model/
│   ├── GameModel.java           // Regras e estado principal do jogo
│   ├── Snake.java               // Representa a cobra do jogador
│   ├── BotSnake.java            // Representa a cobra do bot
│   ├── Food.java                // Representa a comida no jogo
│   └── Grid.java                // Representa o tabuleiro
│
├── view/
│   ├── GameView.java            // Renderiza o jogo
│   ├── MainMenu.java            // Tela inicial/menu principal
│   └── GameOverScreen.java      // Tela de game over
│
├── dao/
│   ├── ScoreDAO.java            // Manipula dados de pontuação (salvar/carregar)
│   ├── DatabaseConnection.java  // Gerencia conexão com banco de dados
│   └── FileHandler.java         // Alternativa para persistir dados em arquivos
│
├── util/
│   ├── Constants.java           // Armazena constantes (tamanhos, cores, etc.)
│   ├── Logger.java              // Classe para logs e debug
│   └── ThreadManager.java       // Gerencia threads no jogo
│
└── main/
    └── Main.java                // Classe principal que inicializa o jogo
