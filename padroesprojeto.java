import java.util.ArrayList;
import java.util.List;

/**
 * Demonstração de três padrões de projeto em um único arquivo:
 * 1. Singleton
 * 2. Factory Method
 * 3. Observer
 */
public class PadroesProjetoDemo {

    public static void main(String[] args) {
        System.out.println("=== Demonstração de Padrões de Projeto ===");
        
        // 1. Testando Singleton
        System.out.println("\n--- Singleton Pattern ---");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        db1.executeQuery("SELECT * FROM users");
        System.out.println("db1 e db2 são a mesma instância? " + (db1 == db2));
        
        // 2. Testando Factory Method
        System.out.println("\n--- Factory Method Pattern ---");
        NotificationFactory factory = new NotificationFactory();
        
        Notification email = factory.createNotification("email");
        email.send("Olá via email!");
        
        Notification sms = factory.createNotification("sms");
        sms.send("Olá via SMS!");
        
        // 3. Testando Observer
        System.out.println("\n--- Observer Pattern ---");
        Subject subject = new Subject();
        new ConcreteObserver(subject);
        
        subject.setState("Primeiro estado");
        subject.setState("Segundo estado");
    }

    // 1. Singleton Pattern Implementation
    static class DatabaseConnection {
        private static DatabaseConnection instance;
        
        private DatabaseConnection() {
            System.out.println("Conexão com o banco de dados estabelecida");
        }
        
        public static synchronized DatabaseConnection getInstance() {
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            return instance;
        }
        
        public void executeQuery(String query) {
            System.out.println("Executando query: " + query);
        }
    }

    // 2. Factory Method Pattern Implementation
    interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {
        @Override
        public void send(String message) {
            System.out.println("Enviando email: " + message);
        }
    }

    static class SMSNotification implements Notification {
        @Override
        public void send(String message) {
            System.out.println("Enviando SMS: " + message);
        }
    }

    static class NotificationFactory {
        public Notification createNotification(String type) {
            if (type == null || type.isEmpty()) {
                return null;
            }
            
            switch (type.toLowerCase()) {
                case "email":
                    return new EmailNotification();
                case "sms":
                    return new SMSNotification();
                default:
                    throw new IllegalArgumentException("Tipo de notificação desconhecido: " + type);
            }
        }
    }

    // 3. Observer Pattern Implementation
    static class Subject {
        private List<Observer> observers = new ArrayList<>();
        private String state;
        
        public String getState() {
            return state;
        }
        
        public void setState(String state) {
            this.state = state;
            notifyAllObservers();
        }
        
        public void attach(Observer observer) {
            observers.add(observer);
        }
        
        private void notifyAllObservers() {
            for (Observer observer : observers) {
                observer.update();
            }
        }
    }

    interface Observer {
        void update();
    }

    static class ConcreteObserver implements Observer {
        protected Subject subject;
        
        public ConcreteObserver(Subject subject) {
            this.subject = subject;
            this.subject.attach(this);
        }
        
        @Override
        public void update() {
            System.out.println("Estado alterado para: " + subject.getState());
        }
    }
}