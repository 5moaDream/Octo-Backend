package com.example.activityservice.vo;

public class Experience {
    private static Experience experience = new Experience();

    public static final int SLEEP_EXPERIENCE = 1;
    public static final int RUNNING_EXPERIENCE = 1;
    public static final int DIARY_EXPERIENCE = 1;
    public static final int GUEST_BOOK_EXPERIENCE = 1;

    public static Experience getInstance(){
        return experience;
    }
}
