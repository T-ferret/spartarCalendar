package spring.lecture.web.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DaySchedule {
    private List<LocalDateTime[]> freeTimes;
    private Map<Integer, List<LocalDateTime[]>> userSchedules;
}
