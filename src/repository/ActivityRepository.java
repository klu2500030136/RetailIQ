package repository;

import model.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityRepository {
    private final List<Activity> activities;
    private final Map<String, Activity> idIndex;
    private final Map<String, List<Activity>> customerIndex;

    public ActivityRepository(List<Activity> activities) {
        this.activities = activities;
        this.idIndex = new HashMap<>();
        this.customerIndex = new HashMap<>();
        for (Activity a : activities) {
            idIndex.put(a.getActivityId(), a);
            customerIndex.computeIfAbsent(a.getCustomerId(), k -> new ArrayList<>()).add(a);
        }
    }

    public Activity findById(String activityId) {
        return idIndex.get(activityId);
    }

    public List<Activity> findByCustomerId(String customerId) {
        return customerIndex.getOrDefault(customerId, new ArrayList<>());
    }

    public List<Activity> findAll() {
        return activities;
    }

    public int count() {
        return activities.size();
    }
}
