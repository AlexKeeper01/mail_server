package mailserver;

import java.util.List;
import java.util.Scanner;
import java.util.Map;


public class Main {

    private static final UserStorage userStorage = new UserStorage();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в почтовый сервер!");
        printHelp();

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "add":
                    addUser();
                    break;
                case "list":
                    listUsers();
                    break;
                case "send":
                    sendMessage();
                    break;
                case "inbox":
                    showInbox();
                    break;
                case "spam":
                    showSpam();
                    break;
                case "outbox":
                    showOutbox();
                    break;
                case "setfilter":
                    setFilter();
                    break;
                case "help":
                    printHelp();
                    break;
                case "exit":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неизвестная команда. Введите 'help' для списка команд.");
            }
        }
    }

    private static void printHelp() {
        System.out.println("Доступные команды:");
        System.out.println("add - добавить нового пользователя");
        System.out.println("list - список всех пользователей");
        System.out.println("send - отправить сообщение");
        System.out.println("inbox - просмотреть входящие сообщения");
        System.out.println("spam - просмотреть спам");
        System.out.println("outbox - просмотреть исходящие сообщения");
        System.out.println("setfilter - установить спам-фильтр");
        System.out.println("help - показать эту справку");
        System.out.println("exit - выйти из программы");
    }

    private static void addUser() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Имя пользователя не может быть пустым.");
            return;
        }

        if (userStorage.userExists(username)) {
            System.out.println("Пользователь с таким именем уже существует.");
            return;
        }

        userStorage.addUser(username);
        System.out.println("Пользователь " + username + " успешно добавлен.");
    }

    private static void listUsers() {
        Map<String, User> users = userStorage.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Нет зарегистрированных пользователей.");
            return;
        }

        System.out.println("Список пользователей:");
        for (String username : users.keySet()) {
            System.out.println("- " + username);
        }
        System.out.println(String.format("Всего зарегестрированно пользователей: %d", users.size()));
    }

    private static void sendMessage() {
        System.out.print("Введите имя отправителя: ");
        String senderName = scanner.nextLine().trim();

        if (!userStorage.userExists(senderName)) {
            System.out.println("Отправитель не найден.");
            return;
        }

        System.out.print("Введите имя получателя: ");
        String receiverName = scanner.nextLine().trim();

        if (!userStorage.userExists(receiverName)) {
            System.out.println("Получатель не найден.");
            return;
        }

        System.out.print("Введите заголовок сообщения: ");
        String caption = scanner.nextLine().trim();

        System.out.print("Введите текст сообщения: ");
        String text = scanner.nextLine().trim();

        User sender = userStorage.getUser(senderName);
        User receiver = userStorage.getUser(receiverName);

        sender.sendMessage(caption, text, receiver);
        System.out.println("Сообщение успешно отправлено.");
    }

    private static void showInbox() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine().trim();

        if (!userStorage.userExists(username)) {
            System.out.println("Пользователь не найден.");
            return;
        }

        User user = userStorage.getUser(username);
        List<Message> inbox = user.getInbox();

        if (inbox.isEmpty()) {
            System.out.println("Входящих сообщений нет.");
            return;
        }

        System.out.println(String.format("Входящие сообщения для %s:", username));
        for (Message message : inbox) {
            System.out.println("=======================================");
            System.out.println("От: " + message.getSender().getUsername());
            System.out.println("Тема: " + message.getCaption());
            System.out.println("Текст: " + message.getText());
            System.out.println("=======================================");
        }
    }

    private static void showSpam() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine().trim();

        if (!userStorage.userExists(username)) {
            System.out.println("Пользователь не найден.");
            return;
        }

        User user = userStorage.getUser(username);
        List<Message> spam = user.getSpam();

        if (spam.isEmpty()) {
            System.out.println("Спама нет.");
            return;
        }

        System.out.println(String.format("Входящие сообщения для %s:", username));
        for (Message message : spam) {
            System.out.println("=======================================");
            System.out.println("От: " + message.getSender().getUsername());
            System.out.println("Тема: " + message.getCaption());
            System.out.println("Текст: " + message.getText());
            System.out.println("=======================================");
        }
    }

    private static void showOutbox() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine().trim();

        if (!userStorage.userExists(username)) {
            System.out.println("Пользователь не найден.");
            return;
        }

        User user = userStorage.getUser(username);
        List<Message> outbox = user.getOutbox();

        if (outbox.isEmpty()) {
            System.out.println("Исходящих сообщений нет.");
            return;
        }

        System.out.println(String.format("Входящие сообщения для %s:", username));
        for (Message message : outbox) {
            System.out.println("=======================================");
            System.out.println("Кому: " + message.getReceiver().getUsername());
            System.out.println("Тема: " + message.getCaption());
            System.out.println("Текст: " + message.getText());
            System.out.println("=======================================");
        }
    }

    private static void setFilter() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine().trim();

        if (!userStorage.userExists(username)) {
            System.out.println("Пользователь не найден.");
            return;
        }

        User user = userStorage.getUser(username);

        System.out.println("Введите тип фильтра (simple/keywords/repetition/sender/composite) или 'done' для завершения:");

        while (true) {
            System.out.print("фильтр> ");
            String filterType = scanner.nextLine().trim().toLowerCase();

            if (filterType.equals("done")) {
                break;
            }

            switch (filterType) {
                case "simple":
                    user.setSpamFilter(new SimpleSpamFilter());
                    System.out.println("Добавлен простой фильтр (по слову 'spam').");
                    break;
                case "keywords":
                    System.out.print("Введите ключевые слова через пробел: ");
                    String keywords = scanner.nextLine().trim();
                    user.setSpamFilter(new KeywordsSpamFilter(keywords));
                    System.out.println("Добавлен фильтр по ключевым словам.");
                    break;
                case "repetition":
                    System.out.print("Введите максимальное количество повторений: ");
                    try {
                        int maxRepetitions = Integer.parseInt(scanner.nextLine().trim());
                        if (maxRepetitions <= 0) {
                            System.out.println("Число должно быть положительным.");
                            continue;
                        }
                        user.setSpamFilter(new RepetitionSpamFilter(maxRepetitions));
                        System.out.println("Добавлен фильтр по повторениям слов.");
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректное число.");
                    }
                    break;
                case "sender":
                    System.out.print("Введите имена отправителей через пробел: ");
                    String sendersNames = scanner.nextLine().trim();
                    user.setSpamFilter(new SenderSpamFilter(sendersNames));
                    System.out.println("Добавлен фильтр по отправителям.");
                    break;
                case "composite":
                    System.out.println("Введите тип фильтра который вы хотите добавить в композитный" +
                            " фильтр (simple/keywords/repetition/sender) или 'done' для завершения:");
                    CompositeSpamFilter CompositeFilter = new CompositeSpamFilter();
                    user.setSpamFilter(CompositeFilter);

                    while (true) {
                        System.out.print("фильтр> ");
                        String filterTypeComposite = scanner.nextLine().trim().toLowerCase();

                        if (filterTypeComposite.equals("done")) {
                            break;
                        }

                        switch (filterTypeComposite) {
                            case "simple":
                                CompositeFilter.addFilter(new SimpleSpamFilter());
                                System.out.println("В композитный фильтр добавлен простой фильтр (по слову 'spam').");
                                break;
                            case "keywords":
                                System.out.print("Введите ключевые слова через пробел: ");
                                String keywordsComposite = scanner.nextLine().trim();
                                CompositeFilter.addFilter(new KeywordsSpamFilter(keywordsComposite));
                                System.out.println("В композитный фильтр добавлен фильтр по ключевым словам.");
                                break;
                            case "repetition":
                                System.out.print("Введите максимальное количество повторений: ");
                                try {
                                    int maxRepetitionsComposite = Integer.parseInt(scanner.nextLine().trim());
                                    if (maxRepetitionsComposite <= 0) {
                                        System.out.println("Число должно быть положительным.");
                                        continue;
                                    }
                                    CompositeFilter.addFilter(new RepetitionSpamFilter(maxRepetitionsComposite));
                                    System.out.println("В композитный фильтр добавлен фильтр по повторениям слов.");
                                } catch (NumberFormatException e) {
                                    System.out.println("Некорректное число.");
                                }
                                break;
                            case "sender":
                                System.out.print("Введите имена отправителей через пробел: ");
                                String sendersNamesComposite = scanner.nextLine().trim();
                                CompositeFilter.addFilter(new SenderSpamFilter(sendersNamesComposite));
                                System.out.println("В композитный фильтр добавлен фильтр по отправителям.");
                                break;
                            default:
                                System.out.println("Неизвестный тип фильтра. Доступные: simple, keywords, repetition, sender");
                        }
                    }
                    System.out.println("Композитный фильтр для пользователя успешно установлен.");
                    break;
                default:
                    System.out.println("Неизвестный тип фильтра. Доступные: simple, keywords, repetition, sender, composite");
            }
        }
        System.out.println(String.format("Фильтр для пользователя %s успешно установлены.", username));
    }
}
