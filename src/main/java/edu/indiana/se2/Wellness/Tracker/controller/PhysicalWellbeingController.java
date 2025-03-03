package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.dto.PhysicalWellbeingDTO;
import edu.indiana.se2.Wellness.Tracker.services.physicalWellbeing.PhysicalWellbeingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/physical-wellbeing")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PhysicalWellbeingController {
    private final PhysicalWellbeingService activityService;

    @PostMapping("/physicalactivity")
    public ResponseEntity<?> postActivity(@RequestBody PhysicalWellbeingDTO dto){
        PhysicalWellbeingDTO createActivity = activityService.postActivity(dto);
        if(createActivity != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createActivity);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    @GetMapping("/physicalactivities")
    public ResponseEntity<?> getActivites(){
        try{
            return ResponseEntity.ok(activityService.getActivities());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }
}
