package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.component.Checker;
import uz.pdp.demo.entity.Role;
import uz.pdp.demo.entity.Turniket;
import uz.pdp.demo.entity.TurniketHistory;
import uz.pdp.demo.enums.RoleName;
import uz.pdp.demo.payload.TurniketHistoryDto;
import uz.pdp.demo.repository.TurniketHistoryRepository;
import uz.pdp.demo.repository.TurniketRepository;
import uz.pdp.demo.payload.response.ApiResponse;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TurniketHistoryService {

    @Autowired
    TurniketHistoryRepository turniketHistoryRepository;

    @Autowired
    TurniketRepository turniketRepository;

    @Autowired
    Checker checker;

    public ApiResponse add(TurniketHistoryDto turniketHistoryDto){
        Optional<Turniket> optionalTurniket = turniketRepository.findByNumber(turniketHistoryDto.getNumber());
        if (!optionalTurniket.isPresent()) {
            return new ApiResponse("Karochchi brat ruxsati yo'q joyga kirmoqchi bo'lyapti! Turniket topilmadi!", false);
        }

        Set<Role> roles = optionalTurniket.get().getOwner().getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role role1 : roles) {
            role = role1.getName().name();
            break;
        }

        boolean check = checker.check(role);
        if (!check)
            return new ApiResponse("Karochchi brat ruxsatiz yo'q joyga kirmoqchi bo'lyapsiz! Sizda huquq yo'q!", false);

        TurniketHistory turniketHistory = new TurniketHistory();
        turniketHistory.setTurniket(optionalTurniket.get());
        turniketHistory.setType(turniketHistoryDto.getType());
        turniketHistoryRepository.save(turniketHistory);
        return new ApiResponse("Yo'liz ochiq, bemalol kirishiz mumkin!", true);
    }

    public ApiResponse getAllByDate(String number, Timestamp startTime, Timestamp endTime){
        Optional<Turniket> optionalTurniket = turniketRepository.findByNumber(number);
        if (!optionalTurniket.isPresent())
            return new ApiResponse("Karochchi brat ruxsati yo'q joyga kirmoqchi bo'lyapti! Bunaqa turniket yoq.", false);

        Set<Role> roles = optionalTurniket.get().getOwner().getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role role1 : roles) {
            role = role1.getName().name();
            break;
        }

        boolean check = checker.check(role);
        if (!check)
            return new ApiResponse("Karochchi brat ruxsatiz yo'q joyga kirmoqchi bo'lyapsiz! Sizda huquq yo'q!", false);

        List<TurniketHistory> historyList = turniketHistoryRepository.findAllByTurniketAndTimeIsBetween(optionalTurniket.get(), startTime, endTime);
        return new ApiResponse("History list by date",true, historyList);
    }

    public ApiResponse getAll(String number){
        Optional<Turniket> optionalTurniket = turniketRepository.findByNumber(number);
        if (!optionalTurniket.isPresent())
            return new ApiResponse("Karochchi brat ruxsati yo'q joyga kirmoqchi bo'lyapti! Bunaqa turniket yoq.", false);

        Set<Role> roles = optionalTurniket.get().getOwner().getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role role1 : roles) {
            role = role1.getName().name();
            break;
        }

        boolean check = checker.check(role);
        if (!check)
            return new ApiResponse("Karochchi brat ruxsatiz yo'q joyga kirmoqchi bo'lyapsiz! Sizda huquq yo'q!", false);

        List<TurniketHistory> allByTurniket = turniketHistoryRepository.findAllByTurniket(optionalTurniket.get());
        return new ApiResponse("All history by turniket!", true, allByTurniket);
    }
}
