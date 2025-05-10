import java.util.*;

//Observer interface
public interface Observer<T> {
    void update(Notification<T> notification);
}

//Notification base class
public abstract class Notification<T> {
    private T content;

    public Notification(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}

//NotificationFactory Interface
public interface NotificationFactory<T> {
    Notification<T> createNotification(T content); // fixed typo
}

//Notification classes
class EmailNotification extends Notification<String> {
    public EmailNotification(String content) {
        super(content);
    }
}

class SMSNotification extends Notification<String> {
    public SMSNotification(String content) {
        super(content);
    }
}

//Observer classes
class EmailObserver implements Observer<String> {
    @Override
    public void update(Notification<String> notification) {
        System.out.println("EmailObserver received: " + notification.getContent());
    }
}

class SMSObserver implements Observer<String> {
    @Override
    public void update(Notification<String> notification) {
        System.out.println("SMSObserver received: " + notification.getContent());
    }
}

//Notification factories
class EmailNotificationFactory implements NotificationFactory<String> {
    @Override
    public Notification<String> createNotification(String content) { // fixed typo
        return new EmailNotification(content);
    }
}

class SMSNotificationFactory implements NotificationFactory<String> {
    @Override
    public Notification<String> createNotification(String content) { // fixed typo
        return new SMSNotification(content);
    }
}

//Notification builder
class NotificationBuilder<T> {
    private T content;

    public NotificationBuilder<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public Notification<T> build(NotificationFactory<T> factory) {
        return factory.createNotification(content);
    }
}

public class NotificationSystem {
    public static void main(String[] args) {
        //create factories
        EmailNotificationFactory emailFactory = new EmailNotificationFactory();
        SMSNotificationFactory smsFactory = new SMSNotificationFactory();

        //create notifications
        Notification<String> welcomeEmail = new NotificationBuilder<String>()
            .setContent("Welcome to MarketBridge!")
            .build(emailFactory);
        
        Notification<String> orderSMS = new NotificationBuilder<String>()
            .setContent("Your order has shipped!")
            .build(smsFactory);

        //create observers
        EmailObserver emailObserver = new EmailObserver();
        SMSObserver smsObserver = new SMSObserver();

        //send notifications
        emailObserver.update(welcomeEmail);
        smsObserver.update(orderSMS);

        //type erasure ex.
        // At runtime, Java erases the generic type information,
        // so you cannot use instanceof with parameterized types.
        if (welcomeEmail instanceof Notification<?>) { //this will always be true
            System.out.println("The generic type is erased");
        }
    }
}
