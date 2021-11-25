package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.component.Checker;
import uz.pdp.demo.entity.Turniket;
import uz.pdp.demo.entity.User;
import uz.pdp.demo.payload.response.ApiResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeadershipService {

    @Autowired
    TurniketHistoryService turniketHistoryService;

    @Autowired
    TurniketService turniketService;

    @Autowired
    Checker checker;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    //Har bir xodim haqidagi ma’lumotlarni ko’rmochi
    // bo’lsa ushbu xodimning belgilangan oraliq vaqt
    // bo’yicha ishga kelib-ketishi va bajargan tasklari haqida ma’lumot chiqishi kerak.
    public ApiResponse getHistoryAndTasks(Timestamp startTime, Timestamp endTime, String email){
        ApiResponse apiResponse = userService.getByEmail(email);
        if (!apiResponse.isStatus())
            return apiResponse;

        User user = (User) apiResponse.getObject();

        ApiResponse responseTurniket = turniketService.getByUser(user);
        if (!responseTurniket.isStatus())
            return responseTurniket;

        Turniket turniket = (Turniket) responseTurniket.getObject();
        ApiResponse historyList = turniketHistoryService.getAllByDate(turniket.getNumber(), startTime, endTime);

        ApiResponse taskList = taskService.getAllByUserAndDate(startTime, endTime, user);

        List<ApiResponse> responseList = new ArrayList<>();
        responseList.add(historyList);
        responseList.add(taskList);

        return new ApiResponse("So'ralgan narsalar", true, responseList);
    }

}
