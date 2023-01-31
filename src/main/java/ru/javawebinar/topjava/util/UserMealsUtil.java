package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Excess;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 0);

        System.out.println("    filteredByCycles:");
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, startTime, endTime, 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("    filteredByStreams:");
        System.out.println(filteredByStreams(meals, startTime, endTime, 2000));

        System.out.println("    filteredByCycles2:");
        List<UserMealWithExcess> mealsTo2 = filteredByCycles2(meals, startTime, endTime, 2000);
        mealsTo2.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> filtratedList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

//        Filling Map with Date (key) and sums of Calories per Date (value)
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();

            if (!caloriesPerDayMap.containsKey(mealDate)) {
                caloriesPerDayMap.put(LocalDate.from(meal.getDateTime()), meal.getCalories());
            } else {
                caloriesPerDayMap.put(LocalDate.from(meal.getDateTime()), meal.getCalories() + caloriesPerDayMap.get(mealDate));
            }
        }

//        Filling List by filtrated meals
        for (UserMeal meal : meals) {
            LocalDateTime mealDateTime = meal.getDateTime();

            if (isBetweenHalfOpen(LocalTime.from(mealDateTime), startTime, endTime)) {
                UserMealWithExcess mealWithExcess = new UserMealWithExcess(
                        mealDateTime,
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDayMap.get(LocalDate.from(mealDateTime)) > caloriesPerDay);
                filtratedList.add(mealWithExcess);
            }
        }
        return filtratedList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> filtratedList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        Map<LocalDateTime, Integer> tempMap = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDateTime, Collectors.summingInt(UserMeal::getCalories)));

        tempMap.forEach((x, y) -> {
            if (!caloriesPerDayMap.containsKey(x.toLocalDate())) {
                caloriesPerDayMap.put(x.toLocalDate(), y);
            } else {
                caloriesPerDayMap.put(x.toLocalDate(), y + caloriesPerDayMap.get(x.toLocalDate()));
            }
        });

        filtratedList = meals.stream().map(userMeal -> {
                    LocalDateTime mealDateTime = userMeal.getDateTime();

                    UserMealWithExcess mealWithExcess = null;
                    if (isBetweenHalfOpen(LocalTime.from(mealDateTime), startTime, endTime)) {
                        mealWithExcess = new UserMealWithExcess(
                                mealDateTime,
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                caloriesPerDayMap.get(LocalDate.from(mealDateTime)) > caloriesPerDay);
                    }
                    return mealWithExcess;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return filtratedList;
    }

    public static List<UserMealWithExcess> filteredByCycles2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> mealWithExcess = new ArrayList<>();
        HashMap<LocalDate, Excess> caloriesPerDate = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            if (!caloriesPerDate.containsKey(date)) {
                caloriesPerDate.put(date, new Excess(meal.getCalories()));
                caloriesPerDate.get(date).checkFlag(caloriesPerDay);
            } else {
                caloriesPerDate.get(date).addCalories(meal.getCalories());
                caloriesPerDate.get(date).checkFlag(caloriesPerDay);
            }

            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {

                LocalDateTime dateTime = meal.getDateTime();
                String description = meal.getDescription();
                int calories = meal.getCalories();

                mealWithExcess.add(new UserMealWithExcess(dateTime, description, calories, caloriesPerDate.get(dateTime.toLocalDate()).getFlag()));
            }
        }
        return mealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
// TODO implement by streams in 1 pass through the original list of meals.stream()
        return null;
    }
}
