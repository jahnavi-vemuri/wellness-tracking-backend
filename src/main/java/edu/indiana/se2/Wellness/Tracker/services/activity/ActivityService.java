package edu.indiana.se2.Wellness.Tracker.services.activity;

import edu.indiana.se2.Wellness.Tracker.dto.ActivityDTO;

import java.util.List;

public interface ActivityService {

    ActivityDTO postActivity(ActivityDTO dto);
    List<ActivityDTO> getActivities();
}
