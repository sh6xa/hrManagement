package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.component.Checker;
import uz.pdp.demo.entity.Role;
import uz.pdp.demo.entity.SalaryTaken;
import uz.pdp.demo.entity.User;
import uz.pdp.demo.enums.Month;
import uz.pdp.demo.enums.RoleName;
import uz.pdp.demo.payload.SalaryTakenDto;
import uz.pdp.demo.payload.response.ApiResponse;
import uz.pdp.demo.repository.SalaryTakenRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class SalaryTakenService {
    @Autowired
    SalaryTakenRepository salaryTakenRepository;

    @Autowired
    Checker checker;

    @Autowired
    UserService userService;

    public ApiResponse add(SalaryTakenDto salaryTakenDto){
        ApiResponse response = userService.getByEmail(salaryTakenDto.getEmail());
        if (!response.isStatus())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        SalaryTaken salaryTaken = new SalaryTaken();
        salaryTaken.setAmount(salaryTakenDto.getAmount());
        salaryTaken.setOwner(user);
        salaryTaken.setPeriod(salaryTakenDto.getPeriod());
        SalaryTaken save = salaryTakenRepository.save(salaryTaken);
        return new ApiResponse("Xodimga oylik kiritildi!", true);
    }

    public ApiResponse edit(SalaryTakenDto salaryTakenDto){

        ApiResponse response = userService.getByEmail(salaryTakenDto.getEmail());
        if (!response.isStatus())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        Optional<SalaryTaken> optional = salaryTakenRepository.findByOwnerAndPeriod(user, salaryTakenDto.getPeriod());
        if (!optional.isPresent())
            return new ApiResponse("Oylik mavjud emas!", false);

        if (optional.get().isPaid())
            return new ApiResponse("Bu oylik allaqachon to'langan, uni o'zgartira olmaysiz!", false);


        SalaryTaken salaryTaken = optional.get();
        salaryTaken.setAmount(salaryTakenDto.getAmount());
        salaryTaken.setOwner(user);
        salaryTaken.setPeriod(salaryTakenDto.getPeriod());
        SalaryTaken save = salaryTakenRepository.save(salaryTaken);
        return new ApiResponse("Xodimning oyligi o'zgartirildi!", true);
    }

    public ApiResponse delete(String email, String month){
        ApiResponse response = userService.getByEmail(email);
        if (!response.isStatus())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        Month period = null;

        for (Month value : Month.values()) {
            if (value.name().equals(month)){
                period = value;
                break;
            }
        }
        if (period == null)
            return new ApiResponse("Month xato!", false);

        Optional<SalaryTaken> optional = salaryTakenRepository.findByOwnerAndPeriod(user, period);
        if (!optional.isPresent())
            return new ApiResponse("Oylik topilmadi!", false);

        if (optional.get().isPaid())
            return new ApiResponse("Bu oylik allaqachon to'langan, uni o'zgartira olmaysiz!", false);

        salaryTakenRepository.delete(optional.get());
        return new ApiResponse("Oylik o'chirildi!", true);
    }

    //OYLIKNI BERILGAN HOLATGA O'TKAZISH
    public ApiResponse customize(String email, String month, boolean stat){
        ApiResponse response = userService.getByEmail(email);
        if (!response.isStatus())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        Month period = null;

        for (Month value : Month.values()) {
            if (value.name().equals(month)){
                period = value;
                break;
            }
        }
        if (period == null)
            return new ApiResponse("Month xato!", false);

        Optional<SalaryTaken> optional = salaryTakenRepository.findByOwnerAndPeriod(user, period);
        if (!optional.isPresent())
            return new ApiResponse("Oylik topilmadi!", false);

        SalaryTaken salaryTaken = optional.get();
        if (salaryTaken.isPaid())
            return new ApiResponse("Bu oylik allaqachon to'langan, uni o'zgartira olmaysiz!", false);

        salaryTaken.setPaid(stat);
        return new ApiResponse("Oylik to'langanlik holati o'zgartirildi!", true);
    }

    public ApiResponse getByUser(String email){
        ApiResponse response = userService.getByEmail(email);
        if (!response.isStatus())
            return response;
        User user = (User) response.getObject();

        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role rolex : roles) {
            role = rolex.getName().name();
        }

        boolean check = checker.check(role);
        if(!check)
            return new ApiResponse("Sizda huquq yo'q!", false);

        return new ApiResponse("List by Owner", true, salaryTakenRepository.findAllByOwner(user));
    }

    public ApiResponse getByMonth(String month){
        boolean check = checker.check();
        if (!check)
            return new ApiResponse("Sizda huquq yo'q", false);

        Month period = null;

        for (Month value : Month.values()) {
            if (value.name().equals(month)){
                period = value;
                break;
            }
        }
        if (period == null)
            return new ApiResponse("Month xato!", false);

        return new ApiResponse("List by period", true, salaryTakenRepository.findAllByPeriod(period));
    }
}
