package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal == null) {
            throw new IllegalArgumentException("Cannot save a null meal");
        }

        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            userMeals.put(meal.getId(), meal);
            return meal;
        } else {
            int mealId = meal.getId();
            Meal existingMeal = userMeals.get(mealId);
            if (existingMeal != null && existingMeal.getUserId() == userId) {
                meal.setUserId(userId);
                return userMeals.computeIfPresent(mealId, (id, oldMeal) -> meal);
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return meal != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        Meal meal = userMeals.get(id);
        return meal != null && meal.getUserId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getSortedMealsStreamByUserId(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllFiltered(LocalDate startDate, LocalDate endDate, int userId) {
        return getSortedMealsStreamByUserId(userId,
                meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate, true));
    }

    private List<Meal> getSortedMealsStreamByUserId(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}