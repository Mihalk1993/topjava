package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealCrud implements MealCrud {
    private static InMemoryMealCrud inMemoryMealCrud;
    private static AtomicInteger count = new AtomicInteger(0);
    private static final Set<Meal> meals = new HashSet<>();

    private InMemoryMealCrud() {
//        meals.addAll(Arrays.asList(
//                new Meal(count.incrementAndGet(), LocalDateTime.of(2023, Month.FEBRUARY, 6, 10, 0), "Завтрак", 500),
//                new Meal(count.incrementAndGet(), LocalDateTime.of(2023, Month.FEBRUARY, 6, 13, 0), "Обед", 1000),
//                new Meal(count.incrementAndGet(), LocalDateTime.of(2023, Month.FEBRUARY, 6, 20, 0), "Ужин", 500),
//                new Meal(count.incrementAndGet(), LocalDateTime.of(2023, Month.FEBRUARY, 7, 0, 0), "Еда на граничное значение", 100),
//                new Meal(count.incrementAndGet(), LocalDateTime.of(2023, Month.FEBRUARY, 7, 10, 0), "Завтрак", 1000),
//                new Meal(count.incrementAndGet(), LocalDateTime.of(2023, Month.FEBRUARY, 7, 13, 0), "Обед", 500),
//                new Meal(count.incrementAndGet(), LocalDateTime.of(2023, Month.FEBRUARY, 7, 20, 0), "Ужин", 410)));
    }

    public static InMemoryMealCrud getInstance() {
        if (inMemoryMealCrud == null) {
            inMemoryMealCrud = new InMemoryMealCrud();
        }
        return inMemoryMealCrud;
    }

    public Set<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal create(Meal meal) {
        Meal tempMeal = new Meal(count.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        meals.add(tempMeal);
        return tempMeal;
    }

    @Override
    public Meal read(int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) return meal;
        }
        return null;
    }

    @Override
    public Meal update(Meal meal) {
        meals.remove(meal);
        meals.add(meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                meals.remove(meal);
                break;
            }
        }
    }
}