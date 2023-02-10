package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Set;

public interface MealCrud {
    Meal create(Meal meal);

    Meal read(int id);

    Meal update(Meal meal);

    void delete(int id);

    Set<Meal> getAll();
}
