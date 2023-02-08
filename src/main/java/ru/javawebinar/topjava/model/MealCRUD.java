package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

public interface MealCRUD {
    void create(LocalDateTime dateTime, String description, int calories);

    Meal read(int id);

    void update(int id, LocalDateTime dateTime, String description, int calories);

    void delete(int id);

    CopyOnWriteArrayList<Meal> getMeals();
}
