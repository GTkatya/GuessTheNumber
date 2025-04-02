import java.util.Random;
import java.util.Scanner;

public class GameOfLife {
    private static final int SIZE = 20; // Размер сетки
    private boolean[][] grid;
    private Random random;
    private Scanner scanner;

    public GameOfLife() {
        grid = new boolean[SIZE][SIZE];
        random = new Random();
        scanner = new Scanner(System.in);
        initializeGrid();
    }

    // Инициализация случайной сетки
    private void initializeGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = random.nextBoolean();
            }
        }
    }

    // Подсчет живых соседей для клетки
    private int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (i == 0 && j == 0) continue;
                if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE) {
                    if (grid[newX][newY]) count++;
                }
            }
        }
        return count;
    }

    // Следующее поколение
    private void nextGeneration() {
        boolean[][] newGrid = new boolean[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int neighbors = countNeighbors(i, j);
                if (grid[i][j]) {
                    // Живая клетка выживает, если 2 или 3 живых соседа
                    newGrid[i][j] = (neighbors == 2 || neighbors == 3);
                } else {
                    // Мертвая клетка становится живой, если ровно 3 живых соседа
                    newGrid[i][j] = (neighbors == 3);
                }
            }
        }

        // Копируем новое поколение в текущую сетку
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(newGrid[i], 0, grid[i], 0, SIZE);
        }
    }

    // Отображение текущего состояния с пояснениями
    private void displayGrid() {
        System.out.println("\nТекущее состояние игры:");
        System.out.println("■ = живая клетка, □ = мертвая клетка");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j]) {
                    System.out.print("■ ");
                } else {
                    System.out.print("□ ");
                }
            }
            System.out.println();
        }
        System.out.println("\nПравила: каждая клетка взаимодействует с соседями. Живые клетки выживают с 2-3 соседями, иначе умирают. Мертвые оживают, если ровно 3 соседа.");
    }

    // Вводное объяснение
    private void showIntroduction() {
        System.out.println("Добро пожаловать в игру 'Жизнь' (Conway's Game of Life)!");
        System.out.println("Это симуляция, где клетки на сетке живут, умирают или рождаются по простым правилам.");
        System.out.println("Вот как это работает:");
        System.out.println("- Каждая клетка имеет 8 соседей (сверху, снизу, слева, справа и по диагоналям).");
        System.out.println("- Живая клетка выживает, если у неё 2 или 3 живых соседа.");
        System.out.println("- Если у живой клетки меньше 2 или больше 3 соседей, она умирает (от одиночества или перенаселения).");
        System.out.println("- Мертвая клетка становится живой, если ровно 3 её соседа живы.");
        System.out.println("\nНажмите Enter, чтобы начать, или введите 'помощь' для подсказок.");
    }

    // Подсказки для пользователя
    private void showHelp() {
        System.out.println("\nПодсказки:");
        System.out.println("- Нажмите Enter, чтобы увидеть следующее поколение клеток.");
        System.out.println("- Введите 'q' и нажмите Enter, чтобы выйти из игры.");
        System.out.println("- Введите 'правила', чтобы повторить правила игры.");
        System.out.println("- Введите 'очистить', чтобы начать заново с пустой сеткой.");
        System.out.println("Готовы? Нажмите Enter для продолжения или введите команду.");
    }

    // Очистка сетки
    private void clearGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = false;
            }
        }
    }

    // Главный игровой цикл с интерактивностью
    public void start() {
        showIntroduction();
        String input = scanner.nextLine();

        while (true) {
            if (input.equalsIgnoreCase("помощь")) {
                showHelp();
            } else if (input.equalsIgnoreCase("правила")) {
                showIntroduction();
            } else if (input.equalsIgnoreCase("очистить")) {
                clearGrid();
                System.out.println("Сетка очищена! Нажмите Enter, чтобы продолжить.");
            } else if (input.equalsIgnoreCase("q")) {
                System.out.println("Спасибо за игру! До встречи!");
                break;
            }

            displayGrid();
            System.out.println("Нажмите Enter для следующего поколения, 'q' для выхода, 'помощь' для подсказок, 'правила' для повторения правил, 'очистить' для сброса.");
            input = scanner.nextLine();

            if (!input.isEmpty() && !input.equalsIgnoreCase("q") && !input.equalsIgnoreCase("помощь") && !input.equalsIgnoreCase("правила") && !input.equalsIgnoreCase("очистить")) {
                System.out.println("Неизвестная команда. Введите 'помощь' для списка команд.");
                continue;
            }

            if (input.isEmpty()) {
                nextGeneration();
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        game.start();
    }
}