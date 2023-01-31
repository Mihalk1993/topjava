package ru.javawebinar.topjava.model;

public class Excess {
    private int calories;
    private final Flag flag = new Flag();

    public Excess(int calories) {
        this.calories = calories;
    }

    public void addCalories(int calories) {
        this.calories += calories;
    }

    public void checkFlag(int maxCalories) {
        if (calories > maxCalories) {
            flag.setFlag(true);
        }
    }

    public Boolean getFlag() {
        return flag.getFlag();
    }
}
