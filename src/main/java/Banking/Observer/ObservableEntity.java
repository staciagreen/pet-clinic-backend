package Banking.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий наблюдаемую сущность.
 * Позволяет добавлять, удалять и уведомлять наблюдателей.
 */
public class ObservableEntity {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Конструктор по умолчанию.
     */
    public ObservableEntity() {
        // Конструктор по умолчанию
    }

    /**
     * Добавляет наблюдателя.
     *
     * @param observer наблюдатель
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Удаляет наблюдателя.
     *
     * @param observer наблюдатель
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Уведомляет всех наблюдателей об изменении состояния.
     *
     * @param message сообщение об изменении состояния
     */
    protected void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}